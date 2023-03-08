package football;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 * @author Kyle Eastin
 * 
 * This program simulates through a football game with 
 * abbreviated football rosters. The game is played between an
 * NFC all-star team and an AFC all-star team. The program should
 * simulate the game accurate to a real football game with the exception of 
 * no kickoffs (ball will be placed at the 25 yard line). There will be pauses
 * after each quarter to allow the user to examine the plays
 * of the previous quarter.
 * 
 * This is version 1 of the football simulator. Version 2 will add in either 
 * graphics using JavaFX, A game engine, or javascript. The stats will be 
 * saved to a database table in MySQL.
 * 
 * things to edit: 
 *  fix touchdown logic (extra yards are added into stats for pass AND run plays)
 *      encapsulate the functions in main more efficiently
 *      Make generation of players and teams their own function
 *      make the generation of the play its own function
 *      make the score being printed out its own function
 *      make printing stats its own function
 *      make big plays more likely?
 *      make run plays take up more time
 * Functionality to add in:
 *      record stats and print at the end of the quarters?
 *           - add them to the qb object, rb object, wr object, etc;
 *      Also add in extra point and kickoff     
 *      football logic - no running on 3rd and long, no running in 2 minute 
 *           warning if down?
 *      save stats to a database for high scores/records
 *      Add a pool of players and allow the teams to be selected
 *      Add in a pauser so you can pause at any point - will experiment with in
 *           another program, test 2 scanners at once in a similar style
 *      Add in graphics
 *           arrow showing direction, 
 *           displaying the println statement in the screen, 
 *           shows the time and score
 *
 */

public class Football
{
    Random RAND = new Random();
    static int minutes = 15;
    static int seconds = 00;
    static int quarter = 1;
    static int yardline;
    boolean touchdown=false;
    boolean gameIsOver=false;
    static int down=1;
    static int ytg=10;
    Team t1;
    Team t2;
    Scanner pauser = new Scanner(System.in);
    
    public Football(Team t1,Team t2)
    {
        this.t1 = t1;
        this.t2 = t2;
        
        System.out.println("Today's Matchup: " + t1.getName() + " vs. " + t2.getName());
        System.out.println(t1.getName()+"'s Roster:");
        System.out.println("Quarterback: "+t1.qb.getName());
        System.out.println("Runningback: "+t1.rb.getName());
        System.out.println("Wide Receiver: "+t1.wr1.getName());
        System.out.println("Wide Receiver: "+t1.wr2.getName());
        System.out.println("Tight End: "+t1.te.getName());
        System.out.println("Middle Linebacker: "+t1.mlb.getName());
        System.out.println("Cornerback: "+t1.cb.getName());
        System.out.println("Safety: "+t1.ss.getName());
        System.out.println("Kicker: "+t1.kr.getName());
        System.out.println("Punter: "+t1.pr.getName());
        System.out.println();
        System.out.println(t2.getName()+"'s Roster:");
        System.out.println("Quarterback: "+t2.qb.getName());
        System.out.println("Runningback: "+t2.rb.getName());
        System.out.println("Wide Receiver: "+t2.wr1.getName());
        System.out.println("Wide Receiver: "+t2.wr2.getName());
        System.out.println("Tight End: "+t2.te.getName());
        System.out.println("Middle Linebacker: "+t2.mlb.getName());
        System.out.println("Cornerback: "+t2.cb.getName());
        System.out.println("Safety: "+t2.ss.getName());
        System.out.println("Kicker: "+t2.kr.getName());
        System.out.println("Punter: "+t2.pr.getName());
        System.out.println();
        
        //Have below in seperate field e.g. Play;
        //pause
        //Thread.sleep(1000) in between plays
        //have game pause when quarter ends
        //once time = 0.00 and quarter = 4, gameisOver() = true - 
        // - displays Final Score Player of the Game and their stats
        
    }
    
