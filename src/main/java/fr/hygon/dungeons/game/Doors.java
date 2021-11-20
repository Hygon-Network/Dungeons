package fr.hygon.dungeons.game;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Doors {
    private static final World world = Bukkit.getWorld("world");
    private static final ServerLevel nmsWorld = ((CraftWorld) world).getHandle();

    private final Component name;
    private final Location doorPoint1;
    private final Location doorPoint2;
    private final double price;
    private boolean isOpened;
    private final List<Location> spawnLocations;

    private final Runnable doorOpen;
    private final Location armorStand1Loc;
    private final Location armorStand2Loc;
    private ArmorStand armorStand1;
    private ArmorStand armorStand2;

    private Doors(Component name, Location doorPoint1, Location doorPoint2, double price, List<Location> spawnLocations,
                  Runnable doorOpen, Location armorStand1Loc, Location armorStand2Loc) {
        this(name, doorPoint1, doorPoint2, price, false, spawnLocations, doorOpen, armorStand1Loc, armorStand2Loc);
    }

    private Doors(Component name, Location doorPoint1, Location doorPoint2, double price, boolean isOpened, List<Location> spawnLocations,
                  Runnable doorOpen, Location armorStand1Loc, Location armorStand2Loc) {
        this.name = name;
        this.doorPoint1 = doorPoint1;
        this.doorPoint2 = doorPoint2;
        this.price = price;
        this.isOpened = isOpened;
        this.spawnLocations = spawnLocations;
        this.doorOpen = doorOpen;
        this.armorStand1Loc = armorStand1Loc;
        this.armorStand2Loc = armorStand2Loc;
    }

    public Component getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public Location getRandomSpawnLocation() {
        return spawnLocations.get(ThreadLocalRandom.current().nextInt(0, spawnLocations.size()));
    }
    
    public void openDoor(Player player) {
        if(isOpened) return;
        if(PlayerUtils.getCoins(player) < getPrice()) {
            player.sendMessage(Component.text("Vous n'avez pas assez d'argent.")); //TODO
            return;
        }
        
        isOpened = true;
        getDoorsBlocks().forEach(block -> nmsWorld.destroyBlock(new BlockPos(block.getX(), block.getY(), block.getZ()), false));
        doorOpen.run();

        PlayerUtils.removeCoins(player, getPrice());

        // TODO add sound & visual effects
    }

    public List<Block> getDoorsBlocks() {
        List<Block> blocks = new ArrayList<>();

        int topBlockX = Math.max(doorPoint1.getBlockX(), doorPoint2.getBlockX());
        int bottomBlockX = Math.min(doorPoint1.getBlockX(), doorPoint2.getBlockX());

        int topBlockY = Math.max(doorPoint1.getBlockY(), doorPoint2.getBlockY());
        int bottomBlockY = Math.min(doorPoint1.getBlockY(), doorPoint2.getBlockY());

        int topBlockZ = Math.max(doorPoint1.getBlockZ(), doorPoint2.getBlockZ());
        int bottomBlockZ = Math.min(doorPoint1.getBlockZ(), doorPoint2.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++) {
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }

        return blocks;
    }


    public void hideArmorStand1Name() {
        armorStand1.setCustomNameVisible(false);
        armorStand1.setCustomName(null);
    }

    public void hideArmorStand2Name() {
        armorStand2.setCustomNameVisible(false);
        armorStand2.setCustomName(null);
    }

    public void hideArmorStandsName() {
        hideArmorStand1Name();
        hideArmorStand2Name();
    }

    public void setArmorStand1Name() {
        armorStand1.customName(getName());
        armorStand1.setCustomNameVisible(true);
    }

    public void setArmorStand2Name() {
        armorStand2.customName(getName());
        armorStand2.setCustomNameVisible(true);
    }

    public static void initArmorStands() {
        for(DoorsList doors : DoorsList.values()) {
            if(doors == DoorsList.GARDEN) continue;
            doors.getDoor().armorStand1 = (ArmorStand) world.spawnEntity(doors.getDoor().armorStand1Loc, EntityType.ARMOR_STAND);
            doors.getDoor().armorStand2 = (ArmorStand) world.spawnEntity(doors.getDoor().armorStand2Loc, EntityType.ARMOR_STAND);

            doors.getDoor().armorStand1.setInvisible(true);
            doors.getDoor().armorStand2.setInvisible(true);

            doors.getDoor().armorStand1.setBasePlate(false);
            doors.getDoor().armorStand2.setBasePlate(false);

            doors.getDoor().armorStand1.setSmall(true);
            doors.getDoor().armorStand2.setSmall(true);

            doors.getDoor().armorStand1.setGravity(false);
            doors.getDoor().armorStand2.setGravity(false);

            doors.getDoor().armorStand1.setInvulnerable(true);
            doors.getDoor().armorStand2.setInvulnerable(true);

            doors.getDoor().armorStand1.setCustomNameVisible(false);
            doors.getDoor().armorStand2.setCustomNameVisible(false);

            if(doors == DoorsList.YARD) {
                doors.getDoor().setArmorStand1Name();
            } else {
                doors.getDoor().hideArmorStandsName();
            }

            doors.getDoor().armorStand1.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "door"), PersistentDataType.STRING, doors.toString());
        }
    }

    public static Doors getRandomOpenedDoor() {
        List<DoorsList> openedDoors = Arrays.stream(DoorsList.values()).filter(door -> door.getDoor().isOpened()).collect(Collectors.toList());
        return openedDoors.get(ThreadLocalRandom.current().nextInt(0, openedDoors.size())).getDoor();
    }

    public enum DoorsList {
        GARDEN(new Doors(
            Component.text("Garden"), // TODO
            new Location(world, 0, 0, 0),
            new Location(world, 0, 0, 0),
            0,
            true,
            Arrays.asList(
                new Location(world, -10, 6, -5),
                new Location(world, 2, 6, -13),
                new Location(world, 12, 6, 13),
                new Location(world, 2, 6, 7)
            ),
            (Runnable) () -> {

            },
            new Location(world, 0, 0, 0),
            new Location(world, 0, 0, 0)
        )),
        YARD(new Doors(
           Component.text("Cour"), // TODO
            new Location(world, 16, 9, 12),
            new Location(world, 16, 6, 14),
            100,
            Arrays.asList(
                new Location(world, 21, 6, 23),
                new Location(world, 10, 6, 24),
                new Location(world, 19, 6, 3),
                new Location(world, 26, 7, 16),
                new Location(world, 26, 13, 11),
                new Location(world, 26, 7, 26)
            ),
            new Runnable() {
                @Override
                public void run() {
                    YARD.getDoor().hideArmorStandsName();
                    CRYPTS.getDoor().setArmorStand1Name();
                }
            },
            new Location(world, 15, 6, 13),
            new Location(world, 17, 6, 13)
        )),
        CRYPTS(new Doors(
            Component.text("Crypts"), // TODO
            new Location(world, 24, 6, 4),
            new Location(world, 24, 8, 6),
            100,
            Arrays.asList(
                new Location(world, 33, 2, 11),
                new Location(world, 43, 2, 5),
                new Location(world, 27, 2, 16)
            ),
                new Runnable() {
                    @Override
                    public void run() {
                        if(!YARD.getDoor().isOpened()) {
                            YARD.getDoor().setArmorStand2Name();
                        } else {
                            YARD.getDoor().hideArmorStandsName();
                        }
                        CRYPTS.getDoor().hideArmorStandsName();
                    }
                },
            new Location(world, 23, 6, 5),
            new Location(world, 25, 6, 5)
        ));

        private final Doors door;

        DoorsList(Doors door) {
            this.door = door;
        }

        public Doors getDoor() {
            return door;
        }
    }
}
