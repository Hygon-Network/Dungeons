package fr.hygon.dungeons.utils;

import fr.hygon.dungeons.shop.SwordList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;

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
        Inventory inventory = Bukkit.createInventory(null, 45, Component.text("Shop > Armures"));

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
        ArrayList<Component> actualArmorLore = new ArrayList<>();

        actualArmorLore.add(Component.text("Armure").color(TextColor.color(30, 110, 240))
                .append(Component.text(" » ").color(NamedTextColor.GRAY))
                .append(PlayerUtils.getPlayerArmor(player).getName().color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

        actualArmorLore.add(Component.text(""));

        actualArmorLore.add(Component.text("Damage Resistance » +" + PlayerUtils.getPlayerArmor(player).getHeartAbsorption()).color(NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));

        actualArmorMeta.lore(actualArmorLore);
        actualArmor.setItemMeta(actualArmorMeta);

        inventory.setItem(39, actualArmor);

        inventory.setItem(5, PlayerUtils.getPlayerFutureArmor(player).getArmorProvider().getHelmet());
        inventory.setItem(14, PlayerUtils.getPlayerFutureArmor(player).getArmorProvider().getChestplate());
        inventory.setItem(23, PlayerUtils.getPlayerFutureArmor(player).getArmorProvider().getLeggings());
        inventory.setItem(32, PlayerUtils.getPlayerFutureArmor(player).getArmorProvider().getBoots());

        ItemStack buyArmor = ItemList.BUY_OBJECT.getItem();
        ItemMeta buyArmorMeta = buyArmor.getItemMeta();
        ArrayList<Component> buyArmorLore = new ArrayList<>();

        buyArmorLore.add(Component.text("Armure").color(TextColor.color(30, 110, 240))
                        .append(Component.text(" » ").color(NamedTextColor.GRAY))
                        .append(PlayerUtils.getPlayerFutureArmor(player).getName().color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

        buyArmorLore.add(Component.text("Prix").color(TextColor.color(255, 255, 50))
                        .append(Component.text(" » ").color(NamedTextColor.GRAY))
                        .append(Component.text(PlayerUtils.getPlayerFutureArmor(player).getPrice() + " Gold").color(TextColor.color(255, 255, 50)))
                        .decoration(TextDecoration.ITALIC, false));

        if (PlayerUtils.getPlayerFutureArmor(player).getMinWave() != 0) {
            buyArmorLore.add(Component.text("Vague Minimum").color(TextColor.color(255, 110, 60))
                    .append(Component.text(" » ").color(NamedTextColor.GRAY))
                    .append(Component.text(PlayerUtils.getPlayerFutureArmor(player).getMinWave()).color(TextColor.color(255, 60, 60)))
                    .decoration(TextDecoration.ITALIC, false));
        }

        buyArmorLore.add(Component.text(""));

        buyArmorLore.add(Component.text("Damage Resistance").color(TextColor.color(235, 35, 35))
                .append(Component.text(" » ").color(NamedTextColor.GRAY))
                .append(Component.text("+" + PlayerUtils.getPlayerFutureArmor(player).getHeartAbsorption()).color(TextColor.color(255, 60, 60)))
                .decoration(TextDecoration.ITALIC, false));

        buyArmorLore.add(Component.text(""));

        if(PlayerUtils.getPlayerFutureArmor(player).getPrice() - PlayerUtils.getCoins(player) > 0) {
            buyArmorLore.add(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                    .append(Component.text("Pas assez de Gold!").color(TextColor.color(210, 10, 10))).decoration(TextDecoration.ITALIC, false));
        } else {
            buyArmorLore.add(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                    .append(Component.text("Cliquez pour acheter").color(TextColor.color(80, 200, 40))).decoration(TextDecoration.ITALIC, false));
        }

        buyArmorMeta.lore(buyArmorLore);
        buyArmor.setItemMeta(buyArmorMeta);

        inventory.setItem(41, buyArmor);
        return inventory;
    })),
    SWORD_GUI(player -> {
        Inventory inventory = Bukkit.createInventory(null, 27, Component.text("Shop > Épées"));

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            inventory.setItem(slot, ItemList.INVENTORY_FILLER.getItem());
        }

        inventory.setItem(18, ItemList.BACK_MENU.getItem());

        int slot = 11;
        for(SwordList swords : SwordList.values()) {
            if(swords == SwordList.WOOD_SWORD) continue;

            ItemStack swordItem = swords.getSwordProvider().getSword();
            ItemMeta swordItemMeta = swordItem.getItemMeta();
            ArrayList<Component> lore = new ArrayList<>();

            if(PlayerUtils.getPlayerFutureSword(player).getSwordLevel() == swords.getSwordLevel()) {
                lore.add(Component.text("Prix").color(TextColor.color(255, 255, 50))
                        .append(Component.text(" » ").color(NamedTextColor.GRAY))
                        .append(Component.text(PlayerUtils.getPlayerFutureSword(player).getPrice() + " Gold").color(TextColor.color(255, 255, 50)))
                        .decoration(TextDecoration.ITALIC, false));

                lore.add(Component.text(""));

                lore.add(Component.text("Damage").color(TextColor.color(235, 35, 35))
                        .append(Component.text(" » ").color(NamedTextColor.GRAY))
                        .append(Component.text("+" + PlayerUtils.getPlayerFutureSword(player).getDamage()).color(TextColor.color(255, 60, 60)))
                        .decoration(TextDecoration.ITALIC, false));

                lore.add(Component.text(""));

                if(PlayerUtils.getPlayerFutureSword(player).getPrice() - PlayerUtils.getCoins(player) > 0) {
                    lore.add(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                            .append(Component.text("Pas assez de Gold!").color(TextColor.color(210, 10, 10))).decoration(TextDecoration.ITALIC, false));
                } else {
                    lore.add(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                            .append(Component.text("Cliquez pour acheter").color(TextColor.color(80, 200, 40))).decoration(TextDecoration.ITALIC, false));
                }

            } else if(PlayerUtils.getPlayerSword(player).getSwordLevel() >= swords.getSwordLevel()) {

                    lore.add(Component.text("Damage » +" + swords.getDamage()).color(NamedTextColor.DARK_GRAY)
                            .decoration(TextDecoration.ITALIC, false));


                lore.add(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                        .append(Component.text("Achetée").color(TextColor.color(80, 200, 40))).decoration(TextDecoration.ITALIC,false));
            } else {
                lore.add(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                        .append(Component.text("Achetez l'").append(SwordList.getSwordFromLevel(swords.getSwordLevel() - 1).getName()).color(TextColor.color(210, 10, 10)))
                        .decoration(TextDecoration.ITALIC, false));
            }

            swordItemMeta.lore(lore);
            swordItemMeta.displayName(swords.getName());
            swordItem.setItemMeta(swordItemMeta);

            inventory.setItem(slot, swordItem);
            slot++;
        }

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
