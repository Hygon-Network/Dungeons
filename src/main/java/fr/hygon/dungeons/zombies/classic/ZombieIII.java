package fr.hygon.dungeons.zombies.classic;

import fr.hygon.dungeons.zombies.goals.ClassicZombieGoal;
import fr.hygon.dungeons.zombies.CustomZombie;
import net.minecraft.world.entity.EquipmentSlot;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ZombieIII extends CustomZombie {
    public ZombieIII() {
        super(28, 1, 40, 10, "Lvl 3");

        if(getBukkitLivingEntity().getEquipment() == null) {
            throw new RuntimeException("Couldn't get the equipments.");
        }

        setSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_HELMET, 1)), false);
        setSlot(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_CHESTPLATE, 1)), false);
        setSlot(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_LEGGINGS, 1)), false);
        setSlot(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_BOOTS, 1)), false);

        setSlot(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.STONE_SWORD, 1)), false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ClassicZombieGoal(this, 1.1D, 0.2D, 2.1D, 2.0F, 4));
    }
}
