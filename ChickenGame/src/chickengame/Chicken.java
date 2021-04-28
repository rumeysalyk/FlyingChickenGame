
package chickengame;


import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Chicken extends GameObject {

    private int dx;
    private List<Egg> eggs;
    private List<Fire> fires;
    private boolean direction;

    public Chicken(int x, int y) {
        super(x, y);

        initCraft();
    }

    private void initCraft() {

        eggs = new ArrayList<>();
        fires = new  ArrayList<>();
        loadImage("chicken.png");
        getImageDimensions();
    }

    public void move() {

        x += dx;

        if (x < 1) {
            x = 1;
        }

        if (x > 1000) {
            x = 1000;
        }

    }

    public List<Egg> getEggs() {
        return eggs;
    }
    public List<Fire> getFires() {
        return fires;
    }    
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            Egg_fire();
        }
        if (key == KeyEvent.VK_F) {
            Fire_fire();
        }        
        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
            direction = false;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
            direction = true;
        }
    }

    public void Egg_fire() {
        int dx = direction ? width : -width;
        eggs.add(new Egg(x + dx, y, direction));
    }
    public void Fire_fire() {
        int dx = direction ? width : -width;
        if(direction == true){
            fires.add(new Fire(x , y+dx));
        }
        else{
            fires.add(new Fire(x , y+dx+130));
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

}

