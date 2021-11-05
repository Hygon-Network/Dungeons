package fr.hygon.dungeons.waves;

import java.util.HashMap;
import java.util.Map;

public class WaveList {
    private static final HashMap<Integer, Wave> waves = new HashMap<>();

    public static void initWaves() {
        waves.put(1, new Wave(5));

        for(Map.Entry<Integer, Wave> waveEntry : waves.entrySet()) {
            waveEntry.getValue().setWaveIdAndUpdateZombies(waveEntry.getKey());
        }
    }

    public static Wave getWave(int waveId) {
        return waves.get(waveId);
    }
}