    void startGame() throws InterruptedException
    {
        System.out.println("Press enter to proceed to the coin toss...");
        pauser.nextLine();
        System.out.println("Simulating coin toss...");
        Thread.sleep(500);
        int n = RAND.nextInt(2);
        if(n==1)
        {
            t1.ct = true;
            t1.possession = true;
            t2.ct = false;
            t2.possession = false;
            yardline = 25;
            System.out.print(t1.getName());
        }
        else
        {
            t1.ct = false;
            t1.possession = false;
            t2.ct = true;
            t2.possession = true;
            yardline = 75;
            System.out.print(t2.getName());
        }
        System.out.println(" has won the toss and will recieve the ball first.");
        System.out.println("Press enter to start the game...");
        pauser.nextLine();
        Play(); 
    }
    
    void Play() throws InterruptedException
    {
        while(gameIsOver!=true)
        { 
            Thread.sleep(1000);

            if(minutes<10&&seconds<10)
                System.out.print("0"+minutes+":0"+seconds+" ");
            else if(minutes<10)
                System.out.print("0"+minutes+":"+seconds+" ");
            else if(seconds<10)
                System.out.print(minutes+":0"+seconds+" ");
            else
                System.out.print(minutes+":"+seconds+" ");
        //time is outputted when the ball is snapped
        
            if(t1.possession==true)
            {
                System.out.print(t1.getName()+" ball on the ");
                if(yardline<50)
                    System.out.print(t1.getName()+" "+yardline);
                else if(yardline>50)
                    System.out.print(t2.getName()+" "+(100-yardline));
                else
                    System.out.print(yardline);
                if(yardline>=90)
                    System.out.print(". " + down + " and goal.  ");
                else
                    System.out.print(". " + down + " and " + ytg + ".  ");

                if(down==4&&yardline>=65)
                {
                    int fieldGoal = t1.kr.power*t1.kr.accuracy;
                    int make = RAND.nextInt(10000);
                    t1.kr.attempts++;
                    
                    System.out.print(t1.kr.getShortName()+" "+(100-yardline+17)+
                            " yard field goal try is ");
                    
                    if(make>fieldGoal)
                    {
                        System.out.println("no good.");
                        yardline=yardline-7;
                    }
                    else
                    {
                        System.out.println("good.");
                        t1.kr.makes++;
                        t1.score=t1.score+3;
                        yardline=75;
                    }
                    System.out.println("SCORE: "+t1.getName()+" "+t1.score+" - "
                            +t2.getName()+" "+t2.score);
                    down=1;
                    ytg=10;
                    t1.possession=false;
                    t2.possession=true;
                    seconds=seconds-5;
                }
                else if(down==4)
                {
                    int v = RAND.nextInt(20)-10;
                    v = v +t1.pr.power/2;
                    if(v+yardline>=100)
                        v = 100-yardline;
                    System.out.print(t1.pr.getShortName()+" punts "+v+" yards");
                    if(v+yardline==100)
                    {
                        System.out.println(" for a touchback.");
                        yardline=80;
                    }
                    else
                    {
                        System.out.println(". Fair catch made by "+
                                t2.wr2.getShortName()+".");
                        yardline=yardline+v;
                    }
                    down=1;
                    ytg=10;
                    t1.possession=false;
                    t2.possession=true;
                    System.out.println("SCORE: "+t1.getName()+" "+t1.score+" - "
                            +t2.getName()+" "+t2.score);
                    seconds=seconds-10;
                }
                else //pass or run
                {
                    int n = RAND.nextInt(3)+1;
                    if(n<=2) //pass
                    {
                        int s = RAND.nextInt(100)+1;
                        if(s<=t1.qb.sack) //QB gets sacked
                        {
                            int loss = RAND.nextInt(3)+2;
                            System.out.print(t1.qb.getShortName()+" sacked for "+loss+
                                    " yard loss.");
                            down++;
                            yardline=yardline-loss;
                            ytg=ytg+loss;
                            seconds=seconds-5-25;
                        }
                        else
                        {
                            int length=0;
                            int i = RAND.nextInt(6);
                            if(i<=3)
                                length=RAND.nextInt(5)+2;
                            else if(i==4||i==5)
                                length=RAND.nextInt(6)+8;
                            else
                            {
                                length = RAND.nextInt(100-yardline)+15;
                                if(length>(100-yardline))
                                    length=100-yardline;
                            }
                            int rec = RAND.nextInt(3)+1;
                            int ctch = RAND.nextInt(100)+1;
                            int t = RAND.nextInt(100)+1;
                            if(ctch<=t1.qb.completion)
                            {
                                if(rec<=2)
                                {
                                    if(t<=t2.cb.forceTurnover) //pass intercepted
                                    {
                                        System.out.print("Pass intercepted by "+
                                                t2.cb.getShortName()+".");
                                        if(yardline+length>=100)
                                            yardline=80;
                                        else
                                            yardline=yardline+length;
                                        down=1;
                                        ytg = 10;
                                        t1.qb.attempts++;
                                        t1.qb.ints++;
                                        t1.possession=false;
                                        t2.possession=true;
                                        seconds=seconds-5;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                    }
                                    else //pass dropped
                                    {
                                        int cr= RAND.nextInt(100)+1;
                                        if(cr>t1.wr1.receive)
                                        {
                                            System.out.print(t1.qb.getShortName()+" pass"
                                                    + " dropped by "+t1.wr1.getShortName()+".");
                                            down++;
                                            t1.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else //complete pass
                                        {
                                            System.out.print(t1.qb.getShortName()+" pass complete to "+
                                                t1.wr1.getShortName()+" for "+length+" yards.");
                                            t1.qb.attempts++;
                                            t1.qb.completions++;
                                            if(length>=ytg)
                                            {
                                                down=1;
                                                ytg=10;
                                                yardline=yardline+length;
                                            }
                                            else
                                            {
                                                ytg=ytg-length;
                                                down++;
                                                yardline=yardline+length;
                                            }
                                            if(yardline>=100)
                                                    touchdown=true;
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                                else if(rec==3)
                                {
                                    if(t<=t2.ss.forceTurnover) // intercepted by SS
                                    {
                                        System.out.print("Pass intercepted by "+
                                                t2.ss.getShortName()+".");
                                        if(yardline+length>=100)
                                            yardline=80;
                                        else
                                            yardline=yardline+length;
                                        down=1;
                                        ytg = 10;
                                        t1.qb.attempts++;
                                        t1.qb.ints++;
                                        t1.possession=false;
                                        t2.possession=true;
                                        seconds=seconds-5;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                    }
                                    else
                                    {
                                        int cr= RAND.nextInt(100)+1;
                                        if(cr>t1.wr2.receive)
                                        {
                                            System.out.print(t1.qb.getShortName()+" pass"
                                                    + " dropped by "+t1.wr2.getShortName()+".");
                                            down++;
                                            t1.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else
                                        {
                                            System.out.print(t1.qb.getShortName()+" pass complete to "+
                                                t1.wr2.getShortName()+" for "+length+" yards.");
                                            t1.qb.attempts++;
                                            t1.qb.completions++;
                                            if(length>=ytg)
                                            {
                                                down=1;
                                                ytg=10;
                                                yardline=yardline+length;
                                            }
                                            else
                                            {
                                                ytg=ytg-length;
                                                yardline=yardline+length;
                                                down++;
                                            }
                                            if(yardline>=100)
                                                    touchdown=true;
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                                else
                                {
                                    if(t<=t2.mlb.forceTurnover) //iontercepted by MLB
                                    {
                                        System.out.print("Pass intercepted by "+
                                                t2.mlb.getShortName()+".");
                                        if(yardline+length>=100)
                                            yardline=80;
                                        else
                                            yardline=yardline+length;
                                        down=1;
                                        ytg = 10;
                                        t1.qb.attempts++;
                                        t1.qb.ints++;
                                        t1.possession=false;
                                        t2.possession=true;
                                        seconds=seconds-5;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                    }
                                    else
                                    {
                                        int cr= RAND.nextInt(100)+1;
                                        if(cr>t1.te.receive)
                                        {
                                            System.out.print(t1.qb.getShortName()+" pass"
                                                    + " dropped by "+t1.te.getShortName()+".");

                                            down++;
                                            t1.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else
                                        {
                                            System.out.print(t1.qb.getShortName()+" pass complete to "+
                                                t1.te.getShortName()+" for "+length+" yards.");
                                            t1.qb.attempts++;
                                            t1.qb.completions++;
                                            if(length>=ytg)
                                            {
                                                down=1;
                                                ytg=10;
                                                yardline=yardline+length;
                                            }
                                            else
                                            {
                                                yardline=yardline+length;
                                                ytg=ytg-length;
                                                down++;
                                            }
                                            if(yardline>=100)
                                            {
                                                t1.qb.tds++;
                                                touchdown=true;
                                            }
                                            seconds=seconds-5-20;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                            }
                            else
                            {
                                System.out.print(t1.qb.getShortName()+
                                        " pass incomplete, intended for ");
                                if(rec<=2)
                                    System.out.print(t1.wr1.getShortName()+".");
                                else if(rec==3)
                                    System.out.print(t1.wr2.getShortName()+".");
                                else
                                    System.out.print(t1.te.getShortName()+".");
                                down++;
                                t1.qb.attempts++;
                                seconds=seconds-5;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                            }

                        }

                    }
                    else //run
                    {
                        //double check logic here, might need to see if more yards
                        //are added if a touchdown is scored
                        
                        int length=0;
                        t1.rb.carries++;
                        int t = RAND.nextInt(100)+1;
                        int i = RAND.nextInt(6);
                        if(i<=3)
                            length=RAND.nextInt(6)-2;
                        else if(i==4||i==5)
                            length=RAND.nextInt(6)+5;
                        else
                        {
                            length = RAND.nextInt(100-yardline)+12;
                            if(length>(100-yardline))
                                length=100-yardline;
                        }
                        if(t<=t2.mlb.forceTurnover)
                        {
                            System.out.print(length+" yard rush by "+
                                t1.rb.getShortName()+". Fumble forced by "+
                                t2.mlb.getShortName()+". Recovered by "
                                    +t2.getName()+".");
                            t1.rb.yards += length; //will need to change when fixing logic
                            if(yardline+length>=100)
                                yardline=80;
                            else
                            {
                                yardline=yardline+length;
                                down=1;
                                ytg = 10;
                                t1.possession=false;
                                t2.possession=true;
                            }
                            seconds=seconds-(int)Math.sqrt(Math.pow(length,2));
                        }
                        else
                        {
                            System.out.print(t1.rb.getShortName()+" "+length+" yard rush.");
                            t1.rb.yards += length;  //will need to change when fixing touchdown logic
                            
                            if(length>=ytg)
                            {
                                down=1;
                                ytg=10;
                                yardline=yardline+length;
                            }
                            else
                            {
                                yardline=yardline+length;
                                down++;
                                ytg=ytg-length;
                            }
                            if(length>10)
                                seconds=seconds-25-length/2;
                            else
                                seconds=seconds-20-length;
                            if(yardline>=100)
                            {
                                touchdown=true;
                                t1.rb.tds++;
                            }
                        }  
                    }
                    System.out.println();
                }

                if(touchdown==true)
                {
                    System.out.println("Touchdown!(extra point good by "
                            +t1.kr.getShortName()+").");
                    t1.score=t1.score+7;
                    System.out.println("SCORE: "+t1.getName()+" "+t1.score+" - "
                            +t2.getName()+" "+t2.score);
                    yardline=75;
                    t1.possession=false;
                    t2.possession=true;
                    down=1;
                    ytg=10;
                    touchdown=false;
                }

            }
            
            else //t2 possession==true
            {
                //do time
                System.out.print(t2.getName()+" ball on the ");
                if(yardline<50)
                    System.out.print(t1.getName()+" "+yardline);
                else if(yardline>50)
                    System.out.print(t2.getName()+" "+(100-yardline));
                else
                    System.out.print(yardline);
                if(yardline<=10)
                    System.out.print(". " + down + " and goal.  ");
                else
                    System.out.print(". " + down + " and " + ytg + ".  ");

                if(down==4&&yardline<=35)
                {
                    int fieldGoal = t2.kr.power*t2.kr.accuracy;
                    int make = RAND.nextInt(10000);
                    t2.kr.attempts++;
                    System.out.print(t2.kr.getShortName()+" "+(yardline+17)+
                            " yard field goal try is ");
                    if(make>fieldGoal)
                    {
                        System.out.println("no good.");
                        yardline=yardline+7;
                    }
                    else
                    {
                        System.out.println("good.");
                        t2.kr.makes++;
                        t2.score=t2.score+3;
                        yardline=25;
                    }
                    System.out.println("SCORE: "+t1.getName()+" "+t1.score+" - "
                            +t2.getName()+" "+t2.score);
                    down=1;
                    ytg=10;
                    t2.possession=false;
                    t1.possession=true;
                    seconds=seconds-5;
                }
                else if(down==4)
                {
                    int v = RAND.nextInt(20)-10;
                    v = v +t2.pr.power/2;
                    if(yardline-v<=0)
                        v = yardline;
                    System.out.print(t2.pr.getShortName()+" punts "+v+" yards");
                    if(v==yardline)
                    {
                        System.out.println(" for a touchback.");
                        yardline=20;
                    }
                    else
                    {
                        System.out.println(". Fair catch made by "+
                                t2.wr1.getShortName()+".");
                        yardline=yardline-v;
                    }
                    down=1;
                    ytg=10;
                    t2.possession=false;
                    t1.possession=true;
                    System.out.println("SCORE: "+t1.getName()+" "+t1.score+" - "
                            +t2.getName()+" "+t2.score);
                    seconds=seconds-10;
                }
                else
                {
                    int n = RAND.nextInt(3)+1;
                    //possibly put in an algorithm 
                    //relating to time or quarter...
                    if(n<=2) //pass
                    {
                        int s = RAND.nextInt(100)+1;
                        if(s<=t2.qb.sack)
                        {
                            int loss = RAND.nextInt(3)+2;
                            System.out.print(t2.qb.getShortName()+" sacked for "+loss+
                                    " yard loss.");
                            down++;
                            ytg=ytg+loss;
                            yardline=yardline+loss;
                            seconds=seconds-7-20;
                        }
                        else //attempt a pass
                        {
                            int length=0;
                            int i = RAND.nextInt(6);
                            if(i<=3)
                                length=RAND.nextInt(5)+2;
                            else if(i==4||i==5)
                                length=RAND.nextInt(6)+8;
                            else
                            {
                                length = RAND.nextInt(yardline)+15;
                                if(length>(yardline))
                                    length=yardline;
                            }
                            int rec = RAND.nextInt(3)+1;
                            int ctch = RAND.nextInt(100)+1;
                            int t = RAND.nextInt(100)+1;
                            if(ctch<=t2.qb.completion)
                            {
                                if(rec<=2)
                                {
                                    if(t<=t1.cb.forceTurnover)
                                    {
                                        System.out.print("Pass intercepted by "+
                                                t1.cb.getShortName()+".");
                                        if(yardline-length<=0)
                                            yardline=20;
                                        else
                                            yardline=yardline-length;
                                        down=1;
                                        ytg = 10;
                                        t2.qb.attempts++;
                                        t2.qb.ints++;
                                        t2.possession=false;
                                        t1.possession=true;
                                        seconds=seconds-5;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                    }
                                    else
                                    {
                                        int cr= RAND.nextInt(100)+1;
                                        if(cr>t2.wr1.receive)
                                        {
                                            System.out.print(t2.qb.getShortName()+" pass"
                                                    + " dropped by "+t2.wr1.getShortName()+".");
                                            down++;
                                            t2.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else
                                        {
                                            System.out.print(t2.qb.getShortName()+" pass complete to "+
                                                t2.wr1.getShortName()+" for "+length+" yards.");
                                            t2.qb.attempts++;
                                            t2.qb.completions++;
                                            if(length>=ytg)
                                            {
                                                down=1;
                                                ytg=10;
                                                yardline=yardline-length;
                                            }
                                            else
                                            {
                                                yardline=yardline-length;
                                                ytg=ytg-length;
                                                down++;
                                            }
                                            if(yardline<=0)
                                                touchdown=true;
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                                else if(rec==3)
                                {
                                    if(t<=t1.ss.forceTurnover)
                                    {
                                        System.out.print("Pass intercepted by "+
                                                t1.ss.getShortName()+".");
                                        if(yardline-length<=0)
                                            yardline=20;
                                        else
                                            yardline=yardline-length;
                                        down=1;
                                        ytg = 10;
                                        t2.qb.attempts++;
                                        t2.qb.ints++;
                                        t2.possession=false;
                                        t1.possession=true;
                                        seconds=seconds-5;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;

                                    }
                                    else
                                    {
                                        int cr= RAND.nextInt(100)+1;
                                        if(cr>t2.wr2.receive)
                                        {
                                            System.out.print(t2.qb.getShortName()+" pass"
                                                    + " dropped by "+t2.wr2.getShortName()+".");
                                            down++;
                                            t2.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else
                                        {
                                            System.out.print(t2.qb.getShortName()+" pass complete to "+
                                                t2.wr2.getShortName()+" for "+length+" yards.");
                                            t2.qb.attempts++;
                                            t2.qb.completions++;
                                            if(length>=ytg)
                                            {
                                                down=1;
                                                ytg=10;
                                                yardline=yardline-length;
                                            }
                                            else
                                            {
                                                ytg=ytg-length;
                                                yardline=yardline-length;
                                                down++;
                                            }
                                            if(yardline<=0)
                                                touchdown=true;
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                                else
                                {
                                    if(t<=t1.mlb.forceTurnover)
                                    {
                                        System.out.print("Pass intercepted by "+
                                                t1.mlb.getShortName()+".");
                                        if(yardline-length<=0)
                                            yardline=20;
                                        else
                                            yardline=yardline-length;
                                        down=1;
                                        ytg = 10;
                                        t2.qb.attempts++;
                                        t2.qb.ints++;
                                        t2.possession=false;
                                        t1.possession=true;
                                        seconds=seconds-5;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                    }
                                    else
                                    {
                                        int cr= RAND.nextInt(100)+1;
                                        if(cr>t2.te.receive)
                                        {
                                            System.out.print(t2.qb.getShortName()+" pass"
                                                    + " dropped by "+t2.te.getShortName()+".");
                                            down++;
                                            t2.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else
                                        {
                                            System.out.print(t2.qb.getShortName()+" pass complete to "+
                                                t2.te.getShortName()+" for "+length+" yards.");
                                            t2.qb.attempts++;
                                            t2.qb.completions++;
                                            if(length>=ytg)
                                            {
                                                down=1;
                                                ytg=10;
                                                yardline=yardline-length;
                                            }
                                            else
                                            {
                                                yardline=yardline-length;
                                                ytg=ytg-length;
                                                down++;
                                            }
                                            if(yardline<=0)
                                            {
                                                t2.qb.tds++;
                                                touchdown=true;
                                            }
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                            }
                            else
                            {
                                System.out.print(t2.qb.getShortName()+
                                        " pass incomplete, intended for ");
                                if(rec<=2)
                                    System.out.print(t2.wr1.getShortName()+".");
                                else if(rec==3)
                                    System.out.print(t2.wr2.getShortName()+".");
                                else
                                    System.out.print(t2.te.getShortName()+".");
                                down++;
                                t2.qb.attempts++;
                                seconds=seconds-5;
                                if(length>10)
                                    seconds=seconds-(length-10)/2;
                            }

                        }

                    }
                    else //run
                    {
                        int length=0;
                        t2.rb.carries++;
                        int t = RAND.nextInt(100)+1;
                        int i = RAND.nextInt(6);
                        if(i<=3)
                            length=RAND.nextInt(6)-2;
                        else if(i==4||i==5)
                            length=RAND.nextInt(6)+5;
                        else
                        {
                            length = RAND.nextInt(yardline)+12;
                            if(length>(yardline))
                                length=yardline;
                        }
                        if(t<=t1.mlb.forceTurnover)
                        {
                            System.out.print(length+" yard rush by "+
                                t2.rb.getShortName()+". Fumble forced by "+
                                t1.mlb.getShortName()+". Recovered by "
                                    +t1.getName()+".");
                            t2.rb.yards += length; //will have to fix when changing logic
                            
                            if(yardline-length<=0)
                                yardline=20;
                            else
                            {
                                yardline=yardline-length;
                                down=1;
                                ytg = 10;
                                t2.possession=false;
                                t1.possession=true;
                            }
                            seconds=seconds-(int)Math.sqrt(Math.pow(length,2));
                        }
                        else
                        {
                            System.out.print(t2.rb.getShortName()+" "+length+" yard rush.");
                            t2.rb.yards += length;
                            
                            if(length>=ytg)
                            {
                                down=1;
                                ytg=10;
                                yardline=yardline-length;
                            }
                            else
                            {
                                down++;
                                yardline=yardline-length;
                                ytg=ytg-length;
                            }
                            if(length>10)
                                seconds=seconds-25-length/2;
                            else
                                seconds=seconds-20-length;
                            if(yardline<=0)
                            {
                                touchdown=true;
                                t2.rb.tds++;
                            }                        
                        }  
                    }
                    System.out.println();
                }

                if(touchdown==true)
                {
                    System.out.println("Touchdown!(extra point good by "
                            +t2.kr.getShortName()+").");
                    t2.score=t2.score+7;
                    System.out.println("SCORE: " +t1.getName() + " " + t1.score + 
                            " - " + t2.getName() + " " + t2.score);
                    yardline=25;
                    t2.possession=false;
                    t1.possession=true;
                    down=1;
                    ytg=10;
                    touchdown=false;
                }

            }

            if(seconds<0&&minutes!=0)
            {
                minutes--;
                seconds = seconds +60;
            }


            if(minutes==0 && seconds<=0)
            {
                if(quarter==4)
                {
                    gameIsOver();
                    gameIsOver=true;
                }
                else if(quarter==2)
                {
                    Half();
                }
                else
                {
                    endOfQuarter();
                }        
            }
        
        } 
        
    }
    
    void Half()
    {
        System.out.println("END OF HALF.");
        System.out.println();
        
        System.out.println("Team 1 Stats:");
        System.out.println(t1.qb.getName() + ": " + t1.qb.completions + "/" +
                t1.qb.attempts + " Passing, " + t1.qb.tds + " TDs, " + t1.qb.ints + " INTs");
        System.out.println(t1.rb.getName() + ": " + t1.rb.carries + " rushing attempts for " +
                t1.rb.yards + " yards");
        System.out.println(t1.kr.getName() + ": " + t1.kr.makes + "/" +
                t1.kr.attempts + " Kicking");
        
        System.out.println("Team 2 Stats:");
        System.out.println(t2.qb.getName() + ": " + t2.qb.completions + "/" +
                t2.qb.attempts + " Passing, " + t2.qb.tds + " TDs, " + t2.qb.ints + " INTs");
        System.out.println(t2.rb.getName() + ": " + t2.rb.carries + " rushing attempts for " +
                t2.rb.yards + " yards");
        System.out.println(t2.kr.getName() + ": " + t2.kr.makes + "/" +
                t2.kr.attempts + " Kicking");
        
        System.out.println("Press enter to continue simulation.");
        pauser.nextLine();
        quarter++;
        minutes = 15;
        seconds = 0;
        if(t1.ct==true)
        {
            t1.possession=false;
            t2.possession=true;
            yardline=75;
        }
        else
        {
            t2.possession=false;
            t1.possession=true;
            yardline=25;
        }
        down=1;
        ytg=10;
    }
    void endOfQuarter()
    {
        System.out.println("END OF QUARTER.");
        System.out.println();
        
        System.out.println("Team 1 Stats:");
        System.out.println(t1.qb.getName() + ": " + t1.qb.completions + "/" +
                t1.qb.attempts + " Passing, " + t1.qb.tds + " TDs, " + t1.qb.ints + " INTs");
        System.out.println(t1.rb.getName() + ": " + t1.rb.carries + " rushing attempts for " +
                t1.rb.yards + " yards");
        System.out.println(t1.kr.getName() + ": " + t1.kr.makes + "/" +
                t1.kr.attempts + " Kicking");
        
        System.out.println("Team 2 Stats:");
        System.out.println(t2.qb.getName() + ": " + t2.qb.completions + "/" +
                t2.qb.attempts + " Passing, " + t2.qb.tds + " TDs, " + t2.qb.ints + " INTs");
        System.out.println(t2.rb.getName() + ": " + t2.rb.carries + " rushing attempts for " +
                t2.rb.yards + " yards");
        System.out.println(t2.kr.getName() + ": " + t2.kr.makes + "/" +
                t2.kr.attempts + " Kicking");
        
        System.out.println("Press enter to continue simulation.");
        pauser.nextLine();
        quarter++;
        minutes = 15;
        seconds = 0;
        
    }
    
    
    void gameIsOver()
    {
        System.out.println("GAME IS OVER");
        System.out.println("");
        System.out.println("FINAL SCORE: "+t1.getName()+" "+t1.score+" - "+
                t2.getName()+" "+t2.score);
        System.out.println();
        System.out.println("----- Final Box Score -----");
        System.out.println("Team 1 Stats:");
        System.out.println(t1.qb.getName() + ": " + t1.qb.completions + "/" +
                t1.qb.attempts + " Passing, " + t1.qb.tds + " TDs, " + t1.qb.ints + " INTs");
        System.out.println(t1.rb.getName() + ": " + t1.rb.carries + " rushing attempts for " +
                t1.rb.yards + " yards");
        System.out.println(t1.kr.getName() + ": " + t1.kr.makes + "/" +
                t1.kr.attempts + " Kicking");
        
        System.out.println("Team 2 Stats:");
        System.out.println(t2.qb.getName() + ": " + t2.qb.completions + "/" +
                t2.qb.attempts + " Passing, " + t2.qb.tds + " TDs, " + t2.qb.ints + " INTs");
        System.out.println(t2.rb.getName() + ": " + t2.rb.carries + " rushing attempts for " +
                t2.rb.yards + " yards");
        System.out.println(t2.kr.getName() + ": " + t2.kr.makes + "/" +
                t2.kr.attempts + " Kicking");
        
    }
    
    
    public static void main(String[] args) 
    {    
        QB qb1 = new QB("Drew","Brees",80,6); 
        QB qb2 = new QB("Andrew","Luck",70,5);
        RB rb1 = new RB("Marshwn","Lynch",2);
        RB rb2 = new RB("Jamaal","Charles",2);
        WR wr1 = new WR("Calvin","Johnson",97);
        WR wr3 = new WR("Odell","Beckham",99);
        WR wr2 = new WR("Demaryius","Thomas",98);
        WR wr4 = new WR("Brandon","Lafell",95);
        TE te1 = new TE("Jimmy","Graham",97,0);
        TE te2 = new TE("Rob","Gronkowski",98,0);
        MLB lb1 = new MLB("Luke","Kuechly",2);
        MLB lb2 = new MLB("D'Qwell","Jackson",2);
        CB cb1 = new CB("Richard","Sherman",3);
     
        
        CB cb2 = new CB("Darell","Revis",2);
        SS ss1 = new SS("Earl","Thomas",2);
        SS ss2 = new SS("Troy","Polamalu",2);
        KR kr1 = new KR("Mason","Crosby",90,95);
        KR kr2 = new KR("Adam","Vinnaterri",95,90);
        PR pr1 = new PR("Thomas","Morestead",95);
        PR pr2 = new PR("Shane","Lechler",90);
        Team NFC = new Team("NFC",qb1,rb1,wr1,wr3,te1,lb1,cb1,ss1,kr1,pr1);
        Team AFC = new Team("AFC",qb2,rb2,wr2,wr4,te2,lb2,cb2,ss2,kr2,pr2);
        Football game = new Football(NFC,AFC);
        try {
            game.startGame();
        } catch (InterruptedException ex) {
            Logger.getLogger(Football.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}