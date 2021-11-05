package fr.hygon.dungeons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
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
        ItemStack normalDifficulty = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta normalDifficultyMeta = normalDifficulty.getItemMeta();

        normalDifficultyMeta.displayName(Difficulty.NORMAL.getName());
        normalDifficulty.setItemMeta(normalDifficultyMeta);

        return normalDifficulty;
    }),
    HARD_DIFFICULTY(() -> {
        ItemStack hardDifficulty = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE, 1);
        ItemMeta hardDifficultyMeta = hardDifficulty.getItemMeta();

        hardDifficultyMeta.displayName(Difficulty.HARD.getName());
        hardDifficulty.setItemMeta(hardDifficultyMeta);

        return hardDifficulty;
    }),
    INSANE_DIFFICULTY(() -> {
        ItemStack insaneDifficulty = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta insaneDifficultyMeta = insaneDifficulty.getItemMeta();

        insaneDifficultyMeta.displayName(Difficulty.INSANE.getName());
        insaneDifficulty.setItemMeta(insaneDifficultyMeta);

        return insaneDifficulty;
    });


    private final Supplier<ItemStack> itemStackSupplier;

    ItemList(Supplier<ItemStack> itemStackSupplier) {
        this.itemStackSupplier = itemStackSupplier;
    }

    public ItemStack getItem() {
        return itemStackSupplier.get();
    }
}
