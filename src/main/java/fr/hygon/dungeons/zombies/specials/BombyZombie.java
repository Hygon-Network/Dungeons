package fr.hygon.dungeons.zombies.specials;

import fr.hygon.dungeons.zombies.CustomZombie;
import fr.hygon.dungeons.zombies.items.Bomb;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BombyZombie extends CustomZombie {
    public BombyZombie() {
        super(12, 1, 20, 10, "Bomby");

        if(getBukkitLivingEntity().getEquipment() == null) {
            throw new RuntimeException("Couldn't get the equipments.");
        }

        setSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.TNT, 1)), false);
        setSlot(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_CHESTPLATE, 1)), false);
        setSlot(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_LEGGINGS, 1)), false);
        setSlot(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_BOOTS, 1)), false);

        setSlot(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.FLINT_AND_STEEL, 1)), false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BombyZombieGoal(this, 1.5, 5.0));
    }

    private static class BombyZombieGoal extends Goal {
        private final BombyZombie bombyZombie;
        private final double speed;
        private final double fearDistance;

        public Player attackedPlayer = null;
        private int ticksSinceLastSearch = 7 * 20;

        private Path path;
        private Status pathStatus;

        private int ticksSinceLastBomb = 0;

        public BombyZombieGoal(BombyZombie bombyZombie, double speed, double fearDistance) {
            this.bombyZombie = bombyZombie;
            this.speed = speed;
            this.fearDistance = fearDistance;
        }

        @Override
        public boolean canUse() {
            ticksSinceLastSearch++;
            if(attackedPlayer == null) {
                updateFollowedPlayer();
                ticksSinceLastSearch = 0;
            } else if (ticksSinceLastSearch == 7 * 20) {
                ticksSinceLastSearch = 0;
                updateFollowedPlayer();
            }

            calculatePath(false);

            if(bombyZombie.getNavigation().isDone()) {
                calculatePath(true);
            }

            return attackedPlayer != null;
        }

        private void calculatePath(boolean force) {
            double distance = bombyZombie.getBukkitEntity().getLocation().distance(attackedPlayer.getLocation());
            if(attackedPlayer != null) {
                if(distance < fearDistance) {
                    if(pathStatus != Status.MOVING_AWAY || force) {
                        Vec3 vec3 = DefaultRandomPos.getPosAway(this.bombyZombie, 10, 10,
                                ((CraftPlayer) attackedPlayer).getHandle().position());
                        if (vec3 != null) {
                            pathStatus = Status.MOVING_AWAY;
                            path = bombyZombie.getNavigation().createPath(vec3.x, vec3.y, vec3.z, 0);
                        }
                    }
                } else if(distance > fearDistance && distance < fearDistance + 1) {
                    if(pathStatus != Status.STOPPED || force) {
                        pathStatus = Status.STOPPED;
                        path = null;
                    }
                } else if (distance > fearDistance + 1) {
                    if(pathStatus != Status.MOVING_CLOSER || force) {
                        pathStatus = Status.MOVING_CLOSER;
                        path = bombyZombie.getNavigation().createPath(((CraftPlayer) attackedPlayer).getHandle(), 1);
                    }
                }
            }
        }

        @Override
        public void tick() {
            ticksSinceLastBomb++;
            if(path == null) {
                bombyZombie.getNavigation().stop();
            } else {
                bombyZombie.getNavigation().moveTo(path, speed);
            }

            if(ticksSinceLastBomb == 40) {
                ticksSinceLastBomb = 0;

                bombyZombie.level.addEntity(new Bomb(bombyZombie, 4, 8, calculateVelocity(1)),
                        CreatureSpawnEvent.SpawnReason.CUSTOM);
            }
        }

        private void updateFollowedPlayer() {
            Player shortestDistancePlayer = null;
            double shortestDistance = Double.MAX_VALUE;

            for(Player players : Bukkit.getOnlinePlayers()) {
                if(bombyZombie.getBukkitEntity().getLocation().distance(players.getLocation()) < shortestDistance) {
                    shortestDistance = bombyZombie.getBukkitEntity().getLocation().distance(players.getLocation());
                    shortestDistancePlayer = players;
                }
            }

            attackedPlayer = shortestDistancePlayer;
        }

        public Vector calculateVelocity(int heightGain) {
            Vector from = new Vector(bombyZombie.getBukkitEntity().getLocation().getX(), bombyZombie.getBukkitEntity().getLocation().getY(),
                    bombyZombie.getBukkitEntity().getLocation().getZ());
            Vector to = new Vector(attackedPlayer.getLocation().getX(), attackedPlayer.getLocation().getY(), attackedPlayer.getLocation().getZ());

            // approximate gravity of an armor stand
            double gravity = 0.260;

            // Block locations
            int endGain = to.getBlockY() - from.getBlockY();
            double horizontalDist = Math.sqrt(distanceSquared(from, to));

            // Height gain

            double maxGain = Math.max(heightGain, (endGain + heightGain));

            // Solve quadratic equation for velocity
            double a = -horizontalDist * horizontalDist / (4 * maxGain);
            double b = horizontalDist;
            double c = -endGain;

            double slope = -b / (2 * a) - Math.sqrt(b * b - 4 * a * c) / (2 * a);

            // Vertical velocity
            double vy = Math.sqrt(maxGain * gravity);

            // Horizontal velocity
            double vh = vy / slope;

            // Calculate horizontal direction
            int dx = to.getBlockX() - from.getBlockX();
            int dz = to.getBlockZ() - from.getBlockZ();
            double mag = Math.sqrt(dx * dx + dz * dz);
            double directionX = dx / mag;
            double directionZ = dz / mag;

            // Horizontal velocity components
            double vx = vh * directionX;
            double vz = vh * directionZ;

            return new Vector(vx, vy, vz);
        }

        private double distanceSquared(Vector from, Vector to) {
            double dx = to.getBlockX() - from.getBlockX();
            double dz = to.getBlockZ() - from.getBlockZ();

            return dx * dx + dz * dz;
        }

        private enum Status {
            MOVING_AWAY, MOVING_CLOSER, STOPPED
        }
    }
}
