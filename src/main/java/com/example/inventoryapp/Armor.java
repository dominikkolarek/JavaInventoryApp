package com.example.inventoryapp;

public class Armor extends Item implements Usable{
    private int defenseBoost;

    public Armor(String name, int value, double weight, int defenseBoost) {
        super(name, value, weight);
        this.defenseBoost = defenseBoost;
    }

    public int getDefenseBoost() {
        return defenseBoost;
    }

    public void setDefenseBoost(int defense) {
        this.defenseBoost = defenseBoost;
    }

    @Override
    public void use() {
        Stats.defense += this.defenseBoost;
        System.out.println("Equipped " + getName() + ". Defense increased by " + defenseBoost);
    }

    @Override
    public void unequip() {
        Stats.defense -= this.defenseBoost;
        System.out.println("Unequipped " + getName() + ". Defense decreased by " + defenseBoost);
    }

    @Override
    public String displayInfo() {
        return String.format("Weapon: %s\nValue: %d\nWeight: %.2f\nDefense: %d",
                getName(),  getValue(), getWeight(), defenseBoost);
    }
}
