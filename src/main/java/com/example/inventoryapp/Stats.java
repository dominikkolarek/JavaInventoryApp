package com.example.inventoryapp;

public class Stats {
    public static int health = 100;
    public static int maxHealth = 100;
    public static int attack = 10;
    public static int defense = 5;

    public static void displayStats() {
        System.out.printf("Health: %d/%d%nAttack: %d%nDefense: %d%n", health, maxHealth, attack, defense);
    }

    public static void takeDamage(int amount) {
        health = Math.max(0, health - amount);
        System.out.println("Took " + amount + " damage. Current health: " + health + "/" + maxHealth);
    }

}

