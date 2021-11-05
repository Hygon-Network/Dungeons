package fr.hygon.dungeons.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class PlayerUtils implements Listener {
    private static final HashMap<Player, Double> playerCoins = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerCoins.putIfAbsent(player, 0.0);
    }

    public static double getCoins(Player player) {
        return playerCoins.getOrDefault(player, 0.0);
    }

    public static void addCoins(Player player, double coins) {
        playerCoins.merge(player, coins, Double::sum);
    }

    public static void removeCoins(Player player, double coins) {
        if(!playerCoins.containsKey(player)) {
            playerCoins.put(player, 0.0);
        } else {
            playerCoins.put(player, playerCoins.get(player) - coins);
        }
    }
}
