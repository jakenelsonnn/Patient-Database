/** Program: Patient Database (Patient.java)
Written by: Jake Nelson
Program Description: Create an application that allows doctors/nurses to access and update information about their patients.
Challenges: Working with the text file handled by the Java program. Particularly, when to overwrite the content of the file, and when to add to it.
Time Spent: <1 hour total (for just this class)

Revision Log
Date:                   By:                  Action:
9/20/2020               JN                   Create the Patient Class
12/5/2020               JN                   Added incrementID method
---------------------------------------------------


**/

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Random;

public class Patient
{
    private String name, notes, patientID;
    
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private LocalDateTime now;
    
    Patient(String name)
    {
        now = LocalDateTime.now();
        String date = dtf.format(now) + " - ";
        
        this.name = name;
        this.notes = date + "Patient record created for " + this.name + "|";
        generateID();
    }
    
    Patient(String name, String notes)
    {
        now = LocalDateTime.now();
        String date = dtf.format(now) + " - ";
        
        this.name = name;
        this.notes = date + "Patient record created for " + this.name + "|" + date + notes + "|";
        generateID();
    }
    
    Patient(String name, String notes, String ID)
    {
        this.name = name;
        this.notes = notes;
        this.patientID = ID;
    }
    
    public void generateID()
    {
        Random random = new Random();
        this.patientID = String.format("%04d", random.nextInt(10000));
    }
    
    public void addNote(String notes)
    {
        now = LocalDateTime.now();
        String date = dtf.format(now) + " - ";
        
        this.notes += date + notes + "|";
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String s)
    {
        this.name = s;
    }
    
    public String getNotes()
    {
        return this.notes;
    }
    
    public String getID()
    {
        return this.patientID;
    }
    
    public void setID(int n)
    {
        this.patientID = String.format("%04d", n);
    }
    
    public void incrementID()
    {
        int n = Integer.valueOf(this.patientID) + 1;
        setID(n);
    }
    
    @Override
    public String toString()
    {
        return "Patient Name: " + this.name + "\n" +
                "Notes: \n" + this.notes.replace('|','\n') + "\n";
                
    }
}