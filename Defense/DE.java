package football.Defense;

import football.Player;
import java.util.Random;

/**
 * @author Triston Lamonte
 */

public class DE implements Player{

    String fname; // Player's first name
    String lname; //Player's last name 
    public int forceTurnover;  // Percent chance that they will force a turnover over
    Random rand;
    public int turnovers;
    
    public DE(String fname, String lname, int forceTurnover)
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
