package fr.hygon.dungeons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
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
    }),
    BREAD(() -> {
        ItemStack bread = new ItemStack(Material.BREAD, 1);
        ItemMeta breadMeta = bread.getItemMeta();

        breadMeta.displayName(Component.text("Pain").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        breadMeta.lore(Arrays.asList(Component.text("Un morceau de pain, volé à la boulangerie").color(NamedTextColor.DARK_GRAY),
                Component.text("du coin par un zombie.").color(NamedTextColor.DARK_GRAY)));
        bread.setItemMeta(breadMeta);

        return bread;
    }),
    RADIO_OFF(() -> {
        ItemStack radioOff = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta radioOffMeta = (SkullMeta) radioOff.getItemMeta();

        radioOffMeta.displayName(Component.text("Radio brouillée").color(TextColor.color(90, 90, 90)));
        radioOffMeta.setBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5l" +
                "Y3JhZnQubmV0L3RleHR1cmUvNGMzYzg1MTc1MTZmOGQ4ZTgwNjc3ODFlN2M2MmVlYTI3ZGU0NzhiMTRjNGE2OGM4ZThjMWFkOGFmMWJhZTIxIn19fQ==");
        radioOff.setItemMeta(radioOffMeta);

        return radioOff;
    }),
    RADIO_ON(() -> {
        ItemStack radioOn = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta radioOnMeta = (SkullMeta) radioOn.getItemMeta();

        radioOnMeta.displayName(Component.text("Radio").color(TextColor.color(90, 90, 90))
                .decoration(TextDecoration.ITALIC, false));
        radioOnMeta.setBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZn" +
                "QubmV0L3RleHR1cmUvZGViZGVhMmI1YzRkYjVmYTQ0YTRlYzQwMzI2NTgzMmZhN2QxY2FmYThjNGE2Y2Y3ZmE2OTYwYmJhY2Q3In19fQ==");
        radioOn.setItemMeta(radioOnMeta);

        return radioOn;
    }),
    SHOP_SWORD(() -> {
        ItemStack shopSword = new ItemStack(Material.STONE_SWORD, 1);
        ItemMeta shopSwordMeta = shopSword.getItemMeta();

        shopSwordMeta.displayName(Component.text("Épées").color(TextColor.color(200, 20, 20))
                .decoration(TextDecoration.ITALIC, false));
        shopSword.setItemMeta(shopSwordMeta);
        shopSword.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        return shopSword;
    }),
    ARMOR_SHOP(() -> {
        ItemStack armorShop = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta armorShopMeta = armorShop.getItemMeta();

        armorShopMeta.displayName(Component.text("Armures").color(TextColor.color(55, 165, 245))
                .decoration(TextDecoration.ITALIC, false));
        armorShop.setItemMeta(armorShopMeta);
        armorShop.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        return armorShop;
    }),
    BACK_MENU(() -> {
        ItemStack backMenu = new ItemStack(Material.ARROW, 1);
        ItemMeta backMenuMeta = backMenu.getItemMeta();

        backMenuMeta.displayName(Component.text("Back").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        backMenu.setItemMeta(backMenuMeta);

        return backMenu;
    }),
    ACTUAL_ARMOR(() -> {
        ItemStack actualArmor = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta actualArmorMeta = actualArmor.getItemMeta();

        actualArmorMeta.displayName(Component.text("Armure Actuelle").color(TextColor.color(70, 185, 30))
                .decoration(TextDecoration.ITALIC, false));
        actualArmor.setItemMeta(actualArmorMeta);

        return actualArmor;
    }),
    BUY_OBJECT(() -> {
        ItemStack buyObject = new ItemStack(Material.LIGHT_BLUE_DYE, 1);
        ItemMeta buyObjectMeta = buyObject.getItemMeta();

        buyObjectMeta.displayName(Component.text("Acheter").color(TextColor.color(40, 185, 210))
                .decoration(TextDecoration.ITALIC, false));
        buyObject.setItemMeta(buyObjectMeta);

        return buyObject;
    });


    private final Supplier<ItemStack> itemStackSupplier;

    ItemList(Supplier<ItemStack> itemStackSupplier) {
        this.itemStackSupplier = itemStackSupplier;
    }

    public ItemStack getItem() {
        return itemStackSupplier.get();
    }
}
