package fr.hygon.dungeons.game;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.events.gui.DifficultySelectorGUI;
import fr.hygon.dungeons.utils.Difficulty;
import fr.hygon.dungeons.waves.WaveList;
import fr.hygon.dungeons.waves.WaveManager;
import fr.hygon.yokura.YokuraAPI;
import fr.hygon.yokura.servers.Status;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
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
                switch (timer) {
                    case 20, 10, 5, 4, 3, 2, 1 -> {
                        Bukkit.broadcast(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                                .append(Component.text("La partie commence dans ").color(TextColor.color(255, 255, 75))
                                        .append(Component.text(timer).color(TextColor.color(250, 65, 65)))
                                        .append(Component.text(" secondes.").color(TextColor.color(255, 255, 75)))));

                        for(Player players : Bukkit.getOnlinePlayers()) {
                            final Title.Times times = Title.Times.of(Duration.ofMillis(100), Duration.ofMillis(1500), Duration.ofMillis(100));
                            final Title title = Title.title(Component.text(""), ((Component.text(timer).color(TextColor.color(255,255, 75)))), times);
                            players.showTitle(title);
                            players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1.0F, 1F);
                        }
                    }
                    case 0 -> {
                        YokuraAPI.setStatus(Status.IN_GAME);
                        gameStatus = GameStatus.PLAYING;

                        gameDifficulty = DifficultySelectorGUI.getDifficulty();

                        stopTask();
                        startGame();
                        GameManager.setGameStatus(GameStatus.PLAYING);
                        for(Player players : Bukkit.getOnlinePlayers()) {

                            final Title.Times times = Title.Times.of(Duration.ofMillis(1000), Duration.ofMillis(5000), Duration.ofMillis(1000));
                            final Title title = Title.title((Component.text("VAGUE 1").color(TextColor.color(200, 20, 20))
                                    .decoration(TextDecoration.BOLD, true)), (Component.text("Difficulté: ").color(TextColor.color(255, 255, 75))
                                    .append(gameDifficulty.getName())), times);

                            players.showTitle(title);
                            players.playSound(players.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0F, 0F);

                        }
                    }
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
