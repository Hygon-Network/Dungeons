package fr.hygon.dungeons.zombies.specials.WolfZombie;

import fr.hygon.dungeons.zombies.goals.ClassicZombieGoal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

import java.text.DecimalFormat;

public class CustomWolf extends Wolf {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");
    private final WolfZombie spawner;

    public CustomWolf(WolfZombie spawner) {
        super(EntityType.WOLF, ((CraftWorld) Bukkit.getWorld("world")).getHandle());
        this.spawner = spawner;

        setCustomNameVisible(true);
        setPos(spawner.position());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ClassicZombieGoal(this, 1.0D, 0.2D, 2.1D, 2.0F, 1));
    }

    @Override
    public void tick() {
        super.tick();

        startPersistentAngerTimer();
        getBukkitEntity().customName(Component.text("[").color(NamedTextColor.DARK_GRAY)
            .append(Component.text("Wolf").color(NamedTextColor.GRAY))
            .append(Component.text("] ").color(NamedTextColor.DARK_GRAY))
            .append(Component.text(DECIMAL_FORMAT.format(getHealth()) + "/" + DECIMAL_FORMAT.format(getMaxHealth())).color(NamedTextColor.WHITE))
            .append(Component.text("❤").color(TextColor.color(200, 35, 35))));

        if(tickCount % 60 == 0) {
            heal(1);
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        spawner.aliveWolfs--;
    }
}
