package football;

import java.util.Random;

/**
 *
 * @author Kyle Eastin
 */

public class RB implements Player{
    
    String fname; // Player's first name
    String lname; //Player's last name 
    int turnover;  // Percent chance that they will turn the ball over
    Random rand;
    
    public int yards = 0;
    public int carries = 0;
    public int tds = 0;
    public int fumbles = 0;
    
    public RB(String fname, String lname, int turnover)
    {
        this.fname = fname;
        this.lname = lname;
        this.turnover = turnover;
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
    
}
