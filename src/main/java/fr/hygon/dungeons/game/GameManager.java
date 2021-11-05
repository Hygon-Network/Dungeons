package fr.hygon.dungeons.game;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.events.gui.DifficultySelectorGUI;
import fr.hygon.dungeons.utils.Difficulty;
import fr.hygon.dungeons.waves.WaveList;
import fr.hygon.dungeons.waves.WaveManager;
import fr.hygon.yokura.YokuraAPI;
import fr.hygon.yokura.servers.Status;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import java.util.Map;

public class GameManager {
    private static GameStatus gameStatus = GameStatus.WAITING;
    private static Difficulty gameDifficulty = Difficulty.NORMAL;

    private static BukkitTask task = null;
    public static int timer = 0;
    private static int oldPlayerCount = 0;

    private static final Map<Integer, Integer> timerForPlayerCount = Map.ofEntries(
            Map.entry(1, Integer.MAX_VALUE),
            Map.entry(2, 10), //TODO put to 60
            Map.entry(3, 30),
            Map.entry(4, 10)
    );

    public static void startTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if(gameStatus == GameStatus.WAITING) {
                    if(Bukkit.getOnlinePlayers().size() > 1) {
                        timer = timerForPlayerCount.get(Bukkit.getOnlinePlayers().size());
                        gameStatus = GameStatus.STARTING;
                    }
                } else if(gameStatus == GameStatus.STARTING) {
                    if(Bukkit.getOnlinePlayers().size() != oldPlayerCount) {
                        timer = timerForPlayerCount.get(Bukkit.getOnlinePlayers().size());
                    }
                }

                if(Bukkit.getOnlinePlayers().size() < 2) {
                    gameStatus = GameStatus.WAITING;
                    return;
                }

                timer--;
                Bukkit.broadcast(Component.text("timer: " + timer)); //TODO

                if(timer == 0) {
                    Bukkit.broadcast(Component.text("Ça commence!")); //TODO
                    YokuraAPI.setStatus(Status.IN_GAME);
                    gameStatus = GameStatus.PLAYING;

                    gameDifficulty = DifficultySelectorGUI.getDifficulty();
                    Bukkit.broadcast(Component.text("Difficulté: ").append(gameDifficulty.getName())); //TODO

                    stopTask();
                    startGame();
                }
                oldPlayerCount = Bukkit.getOnlinePlayers().size();
            }
        }.runTaskTimer(Main.getPlugin(), 0, 20);
    }

    public static void stopTask() {
        if(task != null) {
            task.cancel();
            task = null;
        }
    }

    private static void startGame() {
        WaveList.initWaves();
        int locationArray = 0;
        Location[] locationSpawnArray = new Location[4];
        locationSpawnArray[0] = new Location(Bukkit.getWorld("world"), 0.5, 6, 1.5, 180, 0);
        locationSpawnArray[1] = new Location(Bukkit.getWorld("world"), -1.5, 6, -0.5, -90, 0);
        locationSpawnArray[2] = new Location(Bukkit.getWorld("world"), 0.5, 6, -2.5, 0, 0);
        locationSpawnArray[3] = new Location(Bukkit.getWorld("world"), 2.5, 6, -0.5, 90, 0);

        for(Player players : Bukkit.getOnlinePlayers()) {
            //TODO players.getInventory().clear();

            //TODO players.teleport(locationSpawnArray[locationArray]);
            locationArray++;
        }

        WaveManager.startTask();
    }

    public static GameStatus getGameStatus() {
        return gameStatus;
    }

    public static Difficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public static void setGameStatus(GameStatus gameStatus) {
        GameManager.gameStatus = gameStatus;
    }
}
