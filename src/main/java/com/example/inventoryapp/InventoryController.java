package com.example.inventoryapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.List;

public class InventoryController {

    @FXML
    private ListView<Item> inventoryListView;

    @FXML
    private Label statsLabel;

    @FXML
    private TextArea itemStatsTextArea;

    private List<Item> inventory;

    @FXML
    public void initialize() {
        inventory = new ArrayList<>();
        addSampleItems();
        updateInventoryList();
        updateStatsDisplay();
        setupListViewListener();
    }


    private void addSampleItems() {
        Weapon sword = new Weapon("Sword", 100, 5.0, 15);
        Armor shield = new Armor("Shield", 75, 7.0, 10);
        Potion healthPotion = new Potion("Health Potion", 50, 1.0, 20);

        inventory.add(sword);
        inventory.add(shield);
        inventory.add(healthPotion);
    }


    private void updateInventoryList() {
        inventoryListView.setItems(FXCollections.observableArrayList(inventory));
    }


    @FXML
    private void handleUseItem() {
        Item selectedItem = inventoryListView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Usable) {
            ((Usable) selectedItem).use();
            if (selectedItem instanceof Potion) {
                inventory.remove(selectedItem); // Remove potion after use
            }
            updateInventoryList();
            updateStatsDisplay();
        } else {
            showAlert("No item selected or item is not usable", "Please select a usable item to use.");
        }
    }


    @FXML
    private void handleUnequipItem() {
        Item selectedItem = inventoryListView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Usable) {
            ((Usable) selectedItem).unequip();
            updateInventoryList();
            updateStatsDisplay();
        } else {
            showAlert("No item selected or item is not equippable", "Please select an equippable item to unequip.");
        }
    }


    @FXML
    private void handleTakeDamage() {
        Stats.takeDamage(20); // Simulate taking 20 damage
        updateStatsDisplay();
    }


    private void updateStatsDisplay() {
        statsLabel.setText(String.format("Health: %d/%d\nAttack: %d\nDefense: %d",
                Stats.health, Stats.maxHealth, Stats.attack, Stats.defense));
    }


    private void setupListViewListener() {
        inventoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                itemStatsTextArea.setText(newValue.displayInfo());
            } else {
                itemStatsTextArea.clear();
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
