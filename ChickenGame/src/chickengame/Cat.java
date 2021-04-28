
package chickengame;

import java.util.Random;

public class Cat extends GameObject {

    private static Random rand = new Random();
    private final int INITIAL_Y = 900;

    public Cat() {
        super(0, 0);
        init();
        initCat();
    }

    public void init() {
        this.x = 20+rand.nextInt(800);
        this.y = 1000 - rand.nextInt(700);
        visible = true;
    }

    private void initCat() {

        loadImage("yellowCat.png");
        getImageDimensions();
    }

    public void move(int level) {

        if (y < 0) {
            y = INITIAL_Y;
        }

        y -= 0.5 + level * 0.5;
    }
}

