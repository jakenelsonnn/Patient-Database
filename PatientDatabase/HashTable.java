/** Program: HASH TABLE for Patient Database
Written by: Jake Nelson
Program Description: Assignment 4 - Recap Hospital Work: Choose and build a suitable data structure to implement into the hospital database project                                 
Time Spent: 16 - 20 hours total
Challenges: Deciding upon which data structure to use, writing a proper hash function, collision handling

Revision Log
Date:                   By:                  Action:
12/4/2020               JN                   Create the HashTable Class, write premature hash function
12/5/2020               JN                   Improve upon hash function, repeated testing
12/7/2020               JN                   Write get and remove method
12/8/2020               JN                   Collision handling: wrote a linear probing algorithm (scratched)
12/9/2020               JN                   Changed the collision handling for patient objects so that ID is reset upon collision
12/11/2020              JN                   Finalize code and remove extra comments, etc
---------------------------------------------------


**/


import java.util.NoSuchElementException;

public class HashTable
{
    public static final int size = 1000;
    public static int slotsFilled = 0;
    public static Patient[] patientArray = new Patient[size];
    
    public void add(Patient p)
    {
        //index is obtained by putting ID through hash function
        int index = hash(Integer.valueOf(p.getID()));
        
        //if the table is full
        if(slotsFilled == size-1)
        {
            System.out.println("Table is full!");
        }
        else
        {
            //put the patient here if it is null
            if(patientArray[index] == null)
            {
                patientArray[index] = p;
            }
            //if it is not null, we need to reset the patient id until we have met an empty index
            else
            {
                while(patientArray[index] != null)
                {
                    index = resetIndex(p);
                }
                //finally, put the patient in the given index
                patientArray[index] = p;
            }
            //to keep track of how many entries
            slotsFilled++;
        }
    }
    
    //get the target element by key. key is hashed, returning the element at the index
    public Patient get(int key)
    {
        int index = hash(key);
        if(patientArray[index] == null)
        {
            return null;
        }
        else
        {
            return patientArray[index];
        }
    }
    
    //remove the element by given key and decrement the slotsfilled
    public void remove(int key)
    {
        int index = hash(key);
        if(patientArray[index] == null)
        {
            throw new NoSuchElementException();
        }
        else
        {
            patientArray[index] = null;
            slotsFilled--;
        }
    }
    
    //hash code to return the index value
    public int hash(int key)
    {
        return key * 93717 % size;
    }
    
    //incremenets the given patient ID by one and then hashes it
    public int resetIndex(Patient p)
    {
        p.incrementID();
        return hash(Integer.valueOf(p.getID()));
    }
    
    //function for testing
    public static void printAll()
    {
        for(int i = 0; i < patientArray.length; i++)
        {
            System.out.println(i  + ": " + patientArray[i]);
        }
    }
}