package me.grovre.heartearner.commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerHealthInfo {

    private final Player player;
    private double healthScale;
    private double health;
    private double maxHealth;

    public PlayerHealthInfo(Player player) {
        this.player = player;
        healthScale = player.getHealthScale();
        maxHealth = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();
    }

    public PlayerHealthInfo addToScale(double toAdd) {
        healthScale += toAdd;
        return this;
    }

    public PlayerHealthInfo removeFromScale(double toRemove) {
        healthScale -= toRemove;
        return this;
    }

    public PlayerHealthInfo addToMaxHealth(double toAdd) {
        health += toAdd;
        return this;
    }

    public PlayerHealthInfo removeFromMaxHealth(double toRemove) {
        health += toRemove;
        return this;
    }

    public PlayerHealthInfo setMaxHealth(double health) {
        maxHealth = health;
        return this;
    }

    public PlayerHealthInfo setHealthScale(double healthScale) {
        this.healthScale = healthScale;
        return this;
    }

    public void pushUpdate() {
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(maxHealth);
        player.setHealthScale(healthScale);
        if(player.getHealth() > maxHealth) {
            player.setHealth(maxHealth);
        }
    }

    public double getHealthScale() {
        return healthScale;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }
}
