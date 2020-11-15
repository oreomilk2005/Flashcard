package sample;

import java.io.*;
import java.util.ArrayList;

public class IOHandler {
    private static ArrayList<Term> terms = new ArrayList<Term>();
    private static Term term;
    private static String group;
    private static String display;
    private static String userAnswer;
    private static int index;
    private static int score;

    //Returns an arraylist of all terms in the group.
    public static ArrayList<Term> getAllTerms(String fileName) throws IOException{
        ArrayList<Term> allTerm = new ArrayList<Term>();
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int i = 0;
        String[] fields = new String[3];
        //A term occupies three lines. We record each line into the array field. Once three fields have been filled out, we add the term and repeat until the line is null.
        while((line = br.readLine()) != null){
            fields[i++] = line;
            if (i==3){
                i=0;
                allTerm.add(new Term(fields[0], fields[1], fields[2], fileName.substring(0, fileName.length()-4)));
            }
        }
        br.close();
        return allTerm;
    }

    public static ArrayList<String> getAllGroups() throws IOException{
        //Read all groups in group.txt
        ArrayList<String> fileNames = new ArrayList<String>();
        FileReader fr = new FileReader("group.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null){
            //Add all groups read into the arraylist fileNames, with .txt (text file name)
            fileNames.add(line+".txt");
        }
        br.close();
        //Add Default Group
        fileNames.add("Default Group.txt");
        return fileNames;
    }

    public static ArrayList<Term> getAllTerms() throws IOException{
        ArrayList<Term> allTerms = new ArrayList<Term>();
        //Use getAllTerms(fileName) and getAllGroups() to get all the terms from all groups
        for (String fileName: getAllGroups()){
            ArrayList<Term> tempTerms = getAllTerms(fileName);
            for (Term t : tempTerms){
                allTerms.add(t);
            }
        }
        return allTerms;
    }

    //In all groups, add all terms that starts with the letters the user searches
    public static void search(String word) throws IOException{
        terms.clear();
        for (Term t : getAllTerms()){
            if (t.getName().length()>=word.length() && t.getName().startsWith(word)){
                terms.add(t);
            }
        }
    }

    //Clear or create files
    public static void write(String fileName, boolean append) throws IOException{
        FileWriter fw = new FileWriter(fileName, append);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("");
        bw.close();
    }

    //Getters and setters
    public static ArrayList<Term> getTerms() { return terms; }
    public static void setTerms(ArrayList<Term> terms){
        IOHandler.terms = terms;
    }
    public static void setTerm(Term term) { IOHandler.term = term; }
    public static Term getTerm(){
        return term;
    }
    public static String getGroup() { return group; }
    public static void setGroup(String group) { IOHandler.group = group; }
    public static String getDisplay() { return display; }
    public static void setDisplay(String display) { IOHandler.display = display; }
    public static int getIndex() { return index; }
    public static void setIndex(int index) { IOHandler.index = index; }
    public static String getUserAnswer() { return userAnswer; }
    public static void setUserAnswer(String userAnswer) { IOHandler.userAnswer = userAnswer; }
    public static int getScore() { return score; }
    public static void setScore(int score) { IOHandler.score = score; }
}
