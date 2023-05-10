package Bullet;
import java.awt.*;

import Plants.Plant;
public class Pea extends Plant {
    private int posX;
    private int GamePanel gp;
    private int myLane;
    public Pea(Plants.GamePanel parent, int x, int y, int posX, int gamePanel, int myLane) {
        super(parent, x, y);
        this.posX = posX;
        GamePanel = gamePanel;
        this.myLane = myLane;
    }
    public int getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public int getMyLane() {
        return myLane;
    }
    public void setMyLane(int myLane) {
        this.myLane = myLane;
    }
    public void advance() {
        Rectangle pRect = new Rectangle(posX, 130 + myLane * 120, 28, 28);
        for (int i = 0; i < gp.getLaneZombies().get(myLane).size(); i++) {
            Zombie z = gp.getLaneZombies().get(myLane).get(i);
            Rectangle zRect = new Rectangle(z.getPosX(), 109 + myLane * 120, 400, 120);
            if (pRect.intersects(zRect)) {
                int index = gp.getLanePeas().get(myLane).indexOf(this);
                gp.getLanePeas().get(myLane).remove(index);
                z.setHealth(z.getHealth() - 300);
                boolean exit = false;
                if (z.getHealth() < 0) {
                    System.out.println("ZOMBIE DIED");

                    gp.getLaneZombies().get(myLane).remove(i);
                    GamePanel.setProgress(10);
                    exit = true;
                }
                gp.getLaneZombies().get(myLane).remove(this);
                if (exit) break;
            }
        }
    }
}