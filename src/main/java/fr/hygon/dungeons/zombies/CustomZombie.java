package fr.hygon.dungeons.zombies;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

import java.text.DecimalFormat;
import java.util.Objects;

public class CustomZombie extends Zombie {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");

    private final float regeneration;
    private final int tickPerRegen;
    private final double coins;
    private final String name;

    public CustomZombie(int health, float regeneration, int tickPerRegen, double coins, String name) {
        super(EntityType.ZOMBIE, ((CraftWorld) Bukkit.getWorld("world")).getHandle());
        if(tickPerRegen == 0) {
            tickPerRegen = 1; // We can't divide by zero.
            regeneration = 0;
        }

        this.regeneration = regeneration;
        this.tickPerRegen = tickPerRegen;
        this.coins = coins;
        this.name = name;

        // TODO set position
        setPos(0.5, 6, 1.5);
        setCustomNameVisible(true);
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
        getBukkitEntity().customName(Component.text("[").color(NamedTextColor.DARK_GRAY)
            .append(Component.text(name).color(NamedTextColor.GRAY))
                .append(Component.text("] ").color(NamedTextColor.DARK_GRAY))
                    .append(Component.text(DECIMAL_FORMAT.format(getHealth()) + "/" + DECIMAL_FORMAT.format(getMaxHealth())).color(NamedTextColor.WHITE))
                        .append(Component.text("❤").color(TextColor.color(200, 35, 35))));
        if(tickCount % tickPerRegen == 0) {
            heal(regeneration);
        }
    }

    public double getCoins() {
        return coins;
    }
}
