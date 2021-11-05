package fr.hygon.dungeons.zombies;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import java.util.Objects;

public class CustomZombie extends Zombie {
    private final float regeneration;
    private final int tickPerRegen;
    private final double coins;

    public CustomZombie(int health, float regeneration, int tickPerRegen, double coins) {
        super(EntityType.ZOMBIE, ((CraftWorld) Bukkit.getWorld("world")).getHandle());
        if(tickPerRegen == 0) {
            tickPerRegen = 1; // We can't divide by zero.
            regeneration = 0;
        }

        this.regeneration = regeneration;
        this.tickPerRegen = tickPerRegen;

        this.coins = coins;

        // TODO set position
        setPos(0.5, 6, 1.5);

        setAttribute(Attribute.GENERIC_MAX_HEALTH, health);
    }

    private void setAttribute(Attribute attribute, double value) {
        Objects.requireNonNull(getBukkitLivingEntity().getAttribute(attribute)).setBaseValue(value);
    }

    @Override
    protected void customServerAiStep() {

    }

    @Override
    public void tick() {
        super.tick();

        if(tickCount % tickPerRegen == 0) {
            heal(regeneration);
        }
    }
}
