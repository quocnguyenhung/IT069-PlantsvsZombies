import javax.swing.*;

public class Collider extends JPanel{


    public Collider() {
        setOpaque(false);
        setSize(100, 120);
    }

    public Plant assignedPlant;

    public void setPlant(Plant p) {
        assignedPlant = p;
    }

    public void removePlant() {
        if (assignedPlant != null) {assignedPlant.stop();
        assignedPlant = null;}
    }

    public boolean isInsideCollider(int tx) {
        return (tx > getLocation().x) && (tx < getLocation().x + 70);
    }

}
