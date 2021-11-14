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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffectType;
import java.util.concurrent.ThreadLocalRandom;

public class ZombieUtils implements Listener {
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Zombie && event.getDamager() instanceof Player playerDamager) {
            boolean isCritical = playerDamager.getFallDistance() > 0.0F && !playerDamager.isOnGround() && !playerDamager.isClimbing() &&
                    !playerDamager.isInWater() && !playerDamager.hasPotionEffect(PotionEffectType.BLINDNESS);
            // Taken from minecraft's code by itself. Don't blame me...

            if(playerDamager.getInventory().getItemInMainHand().getType() ==
                    PlayerUtils.getPlayerSword(playerDamager).getSwordProvider().getSword().getType()) {
                event.setDamage(0);
                if(SwordDamage.canHit(playerDamager)) {
                    event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, PlayerUtils.getPlayerSword(playerDamager).getDamage() + (isCritical ? 1 : 0));
                } else {
                    event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
                }
            }


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
        } else if(event.getEntity() instanceof Player player) {
            double oldDamage = event.getDamage();
            event.setDamage(0);
            event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, oldDamage - PlayerUtils.getPlayerArmor(player).getHeartAbsorption() <= 0 ?
                    1 : oldDamage - PlayerUtils.getPlayerArmor(player).getHeartAbsorption());
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if(event.getEntity() instanceof Zombie && ((CraftZombie) event.getEntity()).getHandle() instanceof CustomZombie customZombie) {
            customZombie.dropSpecialItem();
        }
    }
}
