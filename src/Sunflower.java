import javax.swing.*;
import java.awt.event.ActionEvent;

public class Sunflower extends Plant {

    private Timer sunProduceTimer;

    public Sunflower(GamePanel gamePanel, int x, int y) {
        super(gamePanel, x, y);
        sunProduceTimer = new Timer(11500, (ActionEvent e) -> {
            Sun sta = new Sun(getGamePanel(), 60 + x * 100, 110 + y * 120, 130 + y * 120);
            getGamePanel().getActiveSuns().add(sta);
            getGamePanel().add(sta);
        });
        sunProduceTimer.start();
    }
    public void stop() {
        sunProduceTimer.stop();
    }
    public void start(){
        sunProduceTimer.start();
    }
}
