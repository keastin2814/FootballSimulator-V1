package football;

import java.util.Random;

/**
 * @author Kyle Eastin
 */

public class PR implements Player{
    String fname; // Player's first name
    String lname; //Player's last name 
    int power;  // Scale out of 100
    Random rand;
    
    public PR(String fname, String lname, int power)
    {
        this.fname = fname;
        this.lname = lname;
        this.power = power;
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
    
    public void punt()
    {
        
    }
    
}
