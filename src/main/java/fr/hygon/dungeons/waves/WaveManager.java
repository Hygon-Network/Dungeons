package fr.hygon.dungeons.waves;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.game.GameManager;
import fr.hygon.dungeons.game.GameStatus;
import fr.hygon.dungeons.zombies.CustomZombie;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import java.time.Duration;

public class WaveManager implements Listener {
    private static BukkitTask task;

    private static int waveId = 1;
    private static Wave wave = null;
    private static int timer = 3;

    private static int aliveZombies = 0;

    private static final ServerLevel nmsWorld = ((CraftWorld) Bukkit.getWorld("world")).getHandle();

    public static void startTask() {
        wave = WaveList.getWave(waveId);

        task = new BukkitRunnable() {
            @Override
            public void run() {
                if(GameManager.getGameStatus() == GameStatus.PLAYING) {
                    timer--;
                    if(timer == 0) {
                        if(aliveZombies != 0) { // TODO make it better, it's a bit redundant
                            timer = 3;
                        }

                        if(wave.hasZombiesLeft()) {
                            CustomZombie customZombie = wave.getZombie();
                            nmsWorld.addEntity(customZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
                            aliveZombies++;
                            timer = 3;
                        } else if(aliveZombies == 0) {
                            GameManager.setGameStatus(GameStatus.PAUSE_TIME);
                            waveId++;
                            if(waveId == 26) {
                                // TODO end the game
                                return;
                            }
                            wave = WaveList.getWave(waveId);
                            timer = 31; // put 31 so players can see it
                        }
                    }
                } else if(GameManager.getGameStatus() == GameStatus.PAUSE_TIME) {
                    timer--;
                    switch (timer) {
                        case 30, 15, 3, 2, 1 -> {
                            Bukkit.broadcast(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                                    .append(Component.text("La partie reprend dans ").color(TextColor.color(255, 255, 75))
                                            .append(Component.text(timer).color(TextColor.color(250, 65, 65)))
                                                    .append(Component.text("s.").color(TextColor.color(255, 255, 75)))));

                            for(Player players : Bukkit.getOnlinePlayers()) {
                                final Title.Times times = Title.Times.of(Duration.ofMillis(100), Duration.ofMillis(1500), Duration.ofMillis(100));
                                final Title title = Title.title(Component.text(""), ((Component.text(timer).color(TextColor.color(255,255, 75)))), times);
                                players.showTitle(title);
                                players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1.0F, 0F);
                            }
                        }
                        case 0 -> {
                            timer = 3;
                            GameManager.setGameStatus(GameStatus.PLAYING);
                            for(Player players : Bukkit.getOnlinePlayers()) {
                                switch (waveId) {
                                    case 10, 20, 25 -> {
                                        final Title.Times times = Title.Times.of(Duration.ofMillis(1000), Duration.ofMillis(5000), Duration.ofMillis(1000));
                                        final Title title = Title.title(Component.text("☠ BOSS ☠").color(TextColor.color(200, 20, 20)), ((Component.text("VAGUE " + waveId).color(TextColor.color(240, 20, 20))
                                                .decoration(TextDecoration.BOLD, true))), times);

                                        players.showTitle(title);
                                        players.playSound(players.getLocation(), Sound.ENTITY_WITHER_DEATH, 1.0F, 0F);
                                    }
                                    default -> {
                                        final Title.Times times = Title.Times.of(Duration.ofMillis(1000), Duration.ofMillis(5000), Duration.ofMillis(1000));
                                        final Title title = Title.title((Component.text("VAGUE " + waveId).color(TextColor.color(200, 20, 20))
                                                .decoration(TextDecoration.BOLD, true)), (Component.text("")), times);

                                        players.showTitle(title);
                                        players.playSound(players.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0F, 0F);
                                    }
                                }

                            }
                        }
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

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if(event.getEntity() instanceof Zombie) {
            aliveZombies--;
        }
    }
}
