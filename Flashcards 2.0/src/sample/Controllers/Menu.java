package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.IOHandler;
import sample.Term;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Menu {
    public ChoiceBox termGroup;
    public TextField txtSearch;
    public TextField txtNewGroup;
    public Label lblWarning;

    ObservableList<String> termGroupList = FXCollections.observableArrayList("All", "Default Group");

    public void initialize() throws IOException{
        IOHandler.setIndex(0);
        IOHandler.setUserAnswer("");
        IOHandler.setDisplay("");
        IOHandler.setGroup("");
        IOHandler.write("group.txt", true);
        IOHandler.write("Default Group.txt", true);
        termGroup.setItems(termGroupList);
        termGroup.getSelectionModel().select(1);
        termGroup.setDisable(false);
        FileReader fr = new FileReader("group.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null) {
            termGroupList.add(line);
        }
        br.close();
    }

    //Switch scenes, getting groups selected and terms
    public void search(ActionEvent actionEvent) throws IOException {
        if (txtSearch.getText().trim().length()>0){
            lblWarning.setText("");
            IOHandler.search(txtSearch.getText());
            switchScene("SearchTerm");
        }else lblWarning.setText("Enter a term to search.");
    }

    public void add(ActionEvent actionEvent) throws IOException {
        if (!termGroup.getSelectionModel().getSelectedItem().equals("All")){
            IOHandler.setGroup(termGroup.getSelectionModel().getSelectedItem().toString());
            switchScene("AddTerm");
        }else lblWarning.setText("You must add the new term in a specific term group.");
    }

    public void learn(ActionEvent actionEvent) throws IOException {
        IOHandler.setTerms(getTerms());
        IOHandler.setGroup(termGroup.getSelectionModel().getSelectedItem().toString());
        switchScene("LearnTerm");
    }

    public void quiz(ActionEvent actionEvent) throws IOException {
        IOHandler.setTerms(shuffle(getTerms()));
        switchScene("Quiz");
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

    //Add a group. If the group doesn't already exist and isn't blank, add a file for the new group and write it into group.txt
    public void addGroup(ActionEvent actionEvent) throws IOException{
        if (!termGroupList.contains(txtNewGroup.getText())){
            if (txtNewGroup.getText().trim().length()>0){
                lblWarning.setText("");
                FileWriter fw = new FileWriter("group.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(txtNewGroup.getText() + "\n");
                bw.close();
                IOHandler.write(txtNewGroup.getText() + ".txt", true);
                termGroupList.add(txtNewGroup.getText());
                txtNewGroup.clear();
            }else lblWarning.setText("Enter a term group to add.");
        }else lblWarning.setText(txtNewGroup.getText()+" already exists.");
    }

    //Get the terms of the selected group
    private ArrayList<Term> getTerms() throws IOException {
        if (termGroup.getSelectionModel().getSelectedItem().equals("All")) return IOHandler.getAllTerms();
        else  return IOHandler.getAllTerms(termGroup.getSelectionModel().getSelectedItem()+".txt");
    }

    //Shuffle the terms in an arraylist
    private ArrayList<Term> shuffle(ArrayList<Term> terms){
        Random random = new Random();
        for (int i = 0; i < terms.size()-1; i ++){
            //Record a random term and the term of index i.
            int rand = random.nextInt(i+1);
            Term temp = terms.get(rand);
            Term temp2 = terms.get(i);
            //Remove i and add the random term to index position i
            terms.remove(i);
            terms.add(i, temp);
            //Remove random term and add previous term of index i to the index position of the random term
            terms.remove(rand);
            terms.add(rand, temp2);
        }
        return terms;
    }
}
