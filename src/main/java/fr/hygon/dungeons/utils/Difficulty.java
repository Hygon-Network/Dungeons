package fr.hygon.dungeons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum Difficulty {
    NORMAL(Component.text("Normal").color(TextColor.color(0, 170, 0)).decoration(TextDecoration.ITALIC, false), 0),
    HARD(Component.text("Hard").color(TextColor.color(220, 110, 30)).decoration(TextDecoration.ITALIC, false), 10),
    INSANE(Component.text("Insane").color(TextColor.color(170, 30, 10)).decoration(TextDecoration.ITALIC, false), 20);

    private final Component name;
    private final int zombiesMultiplier;

    Difficulty(Component name, int zombiesMultiplier) {
        this.name = name;
        this.zombiesMultiplier = zombiesMultiplier;
    }

    public Component getName() {
        return name;
    }

    public int getZombiesMultiplier() {
        return zombiesMultiplier;
    }
}
