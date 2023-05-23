import javax.swing.*;

public class Zombie {

    private int health = 1250;
    private int speed = LevelData.zombieSpeed;
    private GamePanel gamePanel;

    private int posX = 1000;
    private int myLane;
    private boolean isMoving = true;

    public Zombie(GamePanel parent, int lane) {
        this.gamePanel = parent;
        myLane = lane;
    }

    public void advance() {
            boolean isCollides = false;
            Collider collided = null;
            for (int i = myLane * 9; i < (myLane + 1) * 9; i++) {
                if (gamePanel.getColliders()[i].assignedPlant != null && gamePanel.getColliders()[i].isInsideCollider(posX)) {
                    isCollides = true;
                    collided = gamePanel.getColliders()[i];
                    isMoving = false;
                }
            }
            if (!isCollides) {
                isMoving = true;
                if (slowInt > 0) {
                    if (slowInt % 3 == 0) {
                        posX-=speed;
                    }
                    slowInt--;
                } else {
                    posX -= speed;
                }
            } else {
                collided.assignedPlant.setHealth(collided.assignedPlant.getHealth() - 10);
                if (collided.assignedPlant.getHealth() < 0) {
                    collided.removePlant();
                    isMoving = true;
                }
            }
            if (posX < 0) {
                GamePanel.zombieProducer.stop();
                GamePanel.zombieProducer2.stop();
                isMoving = false;
                JOptionPane.showMessageDialog(gamePanel, "ZOMBIES ATE YOUR BRAIN !" + '\n' + "Starting the level again");
                GameWindow.gw.dispose();
                LevelData.zombieSpeed = 1;
                LevelData.LEVEL = 0;
                GamePanel.zomcount = 0;
                GamePanel.progress = 0;
                GameWindow.gw = new GameWindow();
                gamePanel.resetgame();

            }
        }

    int slowInt = 0;

    public void slow() {
        slowInt = 150;
    }

    public static Zombie getZombie(String type, GamePanel parent, int lane) {
        Zombie z = new Zombie(parent, lane);
        switch (type) {
            case "NormalZombie":
                z = new NormalZombie(parent, lane);
                break;
            case "ConeHeadZombie":
                z = new ConeHeadZombie(parent, lane);
                break;
        }
        return z;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public GamePanel getgamePanel() {
        return gamePanel;
    }

    public void setgamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getSlowInt() {
        return slowInt;
    }

    public void setSlowInt(int slowInt) {
        this.slowInt = slowInt;
    }
}
