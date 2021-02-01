import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    final static int SCREEN_WIDTH = 800;
    final static int SCREEN_HEIGTH = 600;
    final static int UNIT_SIZE = 20;
    final static int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGTH)/(UNIT_SIZE*UNIT_SIZE);
    static int DELAY = 75;
    final int x[] = new int[GAME_UNITS]; // to coordinates body parts including head of the snake
    final int y[] = new int[GAME_UNITS];// to coordinates body parts
    int bodyParts = 2;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'D';
    boolean running = false;  //check if the game starts
    Timer timer;
    Random random;
    boolean text = true;  //check if the buttons are on the screen
    JButton easy;
    JButton medium;
    JButton hard;
    JButton insane;

    /**
     * Constructs and initializes a game panel
     * setPreferredSize - set of the preferred panel size
     * setBackground - set color of background
     * setFocusable - need to use KeyListener
     * addKeyListener - adding KeyListener
     * addActionListener - defined in AbstractButton which is the parent of JButton
     */

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGTH));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        easy = new JButton("Easy");
        medium = new JButton("Medium");
        hard = new JButton("Hard");
        insane = new JButton("Insane");
        this.add(easy);
        this.add(medium);
        this.add(hard);
        this.add(insane);
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DELAY = 100;
                text = false;
                running = true;
                startGame();
                easy.hide();
                medium.hide();
                hard.hide();
                insane.hide();
            }
        });
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DELAY = 75;
                text = false;
                running = true;
                startGame();
                easy.hide();
                medium.hide();
                hard.hide();
                insane.hide();
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DELAY = 50;
                text = false;
                running = true;
                startGame();
                easy.hide();
                medium.hide();
                hard.hide();
                insane.hide();
            }
        });
        insane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DELAY = 25;
                text = false;
                running = true;
                startGame();
                easy.hide();
                medium.hide();
                hard.hide();
                insane.hide();
            }
        });
    }

    /**
     *function run when the application starts
     */
    public void startGame(){
        createNewApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        repaint();    //use to re-draw GUI
    }


    /**
     * pass in the Graphics parameter as a graphics context onto which you can draw
     * @param g - parameter to draw graphics
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);  //calling the overridden method
        draw(g);
    }

    /**
     * function to draw apple and snake's body, and also to start function game over
     * @param g parameter to draw graphics
     */
    public void draw(Graphics g) {
        if (running) {
            //Apple color and shape
            g.setColor(Color.blue);
            g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Snake
            for (int i = 0; i < bodyParts; i++) {

                g.setColor(new Color(76, 0, 0));
                g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

            }
            //Score when game is running
            g.setColor(Color.yellow);
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.drawString("Score: " + appleEaten, 310,50);

        }
        else if(!text) {
            gameOver(g);
        }
    }


    /**
     * function to create new apple
     */
    public void createNewApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGTH/UNIT_SIZE))*UNIT_SIZE;
    }

    /**
     * function to defined move
     */
    public void moveSnake(){
        //iterate through all the body parts
        for (int i = bodyParts; i> 0 ; i--){
            //shifting all the coordinates in this array over by one spot
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    /**
     * function to checking if the apple has been eaten
     */
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)) {
            bodyParts += 1;
            appleEaten +=1;
            createNewApple();
        }
    }

    /**
     * collision checking function
     */
    public void checkCollisions(){
        //check if collides with body
        for (int i = bodyParts; i > 0; i--){
            if ((x[0] == x[i] ) && (y[0] == y[i])){
                running = false;
            }
        }
        //check if head collides left walls
        if(x[0] < 0){
            running = false;
        }
        //check if head collides right walls
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //check if head collides top walls
        if(y[0] < 0){
            running = false;
        }
        //check if head collides bottom walls
        if(y[0] > SCREEN_HEIGTH){
            running = false;
        }
        //
        if (!running&&!text) {
            timer.stop();
        }
    }

    /**
     * function to show the end of the game
     * @param g - parameter to draw graphics
     */
    public void gameOver(Graphics g){
        //Score
        g.setColor(Color.black);
        g.setFont(new Font("arial", Font.BOLD, 50));
        FontMetrics fontMetrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - fontMetrics1.stringWidth("Score: "+ appleEaten))/2,g.getFont().getSize());

        //Game over text
        g.setColor(Color.black);
        g.setFont(new Font("arial", Font.BOLD, 75));
        //To set text in the center
        FontMetrics fontMetrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - fontMetrics2.stringWidth("Game Over"))/2,SCREEN_HEIGTH/2);
        //Text to restart game
        g.drawString("Press Space to restart", 0, 380);

    }

    /**
     * checking the operation of the program
     * @param e - stores information about the source
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (running){
            moveSnake();
            checkApple();
            checkCollisions();
        }

        repaint();

    }

    /**
     *class to defined keys
     */
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
            if(!running&&!text) {
                if(e.getKeyChar()==KeyEvent.VK_SPACE) {
                    startGame();
                    bodyParts = 2;
                    direction = 'D';
                    for(int i=bodyParts;i>0;i--) {
                        x[i] = bodyParts*-1;
                        y[i] = 0;
                    }
                    x[0] = 0;
                    y[0] = 0;
                    repaint();
                    appleEaten = 0;

                }
            }
        }
    }
}
