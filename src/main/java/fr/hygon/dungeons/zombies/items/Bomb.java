package fr.hygon.dungeons.zombies.items;

import fr.hygon.dungeons.utils.ItemList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.util.Vector;

public class Bomb extends ArmorStand {
    private final LivingEntity launcher;
    private final double power;
    private final double damageToDeal;

    private int ticksOnGround = 0;
    private boolean blinkingState = false;

    public Bomb(LivingEntity launcher, double power, double damageToDeal, Vector vector) {
        super(EntityType.ARMOR_STAND, ((CraftWorld) Bukkit.getWorld("world")).getHandle());

        this.launcher = launcher;
        this.power = power;
        this.damageToDeal = damageToDeal;

        setNoBasePlate(true);
        setInvulnerable(true);
        setSmall(true);
        setInvisible(true);

        setSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(ItemList.TNT.getItem()), false);

        moveTo(launcher.getX(), launcher.getY(), launcher.getZ());
        getBukkitEntity().setVelocity(vector);
    }

    @Override
    public void tick() {
        super.tick();

        if(isOnGround()) {
            ticksOnGround++;
            if(ticksOnGround % 4 == 0) {
                if(blinkingState) {
                    setSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(ItemList.TNT.getItem()), false);
                } else {
                    setSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(ItemList.WHITE_HEAD.getItem()), false);
                }

                blinkingState = !blinkingState;
            }

            if(ticksOnGround == 20) {
                explode();
                discard();
            }
        }
    }

    private void explode() {
        for (Entity entities : getBukkitEntity().getNearbyEntities(power, power, power)) {
            if ((launcher instanceof net.minecraft.world.entity.monster.Zombie && entities instanceof Player) ||
                    (launcher instanceof ServerPlayer && entities instanceof Zombie)) {
                double healthDamagePercent = 100 - (entities.getLocation().distance(getBukkitEntity().getLocation()) / power) * 100;
                double damage = (damageToDeal / 100) * healthDamagePercent;

                ((Damageable) entities).damage(damage, launcher.getBukkitEntity());
            }
        }
    }
}
