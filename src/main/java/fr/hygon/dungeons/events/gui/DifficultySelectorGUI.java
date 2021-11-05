package fr.hygon.dungeons.events.gui;

import fr.hygon.dungeons.utils.Difficulty;
import fr.hygon.dungeons.utils.InventoriesList;
import fr.hygon.dungeons.utils.ItemList;
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

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null || event.getItem().getItemMeta() == null || event.getItem().getItemMeta().displayName() == null) return;
        ItemStack clickedItem = event.getItem();

        if(Objects.equals(clickedItem.getItemMeta().displayName(), ItemList.DIFFICULTY_SELECTOR.getItem().getItemMeta().displayName())) {
            openInventory(player, InventoriesList.DIFFICULTY_SELECTOR);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().displayName() == null)
            return;
        ItemStack clickedItem = event.getCurrentItem();
        if(getPlayerOpenInventoryType(player, event.getInventory()) == InventoriesList.DIFFICULTY_SELECTOR) {
            if(clickedItem.isSimilar(ItemList.NORMAL_DIFFICULTY.getItem())) {
                playersVote.put(player, Difficulty.NORMAL);
                closeInventory(player);
            } else if(clickedItem.isSimilar(ItemList.HARD_DIFFICULTY.getItem())) {
                playersVote.put(player, Difficulty.HARD);
                closeInventory(player);
            } else if(clickedItem.isSimilar(ItemList.INSANE_DIFFICULTY.getItem())) {
                playersVote.put(player, Difficulty.INSANE);
                closeInventory(player);
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
