package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.IOHandler;

import java.io.IOException;

public class Result {
    public Label lblScore;
    private int index;
    private int score;

    //Show result
    public void initialize(){
        index = sample.Controllers.Quiz.getIndex();
        score = IOHandler.getScore();
        //Show the result as score/page number
        lblScore.setText(score + "/" + (index+1));
    }

    //Switch scene to menu and reset score
    public void menu(ActionEvent actionEvent) throws IOException {
        switchScene("Menu");
        IOHandler.setScore(0);
    }

    //Switch scene
    private void switchScene(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/scenes/" + path + ".fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
        lblScore.getScene().getWindow().hide();
    }
}
