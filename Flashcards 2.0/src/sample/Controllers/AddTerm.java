package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.IOHandler;
import sample.Term;

import java.io.IOException;
import java.util.ArrayList;

public class AddTerm {
    public Label lblWarning;
    public TextField txtName;
    public TextArea txtDf;
    public TextArea txtNotes;

    public void add(ActionEvent actionEvent) throws IOException {
        //Get the group the user selected in the menu
        String group = IOHandler.getGroup();
        //Check if the name of the term the user wrote is not blank. If blank, provide a warning. If not blank,
        if(txtName.getText().trim().length()>0) {
            //Record the term with the fields that the user wrote
            Term term = new Term(txtName.getText(), txtDf.getText(), txtNotes.getText(), group);
            ArrayList<Term> terms = IOHandler.getAllTerms(group + ".txt");
            //Check if the term is already in the group. If it is, we provide a warning. If not, we write the term to its group file.
            for (Term term2 : terms) {
                if (term.equals(term2)) {
                    lblWarning.setText("The same term has been added.");
                    return;
                }
            }
            term.writeToFile(group + ".txt");
        } else lblWarning.setText("Enter a term to add");
        //Clear all text fields and areas
        txtName.clear();
        txtDf.clear();
        txtNotes.clear();
    }

    //Switch to the menu scene
    public void Menu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/scenes/Menu.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
        lblWarning.getScene().getWindow().hide();
    }
}
