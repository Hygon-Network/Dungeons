package fr.hygon.dungeons.waves;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.game.DeathManager;
import fr.hygon.dungeons.game.GameManager;
import fr.hygon.dungeons.game.GameStatus;
import fr.hygon.dungeons.utils.ItemList;
import fr.hygon.dungeons.zombies.CustomZombie;
import fr.hygon.dungeons.zombies.classic.ZombieI;
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
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import java.time.Duration;
import java.util.ArrayList;

public class WaveManager implements Listener {
    private static BukkitTask task;

    private static int waveId = 1;
    private static Wave wave = null;
    private static int timer = 6;

    private static int aliveZombies = 0;

    private static final ServerLevel nmsWorld = ((CraftWorld) Bukkit.getWorld("world")).getHandle();

    private static final ArrayList<Player> playersSkipping = new ArrayList<>();

    public static void startTask() {
        wave = WaveList.getWave(waveId);

        task = new BukkitRunnable() {
            @Override
            public void run() {
                if(GameManager.getGameStatus() == GameStatus.PLAYING) {
                    if(!wave.hasZombiesLeft() && aliveZombies == 0) {
                        DeathManager.respawnAllPlayers();
                        GameManager.setGameStatus(GameStatus.PAUSE_TIME);
                        waveId++;
                        if(waveId == 26) {
                            // TODO end the game
                            return;
                        }
                        wave = WaveList.getWave(waveId);
                        timer = 30;
                        Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().setItem(8, ItemList.RADIO_ON.getItem()));
                    } else if(wave.hasZombiesLeft()) {
                        timer--;
                        if(timer == 0) {
                            CustomZombie customZombie = wave.getZombie();
                            if(customZombie == null) customZombie = new ZombieI(); // If the server can't create a zombie, instead of doing nothing we spawn a level I zombie.
                            nmsWorld.addEntity(customZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
                            aliveZombies++;
                            timer = 3;
                        }
                    }
                } else if(GameManager.getGameStatus() == GameStatus.PAUSE_TIME) {
                    Bukkit.getOnlinePlayers().forEach(players -> players.sendActionBar(Component.text("Prochaine Vague").color(TextColor.color(250, 65, 65))
                            .append(Component.text(" » ").color(TextColor.color(120,120,120))
                            .append(Component.text(timer + "s").color(TextColor.color(255, 255, 75))))));

                    switch (timer) {
                        case 30 -> {
                            Bukkit.broadcast(Component.text("☠").color(TextColor.color(170, 0, 0))
                                    .append(Component.text(" | ").color(TextColor.color(107, 107, 107)))
                                    .append(Component.text("Vague Dégagée! La partie reprend dans ").color(TextColor.color(255, 255, 75))
                                            .append(Component.text(timer).color(TextColor.color(250, 65, 65)))
                                                    .append(Component.text("s.").color(TextColor.color(255, 255, 75)))));

                            //TODO do a recap of what the player has earned during the wave

                            for(Player players : Bukkit.getOnlinePlayers()) {
                                final Title.Times times = Title.Times.of(Duration.ofMillis(100), Duration.ofMillis(1500), Duration.ofMillis(100));
                                final Title title = Title.title(Component.text(""), ((Component.text("Vague Dégagée!").color(TextColor.color(255,255, 75)))), times);
                                players.showTitle(title);
                                players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.5F);
                            }
                        }
                        case 15, 3, 2, 1 -> {
                            Bukkit.broadcast(Component.text("☠").color(TextColor.color(170, 0, 0))
                                    .append(Component.text(" | ").color(TextColor.color(107, 107, 107)))
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
                        case 0 -> nextWave();
                    }
                    timer--;
                }
            }
        }.runTaskTimer(Main.getPlugin(), 0, 20);
    }

    private static void nextWave() {
        playersSkipping.clear();

        timer = 6;
        GameManager.setGameStatus(GameStatus.PLAYING);

        Bukkit.broadcast(Component.text("☠").color(TextColor.color(170, 0, 0))
            .append(Component.text(" | ").color(TextColor.color(107, 107, 107)))
            .append(Component.text("Vague" + waveId).color(TextColor.color(200, 20, 20))
                .append(Component.text(" » ").color(TextColor.color(NamedTextColor.GRAY)))
                .append(Component.text(wave.getMaxZombies() + " Zombies").color(TextColor.color(255, 255, 75)))));

        for(Player players : Bukkit.getOnlinePlayers()) {
            players.getInventory().setItem(8, ItemList.RADIO_OFF.getItem());
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

    public static void registerSkippingPlayer(Player player) {
        playersSkipping.add(player);
        if(Bukkit.getOnlinePlayers().size() / 2 >= playersSkipping.size()) {
            nextWave();
        }

        Bukkit.broadcast(player.displayName().append(Component.text(" veux passer à la prochaine vague."))); // TODO
    }

    public static int getPlayersSkippingAmount() {
        return playersSkipping.size();
    }

    public static void stopTask() {
        if(task != null) {
            task.cancel();
        }
    }

    public static int getWave() {
        return waveId;
    }

    public static void incrementAliveZombie() {
        aliveZombies++;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if(event.getEntity() instanceof Zombie || event.getEntity() instanceof Wolf) {
            aliveZombies--;
        }
    }
}
