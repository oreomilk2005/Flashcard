package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.IOHandler;
import sample.Term;

import java.io.IOException;
import java.util.ArrayList;

public class Quiz {
    public Button btnEndQuiz;
    public Button btnSeeAnswer;
    public Button btnNext;
    public Label lblTerm;
    public TextArea txtDefinition;
    public Label lblPageNum;
    public Label lblWarning;
    public Label lblDefinition;
    public Label lblResult;
    private boolean keyTyped;
    private ArrayList<Term> terms;
    private static int index;
    private String result;

    public static int getIndex() { return index; }

    public void initialize() throws IOException {
        index = IOHandler.getIndex();
        terms = IOHandler.getTerms();
        keyTyped = false;
        //Start the quiz if there is terms in the group. If not, give warnings
        if (terms.size()==0){
            lblWarning.setText("No term is inside this group");
            lblDefinition.setText("");
            lblPageNum.setText("");
            lblTerm.setText("");
            btnEndQuiz.setText("Back");
            btnEndQuiz.setVisible(true);
            btnNext.setVisible(false);
            btnSeeAnswer.setVisible(false);
            txtDefinition.setVisible(false);
            lblResult.setVisible(false);
        }else if(IOHandler.getDisplay().equals("Quiz")){
            txtDefinition.setText(IOHandler.getUserAnswer());
            txtDefinition.setEditable(false);
            btnEndQuiz.setVisible(true);
            btnNext.setVisible(true);
            btnSeeAnswer.setVisible(true);
            if (index==terms.size()-1){
                btnNext.setVisible(false);
            }
            lblResult.setVisible(true);
            lblResult.setText(result);
            System.out.println("result: " + result);
            lblTerm.setText(terms.get(index).getName());
            lblPageNum.setText(index+1 +" / "+terms.size());
        }else {
            set();
        }
    }


    public void keyTyped(KeyEvent keyEvent) {
        //Show result of the question after enter key is pressed
        if (!keyTyped && keyEvent.getCode()== KeyCode.ENTER){
           lblResult.setVisible(true);
           //If real definition and the definition the user entered are equal, then result is correct. If not, result is incorrect
            if(txtDefinition.getText().equals(terms.get(index).getDefinition())){
                IOHandler.setScore(IOHandler.getScore() + 1);
                result = "Correct";
            } else result = "Incorrect";
            //Show result and several buttons for the user
            lblResult.setText(result);
            txtDefinition.setEditable(false);
            btnEndQuiz.setVisible(true);
            btnSeeAnswer.setVisible(true);
            btnNext.setVisible(true);
            if (index==terms.size()-1){
                btnNext.setVisible(false);
            }
            IOHandler.setUserAnswer(txtDefinition.getText());
            keyTyped = true;
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        keyTyped = false;
    }

    //Increase the index by one and set the question to the next term
    public void next(ActionEvent actionEvent) {
        index++;
        set();
    }

    //Switch scenes
    public void endQuiz(ActionEvent actionEvent) throws IOException {
        if (btnEndQuiz.getText().equals("Back")){
            switchScene("Menu");
            IOHandler.setScore(0);
        }else {
            switchScene("result");
        }
    }

    public void displayTerm(ActionEvent actionEvent) throws IOException {
        IOHandler.setDisplay("Quiz");
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
        lblWarning.getScene().getWindow().hide();
    }

    private void set(){
        //Set to the current question, hiding results and other post-question buttons
        txtDefinition.setText("");
        txtDefinition.setEditable(true);
        btnSeeAnswer.setVisible(false);
        btnEndQuiz.setVisible(false);
        btnNext.setVisible(false);
        lblResult.setVisible(false);
        lblTerm.setText(terms.get(index).getName());
        lblPageNum.setText(index+1 +" / "+terms.size());
    }
}
