package football;

import java.util.Random;

/**
 * @author Kyle Eastin
 */

public class CB implements Player{
    
    String fname; // Player's first name
    String lname; //Player's last name 
    int forceTurnover;  // Percent chance that they will force a turnover
    Random rand;
    int turnovers;
    
    public CB(String fname, String lname, int forceTurnover)
    {
        this.fname = fname;
        this.lname = lname;
        this.forceTurnover = forceTurnover;
    }
    
    @Override
    public String getShortName()
    {
        return fname.charAt(0)+". "+lname;
    }
    
    @Override
    public String getName()
    {
        return fname + " " + lname;
    }
    
    public void forceTurnover()
    {
        
    }
    
}
