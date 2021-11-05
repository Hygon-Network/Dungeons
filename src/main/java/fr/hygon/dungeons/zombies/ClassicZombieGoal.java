package fr.hygon.dungeons.zombies;

import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ClassicZombieGoal extends Goal {
    private final CustomZombie zombie;
    private final double speed;
    private final double reach;
    private final double damage;

    private Player followedPlayer = null;
    private int ticksSinceLastSearch = 7 * 20;

    private int raiseArmTicks = 0;

    public ClassicZombieGoal(CustomZombie zombie, double speed, double reach, double damage) {
        this.zombie = zombie;
        this.speed = speed;
        this.reach = reach;
        this.damage = damage;
    }

    @Override
    public boolean canUse() {
        ticksSinceLastSearch++;
        if(followedPlayer == null) {
            updateFollowedPlayer();
            ticksSinceLastSearch = 0;
        } else if (ticksSinceLastSearch == 7 * 20) {
            ticksSinceLastSearch = 0;
            updateFollowedPlayer();
        }

        return followedPlayer != null;
    }

    @Override
    public void tick() {
        zombie.getNavigation().moveTo(((CraftPlayer) followedPlayer).getHandle(), speed);

        if(zombie.getBukkitEntity().getLocation().distance(followedPlayer.getLocation()) <= reach) {
            zombie.setAggressive(true);
            followedPlayer.damage(damage, zombie.getBukkitMonster());
        } else {
            zombie.setAggressive(false);
        }
    }

    private void updateFollowedPlayer() {
        Player shortestDistancePlayer = null;
        double shortestDistance = Double.MAX_VALUE;

        for(Player players : Bukkit.getOnlinePlayers()) {
            if(zombie.getBukkitEntity().getLocation().distance(players.getLocation()) < shortestDistance) {
                shortestDistance = zombie.getBukkitEntity().getLocation().distance(players.getLocation());
                shortestDistancePlayer = players;
            }
        }

        followedPlayer = shortestDistancePlayer;
    }
}
