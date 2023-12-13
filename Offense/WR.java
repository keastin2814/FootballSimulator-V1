package football.Offense;

import football.Player;
import java.util.Random;

/**
 * @author Kyle Eastin
 * @author Triston Lamonte
 */

public class WR implements Player{
    
    String fname; // Player's first name
    String lname; //Player's last name
    public int receive; //Scale percentage of 1-100 
    
    public WR(String fname, String lname,int receive)
    {
        this.fname = fname;
        this.lname = lname;
        this.receive = receive;
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
