package fr.hygon.dungeons.zombies.classic;

import fr.hygon.dungeons.zombies.CustomZombie;
import fr.hygon.dungeons.zombies.goals.ClassicZombieGoal;
import net.minecraft.world.entity.EquipmentSlot;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ZombieIV extends CustomZombie {
    public ZombieIV() {
        super(36, 1, 40, 13, "Lvl 4");

        if(getBukkitLivingEntity().getEquipment() == null) {
            throw new RuntimeException("Couldn't get the equipments.");
        }

        setSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_HELMET, 1)), false);
        setSlot(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_CHESTPLATE, 1)), false);
        setSlot(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1)), false);
        setSlot(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(Material.CHAINMAIL_BOOTS, 1)), false);

        setSlot(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(getItemWithEnchantedEffect(Material.STONE_SWORD)), false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ClassicZombieGoal(this, 1.4D, 0.2D, 2.1D, 5.0F, 4));
    }
}
