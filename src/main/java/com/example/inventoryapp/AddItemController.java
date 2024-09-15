package com.example.inventoryapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddItemController {

    @FXML
    private ChoiceBox<String> itemTypeChoiceBox;
    @FXML
    private TextField nameField, valueField, weightField, specialAttributeField;
    @FXML
    private Label specialAttributeLabel;

    private InventoryController mainController;

    @FXML
    public void initialize() {
        itemTypeChoiceBox.setItems(FXCollections.observableArrayList("Weapon", "Armor", "Potion"));
        itemTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateSpecialAttributeLabel(newValue));
    }

    private void updateSpecialAttributeLabel(String itemType) {
        specialAttributeLabel.setText(switch (itemType) {
            case "Weapon" -> "Damage:";
            case "Armor" -> "Defense:";
            case "Potion" -> "Heal Amount:";
            default -> "";
        });
    }

    @FXML
    private void handleAddItem() {
        try {
            int value = Integer.parseInt(valueField.getText());
            double weight = Double.parseDouble(weightField.getText());
            Item newItem = getItem(value, weight);

            if (newItem != null) {
                mainController.addItemToInventory(newItem);
                closeWindow();
            }
        } catch (NumberFormatException e) {
            showAlert();
        }
    }

    private Item getItem(int value, double weight) {
        int specialAttribute = Integer.parseInt(specialAttributeField.getText());

        String selectedType = itemTypeChoiceBox.getValue();
        return switch (selectedType) {
            case "Weapon" -> new Weapon(nameField.getText(), value, weight, specialAttribute);
            case "Armor" -> new Armor(nameField.getText(), value, weight, specialAttribute);
            case "Potion" -> new Potion(nameField.getText(), value, weight, specialAttribute);
            default -> null;
        };
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) nameField.getScene().getWindow()).close();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Please enter valid numbers for value, weight, and special attributes.");
        alert.showAndWait();
    }

    public void setMainController(InventoryController controller) {
        this.mainController = controller;
    }
}
