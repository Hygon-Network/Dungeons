package fr.hygon.dungeons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public enum InventoriesList {
    DIFFICULTY_SELECTOR((player) -> {
        Inventory inventory = Bukkit.createInventory(null, 27, Component.text("Difficulté"));

        for (int i = 0; i < 27 ; i++) {
            inventory.setItem(i, ItemList.INVENTORY_FILLER.getItem());
        }

        inventory.setItem(11, ItemList.NORMAL_DIFFICULTY.getItem());
        inventory.setItem(13, ItemList.HARD_DIFFICULTY.getItem());
        inventory.setItem(15, ItemList.INSANE_DIFFICULTY.getItem());
        return inventory;
    }),
    SHOP((player -> {
        Inventory inventory = Bukkit.createInventory(null, 27, Component.text("Shop"));

        for (int i = 0; i < 27 ; i++) {
            inventory.setItem(i, ItemList.INVENTORY_FILLER.getItem());
        }

        inventory.setItem(12, ItemList.SHOP_SWORD.getItem());
        inventory.setItem(14, ItemList.ARMOR_SHOP.getItem());
        return inventory;
    })),
    ARMOR_GUI((player -> {
        Inventory inventory = Bukkit.createInventory(null, 45, Component.text("Armures"));

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, ItemList.INVENTORY_FILLER.getItem());
        }

        inventory.setItem(36, ItemList.BACK_MENU.getItem());

        inventory.setItem(3, PlayerUtils.getPlayerArmor(player).getArmorProvider().getHelmet());
        inventory.setItem(12, PlayerUtils.getPlayerArmor(player).getArmorProvider().getChestplate());
        inventory.setItem(21, PlayerUtils.getPlayerArmor(player).getArmorProvider().getLeggings());
        inventory.setItem(30, PlayerUtils.getPlayerArmor(player).getArmorProvider().getBoots());

        ItemStack actualArmor = ItemList.ACTUAL_ARMOR.getItem();
        ItemMeta actualArmorMeta = actualArmor.getItemMeta();
        actualArmorMeta.lore(List.of(PlayerUtils.getPlayerArmor(player).getName()));
        actualArmor.setItemMeta(actualArmorMeta);

        inventory.setItem(39, actualArmor);

        inventory.setItem(5, PlayerUtils.getPlayerFutureArmor(player).getArmorProvider().getHelmet());
        inventory.setItem(14, PlayerUtils.getPlayerFutureArmor(player).getArmorProvider().getChestplate());
        inventory.setItem(23, PlayerUtils.getPlayerFutureArmor(player).getArmorProvider().getLeggings());
        inventory.setItem(32, PlayerUtils.getPlayerFutureArmor(player).getArmorProvider().getBoots());

        ItemStack buyArmor = ItemList.BUY_OBJECT.getItem();
        ItemMeta buyArmorMeta = buyArmor.getItemMeta();
        buyArmorMeta.lore(Arrays.asList(PlayerUtils.getPlayerFutureArmor(player).getName(),
                Component.text("Prix: " + PlayerUtils.getPlayerFutureArmor(player).getPrice()).color(TextColor.color(230, 200, 50))
                        .decoration(TextDecoration.ITALIC, false)));
        buyArmor.setItemMeta(buyArmorMeta);

        inventory.setItem(41, buyArmor);
        return inventory;
    }));

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
