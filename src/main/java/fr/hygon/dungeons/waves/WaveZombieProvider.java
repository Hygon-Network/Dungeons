package fr.hygon.dungeons.waves;

import fr.hygon.dungeons.game.GameManager;
import fr.hygon.dungeons.zombies.CustomZombie;
import fr.hygon.dungeons.zombies.classic.ZombieI;
import fr.hygon.dungeons.zombies.classic.ZombieII;
import fr.hygon.dungeons.zombies.classic.ZombieIII;
import fr.hygon.dungeons.zombies.classic.ZombieIV;
import fr.hygon.dungeons.zombies.specials.BombyZombie;
import fr.hygon.dungeons.zombies.specials.SpeedyZombie;
import fr.hygon.dungeons.zombies.specials.WolfZombie.WolfZombie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WaveZombieProvider {
    private static final ZombieProvider wave1 = zombiesAmount -> {
        ArrayList<Class<? extends CustomZombie>> zombies = new ArrayList<>();
        for (int i = 0; i < zombiesAmount; i++) {
            zombies.add(ZombieI.class);
        }
        return zombies;
    };

    private static final ZombieProvider wave2 = zombiesAmount -> {
        ArrayList<Class<? extends CustomZombie>> zombies = new ArrayList<>();
        for (int i = 0; i < zombiesAmount; i++) {
            zombies.add(getPercentage() <= 90 ? ZombieII.class : ZombieI.class);
        }
        return zombies;
    };

    private static final ZombieProvider wave3 = zombiesAmount -> {
        ArrayList<Class<? extends CustomZombie>> zombies = new ArrayList<>();
        for (int i = 0; i < zombiesAmount; i++) {
            switch (GameManager.getGameDifficulty()) {
                case NORMAL -> zombies.add(getPercentage() <= 60 ? ZombieI.class : ZombieII.class);
                case HARD -> zombies.add(getPercentage() <= 50 ? ZombieI.class : ZombieII.class);
                case INSANE -> zombies.add(getPercentage() <= 45 ? ZombieI.class : ZombieII.class);
            }
            zombies.add(ThreadLocalRandom.current().nextInt(0, 100) <= 90 ? ZombieII.class : ZombieI.class);
        }
        return zombies;
    };

    private static final ZombieProvider wave4 = zombiesAmount -> {
        ArrayList<Class<? extends CustomZombie>> zombies = new ArrayList<>();
        for (int i = 0; i < zombiesAmount; i++) {
            zombies.add(getPercentage() <= 25 ? ZombieI.class : ZombieII.class);
        }
        return zombies;
    };

    private static final ZombieProvider wave5 = zombiesAmount -> {
        ArrayList<Class<? extends CustomZombie>> zombies = new ArrayList<>();

        zombiesAmount -= 2;
        zombies.add(SpeedyZombie.class);
        zombies.add(BombyZombie.class);

        for (int i = 0; i < zombiesAmount; i++) {
            int percentage = getPercentage();

            if(percentage <= 10) {
                zombies.add(ZombieI.class);
            } else {
               switch (GameManager.getGameDifficulty()) {
                    case NORMAL -> zombies.add(percentage <= 60 ? ZombieII.class : ZombieIII.class);
                    case HARD -> zombies.add(percentage <= 50 ? ZombieII.class : ZombieIII.class);
                    case INSANE -> zombies.add(percentage <= 40 ? ZombieII.class : ZombieIII.class);
                }
            }
        }

        return zombies;
    };

    private static final ZombieProvider wave6 = zombiesAmount -> {
        ArrayList<Class<? extends CustomZombie>> zombies = new ArrayList<>();

        zombiesAmount -= 4;
        zombies.add(SpeedyZombie.class);
        zombies.add(SpeedyZombie.class);
        zombies.add(BombyZombie.class);
        zombies.add(BombyZombie.class);

        for (int i = 0; i < zombiesAmount; i++) {
            int percentage = getPercentage();

            if(percentage <= 5) {
                zombies.add(ZombieI.class);
            } else {
                switch (GameManager.getGameDifficulty()) {
                    case NORMAL -> zombies.add(percentage <= 40 ? ZombieII.class : ZombieIII.class);
                    case HARD, INSANE -> zombies.add(percentage <= 30 ? ZombieII.class : ZombieIII.class);
                }
            }
        }

        return zombies;
    };

    private static final ZombieProvider wave7 = zombiesAmount -> {
        ArrayList<Class<? extends CustomZombie>> zombies = new ArrayList<>();

        zombiesAmount -= 7;
        zombies.add(SpeedyZombie.class);
        zombies.add(SpeedyZombie.class);
        zombies.add(SpeedyZombie.class);
        zombies.add(BombyZombie.class);
        zombies.add(BombyZombie.class);
        zombies.add(WolfZombie.class);
        zombies.add(WolfZombie.class);

        for (int i = 0; i < zombiesAmount; i++) {
            int percentage = getPercentage();

            if(percentage <= 20) {
                zombies.add(ZombieIV.class);
            } else {
                switch (GameManager.getGameDifficulty()) {
                    case NORMAL -> zombies.add(percentage <= 45 ? ZombieII.class : ZombieIII.class);
                    case HARD, INSANE -> zombies.add(percentage <= 35 ? ZombieII.class : ZombieIII.class);
                }
            }
        }

        return zombies;
    };

    private static final ZombieProvider wave8 = zombiesAmount -> {
        ArrayList<Class<? extends CustomZombie>> zombies = new ArrayList<>();

        zombiesAmount -= 8;
        zombies.add(SpeedyZombie.class);
        zombies.add(SpeedyZombie.class);
        zombies.add(SpeedyZombie.class);
        zombies.add(BombyZombie.class);
        zombies.add(BombyZombie.class);
        zombies.add(BombyZombie.class);
        zombies.add(WolfZombie.class);
        zombies.add(WolfZombie.class);

        for (int i = 0; i < zombiesAmount; i++) {
            int percentage = getPercentage();

            if(percentage <= 10) {
                zombies.add(ZombieII.class);
            } else if(percentage <= 60) {
                zombies.add(ZombieIII.class);
            } else {
                zombies.add(ZombieIV.class);
            }
        }

        return zombies;
    };

    private static final ZombieProvider wave9 = zombiesAmount -> {
        ArrayList<Class<? extends CustomZombie>> zombies = new ArrayList<>();

        zombiesAmount -= 11;
        zombies.add(SpeedyZombie.class);
        zombies.add(SpeedyZombie.class);
        zombies.add(SpeedyZombie.class);
        zombies.add(SpeedyZombie.class);

        zombies.add(BombyZombie.class);
        zombies.add(BombyZombie.class);
        zombies.add(BombyZombie.class);
        zombies.add(BombyZombie.class);

        zombies.add(WolfZombie.class);
        zombies.add(WolfZombie.class);
        zombies.add(WolfZombie.class);

        for (int i = 0; i < zombiesAmount; i++) {
            int percentage = getPercentage();

            if(percentage <= 10) {
                zombies.add(ZombieII.class);
            } else if(percentage <= 40) {
                zombies.add(ZombieIII.class);
            } else {
                zombies.add(ZombieIV.class);
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
        waveZombieProvider.put(6, wave6);
        waveZombieProvider.put(7, wave7);
        waveZombieProvider.put(8, wave8);
    }

    public static List<Class<? extends CustomZombie>> getZombiesForWave(Wave wave) {
        List<Class<? extends CustomZombie>> zombies = waveZombieProvider.get(wave.getWaveId()).getZombies(wave.getMaxZombies());
        Collections.shuffle(zombies);

        return zombies;
    }

    public interface ZombieProvider {
        List<Class<? extends CustomZombie>> getZombies(int zombiesAmount);
    }
}
