/** Program: Patient Database
Written by: Jake Nelson
Program Description: Create an application that allows doctors/nurses to access and update information about their patients.
Challenges: Adapting the program to a new data structure, also deciding which data structure
Time Spent: 16 - 20 hours total

Revision Log
Date:                   By:                  Action:
9/20/2020               JN                   Create the Patient Class
9/21/2020               JN                   Start building the driver (created all of the menu functions, just the text)
9/23/2020               JN                   Make all menus interactive and navigable
9/23/2020               JN                   Start building file-writing functions
9/24/2020               JN                   Build the function that allows note-adding
9/25/2020               JN                   Finalizing, implement error-preventing code
* 
12/4/2020               JN                   Began implementing the hash table into this program
12/5/2020               JN                   Fixed methods to be appropriate for new data structure
12/6/2020               JN                   Added remove method
---------------------------------------------------


**/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PatientDatabase
{
    final static String PASSWORD = "2244";
    static HashTable roster = new HashTable();
    static ArrayList<Integer> idList = new ArrayList();
    
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        createRoster();
        displayMainPage();
    }
    
    public static void displayMainPage() throws IOException
    {
        Scanner inp = new Scanner(System.in);
        int input = 0;
        while(input != 1 || input != 2)
        {
            System.out.println("Main Page\n\n1. Login \n2. Exit");
            input = inp.nextInt();
            if(input == 1)
            {
                displayLoginPage();
            }
            else if(input == 2)
            {
                System.exit(0);
            }
        }
    }
    
    //here the user is asked for password for entry
    public static void displayLoginPage() throws IOException
    {
        System.out.println("Login Page \n\nEnter Admin Key or enter '0' to exit.");
        Scanner inp = new Scanner(System.in);
        String pass = inp.nextLine();
        
        if(pass.equals(PASSWORD))
        {
            displayAdminPage();
        }
        if(pass.equals("0"))
        {
            System.exit(0);
        }
        else
        {
            System.out.println("Invalid password.");
            displayLoginPage();
        }
    }
    
    //the user is brought here if the password entered was correct
    public static void displayAdminPage() throws IOException
    {
        String input = "";
        while(!"1".equals(input)||!"2".equals(input)||!"3".equals(input))
        {
            System.out.println("Admin Page \n\n1. View Patient Record \n2. Add Patient\n3. Remove Patient\n4. Exit");
            Scanner inp = new Scanner(System.in);
            input = inp.nextLine();
            
            switch(input)
            {
                case "1":
                {
                    displayPatientRecord();
                    break;
                }
                case "2":
                {
                    displayAddPatientPage();
                    break;
                }
                case "3":
                {
                    removePatient();
                    break;
                }
                case "4":
                {
                    System.exit(0);
                }
                    
            }
        }
    }
    
    //the user is brought here if they selected to add patient
    public static void displayAddPatientPage() throws IOException
    {
        System.out.println("Please enter patient name or enter '0' to return to Admin Page.");
        Scanner inp = new Scanner(System.in);
        String name = inp.nextLine();
        
        if(!"0".equals(name))
        {
            System.out.println("Please enter patient notes if desired, otherwise enter '0'");
            String notes = inp.nextLine();
            
            if(!"0".equals(notes))
            {
                try
                {
                    addPatient(name, notes);
                }
                catch(IOException e)
                {
                    System.out.println("Error in addpatient method.");
                }
            }
            else
            {
                try
                {
                    addPatient(name);
                }
                catch(IOException e)
                {
                    System.out.println("error in addpatient method");
                }
            }
        }
        else
        {
            displayAdminPage();
        }
    }
    
    //1 of 2 functions with this name - only 1 parameter (name)
    //opens the file and writes to it
    //format: firstname lastname#notes notes notes|more notes#ID:####@
    public static void addPatient(String name) throws IOException
    {
        File f = new File("database.txt");
        f.createNewFile();
        FileWriter fw = new FileWriter(f, true);    //second argument for constructor must be true so the file is added to and not overwritten
        PrintWriter pw = new PrintWriter(fw);
        
        Patient p = new Patient(name);
        pw.print(p.getName() + "#" + p.getNotes() + "#" + p.getID() + "@"); //file is organized as such that patients are separated by @ and patient info is separated by #
        roster.add(p);
        idList.add(Integer.valueOf(p.getID()));
        pw.close();
        System.out.println("Patient Added Successfully! Patient ID: " + p.getID());
    }
    
    //2 of 2 functions with this name - 2 parameters (name, and notes)
    //works same as above but uses other constuctor for patient
    public static void addPatient(String name, String notes) throws IOException
    {
        File f = new File("database.txt");
        f.createNewFile();
        FileWriter fw = new FileWriter(f, true);
        PrintWriter pw = new PrintWriter(fw);
        
        Patient p = new Patient(name, notes);
        pw.print(p.getName() + "#" + p.getNotes() + "#" + p.getID() + "@");
        roster.add(p);
        idList.add(Integer.valueOf(p.getID()));
        pw.close();
        System.out.println("Patient Added Successfully! Patient ID: " + p.getID());
    }
    
    public static void addPatient(String name, String notes, String ID) throws IOException
    {
        File f = new File("database.txt");
        f.createNewFile();
        FileWriter fw = new FileWriter(f, true);
        PrintWriter pw = new PrintWriter(fw);
        
        Patient p = new Patient(name, notes, ID);
        pw.print(p.getName() + "#" + p.getNotes() + "#" + p.getID() + "@");
        roster.add(p);
        idList.add(Integer.valueOf(ID));
        pw.close();
        System.out.println("Patient Added Successfully! Patient ID: " + p.getID());
    }
    
    public static void removePatient() throws IOException
    {
        Patient p = null;
        Scanner inp = new Scanner(System.in);
        System.out.println("Please enter the ID number of the patient you wish to remove.");
        String input = inp.next();
        
        boolean found = false;
        try
        {
            p = roster.get(Integer.valueOf(input));
            roster.remove(Integer.valueOf(input));
            found = true;
        }
        catch(NoSuchElementException e)
        {
            found = false;
        }
        if(found)
        {
            System.out.println("Patient record removed.");
            System.out.println(p);
        }
        else
        {
            System.out.println("No patient found by that ID number.");
        }
        idList.remove(Integer.valueOf(input));
        updateDatabase();
        displayAdminPage();
    }
    
    //user is brought here if they wish to view patient record
    public static void displayPatientRecord() throws FileNotFoundException, IOException
    {
        System.out.println("View Patient Record\n");
        System.out.println("Enter patient ID number or enter '0' to return to admin page");
        
        Patient p = null;
        Scanner inp = new Scanner(System.in);
        String ID = inp.nextLine();
        int numberID = Integer.valueOf(ID);
        boolean found = false;
        
        if(!"0".equals(ID))
        {
            try
            {
                p = roster.get(numberID);
                found = true;
            }
            catch(NoSuchElementException e)
            {
                found = false;
            }
            if(found)
            {
                System.out.println("Please enter the name for the patient.");
                String name = inp.nextLine();
                    
                if(name.equals(p.getName()))
                {
                    System.out.println("Patient Record found.");
                    System.out.println(p);
                    System.out.println("Would you like to add a note?");
                    System.out.println("1. Add Note\n2. Return to Admin Page");
                    
                    String input = "";
                    input = inp.nextLine();
                    
                    if("1".equals(input))
                    {
                        System.out.println("Please enter the note you wish to add or enter '0' to return to Admin Page.");
                        input = inp.nextLine();
                        if(!"0".equals(input))
                        {
                            p.addNote(input);
                            updateDatabase();
                            System.out.println("Note added successfully.");
                            displayAdminPage();
                        }
                        else
                        {
                            displayAdminPage();
                        }
                    }
                    else if("2".equals(input))
                    {
                        displayAdminPage();
                    }
                }
                else
                {
                    System.out.println("Invalid name. Access denied.");
                    displayAdminPage();
                }
            }
            else
            {
                System.out.println("No patient found by that ID number.");
                displayAdminPage();
            }
        }
        else
        {
            displayAdminPage();
        }
    }
    
    //must run at the beginning of the program
    //builds the roster (the hash table) by reading the file
    public static void createRoster() throws FileNotFoundException, IOException
    {
        File f = new File("database.txt");
        f.createNewFile();
        Scanner scan = new Scanner(f);
        String name, notes, ID;
        
        scan.useDelimiter("@");
        
        //nested loop:
        //outer loop: iterates through '@' in file, indicating the end of individual patient's record
        //inner loop: iterates through '#' in file, assigning name, notes, and ID, then adds patient to roster.
        while(scan.hasNext())
        {
            String record = scan.next();
            Scanner scanR = new Scanner(record);
            
            scanR.useDelimiter("#");
            while(scanR.hasNext())
            {
                name = scanR.next();
                notes = scanR.next();
                ID = scanR.next();
                idList.add(Integer.valueOf(ID));
                Patient p = new Patient(name, notes, ID);
                roster.add(p);
            }
            
        }
    }
    
    //must run each time a note is added to a patient
    //re-writes the file according to the roster
    public static void updateDatabase() throws FileNotFoundException, IOException
    {
        File f = new File("database.txt");
        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        PrintWriter pw = new PrintWriter(fw);
        for(int i = 0; i < idList.size(); i++)
        {
            if(roster.get(idList.get(i)) != null)
            {
                pw.print(roster.get(idList.get(i)).getName() + "#" + roster.get(idList.get(i)).getNotes() + "#" + roster.get(idList.get(i)).getID() + "@");
            }
        }
        pw.close();
    }
}
