package fr.hygon.dungeons.zombies.specials;

import fr.hygon.dungeons.zombies.goals.ClassicZombieGoal;
import fr.hygon.dungeons.zombies.CustomZombie;
import net.minecraft.world.entity.EquipmentSlot;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;

public class SpeedyZombie extends CustomZombie {
    public SpeedyZombie() {
        super(28, 1, 20, 10, "Speedy");

        if(getBukkitLivingEntity().getEquipment() == null) {
            throw new RuntimeException("Couldn't get the equipments.");
        }

        setSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(getDyedLeather(Material.LEATHER_HELMET, Color.WHITE)), false);
        setSlot(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(getDyedLeather(Material.LEATHER_CHESTPLATE, Color.WHITE)), false);
        setSlot(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(getDyedLeather(Material.LEATHER_LEGGINGS, Color.WHITE)), false);
        setSlot(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(getDyedLeather(Material.LEATHER_BOOTS, Color.WHITE)), false);

        setSlot(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(getItemWithEnchantedEffect(Material.STONE_SWORD)), false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ClassicZombieGoal(this, 1.8D, 0.2D, 2.1D, 2.0F, 1));
    }
}
