package football;

/**
* @author Kyle Eastin
*/

public class Team {
    
    int score=0;
    boolean possession;
    boolean ct;  //coin toss
    String name;
    QB qb;
    RB rb;
    WR wr1;
    WR wr2;
    TE te;
    MLB mlb;
    CB cb;
    SS ss;
    KR kr;
    PR pr;
    
    public Team(String name,QB qb,RB rb,WR wr1,WR wr2,TE te,MLB mlb,CB cb,SS ss,KR kr,PR pr)
    {
        this.name = name;
        this.qb = qb;
        this.rb = rb;
        this.wr1 = wr1;
        this.wr2 = wr2;
        this.te = te;
        this.mlb = mlb;
        this.cb = cb;
        this.ss = ss;
        this.kr = kr;
        this.pr = pr;
    }
    
    String getName()
    {
        return name;
    }
    
}
