package fr.hygon.dungeons.events;

import fr.hygon.dungeons.utils.ItemList;
import fr.hygon.dungeons.utils.PlayerUtils;
import fr.hygon.dungeons.zombies.CustomZombie;
import net.kyori.adventure.text.Component;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.ThreadLocalRandom;

public class ZombieUtils implements Listener {
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Zombie && event.getDamager() instanceof Player playerDamager) {
            if(((CraftZombie) event.getEntity()).getHandle() instanceof CustomZombie customDamagedZombie) {
                if(customDamagedZombie.getHealth() - event.getFinalDamage() <= 0) {
                    PlayerUtils.addCoins(playerDamager, customDamagedZombie.getCoins());
                    playerDamager.sendActionBar(Component.text("+" + customDamagedZombie.getCoins() + " coins!"));
                    playerDamager.addNetworkXp(10);

                    if(ThreadLocalRandom.current().nextInt(0, 100) <= 20) {
                        playerDamager.getInventory().addItem(ItemList.BREAD.getItem());
                    }
                }
            }
        }
    }
}
