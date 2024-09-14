package com.example.inventoryapp;

public class Weapon extends Item implements Usable{
    private int damage;

    public Weapon(String name, int value, double weight, int damage) {
        super(name, value, weight);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void use() {
        Stats.attack += this.damage;
        System.out.println("Equipped " + getName() + ". Attack increased by " + damage);
    }

    @Override
    public void unequip() {
        Stats.attack -= this.damage;
        System.out.println("Unequipped " + getName() + ". Attack decreased by " + damage);
    }

    @Override
    public String displayInfo() {
        return String.format("Weapon: %s\nValue: %d\nWeight: %.2f\nDamage: %d",
                getName(),  getValue(), getWeight(), damage);
    }
}
