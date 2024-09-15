package com.example.inventoryapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonControl {

    public static void saveItemsToJson(List<Item> items, String filePath) throws IOException {
        JSONArray jsonArray = new JSONArray();
        items.forEach(item -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", item.getName());
            jsonObject.put("value", item.getValue());
            jsonObject.put("weight", item.getWeight());
            jsonObject.put("equipped", item.isEquipped());

            if (item instanceof Weapon) jsonObject.put("type", "Weapon").put("damage", ((Weapon) item).getDamage());
            else if (item instanceof Armor) jsonObject.put("type", "Armor").put("defenseBoost", ((Armor) item).getDefenseBoost());
            else if (item instanceof Potion) jsonObject.put("type", "Potion").put("healAmount", ((Potion) item).getHealAmount());

            jsonArray.put(jsonObject);
        });
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toString(4));
        }
    }

    public static List<Item> loadItemsFromJson(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            List<Item> defaultItems = generateDefaultItems();
            saveItemsToJson(defaultItems, filePath);
            return defaultItems;
        }

        String content = new BufferedReader(new FileReader(file)).lines().reduce("", String::concat).trim();
        if (content.isEmpty() || content.equals("[]")) {
            List<Item> defaultItems = generateDefaultItems();
            saveItemsToJson(defaultItems, filePath);
            return defaultItems;
        }

        JSONArray jsonArray = new JSONArray(content);
        List<Item> items = new ArrayList<>();
        jsonArray.forEach(obj -> {
            JSONObject jsonObject = (JSONObject) obj;
            String type = jsonObject.getString("type");
            String name = jsonObject.getString("name");
            int value = jsonObject.getInt("value");
            double weight = jsonObject.getDouble("weight");
            boolean equipped = jsonObject.getBoolean("equipped");

            Item item = switch (type) {
                case "Weapon" -> new Weapon(name, value, weight, jsonObject.getInt("damage"));
                case "Armor" -> new Armor(name, value, weight, jsonObject.getInt("defenseBoost"));
                case "Potion" -> new Potion(name, value, weight, jsonObject.getInt("healAmount"));
                default -> null;
            };
            if (item != null) {
                item.setEquipped(equipped);
                items.add(item);
            }
        });
        return items;
    }

    private static List<Item> generateDefaultItems() {
        return new ArrayList<>(List.of(
                new Weapon("Basic Sword", 50, 3.0, 10),
                new Armor("Basic Shield", 40, 5.0, 5),
                new Potion("Small Health Potion", 25, 0.5, 20)
        ));
    }


    public static void saveStatsToJson(Stats stats, String filePath) throws IOException {
        JSONObject jsonObject = new JSONObject()
                .put("health", stats.health)
                .put("maxHealth", stats.maxHealth)
                .put("attack", stats.attack)
                .put("defense", stats.defense);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonObject.toString(4));
        }
    }

    public static void loadStatsFromJson(String filePath) throws IOException {
        String content = new BufferedReader(new FileReader(filePath)).lines().reduce("", String::concat);
        JSONObject jsonObject = new JSONObject(content);
        Stats.health = jsonObject.getInt("health");
        Stats.maxHealth = jsonObject.getInt("maxHealth");
        Stats.attack = jsonObject.getInt("attack");
        Stats.defense = jsonObject.getInt("defense");
    }
}