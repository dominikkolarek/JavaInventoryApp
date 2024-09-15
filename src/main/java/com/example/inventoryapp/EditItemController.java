package com.example.inventoryapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditItemController {

    @FXML
    private TextField nameField, valueField, weightField, specialAttributeField;
    @FXML
    private Label specialAttributeLabel;

    private Item itemToEdit;
    private InventoryController mainController;

    public void initializeItem(Item item) {
        this.itemToEdit = item;
        nameField.setText(item.getName());
        valueField.setText(String.valueOf(item.getValue()));
        weightField.setText(String.valueOf(item.getWeight()));

        String label = "Special Attribute:";
        String value = "";
        switch (item) {
            case Weapon weapon -> {
                label = "Damage:";
                value = String.valueOf(weapon.getDamage());
            }
            case Armor armor -> {
                label = "Defense:";
                value = String.valueOf(armor.getDefenseBoost());
            }
            case Potion potion -> {
                label = "Heal Amount:";
                value = String.valueOf(potion.getHealAmount());
            }
            default -> {
            }
        }
        specialAttributeLabel.setText(label);
        specialAttributeField.setText(value);
    }

    public void setMainController(InventoryController controller) {
        this.mainController = controller;
    }

    @FXML
    private void handleSaveChanges() {
        try {
            itemToEdit.setName(nameField.getText());
            itemToEdit.setValue(Integer.parseInt(valueField.getText()));
            itemToEdit.setWeight(Double.parseDouble(weightField.getText()));

            int specialAttribute = Integer.parseInt(specialAttributeField.getText());
            if (itemToEdit instanceof Weapon) {
                ((Weapon) itemToEdit).setDamage(specialAttribute);
            } else if (itemToEdit instanceof Armor) {
                ((Armor) itemToEdit).setDefenseBoost(specialAttribute);
            } else if (itemToEdit instanceof Potion) {
                ((Potion) itemToEdit).setHealAmount(specialAttribute);
            }

            mainController.updateInventoryList();
            mainController.saveInventory();
            closeWindow();
        } catch (NumberFormatException e) {
            showAlert();
        }
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
}
