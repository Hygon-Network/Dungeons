package fr.hygon.dungeons.shop;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public enum SwordList {
    WOOD_SWORD(Component.text("Épée en Bois").decoration(TextDecoration.ITALIC, false), 0, 3.0D, () -> {
        ItemStack woodenSword = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta woodenSwordMeta = woodenSword.getItemMeta();

        woodenSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        woodenSword.setItemMeta(woodenSwordMeta);
        return woodenSword;
    }),
    STONE_SWORD(Component.text("Épée en Pierre").decoration(TextDecoration.ITALIC, false), 50, 4, () -> {
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD, 1);
        ItemMeta stoneSwordMeta = stoneSword.getItemMeta();

        stoneSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stoneSword.setItemMeta(stoneSwordMeta);
        return stoneSword;
    }),
    GOLD_SWORD(Component.text("Épée en Or").decoration(TextDecoration.ITALIC, false), 100, 5, () -> {
        ItemStack goldenSword = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta goldenSwordMeta = goldenSword.getItemMeta();

        goldenSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        goldenSword.setItemMeta(goldenSwordMeta);
        return goldenSword;
    }),
    IRON_SWORD(Component.text("Épée en Fer").decoration(TextDecoration.ITALIC, false), 250, 6, () -> {
        ItemStack ironSword = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta ironSwordMeta = ironSword.getItemMeta();

        ironSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ironSword.setItemMeta(ironSwordMeta);
        return ironSword;
    }),
    DIAMOND_SWORD(Component.text("Épée en Diamant").decoration(TextDecoration.ITALIC, false), 750, 8, () -> {
        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta diamondSwordMeta = diamondSword.getItemMeta();

        diamondSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        diamondSword.setItemMeta(diamondSwordMeta);
        return diamondSword;
    }),
    NETHERITE_SWORD(Component.text("Épée en Netherite").decoration(TextDecoration.ITALIC, false), 2000, 9, () -> {
        ItemStack netheriteSword = new ItemStack(Material.NETHERITE_SWORD, 1);
        ItemMeta netheriteSwordMeta = netheriteSword.getItemMeta();

        netheriteSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        netheriteSword.setItemMeta(netheriteSwordMeta);
        return netheriteSword;
    });


    private final Component name;
    private final double price;
    private final double damage;
    private final SwordProvider swordProvider;

    SwordList(Component name, double price, double damage, SwordProvider swordProvider) {
        this.name = name;
        this.price = price;
        this.damage = damage;
        this.swordProvider = swordProvider;
    }

    private static final HashMap<SwordList, Integer> swordLevels = new HashMap<>();

    static {
        int level = 0;
        for(SwordList swords : SwordList.values()) {
            level++;
            swordLevels.put(swords, level);
        }
    }

    public Component getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDamage() {
        return damage;
    }

    public void equipPlayer(Player player) {
        player.getInventory().setItem(0, this.swordProvider.getSword());
    }

    public int getSwordLevel() {
        return swordLevels.get(this);
    }

    public SwordProvider getSwordProvider() {
        return swordProvider;
    }

    public static SwordList getSwordFromLevel(int level) {
        return swordLevels.keySet().stream().filter(swordList -> swordLevels.get(swordList) == level).findFirst().orElse(null);
    }

    public interface SwordProvider {
        ItemStack getSword();
    }
}
