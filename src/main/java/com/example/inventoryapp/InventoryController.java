package com.example.inventoryapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryController {

    @FXML
    private ListView<Item> inventoryListView;

    @FXML
    private Label statsLabel;

    @FXML
    private TextArea itemStatsTextArea;

    private List<Item> inventory;
    private ObservableList<Item> filteredInventory;
    private static final String FILE_PATH = "inventory.json";


    private enum FilterType { ALL, WEAPONS, ARMOR, POTIONS }

    private FilterType currentFilter = FilterType.ALL;


    @FXML
    public void initialize() {
        inventory = new ArrayList<>();
        filteredInventory = FXCollections.observableArrayList();
        Stats.loadStats();
        loadInventory();

        updateInventoryList();
        updateStatsDisplay();
        setupListViewListener();
    }


    public void saveInventory() {
        try {
            JsonControl.saveItemsToJson(inventory, FILE_PATH);
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            showAlert("Error", "Failed to save inventory: " + e.getMessage());
        }
    }

    private void loadInventory() {
        try {
            inventory = JsonControl.loadItemsFromJson(FILE_PATH);
            System.out.println("Inventory loaded successfully.");
        } catch (IOException e) {
            System.out.println("Failed to load inventory: " + e.getMessage());
            inventory = new ArrayList<>();
        }
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
        applyCurrentFilter();
        saveInventory();
        Stats.saveStats();
    }

    @FXML
    private void handleAddItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-item-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Add New Item");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            AddItemController addItemController = loader.getController();
            addItemController.setMainController(this);

            stage.showAndWait();
        } catch (IOException e) {
            showAlert("Error", "Failed to load the Add Item screen.");
        }
    }

    @FXML
    private void handleEditItem() {
        Item selectedItem = inventoryListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.isEquipped()) {
                showAlert("Cannot Edit", "Equipped items cannot be edited. Please unequip the item first.");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-item-view.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setTitle("Edit Item");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);

                EditItemController editItemController = loader.getController();
                editItemController.initializeItem(selectedItem);
                editItemController.setMainController(this);

                stage.showAndWait();
            } catch (IOException e) {
                showAlert("Error", "Failed to load the Edit Item screen.");
            }
        } else {
            showAlert("No Item Selected", "Please select an item to edit.");
        }
    }

    @FXML
    private void handleDeleteItem() {
        Item selectedItem = inventoryListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.isEquipped()) {
                showAlert("Cannot Delete", "Equipped items cannot be deleted. Please unequip the item first.");
                return;
            }

            inventory.removeIf(item -> item == selectedItem);
            applyCurrentFilter();
            saveInventory();
            Stats.saveStats();
        } else {
            showAlert("No Item Selected", "Please select an item to delete.");
        }
    }

    @FXML
    private void handleShowAll() {
        currentFilter = FilterType.ALL;
        filteredInventory.setAll(inventory);
        inventoryListView.setItems(filteredInventory);
    }

    @FXML
    private void handleShowWeapons() {
        currentFilter = FilterType.WEAPONS;
        filteredInventory.setAll(inventory.stream()
                .filter(item -> item instanceof Weapon)
                .collect(Collectors.toList()));
        inventoryListView.setItems(filteredInventory);
    }

    @FXML
    private void handleShowArmor() {
        currentFilter = FilterType.ARMOR;
        filteredInventory.setAll(inventory.stream()
                .filter(item -> item instanceof Armor)
                .collect(Collectors.toList()));
        inventoryListView.setItems(filteredInventory);
    }


    @FXML
    private void handleShowPotions() {
        currentFilter = FilterType.POTIONS;
        filteredInventory.setAll(inventory.stream()
                .filter(item -> item instanceof Potion)
                .collect(Collectors.toList()));
        inventoryListView.setItems(filteredInventory);
    }

    private void applyCurrentFilter() {
        switch (currentFilter) {
            case WEAPONS:
                handleShowWeapons();
                break;
            case ARMOR:
                handleShowArmor();
                break;
            case POTIONS:
                handleShowPotions();
                break;
            case ALL:
            default:
                handleShowAll();
                break;
        }
    }

    public void updateInventoryList() {
        applyCurrentFilter();
    }

    @FXML
    private void handleUseItem() {
        Item selectedItem = inventoryListView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Usable) {
            ((Usable) selectedItem).use();
            if (selectedItem instanceof Potion) {
                inventory.remove(selectedItem);
            }
            applyCurrentFilter();
            updateStatsDisplay();
            saveInventory();
            Stats.saveStats();
        } else {
            showAlert("No item selected or item is not usable", "Please select a usable item to use.");
        }
    }

    @FXML
    private void handleUnequipItem() {
        Item selectedItem = inventoryListView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Usable) {
            ((Usable) selectedItem).unequip();
            applyCurrentFilter();
            updateStatsDisplay();
            saveInventory();
            Stats.saveStats();
        } else {
            showAlert("No item selected or item is not equippable", "Please select an equippable item to unequip.");
        }
    }

    @FXML
    private void handleTakeDamage() {
        Stats.takeDamage(20);
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
