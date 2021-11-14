package fr.hygon.dungeons.zombies.goals;

import fr.hygon.dungeons.zombies.CustomZombie;
import net.minecraft.world.damagesource.DamageSource;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;

public class ClassicZombieGoal extends FollowPlayerGoal {
    private final double reach;
    private final float damage;
    private final double attackSpeed;

    private int ticksSinceLastDamage = 0;

    public ClassicZombieGoal(CustomZombie zombie, double speed, double stopPoint, double reach, float damage, double attackSpeed) {
        super(zombie, speed, stopPoint);
        this.reach = reach;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void tick() {
        super.tick();
        double d = followedPlayer.getLocation().getY() + followedPlayer.getEyeHeight();
        zombie.getLookControl().setLookAt(followedPlayer.getLocation().getX(), d, followedPlayer.getLocation().getZ());
        if(zombie.getBukkitEntity().getLocation().distance(followedPlayer.getLocation()) <= reach) {
            ticksSinceLastDamage++;
            if (ticksSinceLastDamage <= Math.ceil(attackSpeed - (attackSpeed / 4.0))) {
                zombie.setAggressive(false);
            } else if (ticksSinceLastDamage <= 20) {
                if (ticksSinceLastDamage == Math.ceil(attackSpeed - (attackSpeed / 4.0)) + 1) {
                    ((CraftPlayer) followedPlayer).getHandle().hurt(DamageSource.mobAttack(zombie), damage);
                }
                zombie.setAggressive(true);
            } else {
                zombie.setAggressive(false);
                ticksSinceLastDamage = 0;
            }
        } else {
            zombie.setAggressive(false);
        }
    }
}
