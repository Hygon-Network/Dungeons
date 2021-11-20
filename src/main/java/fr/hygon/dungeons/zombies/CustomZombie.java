package fr.hygon.dungeons.zombies;

import fr.hygon.dungeons.game.Doors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import java.text.DecimalFormat;

public abstract class CustomZombie extends Zombie {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");

    private final float regeneration;
    private final int tickPerRegen;
    private final double coins;
    private final String name;

    public CustomZombie(int health, float regeneration, int tickPerRegen, double coins, String name) {
        super(EntityType.ZOMBIE, ((CraftWorld) Bukkit.getWorld("world")).getHandle());
        if(tickPerRegen == 0) {
            tickPerRegen = 1; // We can't divide by zero.
            regeneration = 0;
        }

        this.regeneration = regeneration;
        this.tickPerRegen = tickPerRegen;
        this.coins = coins;
        this.name = name;

        Location pos = Doors.getRandomOpenedDoor().getRandomSpawnLocation();
        setPos(pos.getX(), pos.getY(), pos.getZ());
        setCustomNameVisible(true);
        getBukkitLivingEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        setHealth(health);
    }

    @Override
    protected void customServerAiStep() {

    }

    @Override
    public void tick() {
        super.tick();
        getBukkitEntity().customName(Component.text("[").color(NamedTextColor.DARK_GRAY)
            .append(Component.text(name).color(NamedTextColor.GRAY))
                .append(Component.text("] ").color(NamedTextColor.DARK_GRAY))
                    .append(Component.text(DECIMAL_FORMAT.format(getHealth()) + "/" + DECIMAL_FORMAT.format(getMaxHealth())).color(NamedTextColor.WHITE))
                        .append(Component.text("❤").color(TextColor.color(200, 35, 35))));
        if(tickCount % tickPerRegen == 0) {
            heal(regeneration);
        }
    }

    public double getCoins() {
        return coins;
    }

    public void dropSpecialItem() {

    }

    public ItemStack getDyedLeather(Material material, Color color) {
        ItemStack leatherPiece = new ItemStack(material, 1);
        LeatherArmorMeta leatherPieceMeta = (LeatherArmorMeta) leatherPiece.getItemMeta();

        leatherPieceMeta.setColor(color);
        leatherPiece.setItemMeta(leatherPieceMeta);

        return leatherPiece;
    }

    public ItemStack getItemWithEnchantedEffect(Material material) {
        ItemStack enchantedItem = new ItemStack(material, 1);
        ItemMeta enchantedItemMeta = enchantedItem.getItemMeta();

        enchantedItemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        enchantedItem.setItemMeta(enchantedItemMeta);

        return enchantedItem;
    }
}
