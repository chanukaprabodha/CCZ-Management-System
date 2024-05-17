package lk.ijse.ccz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    private static boolean isTextFieldValid(TextField textField, String text) {
        String field ="";

        switch (textField) {
            case EMAIL:
                field = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case Eid :
                field = "^[e][0-9]{3}$";
                break;
            case CONTACT :
                field = "^([0])([1-9]{2})([0-9]){7}$";
                break;
            case Uid:
                field = "^[u][0-9]{3}$";
                break;
            case Cid:
                field = "^[c][0-9]{3}$";
                break;
            case STOCK:
                field = "^\\d{0,8}[.]?\\d{1,4}$";
                break;
            case PRICE:
                field = "^\\d{0,8}[.]?\\d{1,4}$";
                break;
            case Iid:
                field = "^[i][0-9]{3}$";
                break;
            case PASSWORD:
                field = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
                break;
            case NAME:
                field = "^[A-z][A-z0-9.]{1,}$";
                break;
            case ADDRESS:
                field = "^[A-z][A-z0-9.]{1,}$";
                break;
        }

        Pattern pattern = Pattern.compile(field);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        } else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }


    public static boolean setTextColor(TextField location, javafx.scene.control.TextField field){
        if (isTextFieldValid(location,field.getText())) {
            field.setStyle("-fx-border-color: blue");
            return true;
        } else {
            field.setStyle("-fx-border-color: red");
            return false;
        }
    }

}
