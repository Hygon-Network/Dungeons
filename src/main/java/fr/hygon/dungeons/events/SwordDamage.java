package fr.hygon.dungeons.events;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.game.GameManager;
import fr.hygon.dungeons.utils.Difficulty;
import fr.hygon.dungeons.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class SwordDamage implements Listener {
    private static BukkitTask task;

    private static final Map<Difficulty, Integer> hitsBeforeItemBreak = Map.ofEntries(
            Map.entry(Difficulty.NORMAL, 20),
            Map.entry(Difficulty.HARD, 17),
            Map.entry(Difficulty.INSANE, 15)
    );

    private static final Map<Difficulty, Integer> regenHits = Map.ofEntries(
            Map.entry(Difficulty.NORMAL, 4),
            Map.entry(Difficulty.HARD, 4),
            Map.entry(Difficulty.INSANE, 3)
    );

    private static final HashMap<Player, Integer> playersHits = new HashMap<>();

    public static void startTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for(Player players : Bukkit.getOnlinePlayers()) {
                    if(players.isSneaking()) {
                        ItemStack item = players.getInventory().getItemInMainHand();
                        if(item.getType() == PlayerUtils.getPlayerSword(players).getSwordProvider().getSword().getType() && item.getItemMeta() != null) {
                            if (playersHits.containsKey(players) && playersHits.get(players) > 0) {
                                playersHits.put(players, Math.max(getPlayerHit(players) - getDifficultyRegen(), 0));
                                updateItemDamage(players, item);
                            }
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

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player player)) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType() != PlayerUtils.getPlayerSword(player).getSwordProvider().getSword().getType() || item.getItemMeta() == null) return;

        incrementPlayerHit(player);
        updateItemDamage(player, item);
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }

    private static int getDifficultyMaxHits() {
        return hitsBeforeItemBreak.get(GameManager.getGameDifficulty());
    }

    private static int getDifficultyRegen() {
        return regenHits.get(GameManager.getGameDifficulty());
    }

    private static int getPlayerHit(Player player) {
        if(!playersHits.containsKey(player)) {
            playersHits.put(player, 0);
        }

        return playersHits.get(player);
    }

    private static void incrementPlayerHit(Player player) {
        if(playersHits.containsKey(player)) {
            if(playersHits.get(player) < getDifficultyMaxHits()) {
                playersHits.merge(player, 1, Integer::sum);
            }
        } else {
            playersHits.put(player, 1);
        }
    }

    public static boolean canHit(Player player) {
        return playersHits.getOrDefault(player, 0) < getDifficultyMaxHits();
    }

    private static void updateItemDamage(Player player, ItemStack item) {
        Damageable itemMeta = (Damageable) item.getItemMeta();

        float damage = (float) item.getType().getMaxDurability() / 100F * ((float) getPlayerHit(player) / (float) getDifficultyMaxHits() * 100.0F);
        if(damage >= item.getType().getMaxDurability()) {
            damage = item.getType().getMaxDurability() - 1;
        }

        itemMeta.setDamage((int) Math.floor(damage));
        item.setItemMeta(itemMeta);
    }
}
