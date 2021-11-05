package fr.hygon.dungeons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public enum InventoriesList {
    DIFFICULTY_SELECTOR((player) -> {
        Inventory inventory = Bukkit.createInventory(null, 9, Component.text("Difficulté").color(TextColor.color(130, 130, 130)));

        inventory.setItem(0, ItemList.NORMAL_DIFFICULTY.getItem());
        inventory.setItem(4, ItemList.HARD_DIFFICULTY.getItem());
        inventory.setItem(8, ItemList.INSANE_DIFFICULTY.getItem());
        return inventory;
    });

    private final InventorySupplier inventorySupplier;

    InventoriesList(InventorySupplier inventorySupplier) {
        this.inventorySupplier = inventorySupplier;
    }

    public Inventory getInventory(Player player) {
        return inventorySupplier.getInventory(player);
    }

    private interface InventorySupplier {
        Inventory getInventory(Player player);
    }
}
