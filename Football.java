package football;

import football.SpecialTeams.PR;
import football.SpecialTeams.KR;
import football.Defense.LB;
import football.Defense.S;
import football.Defense.CB;
import football.Defense.DE;
import football.Defense.DT;
import football.Offense.FB;
import football.Offense.OLINE;
import football.Offense.TE;
import football.Offense.RB;
import football.Offense.QB;
import football.Offense.WR;
import football.SpecialTeams.RS;
import java.awt.Color;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * To Do:
 *  Watch text part of swing tutorial
 *  change the pauser.nextLine thing to waiting for a buttonPress
 *  fix the score labels from not always showing properly
 *  fix the timer label from not always showing properly
 *  add in stats to the stats panel
 *  create a function that updates the stats and score after each play
 *  add in a pause button
 *  add in multiple team profiles
 * 
 * Things to fix:
 *  TD yard bug (extra yards are added into stats for pass AND run plays)
 *  multiple times a pass is intercepted
 *  update 1, 2, 3, 4 to 1st, 2nd, 3rd, 4th
 *  bump up yards, lower interceptions
 *  update sacks to take line into consideration
 *  GoForIt clause
 *  overtime clause
 *  
 * 
 * Code cleanup:
 *  make the score being printed out its own function
 *  make printing stats its own function
 * 
 * This program simulates through a football game with 
 * abbreviated football rosters. The game is played between an
 * NFC all-star team and an AFC all-star team. The program should
 * simulate the game accurate to a real football game with the exception of 
 * no kickoffs (ball will be placed at the 25 yard line). 
 * 
 * This is version 1 of the football simulator. 
 * 
 * Version 2 will add in either 
 * graphics using Java Swing, A game engine, or javascript. 
 * 
 * Version 3 - The stats will be saved to a database table in MySQL.
 * 
 * things to tweak:  
 *      encapsulate the functions in football.java more efficiently
 *      make big plays more likely?
 *      make run plays take up more time    
 *            
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
 *      Make generation of players and teams their own function
 *      make the generation of the play its own function
 *      Add in graphics
 *           arrow showing direction, 
 *           displaying the println statement in the screen, 
 *
 */

public class Football
{
    //Random initializor
    Random RAND = new Random();
    
    //Game Time Presets
    static int minutes = 15;
    static int seconds = 00;
    
    //Game Logic Presets
    static int quarter = 1;
    static int yardline;
    boolean touchdown = false;
    boolean gameIsOver = false;
    boolean overtime = false;
    boolean goForIt = false;
    static int down=1;
    static int ytg=10;
    
    Team t1;
    Team t2;
    Scanner pauser = new Scanner(System.in);
    
    static JFrame frame = new JFrame(); 
    static JPanel displayPanel = new JPanel();
    static JPanel buttonPanel = new JPanel();
    static JPanel scorePanel = new JPanel();
    static JPanel statPanel1 = new JPanel();
    static JPanel statPanel2 = new JPanel();
    static JPanel titlePanel = new JPanel();
    static JLabel label1 = new JLabel();
    static JLabel label2 = new JLabel();
    static JLabel label3 = new JLabel();
    static JLabel NFCScore = new JLabel();
    static JLabel AFCScore = new JLabel();
    static JTextArea display = new JTextArea(10,50);
    static JScrollPane scroll = new JScrollPane(display);
    static JButton startButton = new JButton();
    static JButton conButton = new JButton();
    static JButton exportButton = new JButton();
    static ImageIcon image = new ImageIcon("football.png");
    
    
        
        
    
    public Football(Team t1,Team t2)
    {
        //Team Presets
        //t1 is home team
        this.t1 = t1;
        this.t2 = t2;
        
        display.append("Today's Matchup: " + t2.getName() + " vs. " + t1.getName() + "\n");
        //Home Team Offense
        display.append(t1.getName()+"'s Starting Lineup:" + "\n");
        display.append("Quarterback: "+t1.qb.getName() + "\n");
        display.append("Runningback: "+t1.rb.getName() + "\n");
        display.append("Fullback: "+t1.fb.getName() + "\n");
        display.append("Wide Receiver: "+t1.wr1.getName() + "\n");
        display.append("Wide Receiver: "+t1.wr2.getName() + "\n");
        display.append("Wide Receiver: "+t1.wr3.getName() + "\n");
        display.append("Tight End: "+t1.te.getName() + "\n");
        display.append("Left Tackle: "+t1.lt.getName() + "\n");
        display.append("Left Guard: "+t1.lg.getName() + "\n");
        display.append("Center: "+t1.c.getName() + "\n");
        display.append("Right Guard: "+t1.rg.getName() + "\n");
        display.append("Right Tackle: "+t1.rt.getName() + "\n");
        
        //Home Team Defense
        display.append("Left End: "+t1.le.getName() + "\n");
        display.append("Defensive Tackle: "+t1.dt1.getName() + "\n");
        display.append("Defensive Tackle: "+t1.dt2.getName() + "\n");
        display.append("Right End: "+t1.re.getName() + "\n");
        display.append("Left Outside Linebacker: "+t1.lolb.getName() + "\n");
        display.append("Middle Linebacker: "+t1.mlb.getName() + "\n");
        display.append("Right Outside Linebacker: "+t1.rolb.getName() + "\n");
        display.append("Cornerback: "+t1.cb1.getName() + "\n");
        display.append("Cornerback: "+t1.cb2.getName() + "\n");
        display.append("Strong Safety: "+t1.ss.getName() + "\n");
        display.append("Free Safety: "+t1.fs.getName() + "\n");
        
        //Home Team Special Teams
        display.append("Kicker: "+t1.kr.getName() + "\n");
        display.append("Punter: "+t1.pr.getName() + "\n");
        display.append("Return Specialist: " + t1.rs.getName() + "\n\n");
        
        System.out.println();
        
        //Away Team Offense
        display.append(t2.getName()+"'s Starting Lineup:" + "\n");
        display.append("Quarterback: "+t2.qb.getName() + "\n");
        display.append("Runningback: "+t2.rb.getName() + "\n");
        display.append("Fullback: "+t2.fb.getName() + "\n");
        display.append("Wide Receiver: "+t2.wr1.getName() + "\n");
        display.append("Wide Receiver: "+t2.wr2.getName() + "\n");
        display.append("Wide Receiver: "+t2.wr3.getName() + "\n");
        display.append("Tight End: "+t2.te.getName() + "\n");
        display.append("Left Tackle: "+t2.lt.getName() + "\n");
        display.append("Left Guard: "+t2.lg.getName() + "\n");
        display.append("Center: "+t2.c.getName() + "\n");
        display.append("Right Guard: "+t2.rg.getName() + "\n");
        display.append("Right Tackle: "+t2.rt.getName() + "\n");
        
        //Away Team Defense
        display.append("Left End: "+t2.le.getName() + "\n");
        display.append("Defensive Tackle: "+t2.dt1.getName() + "\n");
        display.append("Defensive Tackle: "+t2.dt2.getName() + "\n");
        display.append("Right End: "+t2.re.getName() + "\n");
        display.append("Left Outside Linebacker: "+t2.lolb.getName() + "\n");
        display.append("Middle Linebacker: "+t2.mlb.getName() + "\n");
        display.append("Right Outside Linebacker: "+t2.rolb.getName() + "\n");
        display.append("Cornerback: "+t2.cb1.getName() + "\n");
        display.append("Cornerback: "+t2.cb2.getName() + "\n");
        display.append("Strong Safety: "+t2.ss.getName() + "\n");
        display.append("Free Safety: "+t2.fs.getName() + "\n");
        
        //Home Team Special Teams
        display.append("Kicker: "+t2.kr.getName() + "\n");
        display.append("Punter: "+t2.pr.getName() + "\n");
        display.append("Return Specialist: " + t2.rs.getName() + "\n\n");
        
        //Have below in seperate field e.g. Play;
        //pause
        //Thread.sleep(1000) in between plays
        //have game pause when quarter ends
        //once time = 0.00 and quarter = 4, gameisOver() = true - 
        // - displays Final Score Player of the Game and their stats
        
    }
    
