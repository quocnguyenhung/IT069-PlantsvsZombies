import javax.swing.*;

public class GameWindow extends JFrame {

    enum PlantType {
        None,
        Sunflower,
        Peashooter,
        FreezePeashooter,
        CherryBomb,
        Shovel
    }
    PlantCard sunflower, peashooter, freezepeashooter, shovel, cherrybomb;
    GamePanel gamePanel;
    public GameWindow() {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel sun = new JLabel("SUN");
        sun.setLocation(37, 80);
        sun.setSize(60, 20);

        gamePanel = new GamePanel(sun, this);
        gamePanel.setLocation(0, 0);
        

        sunflower = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_sunflower.png")).getImage());
        sunflower.setLocation(110, 8);
        getLayeredPane().add(sunflower);

        peashooter = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_peashooter.png")).getImage());
        peashooter.setLocation(175, 8);
        getLayeredPane().add(peashooter);

        freezepeashooter = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_freezepeashooter.png")).getImage());
        freezepeashooter.setLocation(240, 8);
        getLayeredPane().add(freezepeashooter);

        cherrybomb = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_cherrybomb.png")).getImage());
        cherrybomb.setLocation(305, 8);
        getLayeredPane().add(cherrybomb);

        shovel = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_shovel.jpg")).getImage());
        shovel.setLocation(500, 8);
        getLayeredPane().add(shovel);

        getLayeredPane().add(sun);
        getLayeredPane().add(gamePanel);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public GameWindow(boolean b) {
        Menu menu = new Menu();
        menu.setLocation(0, 0);
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu);
        menu.repaint();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    static GameWindow gw;

    public static void begin() {
        gw.dispose();
        gw = new GameWindow();
    }

    public static void main(String[] args) {
        gw = new GameWindow(true);
    }

}
