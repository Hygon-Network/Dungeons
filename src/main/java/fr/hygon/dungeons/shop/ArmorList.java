package fr.hygon.dungeons.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public enum ArmorList {
    BASE_ARMOR(Component.text("Armure de base"), 0, 0, 0, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            return new ItemStack(Material.AIR);
        }

        @Override
        public ItemStack getChestplate() {
            return new ItemStack(Material.AIR);
        }

        @Override
        public ItemStack getLeggings() {
            return new ItemStack(Material.AIR);
        }

        @Override
        public ItemStack getBoots() {
            return new ItemStack(Material.AIR);
        }
    }),
    FULL_LEATHER(Component.text("Full leather set"), 2, 0, 100, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            return new ItemStack(Material.LEATHER_HELMET, 1);
        }

        @Override
        public ItemStack getChestplate() {
            return new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        }

        @Override
        public ItemStack getLeggings() {
            return new ItemStack(Material.LEATHER_LEGGINGS, 1);
        }

        @Override
        public ItemStack getBoots() {
            return new ItemStack(Material.LEATHER_BOOTS, 1);
        }
    }),
    FULL_GOLD(Component.text("Full gold set"), 3, 0, 250, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            return new ItemStack(Material.GOLDEN_HELMET, 1);
        }

        @Override
        public ItemStack getChestplate() {
            return new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        }

        @Override
        public ItemStack getLeggings() {
            return new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        }

        @Override
        public ItemStack getBoots() {
            return new ItemStack(Material.GOLDEN_BOOTS, 1);
        }
    }),
    FULL_CHAINMAIL(Component.text("Full chainmail set"), 5, 0, 500, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            return new ItemStack(Material.CHAINMAIL_HELMET, 1);
        }

        @Override
        public ItemStack getChestplate() {
            return new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        }

        @Override
        public ItemStack getLeggings() {
            return new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        }

        @Override
        public ItemStack getBoots() {
            return new ItemStack(Material.CHAINMAIL_BOOTS, 1);
        }
    }),
    FULL_IRON(Component.text("Full iron set"), 6, 0, 1000, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            return new ItemStack(Material.IRON_HELMET, 1);
        }

        @Override
        public ItemStack getChestplate() {
            return new ItemStack(Material.IRON_CHESTPLATE, 1);
        }

        @Override
        public ItemStack getLeggings() {
            return new ItemStack(Material.IRON_LEGGINGS, 1);
        }

        @Override
        public ItemStack getBoots() {
            return new ItemStack(Material.IRON_BOOTS, 1);
        }
    }),
    DIAMOND_AND_IRON(Component.text("Iron helmet & Leggings, Diamond chestplate & boots"), 8, 0, 1000, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            return new ItemStack(Material.IRON_HELMET, 1);
        }

        @Override
        public ItemStack getChestplate() {
            return new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        }

        @Override
        public ItemStack getLeggings() {
            return new ItemStack(Material.IRON_LEGGINGS, 1);
        }

        @Override
        public ItemStack getBoots() {
            return new ItemStack(Material.DIAMOND_BOOTS, 1);
        }
    }),
    FULL_DIAMOND(Component.text("Full diamond set"), 11, 0, 1500, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            return new ItemStack(Material.DIAMOND_HELMET, 1);
        }

        @Override
        public ItemStack getChestplate() {
            return new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        }

        @Override
        public ItemStack getLeggings() {
            return new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        }

        @Override
        public ItemStack getBoots() {
            return new ItemStack(Material.DIAMOND_BOOTS, 1);
        }
    }),
    NETHERITE_AND_DIAMOND(Component.text("Full diamond, netherite helmet"), 13, 20, 2000, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            return new ItemStack(Material.NETHERITE_HELMET, 1);
        }

        @Override
        public ItemStack getChestplate() {
            return new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        }

        @Override
        public ItemStack getLeggings() {
            return new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        }

        @Override
        public ItemStack getBoots() {
            return new ItemStack(Material.DIAMOND_BOOTS, 1);
        }
    });

    private final Component name;
    private final double heartAbsorption;
    private final int minWave;
    private final double price;
    private final ArmorProvider armorProvider;

    ArmorList(Component name, double heartAbsorption, int minWave, double price, ArmorProvider armorProvider) {
        this.name = name;
        this.heartAbsorption = heartAbsorption;
        this.minWave = minWave;
        this.price = price;
        this.armorProvider = armorProvider;
    }

    private static final HashMap<ArmorList, Integer> armorLevels = new HashMap<>();

    static {
        int level = 0;
        for(ArmorList armors : ArmorList.values()) {
            level++;
            armorLevels.put(armors, level);
        }
    }

    public Component getName() {
        return name;
    }

    public double getHeartAbsorption() {
        return heartAbsorption;
    }

    public int getArmorLevel() {
        return armorLevels.get(this);
    }

    public int getMinWave() {
        return minWave;
    }

    public double getPrice() {
        return price;
    }

    public void equipPlayer(Player player) {
        player.getEquipment().setHelmet(armorProvider.getHelmet());
        player.getEquipment().setChestplate(armorProvider.getChestplate());
        player.getEquipment().setLeggings(armorProvider.getLeggings());
        player.getEquipment().setBoots(armorProvider.getBoots());
    }

    public ArmorProvider getArmorProvider() {
        return armorProvider;
    }

    public static ArmorList getArmorFromLevel(int level) {
        return armorLevels.keySet().stream().filter(armorList -> armorLevels.get(armorList) == level).findFirst().orElse(null);
    }

    public interface ArmorProvider {
        ItemStack getHelmet();
        ItemStack getChestplate();
        ItemStack getLeggings();
        ItemStack getBoots();
    }
}
