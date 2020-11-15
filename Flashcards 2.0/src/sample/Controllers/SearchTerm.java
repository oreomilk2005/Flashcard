package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.IOHandler;
import sample.Term;

import java.io.IOException;
import java.util.*;

public class SearchTerm {
    public ListView listResults;
    public Label lblWarning;
    private ArrayList<Term> terms;

    public void initialize(){
        //If there are no terms in the arraylist from the search method, then show "No Result Found" and hide result list
        terms = IOHandler.getTerms();
        if (terms.size()==0){
            lblWarning.setText("No Result Found.");
            listResults.setVisible(false);
        //If there is a result, sort them by length and show the results
        }else {
            sort(terms, 0, terms.size()-1);
            for (Term term : terms){
                listResults.getItems().add(term);
            }
        }
    }

    //sort by length, using the quick sort method
    /*
    we use the partition method(see explanation below) to move terms smaller than the partition on the left and greater on the right
    then, we sort the left side and right side separately
    when the left index equal the right index, which means that there is only one term left to sort, then we finish the sorting
     */
    private void sort(ArrayList<Term> terms, int l, int r){
        if (l>=r)return;
        int p = partition(terms, l, r);
        sort(terms, l, p-1);
        sort(terms, p+1, r);
    }

    /*
    first, we compare the length of the right most term(partition) with the length of all terms
    if the length of a term is smaller than the partition, we switch its position with the barrier
    after all terms are compared, we switch the partition with the barrier
    thus, making all terms smaller than the partition on the left and terms greater on the right
    then, we record the barrier index and sort the left and right side separately (see sort method explanation)
     */
    private int partition(ArrayList<Term> terms, int l, int r){
        int b = l;
        for (int i = l; i < r; i ++){
            if (terms.get(i).getName().length() < terms.get(r).getName().length()){
                Term temp = terms.get(i);
                Term temp2 = terms.get(b);
                terms.remove(i);
                terms.add(i, temp2);
                terms.remove(b);
                terms.add(b++, temp);
            }
        }
        Term temp = terms.get(r);
        Term temp2 = terms.get(b);
        terms.remove(r);
        terms.add(r, temp2);
        terms.remove(b);
        terms.add(b, temp);
        return b;
    }

    //Switch scene to menu
    public void Menu(ActionEvent actionEvent) throws IOException {
        switchScene("Menu");
    }

    //Switch scene to display
    public void Display(MouseEvent actionEvent) throws IOException {
        IOHandler.setTerm(terms.get(listResults.getSelectionModel().getSelectedIndex()));
        //Since the display scene is used for different purposes, we set it to "SearchTerm"
        IOHandler.setDisplay("SearchTerm");
        switchScene("DisplayTerm");
    }

    //Switch scene
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
