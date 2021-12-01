package fr.hygon.dungeons.utils;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.shop.ArmorList;
import fr.hygon.dungeons.shop.SwordList;
import fr.hygon.dungeons.waves.WaveManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PlayerUtils implements Listener {
    private static final HashMap<Player, Double> playerCoins = new HashMap<>();
    private static final HashMap<Player, Integer> armorsLevel = new HashMap<>();
    private static final HashMap<Player, Integer> swordLevel = new HashMap<>();

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
            player.sendMessage(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                    .append(Component.text("Vous pourrez uniquement acheter cette armure à partir de la vague "
                    + armor.getMinWave() + ".").color(TextColor.color(200, 20, 20))));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
            return;
        } else if(PlayerUtils.getCoins(player) < armor.getPrice()) {
            player.sendMessage(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                    .append(Component.text("Vous n'avez pas assez d'argent.").color(TextColor.color(200, 20, 20))));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
            return;
        }

        removeCoins(player, armor.getPrice());
        armorsLevel.put(player, armor.getArmorLevel());
        armor.equipPlayer(player);

        if(armor.getArmorLevel() != 1) {
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 2, 0);
        }
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

    public static void buySword(Player player, SwordList sword) {
        if(PlayerUtils.getCoins(player) < sword.getPrice()) {
            player.sendMessage(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                    .append(Component.text("Vous n'avez pas assez d'argent.").color(TextColor.color(200, 20, 20))));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
            return;
        }
        removeCoins(player, sword.getPrice());
        swordLevel.put(player, sword.getSwordLevel());
        sword.equipPlayer(player);
        if(sword.getSwordLevel() != 1) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 2);
        }
    }

    public static void upgradeSword(Player player) {
        SwordList newSword = SwordList.getSwordFromLevel(swordLevel.getOrDefault(player, 1) + 1);
        if(newSword != null) {
            buySword(player, newSword);
        }
    }

    public static SwordList getPlayerSword(Player player) {
        return SwordList.getSwordFromLevel(swordLevel.getOrDefault(player, 1));
    }

    public static SwordList getPlayerFutureSword(Player player) {
        return SwordList.getSwordFromLevel(swordLevel.getOrDefault(player, 1) + 1);
    }

    @EventHandler
    public void onFoodConsume(PlayerItemConsumeEvent event) {
         Player player = event.getPlayer();
         new BukkitRunnable() {
             @Override
             public void run() {
                 player.setSaturation(10);
             }
         }.runTaskLater(Main.getPlugin(), 1);
    }
}
