
public abstract class Plant {

    private int health = 500;

    private int x;
    private int y;

    private GamePanel gamePanel;


    public Plant(GamePanel parent, int x, int y) {
        this.x = x;
        this.y = y;
        gamePanel = parent;
    }

    public void stop() {
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public int getCountlife() {
        return 0;
    }

    public void advance() {
    }

    public void start() {
    }
}
