package Plants;

public abstract class Plant {
    private int health=200;
    private int x;
    private int y;
    private gamePanel gp;
    public Plant(GamePanel parent, int x, int y) {
        this.x=x;
        this.y=y;
        gp= parent;
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
    public gamePanel getGp() {
        return gp;
    }
    public void setGp(gamePanel gp) {
        this.gp = gp;
    }
    public void stop();
}

