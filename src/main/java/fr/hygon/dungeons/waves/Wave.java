package fr.hygon.dungeons.waves;

import fr.hygon.dungeons.game.GameManager;
import fr.hygon.dungeons.zombies.CustomZombie;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Wave {
    private int waveId;
    private final int maxZombies;
    private List<Class<? extends CustomZombie>> zombies;

    public Wave(int maxZombies) {
        maxZombies = (int) Math.ceil(maxZombies + maxZombies / 100.0 * (double) GameManager.getGameDifficulty().getZombiesMultiplier());
        this.maxZombies = maxZombies;
    }

    public int getWaveId() {
        return waveId;
    }

    public int getMaxZombies() {
        return maxZombies;
    }

    public boolean hasZombiesLeft() {
        return !zombies.isEmpty();
    }

    public CustomZombie getZombie() {
        if(zombies.isEmpty()) {
            throw new RuntimeException("The list of zombies for the wave " + waveId + " is empty.");
        }

        try {
            return (CustomZombie) zombies.remove(0).getConstructors()[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setWaveIdAndUpdateZombies(int waveId) {
        this.waveId = waveId;

        zombies = WaveZombieProvider.getZombiesForWave(this);
    }
}
