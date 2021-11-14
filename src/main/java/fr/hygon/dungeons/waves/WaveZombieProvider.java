package fr.hygon.dungeons.waves;

import fr.hygon.dungeons.game.GameManager;
import fr.hygon.dungeons.zombies.CustomZombie;
import fr.hygon.dungeons.zombies.classic.ZombieI;
import fr.hygon.dungeons.zombies.classic.ZombieII;
import fr.hygon.dungeons.zombies.classic.ZombieIII;
import fr.hygon.dungeons.zombies.specials.BombyZombie;
import fr.hygon.dungeons.zombies.specials.SpeedyZombie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WaveZombieProvider {
    private static final ZombieProvider wave1 = zombiesAmount -> {
        ArrayList<CustomZombie> zombies = new ArrayList<>();
        for (int i = 0; i < zombiesAmount; i++) {
            zombies.add(new ZombieI());
        }
        return zombies;
    };

    private static final ZombieProvider wave2 = zombiesAmount -> {
        ArrayList<CustomZombie> zombies = new ArrayList<>();
        for (int i = 0; i < zombiesAmount; i++) {
            zombies.add(getPercentage() <= 90 ? new ZombieII() : new ZombieI());
        }
        return zombies;
    };

    private static final ZombieProvider wave3 = zombiesAmount -> {
        ArrayList<CustomZombie> zombies = new ArrayList<>();
        for (int i = 0; i < zombiesAmount; i++) {
            switch (GameManager.getGameDifficulty()) {
                case NORMAL -> zombies.add(getPercentage() <= 60 ? new ZombieI() : new ZombieII());
                case HARD -> zombies.add(getPercentage() <= 50 ? new ZombieI() : new ZombieII());
                case INSANE -> zombies.add(getPercentage() <= 45 ? new ZombieI() : new ZombieII());
            }
            zombies.add(ThreadLocalRandom.current().nextInt(0, 100) <= 90 ? new ZombieII() : new ZombieI());
        }
        return zombies;
    };

    private static final ZombieProvider wave4 = zombiesAmount -> {
        ArrayList<CustomZombie> zombies = new ArrayList<>();
        for (int i = 0; i < zombiesAmount; i++) {
            zombies.add(getPercentage() <= 25 ? new ZombieI() : new ZombieII());
        }
        return zombies;
    };

    private static final ZombieProvider wave5 = zombiesAmount -> {
        ArrayList<CustomZombie> zombies = new ArrayList<>();

        zombiesAmount -= 2;
        zombies.add(new SpeedyZombie());
        zombies.add(new BombyZombie());

        for (int i = 0; i < zombiesAmount; i++) {
            int percentage = getPercentage();

            if(percentage <= 10) {
                zombies.add(new ZombieI());
            } else {
               switch (GameManager.getGameDifficulty()) {
                    case NORMAL -> zombies.add(percentage <= 60 ? new ZombieII() : new ZombieIII());
                    case HARD -> zombies.add(percentage <= 50 ? new ZombieII() : new ZombieIII());
                    case INSANE -> zombies.add(percentage <= 40 ? new ZombieII() : new ZombieIII());
                }
            }
        }

        return zombies;
    };

    private static int getPercentage() {
        return ThreadLocalRandom.current().nextInt(0, 100);
    }

    private static final HashMap<Integer, ZombieProvider> waveZombieProvider;

    static {
        waveZombieProvider = new HashMap<>();
        waveZombieProvider.put(1, wave1);
        waveZombieProvider.put(2, wave2);
        waveZombieProvider.put(3, wave3);
        waveZombieProvider.put(4, wave4);
        waveZombieProvider.put(5, wave5);
    }

    public static List<CustomZombie> getZombiesForWave(Wave wave) {
        List<CustomZombie> zombies = waveZombieProvider.get(wave.getWaveId()).getZombies(wave.getMaxZombies());
        Collections.shuffle(zombies);

        return zombies;
    }

    public interface ZombieProvider {
        List<CustomZombie> getZombies(int zombiesAmount);
    }
}
