package com.example.inventoryapp;

public class Armor extends Item implements Usable {
    private int defenseBoost;

    public Armor(String name, int value, double weight, int defenseBoost) {
        super(name, value, weight);
        this.defenseBoost = defenseBoost;
    }

    public int getDefenseBoost() {
        return defenseBoost;
    }

    public void setDefenseBoost(int defenseBoost) {
        this.defenseBoost = defenseBoost;
    }

    @Override
    public void use() {
        if (!isEquipped()) {
            Stats.defense += this.defenseBoost;
            setEquipped(true);
            System.out.println("Equipped " + getName() + ". Defense increased by " + defenseBoost);
        } else {
            System.out.println(getName() + " is already equipped!");
        }
    }

    @Override
    public void unequip() {
        if (isEquipped()) {
            Stats.defense -= this.defenseBoost;
            setEquipped(false);
            System.out.println("Unequipped " + getName() + ". Defense decreased by " + defenseBoost);
        } else {
            System.out.println(getName() + " is not equipped!");
        }
    }

    @Override
    public String displayInfo() {
        return String.format("Armor: %s\nValue: %d\nWeight: %.2f\nDefense: %d\nEquipped: %s",
                getName(), getValue(), getWeight(), defenseBoost, isEquipped() ? "Yes" : "No");
    }
}
