
package chickengame;

import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {

    private Timer timer;
    private Chicken chicken;
    private List<Target> targets;
    private List<Cat> cats;
    private List<Corn> corns;
    private List<Bread> breads;
    private boolean running;
    private int hearts_left;
    private int points;
    private int level = 1;
    private final int chickenX = 150;
    private final int chickenY = 150;
    private final int B_WIDTH = 900;
    private final int B_HEIGHT = 675;
    private final int wait = 15;
    private static Font monoFont = new Font("Algerian", Font.PLAIN, 20);
    private BufferedImage image;
    private BufferedImage image1;

    public Game() {
        
        try {
            image = ImageIO.read(new FileImageInputStream(new File("lose.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            image1 = ImageIO.read(new FileImageInputStream(new File("WinnerPage.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }        
        launchBoard();
    }
    // TO PASS ONE LEVEL TO THE FOLLOWING ONE
    private static int createWindow(int level){
        if(level ==5){
            JOptionPane.showMessageDialog(null,"Game Completed");
            return 1;
        }else{
            String[] options = {"YES", "NO"};
            int x = JOptionPane.showOptionDialog(null, "Level "+level+" completed!!"+" Do you want to continue?","",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            return x;
        }
    }
    public void nextLevel() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        int x = createWindow(level);
        level++;
        
        if(x==0){
           running = true; 
        }
        else{
            System.exit(0);
        }
        if (level > 4) {
            running = false;
        }
        else {
            targets = new ArrayList<>();

            for (int i = targets.size(); i < level * 10 + 20; i++) {
                targets.add(new Target());
            }

            cats = new ArrayList<>();

            for (int i = cats.size(); i < (level * 10)-5; i++) {
                cats.add(new Cat());
            }
        }
    }
    
    
//*****************************LAUNCHING OBJECTS**************************************************
    public void launchCats() {

        cats = new ArrayList<>();

        for (int i = cats.size(); i < 10; i++) {
            cats.add(new Cat());
        }
    }
    public void launchBreads() {

        breads = new ArrayList<>();

        for (int i = breads.size(); i < 10; i++) {
            breads.add(new Bread());
        }
    }
    public void launchCorns() {

        corns = new ArrayList<>();

        for (int i = corns.size(); i < 10; i++) {
            corns.add(new Corn());
        }
    }    
    public void launchTargets() {

        targets = new ArrayList<>();

        for (int i = targets.size(); i < 30; i++) {
            targets.add(new Target());
        }
    }
    private void launchBoard() {

        addKeyListener(new Adapter());
        setFocusable(true);
        setBackground(new Color(130, 170, 131));
        setDoubleBuffered(true);
        running = true;
        hearts_left = 3;
        points = 0;
        setPreferredSize(new Dimension(1000, 800));

        chicken = new Chicken(chickenX, chickenY);

        launchCats();
        launchBreads();
        launchTargets();
        launchCorns();

        timer = new Timer(wait, this);
        timer.start();
    }




    
//*******************************PAINTING OBJECTS**********************************************************   

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {

            drawObjects(g);

        }
        else if(running == false && hearts_left>0){
            drawWin(g);
        }
        else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }
    public void paintInstructions(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0,1000, 70);
        
        g.setColor(Color.WHITE);
        g.setFont(monoFont);

        
        String PointMessage = " <--:Left     -->:Right       Space:Egg      F:Fire(Use After Level 1)";
        
        Font small = new Font("Monospaced", Font.BOLD, 20);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(PointMessage,0,50);         
    }
    private void paintChicken(Graphics g){
        if (chicken.isVisible()) {
            g.drawImage(chicken.getImage(), chicken.getX(), chicken.getY(),this);
        }        
    }
    private void paintEggs(Graphics g){
        List<Egg> ms = chicken.getEggs();

        for (Egg egg : ms) {
            if (egg.isVisible()) {
                g.drawImage(egg.getImage(), egg.getX(),
                        egg.getY(), this);
            }
        }
    }
    private void paintCats(Graphics g){
        for (Cat cat : cats) {
            if (cat.isVisible()) {
                g.drawImage(cat.getImage(), cat.getX(), cat.getY(), this);
            }
        }
    }    
    private void drawObjects(Graphics g) {
        paintInstructions(g);
       
        paintChicken(g);
        
        paintEggs(g);
        
        paintCats(g);
        
        
        if(level==2 || level==3 || level==4){
            List<Fire> fs = chicken.getFires();

            for (Fire fire : fs) {
                if (fire.isVisible()) {
                g.drawImage(fire.getImage(), fire.getX(),fire.getY(), this);
                }
            }            
        }

        if(level ==2 || level==3){
            for (Bread bread : breads) {
                if (bread.isVisible()) {
                    g.drawImage(bread.getImage(), bread.getX(), bread.getY(), this);
                }
            }           
            
        }
        if(level==4){
            for (Corn corn : corns) {
                if (corn.isVisible()) {
                    g.drawImage(corn.getImage(), corn.getX(), corn.getY(), this);
                }
            }           
            
        }
        g.setColor(Color.WHITE);
        g.drawString("Points: " + points, 5, 20);

        g.setColor(Color.WHITE);
        g.drawString("Life: " + hearts_left, 900, 20);
        
        g.setColor(Color.WHITE);
        g.drawString("Level: " + level +"/4",450,20 );

        for (Target target : targets) {
            if (target.isVisible()) {
                target.draw(g);
            }
        }
    }

    private void drawGameOver(Graphics g) {
        g.drawImage(image,0,0,null);
        String PointMessage = "Points: " + points;
        
        Font small = new Font("Algerian", Font.BOLD, 50);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(new Color(167,252,0));
        g.setFont(small);
        g.drawString(PointMessage, (B_WIDTH - fm.stringWidth(PointMessage))-520 / 2,(B_HEIGHT / 2)+300);
    }
    private void drawWin(Graphics g) {
        g.drawImage(image1,20,0,null);
        String PointMessage = "  Points: " + points;
        String WinMessage = "CONGRATULATIONS!! YOU WIN";
        Font small = new Font("Monospaced", Font.BOLD, 20);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(PointMessage, ((B_WIDTH - fm.stringWidth(WinMessage)) / 2)+50,B_HEIGHT / 2-100);
        g.drawString(WinMessage, (B_WIDTH - fm.stringWidth(WinMessage)) / 2,B_HEIGHT / 2-50);
    }
     
    

    //************************UPDATING OBJECTS*****************************************************************
    private void refreshChickens() {

        if (chicken.isVisible()) {

            chicken.move();
        }
    }

    private void refreshTargets() {
        for (int i = 0; i < targets.size(); i++) {
            Target a = targets.get(i);
            
            if (a.isVisible()) {
                a.move(level);
            } else {
                targets.remove(i);
            }
        }
        if (targets.size() == 0) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            nextLevel();

        }
        
    }

    private void refreshEggs() {

        List<Egg> ms = chicken.getEggs();

        for (int i = 0; i < ms.size(); i++) {

            Egg m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }
    private void refreshFires() {

        List<Fire> fs = chicken.getFires();

        for (int i = 0; i < fs.size(); i++) {

            Fire f = fs.get(i);

            if (f.isVisible()) {
                f.move();
            } else {
                fs.remove(i);
            }
        }
    }
    private void refreshCats() {

        for (int i = 0; i < cats.size(); i++) {

            Cat a = cats.get(i);

            if (a.isVisible()) {
                a.move(level);
            } else {
                points++;
                a.init();
                a.setVisible(true);

            }
        }
    }
    private void refreshBreads() {

        for (int i = 0; i < breads.size(); i++) {

            Bread b = breads.get(i);

            if (b.isVisible()) {
                b.move(level);
            } else {
                points++;
                b.init();
                b.setVisible(true);

            }
        }
    }
    private void refreshCorns() {

        for (int i = 0; i < corns.size(); i++) {

            Corn c = corns.get(i);

            if (c.isVisible()) {
                c.move(level);
            } else {
                points++;
                c.init();
                c.setVisible(true);

            }
        }
    }
    //*******************CONTROL COLLISION***************************************************************
    public void controlCollisions() {

        Rectangle r3 = chicken.getBounds();

        for (Cat cat : cats) {

            Rectangle r2 = cat.getBounds();

            if (r3.intersects(r2)) {

                cat.setVisible(false);
                if (hearts_left == 0) {
                    chicken.setVisible(false);
                    running = false;
                }
                hearts_left--;
                break;
            }
        }
        if(level>1){
            Rectangle r4 = chicken.getBounds();

            for (Bread bread : breads) {

                Rectangle r2 = bread.getBounds();

                if (r4.intersects(r2)) {

                    bread.setVisible(false);
                    hearts_left++;
                    break;
                }
            }            
        }

        List<Egg> ms = chicken.getEggs();

        for (Egg m : ms) {

            Rectangle r1 = m.getBounds();
            for (Target target : targets) {

                Rectangle r2 = target.getBounds();

                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    target.setVisible(false);
                    points += target.getPoint();
                }
            }
        }
        
        if(level == 2 || level == 3 || level ==4){
            List<Fire> fs = chicken.getFires();

            for (Fire f : fs) {

                Rectangle r1 = f.getBounds();
                for (Cat cat : cats) {

                    Rectangle r2 = cat.getBounds();

                    if (r1.intersects(r2)) {
                        f.setVisible(false);
                        cat.setVisible(false);
                        points += 10;
                    }
                }
            }             
        }
        if(level == 4){
            Rectangle r5 = chicken.getBounds();

            for (Corn corn : corns) {

                Rectangle r2 = corn.getBounds();

                if (r5.intersects(r2)) {

                    corn.setVisible(false);
                    hearts_left++;
                    break;
                }
            }            
        }
      
    }

    private class Adapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            chicken.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            chicken.keyPressed(e);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        Running();
        controlCollisions();
        refreshChickens(); 
        refreshBreads();      
        refreshCorns();
        refreshTargets();
        refreshEggs();
        refreshFires();
        refreshCats();
        repaint();
        
    }

    private void Running() {

        if (!running) {
            timer.stop();
        }
    }    
}