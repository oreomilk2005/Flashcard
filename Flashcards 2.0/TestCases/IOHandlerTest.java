import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sample.IOHandler;
import sample.Term;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class IOHandlerTest {
    ArrayList<String> existingGroups = new ArrayList<String>();
    ArrayList<String> defaultGroupTerms = new ArrayList<String>();
    ArrayList<String> testGroupList = new ArrayList<String>();
    ArrayList<Term> testAllTerms = new ArrayList<Term>();
    ArrayList<Term> testSearch = new ArrayList<Term>();

    @Before
    public void setup() throws IOException {
        //load the stuff inside Group.txt and Default Group.txt since we will change them during the test
        FileReader fr = new FileReader("Group.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine())!=null){
            existingGroups.add(line);
        }
        br.close();
        fr = new FileReader("Default Group.txt");
        br = new BufferedReader(fr);
        while ((line = br.readLine())!=null){
            defaultGroupTerms.add(line);
        }
        FileWriter fw = new FileWriter("Default Group.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("");
        bw.close();
        //setup test conditions of different lengths and characters
        fw = new FileWriter("group.txt");
        bw = new BufferedWriter(fw);
        bw.write("A\nB-\nCTheLongWord\n");
        bw.close();
        fw = new FileWriter("A.txt");
        bw = new BufferedWriter(fw);
        bw.write("Acoustic\nAmazing\nnotes\nAccount\nFor access to application\nNone\n");
        bw.close();
        fw = new FileWriter("B-.txt");
        bw = new BufferedWriter(fw);
        bw.write("Buoyant\ncheerful and optimistic!\ntone\n");
        bw.close();
        fw = new FileWriter("CTheLongWord.txt");
        bw = new BufferedWriter(fw);
        bw.write("Computer\nelectronic device\ncool, captivating\n");
        bw.close();
        testGroupList.add("A.txt");
        testGroupList.add("B-.txt");
        testGroupList.add("CTheLongWord.txt");
        testGroupList.add("Default Group.txt");
        testAllTerms.add(new Term("Acoustic", "Amazing", "notes", "A"));
        testAllTerms.add(new Term("Account", "For access to application", "None", "A"));
        testAllTerms.add(new Term("Buoyant", "cheerful and optimistic!", "tone", "B-"));
        testAllTerms.add(new Term("Computer", "electronic device", "cool, captivating", "CTheLongWord"));
        testSearch.add(new Term("Acoustic", "Amazing", "notes", "A"));
        testSearch.add(new Term("Account", "For access to application", "None", "A"));
    }

    @Test
    //Check the getAllTerms(filename) method
    public void getTermsTest() throws IOException{
        ArrayList<Term> terms = IOHandler.getAllTerms("A.txt");
        for (int i = 0; i < terms.size(); i ++){
        //Check if the name, definition, and notes of the expected terms(the first two terms in testAllTerms) and the terms from the method are equal
            assertTrue(testAllTerms.get(i).getName().equals(terms.get(i).getName())&&testAllTerms.get(i).getDefinition().equals(terms.get(i).getDefinition())&&testAllTerms.get(i).getNotes().equals(testAllTerms.get(i).getNotes()));
        }
    }

    @Test
    public void getGroupTest() throws IOException{
        //Check if the expected groups and the groups gotten from the method are equal
        assertEquals(testGroupList, IOHandler.getAllGroups());
    }

    @Test
    public void getAllTermsTest() throws IOException{
        ArrayList<Term> terms = IOHandler.getAllTerms();
        //Check if the name, definition, and notes of the expected terms and the term from the method are equal.
        for(int i = 0; i < terms.size(); i ++){
            assertTrue(testAllTerms.get(i).getName().equals(terms.get(i).getName())&&testAllTerms.get(i).getDefinition().equals(terms.get(i).getDefinition())&&testAllTerms.get(i).getNotes().equals(testAllTerms.get(i).getNotes()));
        }
    }

    @Test
    public void searchTest() throws IOException{
        //Check the search() method
        IOHandler.search("Ac");
        ArrayList<Term> terms = IOHandler.getTerms();
        //Check if the name, definition, and notes of the expected terms(the first two terms in testAllTerms) and the terms in the search result are equal
        for(int i = 0; i < IOHandler.getTerms().size(); i ++) {
            assertTrue(testAllTerms.get(i).getName().equals(terms.get(i).getName())&&testAllTerms.get(i).getDefinition().equals(terms.get(i).getDefinition())&&testAllTerms.get(i).getNotes().equals(testAllTerms.get(i).getNotes()));
        }
    }

    @Test
    public void writeTest() throws IOException{
        //checking the effect on existing files
        IOHandler.write("Group.txt", true);
        FileReader fr = new FileReader("Group.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        int index = 0;
        while((line = br.readLine()) != null){
            assertEquals(testGroupList.get(index++), line+".txt");
        }
        IOHandler.write("Group.txt", false);
        while((line = br.readLine()) != null){
            assertEquals("", line);
        }
        br.close();
        //checking the effect of creating files
        File file = new File("F.txt");
        assertFalse(file.exists());
        IOHandler.write("F.txt", true);
        assertTrue(file.exists());
        assertTrue(file.delete());
    }

    @After
    public void after() throws IOException {
        //write the loaded stuff back to Group.txt and Default Group.txt
        FileWriter fw = new FileWriter("Group.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        for (String group : existingGroups){
            bw.write(group+"\n");
        }bw.close();
        fw = new FileWriter("Default Group.txt");
        bw = new BufferedWriter(fw);
        for (String dGTerms : defaultGroupTerms){
            bw.write(dGTerms+"\n");
        }
        bw.close();
        //deleting the test files
        //note: this might result in errors if A.txt, B-.txt, and CTheLongWord.txt exists before the test. Best solution is needed.
        for (int i = 0; i < testGroupList.size()-1; i++){
            File file = new File(testGroupList.get(i));
            file.delete();
        }
    }
}