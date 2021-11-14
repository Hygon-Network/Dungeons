package fr.hygon.dungeons.zombies.goals;

import fr.hygon.dungeons.zombies.CustomZombie;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class FollowPlayerGoal extends Goal {
    public final CustomZombie zombie;
    public final double speed;
    public final double stopPoint;

    public Player followedPlayer = null;
    private int ticksSinceLastSearch = 7 * 20;

    public FollowPlayerGoal(CustomZombie zombie, double speed, double stopPoint) {
        this.zombie = zombie;
        this.speed = speed;
        this.stopPoint = stopPoint;
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
        if(zombie.getBukkitEntity().getLocation().distance(followedPlayer.getLocation()) > stopPoint) {
            zombie.getNavigation().moveTo(((CraftPlayer) followedPlayer).getHandle(), speed);
        } else {
            zombie.getNavigation().stop();
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
