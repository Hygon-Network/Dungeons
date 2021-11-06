package fr.hygon.dungeons.utils;

import fr.hygon.dungeons.shop.ArmorList;
import fr.hygon.dungeons.waves.WaveManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class PlayerUtils implements Listener {
    private static final HashMap<Player, Double> playerCoins = new HashMap<>();
    private static final HashMap<Player, Integer> armorsLevel = new HashMap<>();

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

    public static void buyArmor(Player player, ArmorList armor) {
        if(WaveManager.getWave() < armor.getMinWave()) {
            player.sendMessage(Component.text("Vous pourrez uniquement acheter cette armure à partir de la vague "
                    + armor.getMinWave() + ".").color(TextColor.color(175, 20, 30)));
            return;
        } else if(PlayerUtils.getCoins(player) < armor.getPrice()) {
            player.sendMessage(Component.text("Vous n'avez pas assez d'argent.").color(TextColor.color(170, 20, 20)));
            return;
        }

        removeCoins(player, armor.getPrice());
        armorsLevel.put(player, armor.getArmorLevel());
        armor.equipPlayer(player);
    }

    public static void upgradeArmor(Player player) {
        ArmorList newArmor = ArmorList.getArmorFromLevel(armorsLevel.getOrDefault(player, 1) + 1);
        if(newArmor != null) {
            buyArmor(player, newArmor);
        }
    }

    public static ArmorList getPlayerArmor(Player player) {
        return ArmorList.getArmorFromLevel(armorsLevel.getOrDefault(player, 1));
    }

    public static ArmorList getPlayerFutureArmor(Player player) {
        return ArmorList.getArmorFromLevel(armorsLevel.getOrDefault(player, 1) + 1);
    }
}
