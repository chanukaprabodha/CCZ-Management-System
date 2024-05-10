package lk.ijse.ccz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static boolean isTextFieldValid(TextField textField, String text) {
        String filed = "";

        switch (textField) {
            case NAME:
                filed = "^[A-z|\\\\s]{3,}$";
                break;

            case EMAIL:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;

            case CONTACT:
                filed = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;

            case ADDRESS:
                filed = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
                break;

            case STOCK:
                filed = "^\\d+$";
                break;

            case PRICE:
                filed = "[0-9](.[0-9]*)?";
                break;

            case ID:
                filed = "^[a-zA-Z0-9._-]{3,}$";
                break;

            case PASSWORD:
                filed = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
                break;

            case USERNAME:
                filed = "^[a-zA-Z0-9._-]{3,}$";
                break;
        }

        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField) {
        if (Regex.isTextFieldValid(location, textField.getText())) {

            textField.setStyle("-fx-text-fill: White; -fx-background-color: transparent; -fx-border-color: #19460a; -fx-border-width: 0px 0px 2px 0px;");
        } else {
            textField.setStyle("-fx-text-fill: red; -fx-background-color: transparent; -fx-border-color: #19460a; -fx-border-width: 0px 0px 2px 0px;");

            return false;
        }
        return false;
    }
}