    void startGame() throws InterruptedException
    {
        display.append("Press enter to proceed to the coin toss..." + "\n");
        display.setCaretPosition(display.getDocument().getLength());
        //String input = pauser.nextLine();
        //display.append(input + "\n");
        display.append("Simulating coin toss..." + "\n");
        display.setCaretPosition(display.getDocument().getLength());
        Thread.sleep(500);
        int n = RAND.nextInt(2);
        if(n==1)
        {
            t1.ct = true;
            t1.possession = true;
            t2.ct = false;
            t2.possession = false;
            yardline = 25;
            display.append(t1.getName());
        }
        else
        {
            t1.ct = false;
            t1.possession = false;
            t2.ct = true;
            t2.possession = true;
            yardline = 75;
            display.append(t2.getName());
        }
        display.append(" has won the toss and will recieve the ball first." + "\n");
        display.append("Press enter to start the game..." + "\n");
        display.setCaretPosition(display.getDocument().getLength());
        //pauser.nextLine();
        Play(); 
    }
    
    void Play() throws InterruptedException
    {
        while(gameIsOver!=true)
        { 
            //update the UI here 
            //function for updating scoreboard and stats goes here
            Thread.sleep(600);

            //Correctly Display Time Left
            if(minutes<10&&seconds<10){
                display.append("0"+minutes+":0"+seconds+" ");
                label3.setText("0"+minutes+":0"+seconds+" ");
                scorePanel.add(label3);}
            else if(minutes<10){
                display.append("0"+minutes+":"+seconds+" ");
                label3.setText("0"+minutes+":"+seconds+" ");
                scorePanel.add(label3);}
            else if(seconds<10){
                display.append(minutes+":0"+seconds+" ");
                label3.setText(""+minutes+":0"+seconds+" ");
                scorePanel.add(label3);}
            else{
                display.append(minutes+":"+seconds+" ");
                label3.setText(""+minutes+":"+seconds+" ");
                scorePanel.add(label3);}
        
            if(t1.possession==true)
            {
                //Display Ball on ...
                display.append(t1.getName()+" ball on the ");
                if(yardline<50)
                    display.append(t1.getName()+" "+yardline);
                else if(yardline>50)
                    display.append(t2.getName()+" "+(100-yardline));
                else
                    display.append(Integer.toString(yardline));
                if(yardline>=90 && ytg>=(100-yardline))
                    DisplayDistanceAndGoal(down);
                else
                    DisplayDistance(down); 

                //Field Goal Time
                if(down==4&&yardline>=60&&!goForIt)
                {
                    int fieldGoal = t1.kr.power*t1.kr.accuracy;
                    int make = RAND.nextInt(10000);
                    t1.kr.attempts++;
                    
                    //Adjust Kick Difficulty
                    //0-19
                    if(yardline>=98)
                    {
                        fieldGoal = fieldGoal + ((10000-fieldGoal) * (4/5));
                    }
                    //20-29
                    if(yardline<98&&yardline>=88)
                    {
                        fieldGoal = fieldGoal + ((10000-fieldGoal) * (2/5));
                    }
                    //30-39
                    //Do not change the rate
                    //40-49
                    if(yardline<78&&yardline>=68)
                    {
                        fieldGoal = fieldGoal - (fieldGoal * (5/100));
                    }
                    //50+
                    if(yardline<68)
                    {
                        fieldGoal = fieldGoal - (fieldGoal * (15/100));
                    }                    
                    
                    display.append(t1.kr.getShortName()+" "+(100-yardline+17)+
                            " yard field goal try is ");
                    
                    //Failed Attempt
                    if(make>fieldGoal)
                    {
                        display.append("no good.\n");
                        yardline=yardline-7;
                        if(yardline >80)
                            yardline = 80;
                    }
                    //Made Attempt
                    else
                    {
                        display.append("good.\n");
                        t1.kr.makes++;
                        t1.score=t1.score+3;
                        yardline=75;
                    }
                    display.append("SCORE: "+t1.getName()+" "+t1.score+" - "
                            +t2.getName()+" "+t2.score+"\n");
                    NFCScore.setText(""+t1.score+"");
                    statPanel1.add(NFCScore);
                    AFCScore.setText(""+t2.score+"");
                    statPanel2.add(AFCScore);
                    down=1;
                    ytg=10;
                    t1.possession=false;
                    t2.possession=true;
                    seconds=seconds-5;
                }
                //Punting Time
                else if(down==4&&!goForIt)
                {
                    int muff = 0;
                    //get -10 to 10 range
                    int distance = RAND.nextInt(20)-10;
                    distance = distance + t1.pr.power/2;
                    
                    //If distance exceeds field
                    //Set distance to length limit and do touchback
                    if(distance+yardline>=100)
                        distance = 100-yardline;
                    display.append(t1.pr.getShortName()+" punts "+distance+" yards");
                    
                    //Touchback
                    if(distance+yardline==100)
                    {
                        display.append(" for a touchback.\n");
                        yardline=80;
                    }
                    //Return
                    else
                    {
                        int returnability = RAND.nextInt(10000);
                        //get return distance
                        int runback = RAND.nextInt(20);
                        //Punt Return TD
                        if(returnability + t2.rs.prowess > 9900)
                        {
                            display.append(". \n" + t2.rs.getShortName() + " returns the ball "
                            + (yardline + distance) + " yards.\n");
                            touchdown=true;
                        }
                        //Good Return
                        else if(returnability + t2.rs.prowess > 9300)
                        {
                            runback = runback + 15;
                            display.append(". \n" + t2.rs.getShortName() + " returns the ball "
                            + runback + " yards.\n");
                            yardline = yardline + distance - runback;
                        }
                        //Average Return
                        else if(returnability + t2.rs.prowess > 8000)
                        {
                            runback = runback%10 + 5;
                            display.append(". \n" + t2.rs.getShortName() + " returns the ball "
                            + runback + " yards.\n");
                            yardline = yardline + distance - runback;
                        }
                        //Fair Catch
                        else if(returnability + t2.rs.prowess > 1000)
                        {
                        display.append(". Fair catch made by "+
                                t2.rs.getShortName()+".\n");
                        yardline=yardline+distance;
                        }
                        //Bad Return
                        else if(returnability + t2.rs.prowess > 200)
                        {
                            runback = runback%10 - 11;
                            //Make the minimum starting position the 1 yard line
                            if(yardline + distance - runback >= 100)
                                runback = 99-yardline-distance;
                            
                            display.append(". \n" + t2.rs.getShortName() + " returns the ball "
                            + runback + " yards.\n");
                            yardline = yardline + distance - runback;
                        }
                        //Muffed Punt
                        else
                        {
                            muff = RAND.nextInt() % 2;
                            if(muff==1)
                            display.append(". \n" + t2.rs.getShortName() + " muffs the punt and "
                            + t1.name + " recovers. \n");
                            else
                            display.append(". \n" + t2.rs.getShortName() + " muffs the punt and "
                            + t2.name + " recovers. \n");
                        }
                    }
                    //Muffed Punt possession check
                    if (muff==0)
                    {
                        t1.possession=false;
                        t2.possession=true;
                    }
                    else
                    {
                        t1.possession=true;
                        t2.possession=false;
                    }
                        down=1;
                        ytg=10;
                        display.append("SCORE: "+t1.getName()+" "+t1.score+" - "
                                +t2.getName()+" "+t2.score+"\n");
                        seconds=seconds-10;
                    
                }
                //Pass or Run
                else
                {
                    //Determine if team will pass or run
                    //50/50 for now
                    int n = RAND.nextInt()%4;
                    if(n<2) //pass
                    {
                        int s = RAND.nextInt(100)+1;
                        if(s<=t1.qb.sack) //QB gets sacked
                        {
                            int loss = RAND.nextInt(3)+2;
                            display.append(t1.qb.getShortName()+" sacked for "+loss+
                                    " yard loss.");
                            down++;
                            //Add: check for safety
                            yardline=yardline-loss;
                            ytg=ytg+loss;
                            seconds=seconds-5-25;
                        }
                        else
                        {
                            //length is depth of the pass
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
                            //Receiving by who
                            int rec = RAND.nextInt(15)+1;
                            //Catch Percentage
                            int ctch = RAND.nextInt(100)+1;
                            //Turnover Percentage
                            int t = RAND.nextInt(100)+1;
                            if(ctch<=t1.qb.completion)
                            {
                                //WR1 V CB1
                                if(rec<=7)
                                {
                                    if(t<=t2.cb1.forceTurnover) // intercepted by CB1
                                    {
                                        //Add: return yards based on prowess like RS
                                        display.append("Pass intercepted by "+
                                                t2.cb1.getShortName()+".");
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
                                        if(cr>t1.wr1.receive) //pass dropped
                                        {
                                            display.append(t1.qb.getShortName()+" pass"
                                                    + " dropped by "+t1.wr1.getShortName()+".");
                                            down++;
                                            t1.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else //complete pass
                                        {
                                            display.append(t1.qb.getShortName()+" pass complete to "+
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
                                            {
                                                touchdown=true;
                                                t1.qb.tds++;
                                            }                                                  
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                                //WR2 V CB2 && FS
                                else if(rec<=11&&rec>7)
                                {
                                    if(t<=t2.cb2.forceTurnover) // intercepted by CB2
                                    {
                                        //Add: return yards based on prowess like RS
                                        display.append("Pass intercepted by "+
                                                t2.cb2.getShortName()+".");
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
                                    if(t<=t2.fs.forceTurnover) // intercepted by FS
                                    {
                                        //Add: return yards based on prowess like RS
                                        display.append("Pass intercepted by "+
                                                t2.fs.getShortName()+".");
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
                                        if(cr>t1.wr2.receive) //pass dropped
                                        {
                                            display.append(t1.qb.getShortName()+" pass"
                                                    + " dropped by "+t1.wr2.getShortName()+".");
                                            down++;
                                            t1.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else //complete pass
                                        {
                                            display.append(t1.qb.getShortName()+" pass complete to "+
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
                                            {
                                                touchdown=true;
                                                t1.qb.tds++;
                                            }
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                                //WR3 V FS && MLB
                                else if(rec<=13&&rec>11)
                                {
                                    if(t<=t2.fs.forceTurnover) // intercepted by FS
                                    {
                                        display.append("Pass intercepted by "+
                                                t2.fs.getShortName()+".");
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
                                    if(t<=t2.mlb.forceTurnover) //intercepted by MLB
                                    {
                                        display.append("Pass intercepted by "+
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
                                        if(cr>t1.wr3.receive) //pass dropped
                                        {
                                            display.append(t1.qb.getShortName()+" pass"
                                                    + " dropped by "+t1.wr3.getShortName()+".");
                                            down++;
                                            t1.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else //complete pass
                                        {
                                            display.append(t1.qb.getShortName()+" pass complete to "+
                                                t1.wr3.getShortName()+" for "+length+" yards.");
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
                                            {
                                                touchdown=true;
                                                t1.qb.tds++;
                                            }
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                                //TE V MLB && SS
                                else
                                {
                                    if(t<=t2.mlb.forceTurnover) // intercepted by MLB
                                    {
                                        display.append("Pass intercepted by "+
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
                                    if(t<=t2.ss.forceTurnover) //intercepted by SS
                                    {
                                        display.append("Pass intercepted by "+
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
                                        if(cr>t1.te.receive) //pass dropped
                                        {
                                            display.append(t1.qb.getShortName()+" pass"
                                                    + " dropped by "+t1.te.getShortName()+".");

                                            down++;
                                            t1.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else //complete pass
                                        {
                                            display.append(t1.qb.getShortName()+" pass complete to "+
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
                                                touchdown=true;
                                                t1.qb.tds++;
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
                                display.append(t1.qb.getShortName()+
                                        " pass incomplete, intended for ");
                                if(rec<=7)
                                    display.append(t1.wr1.getShortName()+".");
                                else if(rec<=11&&rec>7)
                                    display.append(t1.wr2.getShortName()+".");
                                else if(rec<=13&&rec>11)
                                    display.append(t1.wr3.getShortName()+".");
                                else
                                    display.append(t1.te.getShortName()+".");
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
                        
                        //length is distance of run
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
                            display.append(length+" yard rush by "+
                                t1.rb.getShortName()+". Fumble forced by "+
                                t2.mlb.getShortName()+". Recovered by "
                                    +t2.getName()+".");
                            t1.rb.yards += length; //will need to change when fixing logic
                            //Touchback
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
                        if(t<=t2.dt1.forceTurnover && length<11) // Only turnovers on shorter runs
                        {
                            display.append(length+" yard rush by "+
                                t1.rb.getShortName()+". Fumble forced by "+
                                t2.dt1.getShortName()+". Recovered by "
                                    +t2.getName()+".");
                            t1.rb.yards += length; //will need to change when fixing logic
                            //Touchback
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
                        if(t<=t2.dt2.forceTurnover && length <6)// Only turnovers on shorter runs
                        {
                            display.append(length+" yard rush by "+
                                t1.rb.getShortName()+". Fumble forced by "+
                                t2.dt2.getShortName()+". Recovered by "
                                    +t2.getName()+".");
                            t1.rb.yards += length; //will need to change when fixing logic
                            //Touchback
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
                            display.append(t1.rb.getShortName()+" "+length+" yard rush.");
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
                    display.append("\n");
                }

                if(touchdown==true)//Yay
                {
                    //Add: XP not always true
                    display.append("Touchdown!(extra point good by "
                            +t1.kr.getShortName()+").\n");
                    t1.score=t1.score+7;
                    display.append("SCORE: "+t1.getName()+" "+t1.score+" - "
                            +t2.getName()+" "+t2.score+"\n");
                    NFCScore.setText(""+t1.score+"");
                    statPanel1.add(NFCScore);
                    AFCScore.setText(""+t2.score+"");
                    statPanel2.add(AFCScore);
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
                display.append(t2.getName()+" ball on the ");
                if(yardline<50)
                    display.append(t1.getName()+" "+yardline);
                else if(yardline>50)
                    display.append(t2.getName()+" "+(100-yardline));
                else
                    display.append(Integer.toString(yardline));
                if(yardline<=10 && ytg >=yardline)
                  DisplayDistanceAndGoal(down);
                else
                    DisplayDistance(down);                    

                //Field Goal Time
                if(down==4&&yardline<=40&&!goForIt )
                {
                    int fieldGoal = t2.kr.power*t2.kr.accuracy;
                    int make = RAND.nextInt(10000);
                    t2.kr.attempts++;
                    
                    //Adjust Kick Difficulty
                    //0-19
                    if(yardline<=2)
                    {
                        fieldGoal = fieldGoal + ((10000-fieldGoal) * (4/5));
                    }
                    //20-29
                    if(yardline>2&&yardline<=12)
                    {
                        fieldGoal = fieldGoal + ((10000-fieldGoal) * (2/5));
                    }
                    //30-39
                    //Do not change the rate
                    //40-49
                    if(yardline>22&&yardline<=32)
                    {
                        fieldGoal = fieldGoal - (fieldGoal * (5/100));
                    }
                    //50+
                    if(yardline>32)
                    {
                        fieldGoal = fieldGoal - (fieldGoal * (15/100));
                    }                
                    
                    display.append(t2.kr.getShortName()+" "+(yardline+17)+
                            " yard field goal try is ");
                    
                    //Failed Attempt
                    if(make>fieldGoal)
                    {
                        display.append("no good.\n");
                        yardline=yardline+7;
                        if(yardline <20)
                            yardline = 20;
                    }
                    //Made Attempt
                    else
                    {
                        display.append("good.\n");
                        t2.kr.makes++;
                        t2.score=t2.score+3;
                        yardline=25;
                    }
                    display.append("SCORE: "+t1.getName()+" "+t1.score+" - "
                            +t2.getName()+" "+t2.score+"\n");
                    NFCScore.setText(""+t1.score+"");
                    statPanel1.add(NFCScore);
                    AFCScore.setText(""+t2.score+"");
                    statPanel2.add(AFCScore);
                    down=1;
                    ytg=10;
                    t1.possession=true;
                    t2.possession=false;
                    seconds=seconds-5;
                }
                //Punting Time
                else if(down==4&&!goForIt)
                {
                    int muff = 0;
                    //get -10 to 10 range
                    int distance = RAND.nextInt(20)-10;
                    distance = distance + t2.pr.power/2;
                    
                    //If distance exceeds field
                    //Set distance to length limit and do touchback
                    if(yardline-distance<=0)
                        distance = yardline;
                    display.append(t2.pr.getShortName()+" punts "+distance+" yards");
                    
                    //Touchback
                    if(distance==yardline)
                    {
                        display.append(" for a touchback.\n");
                        yardline=20;
                    }
                    //Return
                    else
                    {
                        int returnability = RAND.nextInt(10000);
                        //Get return distance
                        int runback = RAND.nextInt(20);
                        //Punt Return TD
                        if(returnability + t1.rs.prowess > 9900)
                        {
                            display.append(". \n" + t1.rs.getShortName() + " returns the ball "
                            + ((100-yardline) + distance) + " yards.\n");
                            touchdown=true;
                        }
                        //Good Return
                        else if(returnability + t1.rs.prowess > 9300)
                        {
                            runback = runback + 15;
                            display.append(". \n" + t1.rs.getShortName() + " returns the ball "
                            + runback + " yards.\n");
                            yardline = yardline - distance + runback;
                        }
                        //Average Return
                        else if(returnability + t1.rs.prowess > 8000)
                        {
                            runback = runback%10 + 5;
                            display.append(". \n" + t1.rs.getShortName() + " returns the ball "
                            + runback + " yards.\n");
                            yardline = yardline - distance + runback;
                        }
                        //Fair Catch
                        else if(returnability + t1.rs.prowess > 1000)
                        {
                        display.append(". Fair catch made by "+
                                t1.rs.getShortName()+".\n");
                        yardline=yardline-distance;
                        }
                        //Bad Return
                        else if(returnability + t1.rs.prowess > 200)
                        {
                            runback = runback%10 - 11;
                            //Make the minimum starting position the 1 yard line
                            if(yardline - distance + runback <= 0)
                                runback = 99-yardline-distance;
                            
                            display.append(". \n" + t1.rs.getShortName() + " returns the ball "
                            + runback + " yards.\n");
                            yardline = yardline - distance + runback;
                        }
                        //Muffed Punt
                        else
                        {
                            muff = RAND.nextInt() % 2;
                            if(muff==1)
                            display.append(". \n" + t1.rs.getShortName() + " muffs the punt and "
                            + t2.name + " recovers. \n");
                            else
                            display.append(". \n" + t1.rs.getShortName() + " muffs the punt and "
                            + t1.name + " recovers. \n");
                        }
                    }
                    //Muffed Punt possession check
                    if (muff==0)
                    {
                        t1.possession=true;
                        t2.possession=false;
                    }
                    else
                    {
                        t1.possession=false;
                        t2.possession=true;
                    }
                        down=1;
                        ytg=10;
                        display.append("SCORE: "+t1.getName()+" "+t1.score+" - "
                                +t2.getName()+" "+t2.score+"\n");
                        seconds=seconds-10;
                    
                }
                else
                {
                    //Determine if team will pass or run
                    //50/50 for now
                    int n = RAND.nextInt()%4;
                    if(n<2) //pass
                    {
                        int s = RAND.nextInt(100)+1;
                        if(s<=t2.qb.sack) //QB gets sacked
                        {
                            int loss = RAND.nextInt(3)+2;
                            display.append(t2.qb.getShortName()+" sacked for "+loss+
                                    " yard loss.");
                            down++;
                            //Add: check for safety
                            ytg=ytg+loss;
                            yardline=yardline+loss;
                            seconds=seconds-7-20;
                        }
                        else
                        {
                            //length is depth of the pass
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
                            //Receiving by who
                            int rec = RAND.nextInt(15)+1;
                            //Catch Percentage
                            int ctch = RAND.nextInt(100)+1;
                            //Turnover Percentage
                            int t = RAND.nextInt(100)+1;
                            if(ctch<=t2.qb.completion)
                            {
                                //WR1 V CB1
                                if(rec<=7)
                                {
                                    if(t<=t1.cb1.forceTurnover)// intercepted by CB1
                                    {
                                        //Add: return yards based on prowess like RS
                                        display.append("Pass intercepted by "+
                                                t1.cb1.getShortName()+".");
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
                                        if(cr>t2.wr1.receive) //pass dropped
                                        {
                                            display.append(t2.qb.getShortName()+" pass"
                                                    + " dropped by "+t2.wr1.getShortName()+".");
                                            down++;
                                            t2.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else //pass complete
                                        {
                                            display.append(t2.qb.getShortName()+" pass complete to "+
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
                                            {
                                                touchdown=true;
                                                t2.qb.tds++;
                                            }
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                                //WR2 V CB2 && FS
                                else if(rec<=11&&rec>7)
                                {
                                    if(t<=t1.cb2.forceTurnover) // intercepted by CB2
                                    {
                                        //Add: return yards based on prowess like RS
                                        display.append("Pass intercepted by "+
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
                                    if(t<=t1.fs.forceTurnover) // intercepted by FS
                                    {
                                        //Add: return yards based on prowess like RS
                                        display.append("Pass intercepted by "+
                                                t1.fs.getShortName()+".");
                                        if(yardline+length<=0)
                                            yardline=20;
                                        else
                                            yardline=yardline+length;
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
                                        if(cr>t2.wr2.receive) //pass dropped
                                        {
                                            display.append(t2.qb.getShortName()+" pass"
                                                    + " dropped by "+t2.wr2.getShortName()+".");
                                            down++;
                                            t2.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else //pass complete
                                        {
                                            display.append(t2.qb.getShortName()+" pass complete to "+
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
                                            {
                                                touchdown=true;
                                                t2.qb.tds++;
                                            }
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                                //WR3 V FS && MLB
                                else if(rec<=13&&rec>11)
                                {
                                    if(t<=t1.fs.forceTurnover) // intercepted by FS
                                    {
                                        //Add: return yards based on prowess like RS
                                        display.append("Pass intercepted by "+
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
                                    if(t<=t1.mlb.forceTurnover) // intercepted by MLB
                                    {
                                        //Add: return yards based on prowess like RS
                                        display.append("Pass intercepted by "+
                                                t1.mlb.getShortName()+".");
                                        if(yardline+length<=0)
                                            yardline=20;
                                        else
                                            yardline=yardline+length;
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
                                        if(cr>t2.wr3.receive) //pass dropped
                                        {
                                            display.append(t2.qb.getShortName()+" pass"
                                                    + " dropped by "+t2.wr3.getShortName()+".");
                                            down++;
                                            t2.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else //pass complete
                                        {
                                            display.append(t2.qb.getShortName()+" pass complete to "+
                                                t2.wr3.getShortName()+" for "+length+" yards.");
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
                                            {
                                                touchdown=true;
                                                t2.qb.tds++;
                                            }
                                            seconds=seconds-5-20;
                                        if(length>10)
                                            seconds=seconds-(length-10)/2;
                                        }
                                    }
                                }
                                //TE V MLB && SS
                                else
                                {
                                    if(t<=t1.mlb.forceTurnover) //intercepted by MLB
                                    {
                                        display.append("Pass intercepted by "+
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
                                    if(t<=t1.ss.forceTurnover) //intercepted by SS
                                    {
                                        display.append("Pass intercepted by "+
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
                                        if(cr>t2.te.receive)
                                        {
                                            display.append(t2.qb.getShortName()+" pass"
                                                    + " dropped by "+t2.te.getShortName()+".");
                                            down++;
                                            t2.qb.attempts++;
                                            seconds=seconds-5;
                                            if(length>10)
                                                seconds=seconds-(length-10)/2;
                                        }
                                        else
                                        {
                                            display.append(t2.qb.getShortName()+" pass complete to "+
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
                                display.append(t2.qb.getShortName()+
                                        " pass incomplete, intended for ");
                                if(rec<=7)
                                    display.append(t2.wr1.getShortName()+".");
                                else if(rec<=11&&rec>7)
                                    display.append(t2.wr2.getShortName()+".");
                                else if(rec<=13&&rec>11)
                                    display.append(t2.wr3.getShortName()+".");
                                else
                                    display.append(t2.te.getShortName()+".");
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
                        //length is distance of run
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
                            display.append(length+" yard rush by "+
                                t2.rb.getShortName()+". Fumble forced by "+
                                t1.mlb.getShortName()+". Recovered by "
                                    +t1.getName()+".");
                            t2.rb.yards += length; //will have to fix when changing logic
                            //Touchback
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
                        if(t<=t1.dt1.forceTurnover && length <11) // Only turnovers on shorter runs
                        {
                            display.append(length+" yard rush by "+
                                t2.rb.getShortName()+". Fumble forced by "+
                                t1.dt1.getShortName()+". Recovered by "
                                    +t1.getName()+".");
                            t2.rb.yards += length; //will have to fix when changing logic
                            //Touchback
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
                        if(t<=t1.dt2.forceTurnover && length <6) // Only turnovers on shorter runs
                        {
                            display.append(length+" yard rush by "+
                                t2.rb.getShortName()+". Fumble forced by "+
                                t1.dt2.getShortName()+". Recovered by "
                                    +t1.getName()+".");
                            t2.rb.yards += length; //will have to fix when changing logic
                            //Touchback
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
                            display.append(t2.rb.getShortName()+" "+length+" yard rush.");
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
                    display.append("\n");
                }

                if(touchdown==true)//Yay
                {
                    //Add: XP not always true                
                    display.append("Touchdown!(extra point good by "
                            +t2.kr.getShortName()+").\n");
                    t2.score=t2.score+7;
                    display.append("SCORE: " +t1.getName() + " " + t1.score + 
                            " - " + t2.getName() + " " + t2.score+"\n");
                    yardline=25;
                    NFCScore.setText(""+t1.score+"");
                    statPanel1.add(NFCScore);
                    AFCScore.setText(""+t2.score+"");
                    statPanel2.add(AFCScore);
                    t2.possession=false;
                    t1.possession=true;
                    down=1;
                    ytg=10;
                    touchdown=false;
                }

            }

            //Curl seconds back to proper minutes
            if(seconds<0&&minutes!=0)
            {
                minutes--;
                seconds = seconds +60;
            }

            //End of Quarter Changes
            if(minutes==0 && seconds<=0)
            {
                switch(quarter)
                {
                    //End Game Condition
                    //End Q4
                    case(4):
                    {
                        gameIsOver();
                        gameIsOver=true;
                    }
                    //Halftime Condition
                    //End Q2
                    case (2):
                    {
                        Half();
                    }
                    //End of Q1, Q3
                    default:
                    {
                        endOfQuarter();
                    }                    
                }       
            }
            display.setCaretPosition(display.getDocument().getLength());
        } 
        
    }
    
    void Half()
    {
        display.append("END OF HALF.\n\n");
        
        display.append("Team 1 Stats:\n");
        display.append(t1.qb.getName() + ": " + t1.qb.completions + "/" +
                t1.qb.attempts + " Passing, " + t1.qb.tds + " TDs, " + t1.qb.ints + " INTs\n");
        display.append(t1.rb.getName() + ": " + t1.rb.carries + " rushing attempts for " +
                t1.rb.yards + " yards\n");
        display.append(t1.kr.getName() + ": " + t1.kr.makes + "/" +
                t1.kr.attempts + " Kicking\n");
        
        display.append("Team 2 Stats:\n");
        display.append(t2.qb.getName() + ": " + t2.qb.completions + "/" +
                t2.qb.attempts + " Passing, " + t2.qb.tds + " TDs, " + t2.qb.ints + " INTs\n");
        display.append(t2.rb.getName() + ": " + t2.rb.carries + " rushing attempts for " +
                t2.rb.yards + " yards\n");
        display.append(t2.kr.getName() + ": " + t2.kr.makes + "/" +
                t2.kr.attempts + " Kicking\n");
        
        display.append("Press enter to continue simulation.\n");
        display.setCaretPosition(display.getDocument().getLength());
        //pauser.nextLine();
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
        display.append("END OF QUARTER.\n\n");
        
        display.append("Team 1 Stats:\n");
        display.append(t1.qb.getName() + ": " + t1.qb.completions + "/" +
                t1.qb.attempts + " Passing, " + t1.qb.tds + " TDs, " + t1.qb.ints + " INTs\n");
        display.append(t1.rb.getName() + ": " + t1.rb.carries + " rushing attempts for " +
                t1.rb.yards + " yards\n");
        display.append(t1.kr.getName() + ": " + t1.kr.makes + "/" +
                t1.kr.attempts + " Kicking\n");
        
        display.append("Team 2 Stats:\n");
        display.append(t2.qb.getName() + ": " + t2.qb.completions + "/" +
                t2.qb.attempts + " Passing, " + t2.qb.tds + " TDs, " + t2.qb.ints + " INTs\n");
        display.append(t2.rb.getName() + ": " + t2.rb.carries + " rushing attempts for " +
                t2.rb.yards + " yards\n");
        display.append(t2.kr.getName() + ": " + t2.kr.makes + "/" +
                t2.kr.attempts + " Kicking\n");
        
        display.append("Press enter to continue simulation.\n");
        display.setCaretPosition(display.getDocument().getLength());

        //pauser.nextLine();
        quarter++;
        minutes = 15;
        seconds = 0;
        
    }
    
    void gameIsOver()
    {
        display.append("GAME IS OVER" + "\n\n");
        
        display.append("FINAL SCORE: "  +t1.getName() + " " + t1.score + " - " +
                t2.getName() + " " + t2.score + "\n\n");
        
        display.append("----- Final Box Score -----\n");
        display.append("Team 1 Stats:\n");
        display.append(t1.qb.getName() + ": " + t1.qb.completions + "/" +
                t1.qb.attempts + "for "+ t1.qb.passYards +"yards Passing, " + 
                t1.qb.tds + " TDs, " + t1.qb.ints + " INTs\n");
        display.append(t1.rb.getName() + ": " + t1.rb.carries + " rushing attempts for " +
                t1.rb.yards + " yards, "+t1.rb.tds+" TDs\n");
        display.append(t1.kr.getName() + ": " + t1.kr.makes + "/" +
                t1.kr.attempts + " Kicking\n");
        
        display.append("Team 2 Stats:\n");
        display.append(t2.qb.getName() + ": " + t2.qb.completions + "/" +
                t2.qb.attempts + "for "+ t2.qb.passYards +"yards Passing, " + 
                t2.qb.tds + " TDs, " + t2.qb.ints + " INTs\n");
        display.append(t2.rb.getName() + ": " + t2.rb.carries + " rushing attempts for " +
                t2.rb.yards + " yards, "+t2.rb.tds+" TDs\n");
        display.append(t2.kr.getName() + ": " + t2.kr.makes + "/" +
                t2.kr.attempts + " Kicking\n");
        display.setCaretPosition(display.getDocument().getLength());
    }
    
    void DisplayDistance(int down)
    {
        if(down==1)
            display.append(". " + down + "st and " + ytg + ".  ");
        else if(down==2)
            display.append(". " + down + "nd and " + ytg + ".  ");
        else if(down==3)
            display.append(". " + down + "rd and " + ytg + ".  ");
        else if(down==4)
            display.append(". " + down + "th and " + ytg + ".  ");
    }
    
        void DisplayDistanceAndGoal(int down)
    {
        if(down==1)
            display.append(". " + down + "st and goal.  ");
        else if(down==2)
            display.append(". " + down + "nd and goal.  ");
        else if(down==3)
            display.append(". " + down + "rd and goal.  ");
        else if(down==4)
            display.append(". " + down + "th and goal.  ");
         
    }
    
    
    public static void main(String[] args) 
    {    
        //initiate the UI
        displayPanel.setBackground(Color.black);
        displayPanel.setBounds(0,300,900,400);
        display.setEditable(false);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        displayPanel.add(scroll);
        
        /*
        buttonPanel.setBackground(Color.blue);
        buttonPanel.setBounds(0,0,100,300);
        startButton.setBounds(50, 50, 75,50);
        */
        //startButton.setHorizontalAlignment(JLabel.CENTER);
        //startButton.setVerticalAlignment(JLabel.CENTER);
        startButton.setText("Start");
        buttonPanel.add(startButton);
        
        statPanel1.setBackground(Color.green);
        statPanel1.setBounds(700,0,200,300);
        label1.setText("NFC \n");
        NFCScore.setText("0");
        statPanel1.add(label1);
        statPanel1.add(NFCScore);
        

        
        statPanel2.setBackground(Color.green);
        statPanel2.setBounds(0,0,200,300);
        label2.setText("AFC \n");
        AFCScore.setText("0");
        statPanel2.add(label2);
        statPanel2.add(AFCScore);
        
        scorePanel.setBackground(Color.RED);
        scorePanel.setBounds(200,200,500,100);
        
        
        titlePanel.setBackground(Color.cyan);
        titlePanel.setBounds(100,0,600,200);
        
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); //play with to get the ui wrapped
        frame.setSize(900,650);
        frame.add(displayPanel);
        frame.add(buttonPanel);
        frame.add(statPanel1);
        frame.add(statPanel2);
        frame.add(scorePanel);
        frame.add(titlePanel);
        //frame.pack();
        frame.setVisible(true);
        frame.setIconImage(image.getImage());
        
        QB qbTeam1 = new QB("Drew","Brees",80,6);
        FB fbTeam1 = new FB("Full","Back",5);
        RB rbTeam1 = new RB("Marshawn","Lynch",2);
        WR wr1Team1 = new WR("Calvin","Johnson",97);
        WR wr2Team1 = new WR("Odell","Beckham",99);
        WR wr3Team1 = new WR("Desean", "Jackson", 98);
        TE teTeam1 = new TE("Jimmy","Graham",97,0);
        OLINE ltTeam1 = new OLINE("Trent", "Williams", 0);
        OLINE lgTeam1 = new OLINE("Ben", "Grubbs", 0);
        OLINE cTeam1 = new OLINE("Ryan", "Kalil", 0);
        OLINE rgTeam1 = new OLINE("Kyle", "Long", 0);
        OLINE rtTeam1 = new OLINE("Tyron", "Smith", 0);
        DE leTeam1 = new DE("Robert","Quinn",0);
        DT dt1Team1 = new DT("Ndamukong","Suh",0);
        DT dt2Team1 = new DT("Gerald","McCoy",0);
        DE reTeam1 = new DE("Cameron","Jordan",5);
        LB lolbTeam1 = new LB("Brian","Orakpo",2);
        LB mlbTeam1 = new LB("Luke","Kuechly",2);
        LB rolbTeam1 = new LB("John","Abraham",2);
        CB cb1Team1 = new CB("Richard","Sherman",3);
        CB cb2Team1 = new CB("Patrick","Peterson",3);
        S ssTeam1 = new S("Earl","Thomas",2);
        S fsTeam1 = new S("Eric","Reid",2);
        KR krTeam1 = new KR("Mason","Crosby",90,95);
        PR prTeam1 = new PR("Thomas","Morestead",95);
        RS rsTeam1 = new RS("Cordarrelle","Patterson",0);
        
        
        QB qbTeam2 = new QB("Andrew","Luck",70,5);
        FB fbTeam2 = new FB("","",5);
        RB rbTeam2 = new RB("Jamaal","Charles",2);        
        WR wr1Team2 = new WR("Demaryius","Thomas",98);
        WR wr2Team2 = new WR("Brandon","Lafell",95);
        WR wr3Team2 = new WR("A.J.", "Green", 99);        
        TE teTeam2 = new TE("Rob","Gronkowski",98,0);        
        OLINE ltTeam2 = new OLINE("Joe", "Thomas", 0);        
        OLINE lgTeam2 = new OLINE("Logan", "Mankins", 0);        
        OLINE cTeam2 = new OLINE("Mike", "Pouncey", 0);
        OLINE rgTeam2 = new OLINE("Marshall", "Yanda", 0);        
        OLINE rtTeam2 = new OLINE("Duane", "Brown", 0);
        DE leTeam2 = new DE("J.J.","Watt",6);        
        DT dt1Team2 = new DT("Dontari","Poe",0);
        DT dt2Team2 = new DT("Kyle","Williams",0);        
        DE reTeam2 = new DE("Cameron","Wake",4);        
        LB lolbTeam2 = new LB("Terell","Suggs",2);  
        LB mlbTeam2 = new LB("D'Qwell","Jackson",2);        
        LB rolbTeam2 = new LB("Justin","Houston",2); 
        CB cb1Team2 = new CB("Darell","Revis",2);
        CB cb2Team2 = new CB("Joe","Haden",3);
        S ssTeam2 = new S("Troy","Polamalu",2);
        S fsTeam2 = new S("Eric","Berry",2);      
        KR krTeam2 = new KR("Adam","Vinatieri",95,90);        
        PR prTeam2 = new PR("Shane","Lechler",90);
        RS rsTeam2 = new RS("Dexter","McCluster",0);
        
        Team NFC = new Team("NFC",qbTeam1,rbTeam1,fbTeam1,wr1Team1,wr2Team1,wr3Team1,teTeam1,
                ltTeam1,lgTeam1,cTeam1,rgTeam1,rtTeam1,
                leTeam1,dt1Team1,dt2Team1,reTeam1,lolbTeam1,mlbTeam1,rolbTeam1,
                cb1Team1,cb2Team1,ssTeam1,fsTeam1,krTeam1,prTeam1,rsTeam1);
        Team AFC = new Team("AFC",qbTeam2,rbTeam2,fbTeam2,wr1Team2,wr2Team2,wr3Team2,teTeam2,
                ltTeam2,lgTeam2,cTeam2,rgTeam2,rtTeam2,
                leTeam2,dt1Team2,dt2Team2,reTeam2,lolbTeam2,mlbTeam2,rolbTeam2,
                cb1Team2,cb2Team2,ssTeam2,fsTeam2,krTeam2,prTeam2,rsTeam2);
        Football game = new Football(NFC,AFC);
        try {
            game.startGame();
        } catch (InterruptedException ex) {
            Logger.getLogger(Football.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}