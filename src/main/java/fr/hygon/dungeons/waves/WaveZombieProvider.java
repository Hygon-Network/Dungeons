package fr.hygon.dungeons.waves;

import fr.hygon.dungeons.zombies.CustomZombie;
import fr.hygon.dungeons.zombies.ZombieI;
import fr.hygon.dungeons.zombies.ZombieII;
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
            zombies.add(ThreadLocalRandom.current().nextInt(0, 100) <= 90 ? new ZombieII() : new ZombieI());
        }
        return zombies;
    };

    private static final HashMap<Integer, ZombieProvider> waveZombieProvider;

    static {
        waveZombieProvider = new HashMap<>();
        waveZombieProvider.put(1, wave1);
        waveZombieProvider.put(2, wave2);
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
