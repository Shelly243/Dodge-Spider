import javax.swing.ImageIcon;

public class Enemy extends Sprite {

    public Enemy(int x, int speed) {
        this.speed = speed;
        w = 200;
        h = 200;
        this.x = x;
        y = 30;
        image = new ImageIcon(Enemy.class.getResource("spider.gif"));
    }

    public void move() {
        if (y > 800) {
            y = -h;
        }
        y = y + speed;
    }
}
