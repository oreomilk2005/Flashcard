package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.IOHandler;
import sample.Term;

import java.io.IOException;
import java.util.ArrayList;

public class Flashcard {
    public Label lblName;
    public TextField txtCurrent;
    public Label lblNums;
    public Label lblWarning;
    private ArrayList<Term> terms;
    private int index;

    public void initialize(){
        terms = IOHandler.getTerms();
        index = IOHandler.getIndex();
        set();
        lblNums.setText("/ "+terms.size());
    }

    //Decrease the index by one and set the term to the term of that index.
    //If it's already the smallest page number, then set the index to the largest page number-1
    public void last(ActionEvent actionEvent) {
        if (index!=0) {
            index--;
        }else{
            index=terms.size()-1;
        }
        set();
    }

    //Increase the index by one and set the term to the term of that index.
    //If it's already the largest page number, then set the index to 0
    public void next(ActionEvent actionEvent) {
        if (index!=terms.size()-1){
            lblWarning.setText("");
            index++;
        }else {
            index=0;
        }
        set();
    }

    //Method to change the page number
    //If the page number the user inputted is a valid input, then change the index and set the term to the page number
    //If not, provide a warning.
    public void changed(ActionEvent actionEvent) {
        int page = safeCastToNum(txtCurrent.getText());
        if (page>0&&page<=terms.size()){
            lblWarning.setText("");
            index=page-1;
            set();
        }else lblWarning.setText("Illegal input");
    }

    //Switch Scene
    public void LearnTerm(ActionEvent actionEvent) throws IOException {
        switchScene("LearnTerm");
    }

    public void flash(MouseEvent mouseEvent) throws IOException {
        IOHandler.setDisplay("Flashcard");
        IOHandler.setTerm(terms.get(index));
        IOHandler.setIndex(index);
        switchScene("DisplayTerm");
    }

    private void switchScene(String path) throws IOException {
        lblWarning.setText("");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/scenes/" + path + ".fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
        lblName.getScene().getWindow().hide();
    }

    //Set the flashcard labels to the current term
    private void set(){
        lblName.setText(terms.get(index).toString());
        txtCurrent.setText(Integer.toString(index+1));
    }

    //Safe cast a string to a number-prevent exceptions; number format exceptions are represented by -1
    private int safeCastToNum(String string){
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }return Integer.parseInt(string);
    }
}
