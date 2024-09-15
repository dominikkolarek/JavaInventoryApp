package com.example.inventoryapp;

public class Weapon extends Item implements Usable {
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
        if (!isEquipped()) {
            Stats.attack += this.damage;
            setEquipped(true);
            System.out.println("Equipped " + getName() + ". Attack increased by " + damage);
        } else {
            System.out.println(getName() + " is already equipped!");
        }
    }

    @Override
    public void unequip() {
        if (isEquipped()) {
            Stats.attack -= this.damage;
            setEquipped(false);
            System.out.println("Unequipped " + getName() + ". Attack decreased by " + damage);
        } else {
            System.out.println(getName() + " is not equipped!");
        }
    }

    @Override
    public String displayInfo() {
        return String.format("Weapon: %s\nValue: %d\nWeight: %.2f\nDamage: %d\nEquipped: %s",
                getName(), getValue(), getWeight(), damage, isEquipped() ? "Yes" : "No");
    }
}
