package fr.hygon.dungeons.zombies.goals;

import fr.hygon.dungeons.game.DeathManager;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class FollowPlayerGoal extends Goal {
    public final Mob mob;
    public final double speed;
    public final double stopPoint;

    public Player followedPlayer = null;
    private int ticksSinceLastSearch = 7 * 20;

    public FollowPlayerGoal(Mob mob, double speed, double stopPoint) {
        this.mob = mob;
        this.speed = speed;
        this.stopPoint = stopPoint;
    }

    @Override
    public boolean canUse() {
        ticksSinceLastSearch++;
        if(followedPlayer == null || DeathManager.isDead(followedPlayer)) {
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
        if(mob.getBukkitEntity().getLocation().distance(followedPlayer.getLocation()) > stopPoint) {
            mob.getNavigation().moveTo(((CraftPlayer) followedPlayer).getHandle(), speed);
        } else {
            mob.getNavigation().stop();
        }
    }

    private void updateFollowedPlayer() {
        Player shortestDistancePlayer = null;
        double shortestDistance = Double.MAX_VALUE;

        for(Player players : Bukkit.getOnlinePlayers()) {
            if(DeathManager.isDead(players)) continue;
            if(mob.getBukkitEntity().getLocation().distance(players.getLocation()) < shortestDistance) {
                shortestDistance = mob.getBukkitEntity().getLocation().distance(players.getLocation());
                shortestDistancePlayer = players;
            }
        }

        followedPlayer = shortestDistancePlayer;
    }
}
