package com.example.inventoryapp;

import java.io.IOException;

public class Stats {
    public static int health = 100;
    public static int maxHealth = 100;
    public static int attack = 10;
    public static int defense = 5;

    private static final String STATS_FILE_PATH = "stats.json"; // File path for saving stats

    // Save stats to a file
    public static void saveStats() {
        try {
            JsonControl.saveStatsToJson(new Stats(), STATS_FILE_PATH);
            System.out.println("Stats saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save stats: " + e.getMessage());
        }
    }

    // Load stats from a file
    public static void loadStats() {
        try {
            JsonControl.loadStatsFromJson(STATS_FILE_PATH);
            System.out.println("Stats loaded successfully.");
        } catch (IOException e) {
            System.out.println("Failed to load stats: " + e.getMessage());
        }
    }

    public static void displayStats() {
        System.out.printf("Health: %d/%d%nAttack: %d%nDefense: %d%n", health, maxHealth, attack, defense);
    }

    public static void takeDamage(int amount) {
        health = Math.max(0, health - amount);
        System.out.println("Took " + amount + " damage. Current health: " + health + "/" + maxHealth);
        saveStats(); // Save stats after taking damage
    }
}
