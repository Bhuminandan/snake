import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {

//    Declaring size of panel
    static final int width = 1200;
    static final int height = 600;
    int unit = 50;
    boolean flag = false;
    Random random;
    int score;
    int fx , fy;

    char dir = 'R';
    Timer timer;
    int xSnake [] = new int [288];
    int ySnake [] = new int [288];
    int length = 3;

    panel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        random = new Random();
        this.setFocusable(true);
        this.addKeyListener(new key());


        gameStart();
    }
    public void gameStart() {
        spawnfood();
        flag = true;
        timer = new Timer(250, this);
        timer.start();
    }

//    To assign the randome x, y coordinates to food
    public void spawnfood() {
        fx = random.nextInt(width/unit) * 50;
        fy = random.nextInt(height/unit) * 50;
    }

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw (Graphics graphic) {
//        When the game is running
        if(flag) {

//            Drawing the food particle
            graphic.setColor(Color.yellow);
            graphic.fillOval(fx, fy, unit, unit);

//            To draw the snake
            for (int i=0; i<length; i++) {
                graphic.setColor(Color.green);
                graphic.fillRect(xSnake[i], ySnake[i], unit, unit);
            }
//            To draw the score element
            graphic.setColor(Color.white);
            graphic.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
            FontMetrics fme = getFontMetrics(graphic.getFont());
            graphic.drawString("Score " + score, (width-fme.stringWidth("Score " + score))/2, graphic.getFont().getSize());
        } else {
//            When the game is not running

//            To display the final score
            graphic.setColor(Color.white);
            graphic.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
            FontMetrics fme = getFontMetrics(graphic.getFont());
            graphic.drawString("Score " + score, (width-fme.stringWidth("Score " + score))/2, graphic.getFont().getSize());

//            To disply the game over text
            graphic.setColor(Color.red);
            graphic.setFont(new Font("Comic Sans MS", Font.BOLD, 80));
            fme = getFontMetrics(graphic.getFont());
//            graphic.drawString("Score " + score, (width-fme.stringWidth("Score " + score)/2),graphic.getFont().getSize());
            graphic.drawString("GAME OVER!", (width-fme.stringWidth("GAME OVER! "))/2, height/2);

//            To display the replay
            graphic.setColor(Color.white);
            graphic.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
            fme = getFontMetrics(graphic.getFont());
//            graphic.drawString("Score " + score, (width-fme.stringWidth("Score " + score)/2),graphic.getFont().getSize());
            graphic.drawString("Press R to Replay ", (width-fme.stringWidth("Press R to Display "))/2, height/2 + 150);


        }
    }

    public void move () {
        for(int i = length; i > 0; i--) {
            xSnake[i] = xSnake[i-1];
            ySnake[i] = ySnake[i-1];
        }

        switch (dir) {

            case 'R' : xSnake [0] = xSnake[0] + unit;
                break;
            case 'L' : xSnake [0] = xSnake[0] - unit;
                break;
            case 'U' : ySnake [0] = ySnake[0] - unit;
                break;
            case 'D' : ySnake [0] = ySnake[0] + unit;
                break;

        }

    }

    public void foodEaten () {
        if((xSnake[0] == fx) && (ySnake[0] == fy)) {
            length++;
            score++;
            spawnfood();
        }
    }

    public void checkHIt() {
//        To check hit with boundries
        if(ySnake[0] < 0) {
            flag = false;
        }
        if(ySnake[0] > 600) {
            flag = false;
        }
        if(xSnake[0] < 0) {
            flag = false;
        }
        if(xSnake[0] > 1200) {
            flag = false;
        }
//        To check hit with own body
        for(int i = length; i > 0; i--) {
            if((xSnake[0] == xSnake[i])  && (ySnake[0] == ySnake[i]) ) {
                flag = false;
            }
        }
        if(flag == false) {
            timer.stop();
        }
    }

    public class key extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if(dir != 'R') {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir != 'L') {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir != 'D') {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir != 'U') {
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_R:
                    score = 0;
                    length = 3;
                    dir = 'R';
                    Arrays.fill(xSnake, 0);
                    Arrays.fill(ySnake, 0);
                    gameStart();
            }
        }
    }

    public void actionPerformed(ActionEvent evt) {
        if(flag) {
            move();
            foodEaten();
            checkHIt();
        }
        repaint();
    }
}
