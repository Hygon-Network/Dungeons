package fr.hygon.dungeons;

import fr.hygon.dungeons.events.ZombieUtils;
import fr.hygon.dungeons.events.gui.DifficultySelectorGUI;
import fr.hygon.dungeons.events.PlayerJoinLeaveEvent;
import fr.hygon.dungeons.events.gui.ShopGUI;
import fr.hygon.dungeons.game.GameManager;
import fr.hygon.dungeons.utils.PlayerUtils;
import fr.hygon.dungeons.waves.WaveManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        registerEvents();
        startTasks();
    }

    @Override
    public void onDisable() {
        stopTasks();
    }

    private void registerEvents() {
       getServer().getPluginManager().registerEvents(new PlayerJoinLeaveEvent(), this);
       getServer().getPluginManager().registerEvents(new PlayerUtils(), this);
       getServer().getPluginManager().registerEvents(new WaveManager(), this);
       getServer().getPluginManager().registerEvents(new ZombieUtils(), this);

       /* GUIs */
        getServer().getPluginManager().registerEvents(new DifficultySelectorGUI(), this);
        getServer().getPluginManager().registerEvents(new ShopGUI(), this);
    }

    private void startTasks() {
        GameManager.startTask();
    }

    private void stopTasks() {
        GameManager.stopTask();
        WaveManager.stopTask();
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
