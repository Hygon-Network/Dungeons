package fr.hygon.dungeons.waves;

import fr.hygon.dungeons.game.GameManager;
import fr.hygon.dungeons.zombies.CustomZombie;
import java.util.List;

public class Wave {
    private int waveId;
    private int maxZombies;
    private List<CustomZombie> zombies;

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

        return zombies.remove(0);
    }

    public void setWaveIdAndUpdateZombies(int waveId) {
        this.waveId = waveId;

        zombies = WaveZombieProvider.getZombiesForWave(this);
    }
}
