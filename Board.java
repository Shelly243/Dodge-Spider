import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Board extends JPanel {
    Timer timer;
    BufferedImage backgroundImage; // it preserves background image
    Player player;
    Enemy enemies[] = new Enemy[3];

    public Board() {
        setSize(1500, 800);
        loadBackgroundImage();
        player = new Player();
        loadEnemies();
        gameLoop();
        bindEvents();
    }

    // gameover
    private void gameOver(Graphics pen) {
        // game win
        if (player.outOfScreen()) {
            pen.setFont(new Font("times", Font.BOLD, 150));
            pen.setColor(Color.GREEN);
            pen.drawString("GAME WIN", 1500 / 5, 800 / 2);
            timer.stop();
            return;
        }

        // game lost due to collision
        for (Enemy enemy : enemies) {
            if (isCollide(enemy)) {
                pen.setFont(new Font("times", Font.BOLD, 150));
                pen.setColor(Color.RED);
                pen.drawString("GAME OVER", 1500 / 5, 800 / 2);
                timer.stop();
            }
        }
    }

    private boolean isCollide(Enemy enemy) {
        int xDistance = Math.abs(player.x - enemy.x);
        int yDistance = Math.abs(player.y - enemy.y);
        int maxH = Math.max(player.h, enemy.h);
        int maxW = Math.max(player.w, enemy.w);
        return xDistance <= maxW - 80 && yDistance <= maxH - 80;
    }

    private void bindEvents() {
        setFocusable(true); // Ensure the component can receive focus
        requestFocus(); // Request focus to receive key events

        addKeyListener(new KeyAdapter() { // Using KeyAdapter for simplicity
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT) {
                    player.speed = 10; // Set player speed to move right
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    player.speed = -10; // Set player speed to move left
                }
            }

            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT) {
                    player.speed = 0; // Reset player speed when arrow keys are released
                }
            }
        });
    }

    private void loadEnemies() {
        int x = 400;
        int gap = 400;
        int speed = 5;
        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy(x, speed);
            x = x + gap;
            speed = speed + 5;
        }
    }

    // multithreading
    private void gameLoop() {
        timer = new Timer(50, (e) -> repaint());
        timer.start();
    }

    // game background
    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(Board.class.getResource("Forest-bg.jpg"));
        } catch (IOException e) {
            System.out.println("Background image not found...");
            System.exit(1);
            e.printStackTrace();
        }
    }

    private void printEnemies(Graphics pen) {
        for (Enemy enemy : enemies) {
            enemy.draw(pen);
            enemy.move();
        }
    }

    public void paintComponent(Graphics pen) {
        // all printing logic
        super.paintComponent(pen);
        pen.drawImage(backgroundImage, 0, 0, 1500, 800, null);
        player.draw(pen);
        player.move();
        printEnemies(pen);
        gameOver(pen);
    }
}
