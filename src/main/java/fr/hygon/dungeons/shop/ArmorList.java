package fr.hygon.dungeons.shop;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public enum ArmorList {
    BASE_ARMOR(Component.text("None").decoration(TextDecoration.ITALIC, false), 0, 0, 0, new ArmorProvider() {
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
    FULL_LEATHER(Component.text("Full Leather Set").decoration(TextDecoration.ITALIC, false), 2, 0, 100, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET, 1);
            ItemMeta leatherHelmetMeta = leatherHelmet.getItemMeta();

            leatherHelmetMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            leatherHelmet.setItemMeta(leatherHelmetMeta);
            return leatherHelmet;
        }

        @Override
        public ItemStack getChestplate() {
            ItemStack leatherChestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
            ItemMeta leatherChestplateMeta = leatherChestplate.getItemMeta();

            leatherChestplateMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            leatherChestplate.setItemMeta(leatherChestplateMeta);
            return leatherChestplate;
        }

        @Override
        public ItemStack getLeggings() {
            ItemStack leatherLeggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
            ItemMeta leatherLeggingsMeta = leatherLeggings.getItemMeta();

            leatherLeggingsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            leatherLeggings.setItemMeta(leatherLeggingsMeta);
            return leatherLeggings;
        }

        @Override
        public ItemStack getBoots() {
            ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS, 1);
            ItemMeta leatherBootsMeta = leatherBoots.getItemMeta();

            leatherBootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            leatherBoots.setItemMeta(leatherBootsMeta);
            return leatherBoots;
        }
    }),
    FULL_GOLD(Component.text("Full Gold Set").decoration(TextDecoration.ITALIC, false), 3, 0, 250, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            ItemStack goldenHelmet = new ItemStack(Material.GOLDEN_HELMET, 1);
            ItemMeta goldenHelmetMeta = goldenHelmet.getItemMeta();

            goldenHelmetMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            goldenHelmet.setItemMeta(goldenHelmetMeta);
            return goldenHelmet;
        }

        @Override
        public ItemStack getChestplate() {
            ItemStack goldenChestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
            ItemMeta goldenChestplateMeta = goldenChestplate.getItemMeta();

            goldenChestplateMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            goldenChestplate.setItemMeta(goldenChestplateMeta);
            return goldenChestplate;
        }

        @Override
        public ItemStack getLeggings() {
            ItemStack goldenLeggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
            ItemMeta goldenLeggingsMeta = goldenLeggings.getItemMeta();

            goldenLeggingsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            goldenLeggings.setItemMeta(goldenLeggingsMeta);
            return goldenLeggings;
        }

        @Override
        public ItemStack getBoots() {
            ItemStack goldenBoots = new ItemStack(Material.GOLDEN_BOOTS, 1);
            ItemMeta goldenBootsMeta = goldenBoots.getItemMeta();

            goldenBootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            goldenBoots.setItemMeta(goldenBootsMeta);
            return goldenBoots;
        }
    }),
    FULL_CHAINMAIL(Component.text("Full Chainmail Set").decoration(TextDecoration.ITALIC, false), 5, 0, 500, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            ItemStack chainmailHelmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
            ItemMeta chainmailHelmetMeta = chainmailHelmet.getItemMeta();

            chainmailHelmetMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            chainmailHelmet.setItemMeta(chainmailHelmetMeta);
            return chainmailHelmet;
        }

        @Override
        public ItemStack getChestplate() {
            ItemStack chainmailChestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
            ItemMeta chainmailChestplateMeta = chainmailChestplate.getItemMeta();

            chainmailChestplateMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            chainmailChestplate.setItemMeta(chainmailChestplateMeta);
            return chainmailChestplate;
        }

        @Override
        public ItemStack getLeggings() {
            ItemStack chainmailLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
            ItemMeta chainmailLeggingsMeta = chainmailLeggings.getItemMeta();

            chainmailLeggingsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            chainmailLeggings.setItemMeta(chainmailLeggingsMeta);
            return chainmailLeggings;
        }

        @Override
        public ItemStack getBoots() {
            ItemStack chainmailBoots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
            ItemMeta chainmailBootsMeta = chainmailBoots.getItemMeta();

            chainmailBootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            chainmailBoots.setItemMeta(chainmailBootsMeta);
            return chainmailBoots;
        }
    }),
    FULL_IRON(Component.text("Full Iron Set").decoration(TextDecoration.ITALIC, false), 6, 0, 1000, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            ItemStack ironHelmet = new ItemStack(Material.IRON_HELMET, 1);
            ItemMeta ironHelmetMeta = ironHelmet.getItemMeta();

            ironHelmetMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            ironHelmet.setItemMeta(ironHelmetMeta);
            return ironHelmet;
        }

        @Override
        public ItemStack getChestplate() {
            ItemStack ironChestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
            ItemMeta ironChestplateMeta = ironChestplate.getItemMeta();

            ironChestplateMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            ironChestplate.setItemMeta(ironChestplateMeta);
            return ironChestplate;
        }

        @Override
        public ItemStack getLeggings() {
            ItemStack ironLeggings = new ItemStack(Material.IRON_LEGGINGS, 1);
            ItemMeta ironLeggingsMeta = ironLeggings.getItemMeta();

            ironLeggingsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            ironLeggings.setItemMeta(ironLeggingsMeta);
            return ironLeggings;
        }

        @Override
        public ItemStack getBoots() {
            ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS, 1);
            ItemMeta ironBootsMeta = ironBoots.getItemMeta();

            ironBootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            ironBoots.setItemMeta(ironBootsMeta);
            return ironBoots;
        }
    }),
    DIAMOND_AND_IRON(Component.text("Semi Iron/Diamond Set").decoration(TextDecoration.ITALIC, false), 8, 0, 1000, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            ItemStack ironHelmet = new ItemStack(Material.IRON_HELMET, 1);
            ItemMeta ironHelmetMeta = ironHelmet.getItemMeta();

            ironHelmetMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            ironHelmet.setItemMeta(ironHelmetMeta);
            return ironHelmet;
        }

        @Override
        public ItemStack getChestplate() {
            ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
            ItemMeta diamondChestplateMeta = diamondChestplate.getItemMeta();

            diamondChestplateMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            diamondChestplate.setItemMeta(diamondChestplateMeta);
            return diamondChestplate;
        }

        @Override
        public ItemStack getLeggings() {
            ItemStack ironLeggings = new ItemStack(Material.IRON_LEGGINGS, 1);
            ItemMeta ironLeggingsMeta = ironLeggings.getItemMeta();

            ironLeggingsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            ironLeggings.setItemMeta(ironLeggingsMeta);
            return ironLeggings;
        }

        @Override
        public ItemStack getBoots() {
            ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS, 1);
            ItemMeta diamondBootsMeta = diamondBoots.getItemMeta();

            diamondBootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            diamondBoots.setItemMeta(diamondBootsMeta);
            return diamondBoots;
        }
    }),
    FULL_DIAMOND(Component.text("Full Diamond Set").decoration(TextDecoration.ITALIC, false), 11, 0, 1500, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            ItemStack diamondHelmet = new ItemStack(Material.DIAMOND_HELMET, 1);
            ItemMeta diamondHelmetMeta = diamondHelmet.getItemMeta();

            diamondHelmetMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            diamondHelmet.setItemMeta(diamondHelmetMeta);
            return diamondHelmet;
        }

        @Override
        public ItemStack getChestplate() {
            ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
            ItemMeta diamondChestplateMeta = diamondChestplate.getItemMeta();

            diamondChestplateMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            diamondChestplate.setItemMeta(diamondChestplateMeta);
            return diamondChestplate;
        }

        @Override
        public ItemStack getLeggings() {
            ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
            ItemMeta diamondLeggingsMeta = diamondLeggings.getItemMeta();

            diamondLeggingsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            diamondLeggings.setItemMeta(diamondLeggingsMeta);
            return diamondLeggings;
        }

        @Override
        public ItemStack getBoots() {
            ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS, 1);
            ItemMeta diamondBootsMeta = diamondBoots.getItemMeta();

            diamondBootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            diamondBoots.setItemMeta(diamondBootsMeta);
            return diamondBoots;
        }
    }),
    NETHERITE_AND_DIAMOND(Component.text("Full Diamond & Netherite Helmet").decoration(TextDecoration.ITALIC, false), 13, 20, 2000, new ArmorProvider() {
        @Override
        public ItemStack getHelmet() {
            ItemStack netheriteHelmet = new ItemStack(Material.NETHERITE_HELMET, 1);
            ItemMeta netheriteHelmetMeta = netheriteHelmet.getItemMeta();

            netheriteHelmetMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            netheriteHelmet.setItemMeta(netheriteHelmetMeta);
            return netheriteHelmet;
        }

        @Override
        public ItemStack getChestplate() {
            ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
            ItemMeta diamondChestplateMeta = diamondChestplate.getItemMeta();

            diamondChestplateMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            diamondChestplate.setItemMeta(diamondChestplateMeta);
            return diamondChestplate;
        }

        @Override
        public ItemStack getLeggings() {
            ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
            ItemMeta diamondLeggingsMeta = diamondLeggings.getItemMeta();

            diamondLeggingsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            diamondLeggings.setItemMeta(diamondLeggingsMeta);
            return diamondLeggings;
        }

        @Override
        public ItemStack getBoots() {
            ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS, 1);
            ItemMeta diamondBootsMeta = diamondBoots.getItemMeta();

            diamondBootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            diamondBoots.setItemMeta(diamondBootsMeta);
            return diamondBoots;
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
