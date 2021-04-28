package chickengame;

public class Fire extends GameObject {

    private final int BOARD_HEIGHT = 675;
    private int fire_speed = 2;


    public Fire(int x, int y) {
        super(x, y);
        initMissile();
        this.y -= height / 2;
        this.fire_speed = 5;       
    }

    private void initMissile() {
        loadImage("fire.png");
        getImageDimensions();
    }

    public void move() {

        y += fire_speed;

        if (y > BOARD_HEIGHT || y < 0)
            visible = false;
    }
}