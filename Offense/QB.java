package football.Offense;

//import java.util.Random;

import football.Player;

/**
 * @author Triston Lamonte
 */

public class QB implements Player{
    
    String fname; // Player's first name
    String lname; //Player's last name
    public int completion; //Scale percentage of 1-100 
    public int sack;  // Percent chance that they will get sacked
    
    public int passYards = 0;
    public int completions = 0;
    public int attempts = 0;
    public int tds = 0;
    public int ints = 0;
    
    
    public QB(String fname, String lname,int completion, int sack)
    {
        this.fname = fname;
        this.lname = lname;
        this.completion = completion;
        this.sack = sack;
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
