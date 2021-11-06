package fr.hygon.dungeons.zombies;

public class ZombieI extends CustomZombie {
    public ZombieI() {
        super(20, 0, 0, 1, "Lvl 1");
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ClassicZombieGoal(this, 1.1D, 2.0D, 2.0F));
    }
}
