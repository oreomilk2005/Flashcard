package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.IOHandler;
import sample.Term;

import java.io.IOException;
import java.util.ArrayList;

public class DisplayTerm {
    public Label lblName;
    public TextArea txtNotes;
    public TextArea txtDefinition;
    public Label lblGroup;
    public Button btnBack;
    public HBox cover;
    public Label lblFrom;
    public Button btnDelete;

    public void initialize(){
        //Initialize: show the fields of the term displayed and set the buttons to desired state
        cover.setVisible(false);
        txtNotes.setEditable(false);
        txtDefinition.setEditable(false);
        lblGroup.setText(IOHandler.getTerm().getGroup());
        lblName.setText(IOHandler.getTerm().getName());
        txtNotes.setText(IOHandler.getTerm().getNotes());
        txtDefinition.setText(IOHandler.getTerm().getDefinition());
        btnDelete.setVisible(false);
        if (!IOHandler.getGroup().equals("All")){
            lblFrom.setText("");
            lblGroup.setText("");
        }
        if (IOHandler.getDisplay().equals("Flashcard")){
            btnBack.setDisable(true);
            btnBack.setVisible(false);
            cover.setVisible(true);
        }else if (IOHandler.getDisplay().equals("LearnTerm")){
            btnDelete.setVisible(true);
        }
    }

    //Switch scenes
    public void Back(ActionEvent actionEvent) throws IOException {
        switchScene(IOHandler.getDisplay());
    }

    public void flash(MouseEvent mouseEvent) throws IOException {
        switchScene("Flashcard");
    }

    private void switchScene(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/scenes/" + path + ".fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
        lblName.getScene().getWindow().hide();
    }

    //Delete the term
    public void delete(ActionEvent actionEvent) throws IOException {
        ArrayList<Term> tempTerms = IOHandler.getAllTerms(IOHandler.getTerm().getGroup()+".txt");
        //Make an arraylist with all terms in the group except for the deleted term
        ArrayList<Term> newTerms = new ArrayList<Term>();
        for (Term t : tempTerms){
            if (!t.equals(IOHandler.getTerm())) newTerms.add(t);
        }
        //Clear the group file and rewrite it with terms in the arraylist that does not have the deleted term
        IOHandler.write(IOHandler.getTerm().getGroup()+".txt", false);
        for (Term t : newTerms){
            t.writeToFile(IOHandler.getTerm().getGroup()+".txt");
        }
        IOHandler.setTerms(newTerms);
        switchScene(IOHandler.getDisplay());
    }
}
