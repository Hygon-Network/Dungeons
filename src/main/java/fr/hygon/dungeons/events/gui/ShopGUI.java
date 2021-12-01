package fr.hygon.dungeons.events.gui;

import fr.hygon.dungeons.utils.InventoriesList;
import fr.hygon.dungeons.utils.ItemList;
import fr.hygon.dungeons.utils.PlayerUtils;
import fr.hygon.dungeons.waves.WaveManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ShopGUI extends GUI implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null || event.getItem().getItemMeta() == null || event.getItem().getItemMeta().displayName() == null) return;
        ItemStack clickedItem = event.getItem();

        if(Objects.equals(clickedItem.getItemMeta().displayName(), ItemList.RADIO_ON.getItem().getItemMeta().displayName())) {
            openInventory(player, InventoriesList.SHOP);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) return;
        ItemStack clickedItem = event.getCurrentItem();
        if(getPlayerOpenInventoryType(player, event.getInventory()) == InventoriesList.SHOP) {
            PlayerUtils.addCoins(player, 1000);
            event.setCancelled(true);
            if(clickedItem.isSimilar(ItemList.ARMOR_SHOP.getItem())) {
                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 2, 1);
                if(PlayerUtils.getPlayerFutureArmor(player) == null) {
                    player.sendMessage(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                            .append(Component.text("Vous avez déjà débloqué toutes les armures.").color(TextColor.color(200, 20, 30))));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
                    closeInventory(player);
                    return;
                }
                openInventory(player, InventoriesList.ARMOR_GUI);
            } else if(clickedItem.isSimilar(ItemList.SHOP_SWORD.getItem())) {
                if(PlayerUtils.getPlayerFutureSword(player) == null) {
                    player.sendMessage(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                            .append(Component.text("Vous avez déjà débloqué toutes les épées.").color(TextColor.color(200, 20, 30))));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
                    closeInventory(player);
                    return;
                } else {
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 2, 1);
                }
                openInventory(player, InventoriesList.SWORD_GUI);
            } else if(clickedItem.isSimilar(ItemList.SKIP_WAVE.getItem())) {
                WaveManager.registerSkippingPlayer(player);
                closeInventory(player);
            }
        } else if(getPlayerOpenInventoryType(player, event.getInventory()) == InventoriesList.ARMOR_GUI) {
            event.setCancelled(true);
            if(Objects.equals(clickedItem.getItemMeta().displayName(), ItemList.BUY_OBJECT.getItem().getItemMeta().displayName())) {
                PlayerUtils.upgradeArmor(player);
                closeInventory(player);

                if(PlayerUtils.getPlayerFutureArmor(player) != null) {
                    openInventory(player, InventoriesList.ARMOR_GUI);
                } else {
                    openInventory(player, InventoriesList.SHOP);
                }

            } else if(clickedItem.isSimilar(ItemList.BACK_MENU.getItem())) {
                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 2, 1);
                openInventory(player, InventoriesList.SHOP);
            }
        } else if(getPlayerOpenInventoryType(player, event.getInventory()) == InventoriesList.SWORD_GUI) {
            event.setCancelled(true);
            if(clickedItem.getType() == ItemList.INVENTORY_FILLER.getItem().getType()) return;

            if(PlayerUtils.getPlayerFutureSword(player) != null &&
                    PlayerUtils.getPlayerFutureSword(player).getSwordProvider().getSword().getType() == clickedItem.getType()) {
                PlayerUtils.upgradeSword(player);
                closeInventory(player);

                if(PlayerUtils.getPlayerFutureSword(player) != null) {
                    openInventory(player, InventoriesList.SWORD_GUI);
                } else {
                    openInventory(player, InventoriesList.SHOP);
                }

            } else if(clickedItem.isSimilar(ItemList.BACK_MENU.getItem())) {
                openInventory(player, InventoriesList.SHOP);
                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 2, 1);
            }
        }
    }
}
