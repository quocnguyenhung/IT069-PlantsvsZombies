import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardInput implements KeyListener {
    private Gardener gardener;
    private GamePanel gamePanel;



    public KeyBoardInput(Gardener gardener, GamePanel gamePanel){
        this.gardener = gardener;
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()){
        // case 'r':
        //         if (gamePanel.getIsRunning()) {gamePanel.gamePause(true); gamePanel.setIsRunning(false);}
        //         else {gamePanel.gamePause(false); gamePanel.setIsRunning(true);}
        //         break;
        case 'u':
                gamePanel.setActivePlantingBrush(GameWindow.PlantType.Sunflower);
                break;
        case 'i':
                gamePanel.setActivePlantingBrush(GameWindow.PlantType.Peashooter);
                break;
        case 'o':
                gamePanel.setActivePlantingBrush(GameWindow.PlantType.FreezePeashooter);
                break;
        case 'p':
                gamePanel.setActivePlantingBrush(GameWindow.PlantType.CherryBomb);
                break;
        case 'j':
                gamePanel.setActivePlantingBrush(GameWindow.PlantType.Shovel);   
                break;    
            
        }
        }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                gardener.setIsStanding(false);
                gardener.setDirection("UP");
                break;
            case KeyEvent.VK_S:
                gardener.setIsStanding(false);
                gardener.setDirection("DOWN");
                break;
            case KeyEvent.VK_A:
                gardener.setIsStanding(false);
                gardener.setDirection("LEFT");
                break;
            case KeyEvent.VK_D:
                gardener.setIsStanding(false);
                gardener.setDirection("RIGHT");
                break;
            case KeyEvent.VK_SPACE:
            if (((gamePanel.getActivePlantingBrush() == GameWindow.PlantType.Sunflower && gamePanel.getSunScore() >= 50) ||
                (gamePanel.getActivePlantingBrush() == GameWindow.PlantType.Peashooter && gamePanel.getSunScore() >= 100) ||
                (gamePanel.getActivePlantingBrush() == GameWindow.PlantType.FreezePeashooter && gamePanel.getSunScore() >= 175) ||
                (gamePanel.getActivePlantingBrush() == GameWindow.PlantType.CherryBomb && gamePanel.getSunScore() >= 150)) && gamePanel.getCollider(gardener.getXlocation(), gardener.getYlocation()).assignedPlant == null)
                gardener.setIsPlanting(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                gardener.setIsStanding(true);
                break;
            case KeyEvent.VK_S:
                gardener.setIsStanding(true);
                break;
            case KeyEvent.VK_A:
                gardener.setIsStanding(true);
                gardener.setIsRight(false);
                break;
            case KeyEvent.VK_D:
                gardener.setIsStanding(true);
                gardener.setIsRight(true);
                break;
            case KeyEvent.VK_SPACE:
                if (gamePanel.getActivePlantingBrush() == GameWindow.PlantType.Sunflower) {
                    if (gamePanel.getSunScore() >= 50 && gamePanel.getCollider(gardener.getXlocation(), gardener.getYlocation()).assignedPlant == null) {
                        gamePanel.getCollider(gardener.getXlocation(), gardener.getYlocation()).setPlant(new Sunflower(gamePanel, gardener.getXlocation(), gardener.getYlocation()));
                        gamePanel.setSunScore(gamePanel.getSunScore() - 50);
                    }
                }
                if (gamePanel.getActivePlantingBrush() == GameWindow.PlantType.Peashooter) {
                    if (gamePanel.getSunScore() >= 100 && gamePanel.getCollider(gardener.getXlocation(), gardener.getYlocation()).assignedPlant == null) {
                        gamePanel.getCollider(gardener.getXlocation(), gardener.getYlocation()).setPlant(new Peashooter(gamePanel, gardener.getXlocation(), gardener.getYlocation()));
                        gamePanel.setSunScore(gamePanel.getSunScore() - 100);
                    }
                }
                if (gamePanel.getActivePlantingBrush() == GameWindow.PlantType.FreezePeashooter) {
                    if (gamePanel.getSunScore() >= 175 && gamePanel.getCollider(gardener.getXlocation(), gardener.getYlocation()).assignedPlant == null) {
                        gamePanel.getCollider(gardener.getXlocation(), gardener.getYlocation()).setPlant(new FreezePeashooter(gamePanel, gardener.getXlocation(), gardener.getYlocation()));
                        gamePanel.setSunScore(gamePanel.getSunScore() - 175);
                    }
                }
                if (gamePanel.getActivePlantingBrush() == GameWindow.PlantType.CherryBomb) {
                    if (gamePanel.getSunScore() >= 150 && gamePanel.getCollider(gardener.getXlocation(), gardener.getYlocation()).assignedPlant == null) {
                        gamePanel.getCollider(gardener.getXlocation(), gardener.getYlocation()).setPlant(new CherryBomb(gamePanel, gardener.getXlocation(), gardener.getYlocation()));
                        gamePanel.setSunScore(gamePanel.getSunScore() - 150);
                    }
                }
                if (gamePanel.getActivePlantingBrush() == GameWindow.PlantType.Shovel) {
                    gamePanel.getCollider(gardener.getXlocation(), gardener.getYlocation()).removePlant();
                }
                gardener.setIsPlanting(false);
                //gamePanel.setActivePlantingBrush(GameWindow.PlantType.None);
                break;
        }
    }
    
}
