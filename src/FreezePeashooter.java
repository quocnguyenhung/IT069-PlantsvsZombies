import javax.swing.*;
import java.awt.event.ActionEvent;

public class FreezePeashooter extends Plant {

    private Timer shootTimer;


    public FreezePeashooter(GamePanel parent, int x, int y) {
        super(parent, x, y);
        shootTimer = new Timer(850, (ActionEvent e) -> {
            if (getGamePanel().getLaneZombies().get(y).size() > 0) {
                getGamePanel().getLanePeas().get(y).add(new FreezePea(getGamePanel(), y, 103 + this.getX() * 100));
            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }

}