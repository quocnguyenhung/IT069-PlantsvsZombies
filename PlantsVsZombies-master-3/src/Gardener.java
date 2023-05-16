import java.awt.Rectangle;

import javax.swing.JOptionPane;

public class Gardener {
    private int x, y;
    private boolean isRight;
    private boolean isStanding;
    private boolean isPlanting;
    private String direction;
    private GamePanel gamePanel;
    private int health;
    public Gardener(GamePanel gamePanel){
        this.health = 100;
        this.gamePanel = gamePanel;
        this.x = 40;
        this.y = 100;
        isRight = true;
        isStanding = true;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getDirection(){
        return direction;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int x){
        if (x>15 && x<850)
        this.x = x;
    }
    public void setY(int y){
        if (y>50 && y<600)
        this.y = y;
    }
    public void setIsStanding(boolean t){
        this.isStanding = t;
    }
    public void setIsRight(boolean t){
        this.isRight = t;
    }
    public boolean getIsStanding(){
        return isStanding;
    }
    public boolean getIsRight(){
        return isRight;
    }
    public boolean getIsPlanting(){
        return isPlanting;
    }
    public void setIsPlanting(boolean t){
        this.isPlanting = t;
    }
    public int getXlocation(){
        return (x+64-44)/100;
    }
    public int getYlocation(){
        return (y+90-109)/120;
    }
    public void setHealth(int health){
        this.health = health;
    }
    public int getHealth(){
        return health;
    }
    public void advance(){
        Rectangle gRect = new Rectangle(x+39, y+25, 50, 78);
        for (int i = 0; i < gamePanel.getActiveSuns().size(); i++) {
            Sun s = gamePanel.getActiveSuns().get(i);
            Rectangle sRect = new Rectangle(s.getPosX(), s.getPosY(), 80, 80);
            if (gRect.intersects(sRect)) {
                gamePanel.setSunScore(gamePanel.getSunScore() + 25);
                gamePanel.remove(gamePanel.getActiveSuns().get(i));
                gamePanel.getActiveSuns().remove(i);
            }
    }
        for (int k = 0; k < 5; ++k){
        for (int i = 0; i < gamePanel.getLaneZombies().get(k).size(); i++) {
            Zombie z = gamePanel.getLaneZombies().get(k).get(i);
            Rectangle zRect = new Rectangle(z.getPosX()+5, 129 + k * 120, 50, 80);
            if (gRect.intersects(zRect)){
                setHealth(getHealth() - 3);
            }
            if (getHealth()<=0) {
                JOptionPane.showMessageDialog(gamePanel, "YOUR GARDENER DIED !" + '\n' + "Starting the level again");
                gamePanel.resetgame();
                LevelData.zombieSpeed = 1;
                GamePanel.progress = 0;
                GamePanel.zomcount = 0;
                gamePanel = null;
                GameWindow.gw.dispose();
                GameWindow.gw = new GameWindow();
            }}
    }
}
}
