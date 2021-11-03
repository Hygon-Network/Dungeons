package fr.hygon.dungeons.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeaveEvent implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.joinMessage(Component.text("☠").color(TextColor.color(170, 0, 0))
                .append(Component.text(" | ").color(TextColor.color(107, 107, 107)))
                .append(player.displayName()
                .append(Component.text(" a rejoint la partie. ")).color(TextColor.color(255, 250, 0)))
                .append(Component.text("(" + Bukkit.getOnlinePlayers().size() + "/4)").color(TextColor.color(157, 157, 157))));

        player.getInventory().clear();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.quitMessage(Component.text("☠").color(TextColor.color(170, 0, 0))
                .append(Component.text(" | ").color(TextColor.color(107, 107, 107)))
                .append(player.displayName()
                        .append(Component.text(" a quitté la partie. ")).color(TextColor.color(255, 250, 0)))
                .append(Component.text("(" + (Bukkit.getOnlinePlayers().size() - 1) + "/4)").color(TextColor.color(157, 157, 157))));
    }
}
