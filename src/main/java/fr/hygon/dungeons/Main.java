package fr.hygon.dungeons;

import fr.hygon.dungeons.events.PlayerJoinLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    private void registerEvents() {
       getServer().getPluginManager().registerEvents(new PlayerJoinLeaveEvent(), this);
    }
}
