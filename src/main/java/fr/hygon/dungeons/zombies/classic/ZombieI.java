package fr.hygon.dungeons.zombies.classic;

import fr.hygon.dungeons.zombies.goals.ClassicZombieGoal;
import fr.hygon.dungeons.zombies.CustomZombie;

public class ZombieI extends CustomZombie {
    public ZombieI() {
        super(20, 0, 0, 5, "Lvl 1");
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ClassicZombieGoal(this, 1.2D, 0.2D, 2.1D, 2.0F, 4));
    }
}
