import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public GameFrame() {
        Board board = new Board();

        setSize(1500, 800);
        setTitle("Game Dev In JAVA");

        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(board);

        setVisible(true);
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}