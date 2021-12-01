package fr.hygon.dungeons.game;

import fr.hygon.dungeons.Main;
import fr.hygon.dungeons.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
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
            player.sendMessage(Component.text("Vous n'avez pas assez d'argent.").color(TextColor.color(180, 20, 20)));
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
        for(Entity entities : world.getEntities()) {
            if(entities instanceof ArmorStand armorStand) {
                if(armorStand.getPersistentDataContainer().has(new NamespacedKey(Main.getPlugin(), "door"), PersistentDataType.STRING)) {
                    armorStand.remove();
                }
            }
        }

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

            if(doors == DoorsList.YARD || doors == DoorsList.GARDEN_DUNGEON || doors == DoorsList.GARDEN_LABORATORY) {
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
            Component.text("Garden").color(TextColor.color(0, 200, 0)),
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
           Component.text("Yard").color(TextColor.color(75, 75, 90)),
            new Location(world, 16, 9, 12),
            new Location(world, 16, 6, 14),
            100,
            Arrays.asList(
                new Location(world, 21, 6, 23),
                new Location(world, 10, 6, 24),
                new Location(world, 19, 6, 3),
                new Location(world, 26, 7, 16),
                new Location(world, 26, 13, 11),
                new Location(world, 26, 13, 26)
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
            Component.text("Crypts").color(TextColor.color(130, 130, 140)),
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
                        if(!PARKING.getDoor().isOpened()) {
                            PARKING.getDoor().setArmorStand2Name();
                        } else {
                            PARKING.getDoor().hideArmorStandsName();
                        }
                        if(!UNDERGROUND_CRYPTS.getDoor().isOpened()) {
                            UNDERGROUND_CRYPTS.getDoor().setArmorStand2Name();
                        } else {
                            UNDERGROUND_CRYPTS.getDoor().hideArmorStandsName();
                        }
                        CRYPTS.getDoor().hideArmorStandsName();
                    }
                },
            new Location(world, 23, 6, 5),
            new Location(world, 25, 6, 5)
        )),
        PARKING(new Doors(
                Component.text("Parking").color(TextColor.color(130, 130, 140)),
                new Location(world, 33, 2, 2),
                new Location(world, 31, 4, 2), //DOOR LOCATION
                100,
                Arrays.asList(
                        new Location(world, 28, 6, -7), //SPAWN ZONES
                        new Location(world, 26, 6, -16),
                        new Location(world, 18, 6, -9),
                        new Location(world, 11, 6, -20)
                ),
                new Runnable() {
                    @Override
                    public void run() {
                        PARKING.getDoor().hideArmorStandsName();
                    }
                },
                new Location(world, 32, 2, 1), //ARMORSTAND1
                new Location(world, 32, 2, 3) //ARMORSTAND2
        )),
        UNDERGROUND_CRYPTS(new Doors(
                Component.text("Underground").color(TextColor.color(130, 130, 140)),
                new Location(world, 24, 2, 12),
                new Location(world, 24, 4, 14),
                100,
            Arrays.asList(
                new Location(world, 15, 1, 6),
                new Location(world, 10, 1, 25),
                new Location(world, 0, 1, 1),
                new Location(world, 14, 1, 12)
            ),
                    new Runnable() {
            @Override
            public void run() {
                if(!CRYPTS.getDoor().isOpened()) {
                    CRYPTS.getDoor().setArmorStand2Name();
                } else {
                    CRYPTS.getDoor().hideArmorStandsName();
                }
                if(!PARKING.getDoor().isOpened()) {
                    PARKING.getDoor().setArmorStand2Name();
                } else {
                    PARKING.getDoor().hideArmorStandsName();
                }
                if(!UNDERGROUND_LABORATORY.getDoor().isOpened()) {
                    UNDERGROUND_LABORATORY.getDoor().setArmorStand2Name();
                } else {
                    UNDERGROUND_LABORATORY.getDoor().hideArmorStandsName();
                }
                if(!UNDERGROUND_DUNGEON.getDoor().isOpened()) {
                    UNDERGROUND_DUNGEON.getDoor().setArmorStand2Name();
                } else {
                    UNDERGROUND_DUNGEON.getDoor().hideArmorStandsName();
                }
                UNDERGROUND_CRYPTS.getDoor().hideArmorStandsName();
            }
        },
                new Location(world, 23, 2, 13),
                new Location(world, 25, 2, 13)
        )),
        UNDERGROUND_LABORATORY(new Doors(
                Component.text("Laboratory").color(TextColor.color(50, 140, 235)),
                new Location(world, -8, 1, -6),
                new Location(world, -6, 3, -6), //DOOR LOCATION
                100,
                Arrays.asList(
                        new Location(world, -4, 1, -8), //SPAWN ZONES
                        new Location(world, -10, 1, -8),
                        new Location(world, -4, 1, -14),
                        new Location(world, -10, 1, -14),
                        new Location(world, -16, 1, -21),
                        new Location(world, -16, 1, -17),
                        new Location(world, -26, 1, -19),
                        new Location(world, -9, 6, -27)
                ),
                new Runnable() {
                    @Override
                    public void run() {
                        if(!RESEARCH_ROOM.getDoor().isOpened()) {
                            RESEARCH_ROOM.getDoor().setArmorStand2Name();
                        } else {
                            RESEARCH_ROOM.getDoor().hideArmorStandsName();
                        }
                        if(!GARDEN_LABORATORY.getDoor().isOpened()) {
                            GARDEN_LABORATORY.getDoor().setArmorStand2Name();
                        } else {
                            GARDEN_LABORATORY.getDoor().hideArmorStandsName();
                        }
                        UNDERGROUND_LABORATORY.getDoor().hideArmorStandsName();
                    }
                },
                new Location(world, -7, 1, -7), //ARMORSTAND1
                new Location(world, -7, 1, -5) //ARMORSTAND2
        )),
        RESEARCH_ROOM(new Doors(
                Component.text("Research Room").color(TextColor.color(50, 140, 235)),
                new Location(world, -23, 1, -26),
                new Location(world, -25, 3, -26), //DOOR LOCATION
                100,
                Arrays.asList(
                        new Location(world, -13, -1, -25), //SPAWN ZONES
                        new Location(world, -21, -1, -35),
                        new Location(world, -26, 0, -28)
                ),
                new Runnable() {
                    @Override
                    public void run() {
                        RESEARCH_ROOM.getDoor().hideArmorStandsName();
                    }
                },
                new Location(world, -24, 1, -27), //ARMORSTAND1
                new Location(world, -24, 1, -25) //ARMORSTAND2
        )),
        UNDERGROUND_DUNGEON(new Doors(
                Component.text("Dungeon").color(TextColor.color(50, 140, 235)),
                new Location(world, -11, 1, -3),
                new Location(world, -11, 3, -1), //DOOR LOCATION
                100,
                        Arrays.asList(
                        new Location(world, -18, 1, -2), //SPAWN ZONES
                        new Location(world, -39, 6, -2),
                        new Location(world, -42, 3, -15),
                        new Location(world, -37, 6, -15),
                        new Location(world, -32, 3, -15),
                        new Location(world, -33, 12, 1),
                        new Location(world, -43, 12, 24),
                        new Location(world, -42, 12, -2),
                        new Location(world, -52, 12, 2),
                        new Location(world, -48, 12, 2),
                        new Location(world, -56, 12, 18),
                        new Location(world, -37, 12, 32),
                        new Location(world, -43, 6, 11),
                        new Location(world, -36, 6, 21),
                        new Location(world, -22, 6, 18)
                ),
                        new Runnable() {
            @Override
            public void run() {
                if(!GARDEN_DUNGEON.getDoor().isOpened()) {
                    GARDEN_DUNGEON.getDoor().setArmorStand2Name();
                } else {
                    GARDEN_DUNGEON.getDoor().hideArmorStandsName();
                }
                if(!UNDERGROUND_LABORATORY.getDoor().isOpened()) {
                    UNDERGROUND_LABORATORY.getDoor().setArmorStand2Name();
                } else {
                    UNDERGROUND_LABORATORY.getDoor().hideArmorStandsName();
                }
                if(!UNDERGROUND_CRYPTS.getDoor().isOpened()) {
                    UNDERGROUND_CRYPTS.getDoor().setArmorStand1Name();
                } else {
                    UNDERGROUND_CRYPTS.getDoor().hideArmorStandsName();
                }
                UNDERGROUND_DUNGEON.getDoor().hideArmorStandsName();
            }
        },
                new Location(world, -12, 1, -2), //ARMORSTAND1
                new Location(world, -10, 1, -2) //ARMORSTAND2
        )),
        GARDEN_DUNGEON(new Doors(
                Component.text("Dungeon").color(TextColor.color(50, 140, 235)),
                new Location(world, -18, 6, 4),
                new Location(world, -18, 8, 6), //DOOR LOCATION
                100,
                Arrays.asList(
                        new Location(world, -18, 1, -2), //SPAWN ZONES
                        new Location(world, -39, 6, -2),
                        new Location(world, -42, 3, -15),
                        new Location(world, -37, 6, -15),
                        new Location(world, -32, 3, -15),
                        new Location(world, -33, 12, 1),
                        new Location(world, -43, 12, 24),
                        new Location(world, -42, 12, -2),
                        new Location(world, -52, 12, 2),
                        new Location(world, -48, 12, 2),
                        new Location(world, -56, 12, 18),
                        new Location(world, -37, 12, 32),
                        new Location(world, -43, 6, 11),
                        new Location(world, -36, 6, 21),
                        new Location(world, -22, 6, 18)
                ),
                new Runnable() {
                    @Override
                    public void run() {
                        if(!UNDERGROUND_DUNGEON.getDoor().isOpened()) {
                            UNDERGROUND_DUNGEON.getDoor().setArmorStand1Name();
                        } else {
                            UNDERGROUND_DUNGEON.getDoor().hideArmorStandsName();
                        }
                        GARDEN_DUNGEON.getDoor().hideArmorStandsName();
                    }
                },
                new Location(world, -17, 6, 5), //ARMORSTAND1
                new Location(world, -19, 6, 5) //ARMORSTAND2
        )),
        GARDEN_LABORATORY(new Doors(
                Component.text("Laboratory").color(TextColor.color(50, 140, 235)),
                new Location(world, -5, 6, -17),
                new Location(world, -9, 8, -17), //DOOR LOCATION
                100,
                Arrays.asList(
                        new Location(world, -4, 1, -8), //SPAWN ZONES
                        new Location(world, -10, 1, -8),
                        new Location(world, -4, 1, -14),
                        new Location(world, -10, 1, -14),
                        new Location(world, -16, 1, -21),
                        new Location(world, -16, 1, -17),
                        new Location(world, -26, 1, -19),
                        new Location(world, -9, 6, -27)
                ),
                new Runnable() {
                    @Override
                    public void run() {
                        if(!UNDERGROUND_LABORATORY.getDoor().isOpened()) {
                            UNDERGROUND_LABORATORY.getDoor().setArmorStand1Name();
                        } else {
                            UNDERGROUND_LABORATORY.getDoor().hideArmorStandsName();
                        }
                        if(!RESEARCH_ROOM.getDoor().isOpened()) {
                            RESEARCH_ROOM.getDoor().setArmorStand2Name();
                        } else {
                            RESEARCH_ROOM.getDoor().hideArmorStandsName();
                        }
                        GARDEN_LABORATORY.getDoor().hideArmorStandsName();
                    }
                },
                new Location(world, -7, 6, -16), //ARMORSTAND1
                new Location(world, -7, 6, -18) //ARMORSTAND2
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
