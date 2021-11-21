package fr.hygon.dungeons.zombies.goals;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;

public class ClassicZombieGoal extends FollowPlayerGoal {
    private final double reach;
    private final float damage;
    private final double attackSpeed;

    private int ticksSinceLastDamage = 0;

    public ClassicZombieGoal(Mob zombie, double speed, double stopPoint, double reach, float damage, double attackSpeed) {
        super(zombie, speed, stopPoint);
        this.reach = reach;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void tick() {
        super.tick();
        double d = followedPlayer.getLocation().getY() + followedPlayer.getEyeHeight();
        mob.getLookControl().setLookAt(followedPlayer.getLocation().getX(), d, followedPlayer.getLocation().getZ());
        if(mob.getBukkitEntity().getLocation().distance(followedPlayer.getLocation()) <= reach) {
            ticksSinceLastDamage++;
            if (ticksSinceLastDamage <= Math.ceil(attackSpeed - (attackSpeed / 4.0))) {
                mob.setAggressive(false);
            } else if (ticksSinceLastDamage <= 20) {
                if (ticksSinceLastDamage == Math.ceil(attackSpeed - (attackSpeed / 4.0)) + 1) {
                    ((CraftPlayer) followedPlayer).getHandle().hurt(DamageSource.mobAttack(mob), damage);
                }
                mob.setAggressive(true);
            } else {
                mob.setAggressive(false);
                ticksSinceLastDamage = 0;
            }
        } else {
            mob.setAggressive(false);
        }
    }
}
