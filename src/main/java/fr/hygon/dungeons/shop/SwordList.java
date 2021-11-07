package fr.hygon.dungeons.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public enum SwordList {
    WOOD_SWORD(Component.text("Épée en bois"), 0, 3.0D, () -> {
        return new ItemStack(Material.WOODEN_SWORD, 1);
    }),
    STONE_SWORD(Component.text("Épée en pierre"), 50, 4, () -> {
        return new ItemStack(Material.STONE_SWORD, 1);
    }),
    GOLD_SWORD(Component.text("Épée en or"), 100, 5, () -> {
        return new ItemStack(Material.GOLDEN_SWORD, 1);
    }),
    IRON_SWORD(Component.text("Épée en fer"), 250, 6, () -> {
        return new ItemStack(Material.IRON_SWORD, 1);
    }),
    DIAMOND_SWORD(Component.text("Épée en diamant"), 750, 8, () -> {
        return new ItemStack(Material.DIAMOND_SWORD, 1);
    }),
    NETHERITE_SWORD(Component.text("Épée en netherite"), 2000, 9, () -> {
        return new ItemStack(Material.NETHERITE_SWORD, 1);
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
