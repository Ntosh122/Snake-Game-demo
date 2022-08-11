import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;


public class GamePanel extends JPanel implements ActionListener{
    //decalring & initialise the variables used to complete the game
    static final int screen_width=700;
    static final int screen_height=700;
    static final int unit_size = 15;
    static final int game_unit = (screen_width*screen_height)/unit_size;
    static final int delay = 52;
    final int x[] = new int[game_unit];
    final int y[] = new int[game_unit];
    int  bodyparts = 6;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random rand;

    //constructor
    public GamePanel(){
        rand = new Random();
        this.setPreferredSize(new Dimension(screen_width,screen_height));
        this.setBackground(Color.decode("#282828"));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(delay,this);
        timer.start();
    }
    public void move(){

        for (int i = bodyparts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];

        }
        switch(direction){
            case 'U':
                y[0] = y[0] - unit_size;
                break;
            case 'D':
                y[0] = y[0] + unit_size;
                break;
            case 'L':
                x[0] = x[0] - unit_size;
                break;
            case 'R':
                x[0] = x[0] + unit_size;
                break;
        }
    }
    public void checkApple(){

        //every time the snake eats the apple the body parts of the snake increase at every intake

        if((x[0]==appleX) && (y[0]==appleY)){
            bodyparts++;
            appleEaten++;
            newApple();
        }
    }

    public void checkCollision(){
        //checking if the head of the snake collides with the body

        for (int i = bodyparts; i > 0; i--) {
            if((x[0]==x[i]) && (y[0]==y[i])){
                running = false;
            }
        }

        //checking if the head of the snake touches the left border
        if(x[0] < 0){
            running = false;
        }
        //checking if the head of the snake touches the right border
        if(x[0] > screen_width){
            running = false;
        }
        //checking if the head of the snake touches the top border
        if(y[0] < 0){
            running = false;
        }
        //checking if the head of the snake touches the bottom border
        if(y[0] > screen_height){
            running = false;
        }

        //if there's any collision the timer will stop
        if(!running ){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){

        g.setColor(Color.decode("#20a620"));
        g.setFont(new Font("Open Sans", Font.BOLD,30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + appleEaten, (screen_width - metrics1.stringWidth("Score: " + appleEaten))/2, screen_height/5);
        //
        g.setColor(Color.decode("#20a620"));
        g.setFont(new Font("Open Sans", Font.BOLD,40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Java Snake - Game Over", (screen_width - metrics.stringWidth("Java Snake Game Over"))/2, screen_height/2);


    }


    @Override
    public void paintComponent(Graphics g){

        //drawing up the panel

        super.paintComponent(g);
        draw(g);
    }
    //
    public void draw(Graphics g){


        if(running){


            //drawing the index lines.

//            for (int i = 0; i <= screen_height/unit_size;  i++) {
//                g.drawLine(i*unit_size, 0, i*unit_size, screen_height);
//                g.drawLine(0,i*unit_size, screen_width, i*unit_size);
//            }


            //creating the apple and giving the color of the apple

            g.setColor(Color.decode("#e63333"));
            g.fillOval(appleX, appleY, unit_size, unit_size);

            //creating the parts of the snake according to the units of the grid

            for (int i = 0; i < bodyparts; i++) {
                if(i == 0){
                    g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                }else{
                    g.setColor(Color.decode("#20a620"));

                    g.fillRect(x[i], y[i], unit_size, unit_size);
                }
            }
        }
        else{
            gameOver(g);

        }
    }
    //
    public void newApple(){

        //placing the apple at random positions of the unit everytime
        // - the program executes.

        appleX = rand.nextInt((int)(screen_width/unit_size))*unit_size;
        appleY = rand.nextInt((int)(screen_width/unit_size))*unit_size;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{

        //the movenent of the snake will be controlled by the arrows on the keyboard.
        @Override
        public void keyPressed(KeyEvent event){
            switch(event.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;

            }
        }
    }
}
