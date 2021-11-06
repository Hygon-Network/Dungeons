package fr.hygon.dungeons.zombies;

import net.minecraft.world.entity.EquipmentSlot;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ZombieII extends CustomZombie {
    public ZombieII() {
        super(24, 1, 40, 2, "Lvl 2");

        if(getBukkitLivingEntity().getEquipment() == null) {
            throw new RuntimeException("Couldn't get the equipments.");
        }

        setSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_HELMET, 1)), false);
        setSlot(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_CHESTPLATE, 1)), false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ClassicZombieGoal(this, 1.1D, 3.0D, 2.0F));
    }
}
