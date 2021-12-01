package fr.hygon.dungeons.game;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.utils.Difficulty;
import fr.hygon.dungeons.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import java.util.HashMap;
import java.util.HashSet;

public class DeathManager implements Listener {
    private static BukkitTask task;
    private static final HashMap<Player, Integer> deadPlayers = new HashMap<>();
    private static final HashMap<Player, HashMap<Integer, ItemStack>> playersStuff = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        double coinsToRemove = 0;

        switch (GameManager.getGameDifficulty()) {
            case NORMAL -> {
                deadPlayers.put(player, 40);
                coinsToRemove = PlayerUtils.getCoins(player) / 100D * 10D;
            }
            case HARD -> {
                deadPlayers.put(player, 50);
                coinsToRemove = PlayerUtils.getCoins(player) / 100D * 25D;
            } case INSANE -> {
                deadPlayers.put(player, Integer.MAX_VALUE);
                coinsToRemove = PlayerUtils.getCoins(player) / 100D * 50D;
            }
        }

        player.sendMessage(Component.text("-" + coinsToRemove + " coins.").color(TextColor.color(130, 130, 140)));
        PlayerUtils.removeCoins(player, coinsToRemove);

        HashMap<Integer, ItemStack> playerStuff = new HashMap<>();
        for(int i = 0; i < player.getInventory().getSize(); i++) {
            if(player.getInventory().getItem(i) != null) {
                playerStuff.put(i, player.getInventory().getItem(i));
            }
        }

        playersStuff.put(player, playerStuff);

        player.getInventory().clear();

        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
                player.setAllowFlight(true);
                player.setInvisible(true);
                player.setCollidable(false);
            }
        }.runTaskLater(Main.getPlugin(), 1);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player && isDead((Player) event.getEntity())) {
            event.setCancelled(true);
        }
    }

    public static boolean isDead(Player player) {
        return deadPlayers.containsKey(player);
    }

    public static void respawnAllPlayers() {
        new HashSet<>(deadPlayers.keySet()).forEach(DeathManager::respawnPlayer);
    }

    public static void respawnPlayer(Player player) {
        deadPlayers.remove(player);

        player.setInvisible(false);
        player.setAllowFlight(false);
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 6, 1.5, 180, 0));
        player.setCollidable(true);

        for(int i : playersStuff.get(player).keySet()) {
            player.getInventory().setItem(i, playersStuff.get(player).get(i));
        }

        playersStuff.remove(player);
    }

    public static void startTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if(GameManager.getGameDifficulty() != Difficulty.INSANE) {
                    for (Player players : new HashSet<>(deadPlayers.keySet())) {
                        if (deadPlayers.get(players) == 0) {
                            respawnPlayer(players);
                        } else {
                            deadPlayers.put(players, deadPlayers.get(players) - 1);
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getPlugin(), 0, 20);
    }

    public static void stopTask() {
        if(task != null) {
            task.cancel();
        }
    }
}
