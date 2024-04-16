package gameoflife;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame() {
        this.setTitle("Game Of Life");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(new GamePanel());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
