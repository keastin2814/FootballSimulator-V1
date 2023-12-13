package football.SpecialTeams;

import football.Defense.*;
import football.Player;
import java.util.Random;

/**
 * @author Kyle Eastin
 * @author Triston Lamonte
 */

public class RS implements Player{
    
    String fname; // Player's first name
    String lname; //Player's last name 
    public int forceTurnover;  // Percent chance that they will force a turnover
    public int prowess; //Scaled out of 100
    Random rand;
    
    public RS(String fname, String lname, int forceTurnover)
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
