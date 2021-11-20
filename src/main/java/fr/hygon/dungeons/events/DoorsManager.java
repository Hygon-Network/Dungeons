package fr.hygon.dungeons.events;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.game.Doors;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class DoorsManager implements Listener {
    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getClickedBlock() == null || event.getHand() == EquipmentSlot.OFF_HAND) return;

        for(Doors.DoorsList doors : Doors.DoorsList.values()) {
            if(doors.getDoor().getDoorsBlocks().contains(event.getClickedBlock())) {
                doors.getDoor().openDoor(player);
                break;
            }
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if(event.getRightClicked() instanceof ArmorStand armorStand) {
            if(armorStand.getPersistentDataContainer().has(new NamespacedKey(Main.getPlugin(), "door"), PersistentDataType.STRING)) {
                Doors.DoorsList.valueOf(armorStand.getPersistentDataContainer()
                    .get(new NamespacedKey(Main.getPlugin(), "door"), PersistentDataType.STRING)).getDoor().openDoor(player);
            }
        }
    }
}
