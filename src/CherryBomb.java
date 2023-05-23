import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.Rectangle;

public class CherryBomb extends Plant{

    private Timer cherrebombLife;
    private int countLife = 0;

    public CherryBomb(GamePanel gamePanel, int x, int y){
        super(gamePanel, x, y);
        super.setHealth(2700);
        cherrebombLife = new Timer(120, (ActionEvent e) -> {
            countLife++;
            if (countLife == 24) stop();
        });
        cherrebombLife.start();
    }

    public void advance(){
        Rectangle cRect = new Rectangle(70 + (super.getX() - 1)*100, 100 + (super.getY()-1)*120, 250, 360);
        for (int k = 0; k < 5; ++k){
        for (int i = 0; i < super.getGamePanel().getLaneZombies().get(k).size(); i++) {
            Zombie z = super.getGamePanel().getLaneZombies().get(k).get(i);
            Rectangle zRect = new Rectangle(z.getPosX()+10, 129 + k * 120, 50, 80);
            if (cRect.intersects(zRect)){
                super.getGamePanel().getLaneZombies().get(k).remove(i);
                GamePanel.setProgress(10);
            }
        }
    }
}

    public int getCountlife(){
        return countLife;
    }
    public void stop() {
        cherrebombLife.stop();
    }
}
