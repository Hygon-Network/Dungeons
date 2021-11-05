package fr.hygon.dungeons.waves;

import fr.hygon.dungeons.zombies.CustomZombie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WaveZombieProvider {
    private static final ZombieProvider wave1 = zombiesAmount -> new ArrayList<>();

    private static final HashMap<Integer, ZombieProvider> waveZombieProvider;

    static {
        waveZombieProvider = new HashMap<>();
        waveZombieProvider.put(1, wave1);
    }

    public static List<CustomZombie> getZombiesForWave(Wave wave) {
        return waveZombieProvider.get(wave.getWaveId()).getZombies(wave.getMaxZombies());
    }

    public interface ZombieProvider {
        List<CustomZombie> getZombies(int zombiesAmount);
    }
}
