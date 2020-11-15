package sample;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

    public class Term {
    //Fields
    private String name;
    private String definition;
    private String notes;
    private String group;

    //Constructor
    public Term(String name, String definition, String notes, String group) {
        this.name = name;
        this.definition = definition;
        this.notes = notes;
        this.group = group;
    }

    //Records term to the group file it belongs to
    public void writeToFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(name + "\n");
        bw.write(definition + "\n");
        bw.write(notes + "\n");
        bw.close();
    }
    
    //Display the term as the name of the term
    public String toString(){ return name; }

    //Method to check if two terms are equal
    public boolean equals(Term term){
        return (name.equals(term.name)&&definition.equals(term.definition)&&notes.equals(term.notes));
    }

    //Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDefinition() { return definition; }
    public void setDefinition(String definition) { this.definition = definition; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }

    }
