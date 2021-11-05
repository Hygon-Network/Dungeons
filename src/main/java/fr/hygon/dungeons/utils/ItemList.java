package fr.hygon.dungeons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public enum ItemList {
    DIFFICULTY_SELECTOR(() -> {
        ItemStack difficultySelector = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta difficultySelectorMeta = (SkullMeta) difficultySelector.getItemMeta();

        difficultySelectorMeta.setBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmYyNGVkNjg3NTMwNGZhNG" +
                "ExZjBjNzg1YjJjYjZhNmE3MjU2M2U5ZjNlMjRlYTU1ZTE4MTc4NDUyMTE5YWE2NiJ9fX0=");
        difficultySelectorMeta.displayName(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY))
                .append(Component.text("Choisir la difficulté").color(TextColor.color(215, 10, 10))
                .append(Component.text(" «").color(TextColor.color(NamedTextColor.GRAY)))).decoration(TextDecoration.ITALIC, false));

        difficultySelector.setItemMeta(difficultySelectorMeta);

        return difficultySelector;
    }),
    NORMAL_DIFFICULTY(() -> {
        ItemStack normalDifficulty = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta normalDifficultyMeta = (SkullMeta) normalDifficulty.getItemMeta();

        normalDifficultyMeta.setBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmJlY2M2NDVmMTI5YzhiYzJmY" +
                "WE0ZDgxNDU0ODFmYWIxMWFkMmVlNzU3NDlkNjI4ZGNkOTk5YWE5NGU3In19fQ==");

        normalDifficultyMeta.displayName(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false)
                .append(Difficulty.NORMAL.getName()));
        normalDifficulty.setItemMeta(normalDifficultyMeta);

        return normalDifficulty;
    }),
    HARD_DIFFICULTY(() -> {
        ItemStack hardDifficulty = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta hardDifficultyMeta = (SkullMeta) hardDifficulty.getItemMeta();

        hardDifficultyMeta.setBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2I4NTJiYTE1ODRkYTllNTcxNDg1O" +
                "Tk5NTQ1MWU0Yjk0NzQ4YzRkZDYzYWU0NTQzYzE1ZjlmOGFlYzY1YzgifX19");

        hardDifficultyMeta.displayName(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false)
                .append(Difficulty.HARD.getName()));
        hardDifficulty.setItemMeta(hardDifficultyMeta);

        return hardDifficulty;
    }),
    INSANE_DIFFICULTY(() -> {
        ItemStack insaneDifficulty = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta insaneDifficultyMeta = (SkullMeta) insaneDifficulty.getItemMeta();

        insaneDifficultyMeta.setBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ5ZDgwYjc5NDQyY2YxYTNhZmVhYTIz" +
                "N2JkNmFkYWFhY2FiMGMyODgzMGZiMzZiNTcwNGNmNGQ5ZjU5MzdjNCJ9fX0=");

        insaneDifficultyMeta.displayName(Component.text("» ").color(TextColor.color(NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false)
                .append(Difficulty.INSANE.getName()));
        insaneDifficulty.setItemMeta(insaneDifficultyMeta);

        return insaneDifficulty;
    }),
    INVENTORY_FILLER(() -> {
        ItemStack inventoryFiller = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta inventoryFillerMeta = inventoryFiller.getItemMeta();

        inventoryFillerMeta.displayName(Component.text(""));
        inventoryFiller.setItemMeta(inventoryFillerMeta);

        return inventoryFiller;
    });


    private final Supplier<ItemStack> itemStackSupplier;

    ItemList(Supplier<ItemStack> itemStackSupplier) {
        this.itemStackSupplier = itemStackSupplier;
    }

    public ItemStack getItem() {
        return itemStackSupplier.get();
    }
}
