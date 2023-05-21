import javax.swing.JFrame;
public class Frame extends JFrame {
    Frame() {
//        Setting title of game
        this.setTitle("Snake Game 101");
//        Adding panel to frame
        this.add(new panel());
//        Making the Frame visible
        this.setVisible(true);
//        Making the not resizable to get uniform experince
        this.setResizable(false);
//        to make its size atleast of prefered size
        this.pack();
    }
}
