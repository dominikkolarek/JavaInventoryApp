    package com.example.inventoryapp;

    public abstract class Item {
        private String name;
        private int value;
        private double weight;
        private boolean equipped;

        public Item(String name, int value, double weight) {
            this.name = name;
            this.value = value;
            this.weight = weight;
            this.equipped = false;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public boolean isEquipped() {
            return equipped;
        }

        public void setEquipped(boolean equipped) {
            this.equipped = equipped;
        }

        public abstract void use();

        public abstract String displayInfo();

        @Override
        public String toString() {
            return name;
        }
        }

