package heuristics;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Helper {

    public static ArrayList<Long> readFile(String filrName){
        ArrayList<Long> list = new ArrayList<>();
        try
        {
//the file to be opened for reading
            FileInputStream fis=new FileInputStream(filrName);
            Scanner sc=new Scanner(fis);    //file to be scanned
//returns true if there is another line to read
            while(sc.hasNextLong())
            {
                list.add( sc.nextLong());      //returns the line that was skipped
            }
            sc.close();     //closes the scanner
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }


    private static int genrateRandomIntBetween(int lowerBound,int upperBound){
        Random rnd =new Random();
        int num = lowerBound+ rnd.nextInt(upperBound-lowerBound);
        return num;
    }
}
