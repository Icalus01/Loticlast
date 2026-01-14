package net.noon.loticlast.core.damage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.core.TagsL;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Mod.EventBusSubscriber(modid = Loticlast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DamageStacker extends SavedData {
    private static final String DATA_NAME = TagsL.of("DamageStacker");

    private final Map<UUID, LinkedList<Damage>> conveyor = new HashMap<>();
    private final Map<UUID, Map<String, Damage>> bufferZone = new HashMap<>();

    // ============ 静态访问方法 ============

    public static DamageStacker get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(DamageStacker::load, DamageStacker::new, DATA_NAME);
    }

    private static DamageStacker getStorage(LivingEntity entity) {
        return get((ServerLevel) entity.level());
    }

    // ============ 静态快速伤害方法 ============

    public static void flushHurt(LivingEntity target, float damage, ResourceLocation damageType) {
        if (isClient(target)) return;
        DamageStacker storage = getStorage(target);
        storage.addToHead(target.getUUID(), Damage.of(damage, damageType, 9, 9, "undefined"));
    }

    public static void hurt(LivingEntity target, float damage, ResourceLocation damageType) {
        if (isClient(target)) return;
        getStorage(target).addToTail(target.getUUID(), Damage.of(damage, damageType, 0, 0, "undefined"));
    }

    public static void pendingHurt(LivingEntity target, String identifier, float damage, ResourceLocation damageType) {
        if (isClient(target)) return;
        getStorage(target).addToBuffer(target.getUUID(), identifier, Damage.of(damage, damageType, 0, 0, identifier));
    }

    public static boolean triggerHurt(LivingEntity target, String identifier) {
        if (isClient(target)) return false;
        return getStorage(target).transferToConveyor(target.getUUID(), identifier);
    }

    // ============ 传送带操作 ============

    public void addToHead(UUID entityId, Damage damage) {
        conveyor.computeIfAbsent(entityId, k -> new LinkedList<>()).addFirst(damage);
        setDirty();
    }

    public void addToTail(UUID entityId, Damage damage) {
        conveyor.computeIfAbsent(entityId, k -> new LinkedList<>()).addLast(damage);
        setDirty();
    }

    @Nullable
    public Damage removeFromConveyor(UUID entityId) {
        LinkedList<Damage> list = conveyor.get(entityId);
        if (list == null || list.isEmpty()) return null;
        Damage removed = list.removeFirst();
        if (list.isEmpty()) conveyor.remove(entityId);
        setDirty();
        return removed;
    }

    @Nullable
    public Damage peekFromConveyor(UUID entityId) {
        LinkedList<Damage> list = conveyor.get(entityId);
        return list != null && !list.isEmpty() ? list.getFirst() : null;
    }

    // ============ 缓冲区操作 ============

    public void addToBuffer(UUID entityId, String identifier, Damage damage) {
        bufferZone.computeIfAbsent(entityId, k -> new HashMap<>()).put(identifier, damage);
        setDirty();
    }
    @Nullable
    public Damage removeFromBuffer(UUID entityId, String identifier) {
        Map<String, Damage> map = bufferZone.get(entityId);
        if (map == null) return null;
        Damage removed = map.remove(identifier);
        if (map.isEmpty()) bufferZone.remove(entityId);
        if (removed != null) setDirty();
        return removed;
    }
    public boolean transferToConveyor(UUID entityId, String identifier) {
        Damage damage = removeFromBuffer(entityId, identifier);
        if (damage != null) {
            addToTail(entityId, damage);
            return true;
        }
        return false;
    }
    public boolean hasInBuffer(UUID entityId, String identifier) {
        Map<String, Damage> map = bufferZone.get(entityId);
        return map != null && map.containsKey(identifier);
    }

    // ============ 伤害处理逻辑 ============

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        event.getServer().getAllLevels().forEach(level -> {
            if (level instanceof ServerLevel serverLevel) {
                DamageStacker stacker = get(serverLevel);
                stacker.cleanup(serverLevel);

                serverLevel.getAllEntities().forEach(entity -> {
                    if (entity instanceof LivingEntity living && living.isAlive()) {
                        processDamage(stacker, living);
                    }
                });
            }
        });
    }

    private static void processDamage(DamageStacker storage, LivingEntity living) {
        Damage damage = storage.peekFromConveyor(living.getUUID());
        if (damage == null || !living.isAlive() || damage.damage <= 0) {
            if (damage != null && damage.damage <= 0) {
                storage.removeFromConveyor(living.getUUID());
            }
            return;
        }

        if (living.invulnerableTime - damage.attackSpeed <= 10) {
            storage.removeFromConveyor(living.getUUID());
            living.invulnerableTime -= damage.attackSpeed;
            living.hurt(sourceOf(living, damage.damageType), damage.damage);
            living.invulnerableTime -= damage.recoverySpeed;
        }
    }

    // ============ 清理方法 ============

    private void cleanup(ServerLevel level) {
        boolean changed = false;
        changed |= conveyor.keySet().removeIf(id -> level.getEntity(id) == null);
        changed |= bufferZone.keySet().removeIf(id -> level.getEntity(id) == null);
        if (changed) setDirty();
    }

    // ============ 序列化 ============

    private static DamageStacker load(CompoundTag tag) {
        DamageStacker data = new DamageStacker();

        if (tag.contains("Conveyor", 10)) {
            CompoundTag conveyorTag = tag.getCompound("Conveyor");
            for (String uuidStr : conveyorTag.getAllKeys()) {
                UUID uuid = UUID.fromString(uuidStr);
                ListTag damageListTag = conveyorTag.getList(uuidStr, 10);
                LinkedList<Damage> damageList = new LinkedList<>();
                for (int i = 0; i < damageListTag.size(); i++) {
                    damageList.add(Damage.fromNBT(damageListTag.getCompound(i)));
                }
                if (!damageList.isEmpty()) {
                    data.conveyor.put(uuid, damageList);
                }
            }
        }

        if (tag.contains("BufferZone", 10)) {
            CompoundTag bufferZoneTag = tag.getCompound("BufferZone");
            for (String uuidStr : bufferZoneTag.getAllKeys()) {
                UUID uuid = UUID.fromString(uuidStr);
                ListTag damageListTag = bufferZoneTag.getList(uuidStr, 10);
                Map<String, Damage> innerMap = new HashMap<>();
                for (int i = 0; i < damageListTag.size(); i++) {
                    CompoundTag damageTag = damageListTag.getCompound(i);
                    String identifier = damageTag.getString("identifier");
                    innerMap.put(identifier, Damage.fromNBT(damageTag));
                }
                if (!innerMap.isEmpty()) {
                    data.bufferZone.put(uuid, innerMap);
                }
            }
        }

        return data;
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag tag) {
        CompoundTag conveyorTag = new CompoundTag();
        conveyor.forEach((uuid, damageList) -> {
            ListTag listTag = new ListTag();
            for (Damage damage : damageList) {
                listTag.add(damage.toNBT());
            }
            conveyorTag.put(uuid.toString(), listTag);
        });
        tag.put("Conveyor", conveyorTag);

        CompoundTag bufferZoneTag = new CompoundTag();
        bufferZone.forEach((uuid, innerMap) -> {
            ListTag listTag = new ListTag();
            innerMap.forEach((id, damage) -> listTag.add(damage.toNBT()));
            bufferZoneTag.put(uuid.toString(), listTag);
        });
        tag.put("BufferZone", bufferZoneTag);

        return tag;
    }

    // ============ 工具方法 ============

    private static boolean isClient(LivingEntity entity) {
        return entity.level().isClientSide;
    }

    private static DamageSource sourceOf(LivingEntity entity, ResourceLocation damageType) {
        return new DamageSource(entity.level().registryAccess()
                .registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE)
                .getHolderOrThrow(net.minecraft.resources.ResourceKey.create(
                        net.minecraft.core.registries.Registries.DAMAGE_TYPE, damageType
                )));
    }

    // ============ Damage内部类 ============

    private static class Damage {
        public float damage;
        public int attackSpeed;
        public int recoverySpeed;
        public ResourceLocation damageType;
        public String identifier;

        public Damage(float damage, int attackSpeed, int recoverySpeed,
                      ResourceLocation damageType, String identifier) {
            this.damage = damage;
            this.attackSpeed = attackSpeed;
            this.recoverySpeed = recoverySpeed;
            this.damageType = damageType;
            this.identifier = identifier != null ? identifier : "undefined";
        }

        public static Damage of(float damage, ResourceLocation damageType) {
            return new Damage(damage, 0, 0, damageType, "undefined");
        }

        public static Damage of(float damage, ResourceLocation damageType,
                                int attackSpeed, int recoverySpeed, String identifier) {
            return new Damage(damage, attackSpeed, recoverySpeed, damageType, identifier);
        }

        public CompoundTag toNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putFloat("damage", damage);
            tag.putInt("attackSpeed", attackSpeed);
            tag.putInt("recoverySpeed", recoverySpeed);
            tag.putString("type", damageType.toString());
            tag.putString("identifier", identifier);
            return tag;
        }

        public static Damage fromNBT(CompoundTag tag) {
            return new Damage(
                    tag.getFloat("damage"),
                    tag.getInt("attackSpeed"),
                    tag.getInt("recoverySpeed"),
                    new ResourceLocation(tag.getString("type")),
                    tag.getString("identifier")
            );
        }
    }
}