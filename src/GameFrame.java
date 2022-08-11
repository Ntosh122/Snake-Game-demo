import javax.swing.*;


public class GameFrame extends JFrame{
    //constructor
    public GameFrame(){
        this.add(new GamePanel());
        this.setVisible(true);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Snake Game Demo Project");
    }
}
