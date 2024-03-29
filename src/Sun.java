import javax.swing.*;
import java.awt.*;

public class Sun extends JPanel{

    private GamePanel gamePanel;
    private Image sunImage;

    private int myX;
    private int myY;
    private int endY;

    private int destruct = 200;

    public Sun(GamePanel parent, int startX, int startY, int endY) {
        this.gamePanel = parent;
        this.endY = endY;
        setSize(80, 80);
        setOpaque(false);
        myX = startX;
        myY = startY;
        setLocation(myX, myY);
        sunImage = new ImageIcon(this.getClass().getResource("images/sun.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sunImage, 0, 0, null);
    }

    public void advance() {
        if (myY < endY) {
            myY += 4;
        } else {
            destruct--;
            if (destruct < 0) {
                gamePanel.remove(this);
                gamePanel.getActiveSuns().remove(this);
            }
        }
        setLocation(myX, myY);
    }
    public void setGamePanel(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public int getPosX(){
        return myX;
    }
    public int getPosY(){
        return myY;
    }
}
