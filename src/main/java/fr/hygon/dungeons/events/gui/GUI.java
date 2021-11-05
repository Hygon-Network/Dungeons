package fr.hygon.dungeons.events.gui;

import fr.hygon.dungeons.utils.InventoriesList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GUI {
    private final HashMap<Player, Inventory> playersInventories = new HashMap<>();
    private final HashMap<Player, InventoriesList> playerInventoriesType = new HashMap<>();

    public void openInventory(Player player, InventoriesList inventoryType) {
        Inventory inventory = inventoryType.getInventory(player);

        closeInventory(player);
        player.openInventory(inventory);

        playersInventories.put(player, inventory);
        playerInventoriesType.put(player, inventoryType);
    }

    public void closeInventory(Player player) {
        player.closeInventory();
        playersInventories.remove(player);
        playerInventoriesType.remove(player);
    }

    public InventoriesList getPlayerOpenInventoryType(Player player, Inventory inventory) {
        if (playersInventories.containsKey(player)) {
            if (inventory.equals(playersInventories.get(player))) {
                return playerInventoriesType.get(player);
            }
        }

        return null;
    }
}
