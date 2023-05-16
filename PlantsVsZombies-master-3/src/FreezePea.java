import java.awt.*;

public class FreezePea extends Pea {

    public FreezePea(GamePanel parent, int lane, int startX) {
        super(parent, lane, startX);
    }

    @Override
    public void advance() {
        Rectangle pRect = new Rectangle(getPosX(), 130 + getMyLane() * 120, 28, 28);
        for (int i = 0; i < gp.getLaneZombies().get(getMyLane()).size(); i++) {
            Zombie z = gp.getLaneZombies().get(getMyLane()).get(i);
            Rectangle zRect = new Rectangle(z.getPosX(), 109 + getMyLane() * 120, 400, 120);
            if (pRect.intersects(zRect)) {
                z.setHealth(z.getHealth() - 250);
                z.slow();
                boolean exit = false;
                if (z.getHealth() < 0) {
                    GamePanel.setProgress(10);
                    gp.getLaneZombies().get(getMyLane()).remove(i);
                    exit = true;
                }
                gp.getLanePeas().get(getMyLane()).remove(this);
                if (exit) break;
            }
        }
        if(getPosX() > 1100){
            gp.getLanePeas().get(getMyLane()).remove(this);
        }
        setPosX(getPosX() + 15);
    }

}
