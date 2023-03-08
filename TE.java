package football;

import java.util.Random;

/**
 *
 * @author Kyle Estin
 */

public class TE implements Player{
    
    String fname; // Player's first name
    String lname; //Player's last name
    int receive; //Scale percentage of 1-100 
    int turnover;  // Percent chance that they will turn the ball over
    Random rand;
    
    public TE(String fname, String lname,int receive, int turnover)
    {
        this.fname = fname;
        this.lname = lname;
        this.receive = receive;
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
    
    public void Catch()
    {
        
    }
    
    
}
