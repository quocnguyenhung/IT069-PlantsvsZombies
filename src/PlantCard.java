import javax.swing.*;
import java.awt.*;


public class PlantCard extends JPanel{

    private Image img;

    public PlantCard(Image img) {
        setSize(64, 90);
        this.img = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }
}
