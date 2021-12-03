package fr.hygon.dungeons.events.gui;

import fr.hygon.dungeons.utils.Difficulty;
import fr.hygon.dungeons.utils.InventoriesList;
import fr.hygon.dungeons.utils.ItemList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DifficultySelectorGUI extends GUI implements Listener {
    private static final HashMap<Player, Difficulty> playersVote = new HashMap<>();
    private static final HashMap<Player, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null || event.getItem().getItemMeta() == null || event.getItem().getItemMeta().displayName() == null) return;
        ItemStack clickedItem = event.getItem();

        if(Objects.equals(clickedItem.getItemMeta().displayName(), ItemList.DIFFICULTY_SELECTOR.getItem().getItemMeta().displayName())) {
            if(cooldown.containsKey(player) && cooldown.get(player) + 3000 > System.currentTimeMillis()) {
                player.sendMessage(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                        .append(Component.text("Veuillez attendre avant de re-choisir une difficulté.").color(TextColor.color(230, 30, 30))));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
            } else {
                cooldown.put(player, System.currentTimeMillis());
                openInventory(player, InventoriesList.DIFFICULTY_SELECTOR);
                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 2, 1);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().displayName() == null)
            return;
        ItemStack clickedItem = event.getCurrentItem();
        if(getPlayerOpenInventoryType(player, event.getInventory()) == InventoriesList.DIFFICULTY_SELECTOR) {
            event.setCancelled(true);
            if(Objects.equals(clickedItem.getItemMeta().displayName(), ItemList.NORMAL_DIFFICULTY.getItem().getItemMeta().displayName())) {
                playersVote.put(player, Difficulty.NORMAL);
                closeInventory(player);

                Bukkit.broadcast(Component.text("☠").color(TextColor.color(170, 0, 0))
                        .append(Component.text(" | ").color(TextColor.color(107, 107, 107)))
                        .append(player.displayName()
                        .append(Component.text(" a voté pour la ").color(TextColor.color(255, 255, 75))
                        .append(Component.text("Difficulté Normale").color(TextColor.color(0, 170, 0))
                        .append(Component.text(".").color(TextColor.color(TextColor.color(255, 255, 75))))))));

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 2, 0);
            } else if(Objects.equals(clickedItem.getItemMeta().displayName(), ItemList.HARD_DIFFICULTY.getItem().getItemMeta().displayName())) {
                playersVote.put(player, Difficulty.HARD);
                closeInventory(player);

                Bukkit.broadcast(Component.text("☠").color(TextColor.color(170, 0, 0))
                        .append(Component.text(" | ").color(TextColor.color(107, 107, 107)))
                        .append(player.displayName()
                        .append(Component.text(" a voté pour la ").color(TextColor.color(255, 255, 75))
                        .append(Component.text("Difficulté Hard").color(TextColor.color(220, 110, 30))
                        .append(Component.text(".").color(TextColor.color(TextColor.color(255, 255, 75))))))));

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 2, 0);
            } else if(Objects.equals(clickedItem.getItemMeta().displayName(), ItemList.INSANE_DIFFICULTY.getItem().getItemMeta().displayName())) {
                playersVote.put(player, Difficulty.INSANE);
                closeInventory(player);

                Bukkit.broadcast(Component.text("☠").color(TextColor.color(170, 0, 0))
                        .append(Component.text(" | ").color(TextColor.color(107, 107, 107)))
                        .append(player.displayName()
                        .append(Component.text(" a voté pour la ").color(TextColor.color(255, 255, 75))
                        .append(Component.text("☠ Difficulté Insane ☠").color(TextColor.color(170, 30, 10))
                        .append(Component.text(".").color(TextColor.color(TextColor.color(255, 255, 75))))))));

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 2, 0);
            }
        }
    }

    public static Difficulty getDifficulty() {
        HashMap<Difficulty, Integer> votes = playersVote.values().stream()
                .collect(Collectors.toMap(difficulty -> difficulty, difficulty -> 1, Integer::sum, HashMap::new));

        if(votes.size() == 0) {
            return Difficulty.NORMAL;
        }

        int highestNumber = Collections.max(votes.values());

        ArrayList<Difficulty> difficulties = votes.keySet().stream().filter(difficulty -> votes.get(difficulty) == highestNumber)
                .collect(Collectors.toCollection(ArrayList::new));

        return difficulties.get(ThreadLocalRandom.current().nextInt(0, difficulties.size()));
    }
}
