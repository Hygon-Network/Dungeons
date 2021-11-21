package fr.hygon.dungeons.zombies.specials.WolfZombie;

import fr.hygon.dungeons.waves.WaveManager;
import fr.hygon.dungeons.zombies.CustomZombie;
import fr.hygon.dungeons.zombies.goals.ClassicZombieGoal;
import net.minecraft.world.entity.EquipmentSlot;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

public class WolfZombie extends CustomZombie {
    public int aliveWolfs = 0;

    public WolfZombie() {
        super(24, 0, 0, 15, "Wolf");

        if(getBukkitLivingEntity().getEquipment() == null) {
            throw new RuntimeException("Couldn't get the equipments.");
        }

        setSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.CHAINMAIL_HELMET)), false);
        setSlot(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(Material.CHAINMAIL_CHESTPLATE)), false);
        setSlot(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(new ItemStack(Material.CHAINMAIL_LEGGINGS)), false);
        setSlot(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(Material.CHAINMAIL_BOOTS)), false);

        setSlot(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.BONE, 1)), false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ClassicZombieGoal(this, 0.8D, 0.2D, 2.1D, 3.0F, 1));
    }

    @Override
    public void tick() {
        super.tick();


        if(tickCount % 60 == 0 && aliveWolfs < 6) {
            aliveWolfs++;
            WaveManager.incrementAliveZombie();
            level.addEntity(new CustomWolf(this), CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
}
