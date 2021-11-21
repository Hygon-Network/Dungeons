package fr.hygon.dungeons.waves;

import java.util.HashMap;
import java.util.Map;

public class WaveList {
    private static final HashMap<Integer, Wave> waves = new HashMap<>();

    public static void initWaves() {
        waves.put(1, new Wave(10));
        waves.put(2, new Wave(15));
        waves.put(3, new Wave(20));
        waves.put(4, new Wave(25));
        waves.put(5, new Wave(27));
        waves.put(6, new Wave(30));
        waves.put(7, new Wave(37));

        for(Map.Entry<Integer, Wave> waveEntry : waves.entrySet()) {
            waveEntry.getValue().setWaveIdAndUpdateZombies(waveEntry.getKey());
        }
    }

    public static Wave getWave(int waveId) {
        return waves.get(waveId);
    }
}
