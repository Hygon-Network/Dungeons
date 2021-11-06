package fr.hygon.dungeons.events.gui;

import fr.hygon.dungeons.utils.InventoriesList;
import fr.hygon.dungeons.utils.ItemList;
import fr.hygon.dungeons.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
            event.setCancelled(true);
            if(clickedItem.isSimilar(ItemList.ARMOR_SHOP.getItem())) {
                if(PlayerUtils.getPlayerFutureArmor(player) == null) {
                    player.sendMessage(Component.text("Vous avez déjà débloqué toutes les armures.").color(TextColor.color(175, 20, 30)));
                    closeInventory(player);
                    return;
                }
                openInventory(player, InventoriesList.ARMOR_GUI);
            }
        } else if(getPlayerOpenInventoryType(player, event.getInventory()) == InventoriesList.ARMOR_GUI) {
            event.setCancelled(true);
            if(Objects.equals(clickedItem.getItemMeta().displayName(), ItemList.BUY_OBJECT.getItem().getItemMeta().displayName())) {
                PlayerUtils.upgradeArmor(player);
                openInventory(player, InventoriesList.SHOP);
            } else if(clickedItem.isSimilar(ItemList.BACK_MENU.getItem())) {
                openInventory(player, InventoriesList.SHOP);
            }
        }
    }
}
