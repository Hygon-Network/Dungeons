package fr.hygon.dungeons.waves;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.game.GameManager;
import fr.hygon.dungeons.game.GameStatus;
import fr.hygon.dungeons.zombies.CustomZombie;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class WaveManager {
    private static BukkitTask task;

    private static int waveId = 1;
    private static Wave wave = null;
    private static int timer = 3;

    public static void startTask() {
        wave = WaveList.getWave(waveId);

        task = new BukkitRunnable() {
            @Override
            public void run() {
                if(GameManager.getGameStatus() == GameStatus.PLAYING) {
                    timer--;
                    if(timer == 0) {
                        if(wave.hasZombiesLeft()) {
                            CustomZombie customZombie = wave.getZombie();
                            timer = 3;
                        } else {
                            GameManager.setGameStatus(GameStatus.PAUSE_TIME);
                            waveId++;
                            if(waveId == 26) {
                                // TODO end the game
                                return;
                            }
                            wave = WaveList.getWave(waveId);
                            timer = 30;
                        }
                    }
                } else if(GameManager.getGameStatus() == GameStatus.PAUSE_TIME) {
                    timer--;
                    Bukkit.broadcast(Component.text("Timer: " + timer)); //TODO
                    if (timer == 0) {
                        timer = 3;

                        GameManager.setGameStatus(GameStatus.PLAYING);
                        Bukkit.broadcast(Component.text("Nouvelle vague: " + waveId)); //TODO
                    }
                }
            }
        }.runTaskTimer(Main.getPlugin(), 0, 20);
    }

    public static void stopTask() {
        if(task != null) {
            task.cancel();
        }
    }
}
