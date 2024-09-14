package com.example.inventoryapp;

public class Potion extends Item implements Usable {
    private int healAmount;

    public Potion(String name, int value, double weight, int healAmount) {
        super(name, value, weight);
        this.healAmount = healAmount;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    public void use() {
        Stats.health = Math.min(Stats.health + this.healAmount, Stats.maxHealth);
        System.out.println("Used " + getName() + ". Health restored by " + healAmount);
    }

    @Override
    public void unequip() {
        System.out.println("You can't unequip this!");
    }

    @Override
    public String displayInfo() {
        return String.format("Potion: %s\nValue: %d\nWeight: %.2f\nHeal Amount: %d",
                getName(), getValue(), getWeight(), healAmount);
    }
}
