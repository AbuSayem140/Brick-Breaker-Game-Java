import javax.swing.JFrame;

public class Main  {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Brick Breaker");
        frame.setBounds(100, 100, 700, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        GamePlay gamePlay = new GamePlay();
        frame.add(gamePlay);
    }
}