package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.IOHandler;
import sample.Term;

import java.io.IOException;
import java.util.ArrayList;

public class LearnTerm {

    public Label lblWarning;
    public ListView listLearn;
    public Button btnFC;
    private ArrayList<Term> terms;

    //Initialize. Set texts and buttons to wanted state. If no term in the group, then all invisible and provide warning.
    //If there are terms in the group, then add terms of the group to the listLearn listView
    public void initialize() throws  IOException{
        terms = IOHandler.getTerms();
        if (terms.size()==0){
            lblWarning.setText("No term is inside this group");
            btnFC.setVisible(false);
            listLearn.setVisible(false);
        }else {
            for (Term term : terms){
                listLearn.getItems().add(term);
            }
        }
    }

    //Display term of the selected term
    public void Display(MouseEvent mouseEvent) throws  IOException{
        if (listLearn.getSelectionModel().getSelectedItem() != null) {
            IOHandler.setTerm(terms.get(listLearn.getSelectionModel().getSelectedIndex()));
            IOHandler.setDisplay("LearnTerm");
            switchScene("DisplayTerm");
        }
    }

    //Switch scene to menu
    public void Back(ActionEvent actionEvent) throws IOException {
        switchScene("Menu");
    }

    //Switch scene to flashcard
    public void Flashcard(ActionEvent actionEvent) throws IOException {
        IOHandler.setTerms(terms);
        switchScene("Flashcard");
    }

    //Switch Scene
    private void switchScene(String path) throws IOException {
        lblWarning.setText("");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/scenes/" + path + ".fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
        lblWarning.getScene().getWindow().hide();
    }
}
