package football.SpecialTeams;

import football.Player;
import java.util.Random;

/**
 * @author Kyle Eastin
 * @author Triston Lamonte
 */

public class KR implements Player{
    String fname; // Player's first name
    String lname; //Player's last name 
    public int accuracy; //Scaled out of 100
    public int power;//Scaled out of 100
    Random rand;
    
    public int attempts = 0;
    public int makes = 0;
    public int longest = 0;
    
    public KR(String fname, String lname, int accuracy, int power)
    {
        this.fname = fname;
        this.lname = lname;
        this.accuracy = accuracy;
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
    
    public void kick()
    {
        //do boolean kick if its in the number range of 1-whatever its good
    }
    
}
