/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.toolkit;


import com.jfoenix.controls.JFXDatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author flori
 */
public final class Tools {

    private Tools() {
    }

    public static String getFirstTwoNames(String name) {
        String[] names = name.trim().split(" +");
        if (names.length == 1) {
            return names[0];
        }else{
            return names[0] + " " + names[1];
        }
    }

    public static void changeDateConverter(DateTimeFormatter formatter, JFXDatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                return object != null ?object.format(formatter): "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                }else{
                    return null;
                }
            }
        });
    }

    public static String formatString(String string){
        if (string == null || string.isEmpty()) {
            return "";
        }
        String[] parts = string.trim().split(" +");
        String result = "";
        for (int i = 0; i < parts.length; i++) {
            StringBuilder stringBuilder = new StringBuilder(parts[i].toLowerCase());
            stringBuilder.setCharAt(0, Character.toUpperCase(parts[i].charAt(0)));
            result += stringBuilder.toString() + " ";
        }

        return result.trim();
        
    }

    public static void addNaturalNumberValidation(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    return;
                }

                if (!isNaturalNumber(newValue)) {
                    textField.setText(oldValue);
                }
            }
        });
    }

    public static boolean isNaturalNumber(String string) {
        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static void addDoubleValidation(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([.]\\d*)?")) {
                    textField.setText(oldValue);
                }
            }
        });
    }

    public static void addValidationToComboBox(ComboBox<String> comboBox) {
        comboBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && !comboBox.getItems().contains(comboBox.getValue().toUpperCase())) {//out of focus
                    comboBox.getSelectionModel().selectFirst();
                }
            }
        });
    }
}
