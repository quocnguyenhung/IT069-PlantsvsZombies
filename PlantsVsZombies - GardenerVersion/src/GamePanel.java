import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JLayeredPane{

    private Image bgImage;
    private Image peashooterImage;
    private Image freezePeashooterImage;
    private Image sunflowerImage;
    private Image peaImage;
    private Image freezePeaImage;
    private BufferedImage sprite_cherrybomb;

    private Image zombie1_eating;
    private Image zombie2_eating;
    private Image zombie1_fast;
    private Image zombie2_fast;
    private Image zombie1_normal;
    private Image zombie2_normal;


    Image gardenerFace;
    private BufferedImage imgstdL;
    private BufferedImage imgstdR;
    private BufferedImage imgrunL;
    private BufferedImage imgrunR;
    private Image plantLeft, plantRight;
    private BufferedImage[] cherryBomb;
    private BufferedImage[] standLeft;
    private BufferedImage[] standRight;
    private BufferedImage[] runLeft;
    private BufferedImage[] runRight;

    
    private int tick;
    private int aniIndex;
    private final int anispeed = 5;

    private Collider[] colliders;

    private Gardener gardener;

    private ArrayList<ArrayList<Zombie>> laneZombies;
    private ArrayList<ArrayList<Pea>> lanePeas;
    private ArrayList<Sun> activeSuns;

    public static int zomcount = 0;

    private Timer redrawTimer;
    private Timer advancerTimer;
    private Timer sunProducer;
    static Timer zombieProducer, zombieProducer2;
    
    private JLabel sunScoreboard;

    private GameWindow gw;

    private KeyBoardInput keyBoardInput;

    private JProgressBar bar;

    private GameWindow.PlantType activePlantingBrush = GameWindow.PlantType.None;

    private int sunScore;

    private Clip clip;

    public int getSunScore() {
        return sunScore;
    }

    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        sunScoreboard.setText(String.valueOf(sunScore));
        sunScoreboard.setFont(new Font("MV Boli", Font.BOLD, 20));
        sunScoreboard.setHorizontalAlignment(SwingConstants.CENTER);
        sunScoreboard.setVerticalAlignment(SwingConstants.CENTER);
        sunScoreboard.setLocation(32, 79);
    }

    public GamePanel(JLabel sunScoreboard, GameWindow gw) {
        playSound();
        this.gw = gw;
        setSize(1000, 752);
        setLayout(null);
        this.sunScoreboard = sunScoreboard;
        setSunScore(150);
        gardener = new Gardener(this);
        keyBoardInput = new KeyBoardInput(gardener, this);
        addKeyListener(keyBoardInput);
        setFocusable(true);
        importImg();
        barhealth();    
        bgImage = new ImageIcon(this.getClass().getResource("images/mainBG.png")).getImage();
        peashooterImage = new ImageIcon(this.getClass().getResource("images/plants/peashooter.gif")).getImage();
        freezePeashooterImage = new ImageIcon(this.getClass().getResource("images/plants/freezepeashooter.gif")).getImage();
        sunflowerImage = new ImageIcon(this.getClass().getResource("images/plants/sunflower.gif")).getImage();
        peaImage = new ImageIcon(this.getClass().getResource("images/pea.png")).getImage();
        freezePeaImage = new ImageIcon(this.getClass().getResource("images/freezepea.png")).getImage();


        gardenerFace = new ImageIcon(this.getClass().getResource("images/dave_icon.png")).getImage();

        zombie1_eating = new ImageIcon(this.getClass().getResource("images/zombies/zombie1_eating.gif")).getImage();
        zombie2_eating = new ImageIcon(this.getClass().getResource("images/zombies/zombie2_eating.gif")).getImage();
        zombie1_fast = new ImageIcon(this.getClass().getResource("images/zombies/zombie1_fast.gif")).getImage();
        zombie2_fast = new ImageIcon(this.getClass().getResource("images/zombies/zombie2_fast.gif")).getImage();
        zombie1_normal = new ImageIcon(this.getClass().getResource("images/zombies/zombie1_normal.gif")).getImage();
        zombie2_normal = new ImageIcon(this.getClass().getResource("images/zombies/zombie2_normal.gif")).getImage();

        laneZombies = new ArrayList<>();
        laneZombies.add(new ArrayList<>()); //line 1
        laneZombies.add(new ArrayList<>()); //line 2
        laneZombies.add(new ArrayList<>()); //line 3
        laneZombies.add(new ArrayList<>()); //line 4
        laneZombies.add(new ArrayList<>()); //line 5

        lanePeas = new ArrayList<>();
        lanePeas.add(new ArrayList<>()); //line 1
        lanePeas.add(new ArrayList<>()); //line 2
        lanePeas.add(new ArrayList<>()); //line 3
        lanePeas.add(new ArrayList<>()); //line 4
        lanePeas.add(new ArrayList<>()); //line 5

        colliders = new Collider[45];
        for (int i = 0; i < 45; i++) {
            Collider a = new Collider();
            a.setLocation(44 + (i % 9) * 100, 109 + (i / 9) * 120);
            colliders[i] = a;
            add(a);
        }


        activeSuns = new ArrayList<>();

        redrawTimer = new Timer(24, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        advancerTimer = new Timer(60, (ActionEvent e) -> advance());
        advancerTimer.start();

        sunProducer = new Timer(8000, (ActionEvent e) -> {
            Random rnd = new Random();
            Sun sta = new Sun(this, rnd.nextInt(800) + 100, 0, rnd.nextInt(300) + 200);
            activeSuns.add(sta);
            add(sta);
        });
        sunProducer.start();

        zombieProducer = new Timer(7000, (ActionEvent e) -> {
            Random rnd = new Random();
            String[] Level = LevelData.LEVEL_CONTENT;
            int[][] LevelValue = LevelData.LEVEL_VALUE[LevelData.LEVEL];
            int l = rnd.nextInt(5);
            int t = rnd.nextInt(100);
            Zombie z = null;
            for (int i = 0; i < LevelValue.length; i++) {
                if (t >= LevelValue[i][0] && t <= LevelValue[i][1]) {
                    z = Zombie.getZombie(Level[i], this, l);
                }
            }
            zomcount++;
            laneZombies.get(l).add(z);
            if (zomcount == 16) zombieProducer.stop();
        });
        zombieProducer.start();

        zombieProducer2 = new Timer(800, (ActionEvent e) -> {
            Random rnd = new Random();
            String[] Level = LevelData.LEVEL_CONTENT;
            int[][] LevelValue = LevelData.LEVEL_VALUE[LevelData.LEVEL];
            int l = rnd.nextInt(5);
            int t = rnd.nextInt(100);
            Zombie z = null;
            for (int i = 0; i < LevelValue.length; i++) {
                if (t >= LevelValue[i][0] && t <= LevelValue[i][1]) {
                    z = Zombie.getZombie(Level[i], this, l);
                }
            }
            zomcount++;
            laneZombies.get(l).add(z);
            if (zomcount == 60) zombieProducer2.stop();
        });
    }

    private void barhealth() {
        bar = new JProgressBar(0, 100);
        bar.setBounds(680, 20, 300, 40);
        bar.setForeground(new Color(178, 236, 115));
        bar.setBackground(Color.GRAY);
        bar.setValue(gardener.getHealth());
        this.add(bar);
    }

    
    private void importImg() {
        standLeft = new BufferedImage[8];
        standRight = new BufferedImage[8];
        runLeft = new BufferedImage[8];
        runRight = new BufferedImage[8];
        cherryBomb = new BufferedImage[25];
        InputStream is1 = getClass().getResourceAsStream("images/standLeft.png");
        InputStream is2 = getClass().getResourceAsStream("images/standRight.png");
        InputStream is3 = getClass().getResourceAsStream("images/runLeft.png");
        InputStream is4 = getClass().getResourceAsStream("images/runRight.png");
        InputStream is5 = getClass().getResourceAsStream("images/sprite_cherrybomb.png");
        plantLeft = new ImageIcon(this.getClass().getResource("images/plantleft1.png")).getImage();
        plantRight = new ImageIcon(this.getClass().getResource("images/plantright1.png")).getImage();
        try{
            imgstdL = ImageIO.read(is1);
            imgstdR = ImageIO.read(is2);
            imgrunL = ImageIO.read(is3);
            imgrunR = ImageIO.read(is4); 
            sprite_cherrybomb = ImageIO.read(is5); 
        }
        catch (IOException e){
            e.printStackTrace();
        }
        for (int i=0; i<8; ++i){
            standLeft[i] = imgstdL.getSubimage(i*90, 0, 90, 90);
            standRight[i] = imgstdR.getSubimage(i*90, 0, 90, 90);
            runLeft[i] = imgrunL.getSubimage(i*90, 0, 90, 90);
            runRight[i] = imgrunR.getSubimage(i*90, 0, 90, 90);
        }
        for (int j=0; j<24; ++j){
            cherryBomb[j] = sprite_cherrybomb.getSubimage(79*j, 0, 79, 75);
        }
        cherryBomb[24] = sprite_cherrybomb.getSubimage(79*23, 0, 79, 75);
    }

    private void anitick() {
        tick++;
        if (tick>= anispeed) {
            tick = 0;
            aniIndex++;
            if (aniIndex>=8) aniIndex = 0;
        }
    }

    
    private void advance() {
        if (bar.getValue() >= 50)
        bar.setForeground(new Color(178, 236, 115));
        else if (bar.getValue() >= 25) 
        bar.setForeground(Color.YELLOW);
        else bar.setForeground(Color.RED);
        bar.setValue(gardener.getHealth());
        for (int i = 0; i < 5; i++) {
            for (Zombie z : laneZombies.get(i)) {
                z.advance();
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                p.advance();
            }

        }

        for (int i = 0; i < activeSuns.size(); i++) {
            activeSuns.get(i).advance();
        }
        if (gardener!=null) gardener.advance();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);
        g.drawImage(gardenerFace, 585, 7, 85, 85, null);

        if (getActivePlantingBrush() == GameWindow.PlantType.Sunflower) {gw.sunflower.setBorder(new LineBorder(Color.ORANGE, 3));
            gw.peashooter.setBorder(null); gw.freezepeashooter.setBorder(null); gw.shovel.setBorder(null); gw.cherrybomb.setBorder(null);}
        else if (getActivePlantingBrush() == GameWindow.PlantType.Peashooter) {gw.peashooter.setBorder(new LineBorder(Color.ORANGE, 3));
            gw.sunflower.setBorder(null); gw.freezepeashooter.setBorder(null); gw.shovel.setBorder(null); gw.cherrybomb.setBorder(null);}
        else if (getActivePlantingBrush() == GameWindow.PlantType.FreezePeashooter) {gw.freezepeashooter.setBorder(new LineBorder(Color.ORANGE, 3));
            gw.sunflower.setBorder(null); gw.peashooter.setBorder(null); gw.shovel.setBorder(null); gw.cherrybomb.setBorder(null);}
        else if (getActivePlantingBrush() == GameWindow.PlantType.CherryBomb) {gw.cherrybomb.setBorder(new LineBorder(Color.ORANGE, 3));
            gw.sunflower.setBorder(null); gw.peashooter.setBorder(null); gw.shovel.setBorder(null); gw.freezepeashooter.setBorder(null);}
        else if (getActivePlantingBrush() == GameWindow.PlantType.Shovel) {gw.freezepeashooter.setBorder(null); gw.cherrybomb.setBorder(null);
            gw.sunflower.setBorder(null); gw.peashooter.setBorder(null); gw.shovel.setBorder(new LineBorder(Color.RED, 3));}
        
        anitick();

        for (int i = 0; i<5; i++) {
            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea pea = lanePeas.get(i).get(j);
                if (pea instanceof FreezePea) {
                    g.drawImage(freezePeaImage, pea.getPosX(), 130 + (i * 120), null);
                } else {
                    g.drawImage(peaImage, pea.getPosX(), 130 + (i * 120), null);
                }
            }
            if (gardener.getY()+105>= 74 + ((i)*120) && gardener.getY()+105<74 + ((i+1)*120)){
                if (gardener.getIsPlanting()) 
                {if (gardener.getIsRight())
                    g.drawImage(plantRight, gardener.getX()+16, gardener.getY()+24, 85, 85, null);
                 else g.drawImage(plantLeft, gardener.getX()+16, gardener.getY()+24, 85, 85, null);
                }
                else if (gardener.getIsStanding()){
                    if (gardener.getIsRight() == true) g.drawImage(standRight[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);
                    else g.drawImage(standLeft[aniIndex],gardener.getX(), gardener.getY(), 128, 128, null);
                }
                else if (gardener.getIsRight()){
                    if (gardener.getDirection().equals("UP")) 
                    {   gardener.setY(gardener.getY()-8);
                     g.drawImage(runRight[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                 if (gardener.getDirection().equals("DOWN")) 
                    {   gardener.setY(gardener.getY()+8);
                        g.drawImage(runRight[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                    if (gardener.getDirection().equals("LEFT")) 
                    {   gardener.setX(gardener.getX()-8);
                        g.drawImage(runLeft[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                    if (gardener.getDirection().equals("RIGHT")) 
                    {   gardener.setX(gardener.getX()+8);
                        g.drawImage(runRight[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                }
                else {
                    if (gardener.getDirection().equals("UP")) 
                    {   gardener.setY(gardener.getY()-7);
                        g.drawImage(runLeft[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                    if (gardener.getDirection().equals("DOWN")) 
                    {   gardener.setY(gardener.getY()+7);
                        g.drawImage(runLeft[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                    if (gardener.getDirection().equals("LEFT")) 
                    {   gardener.setX(gardener.getX()-8);
                        g.drawImage(runLeft[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                    if (gardener.getDirection().equals("RIGHT")) 
                    {   gardener.setX(gardener.getX()+8);
                        g.drawImage(runRight[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                }
                }
            for (int j=0; j<9; ++j){
                Collider c = colliders[9*i+j];
            if (c.assignedPlant != null) {
                Plant p = c.assignedPlant;
                if (p instanceof Peashooter) {
                    g.drawImage(peashooterImage, 60 + (j) * 100, 129 + (i) * 120, null);
                }
                if (p instanceof FreezePeashooter) {
                    g.drawImage(freezePeashooterImage, 60 + (j) * 100, 129 + (i) * 120, null);
                }
                if (p instanceof Sunflower) {
                    g.drawImage(sunflowerImage, 60 + (j) * 100, 129 + (i) * 120, null);
                }
                if (p instanceof CherryBomb) {
                    if (p.getCountlife() == 20) p.advance();
                    if (p.getCountlife() == 24) c.removePlant();
                    g.drawImage(cherryBomb[p.getCountlife()], 5 + (j) * 100, 70 + (i) * 120, 170, 170, null);
                }
            }
            }

            for (Zombie z : laneZombies.get(i)) {
                if (z instanceof NormalZombie) {
                    if (z.isMoving()){
                        if (z.getSpeed() == 3) g.drawImage(zombie1_fast, z.getPosX(), 74 + (i * 120), null);
                        else g.drawImage(zombie1_normal, z.getPosX(), 74 + (i * 120), null);
                    }
                    else 
                    g.drawImage(zombie1_eating, z.getPosX(), 74 + (i * 120), null);
                } 
                else if (z instanceof ConeHeadZombie) {
                    if (z.isMoving()){
                        if (z.getSpeed() == 3) g.drawImage(zombie2_fast, z.getPosX(), 74 + (i * 120), null);
                        else g.drawImage(zombie2_normal, z.getPosX(), 74 + (i * 120), null);
                    }
                    else 
                    g.drawImage(zombie2_eating, z.getPosX(), 74 + (i * 120), null);
                }

            }
            if (gardener.getY()>=569 && i ==4){
                if (gardener.getIsPlanting()) 
                {if (gardener.getIsRight())
                    g.drawImage(plantRight, gardener.getX()+16, gardener.getY()+24, 85, 85, null);
                 else g.drawImage(plantLeft, gardener.getX()+16, gardener.getY()+24, 85, 85, null);
                }
                else if (gardener.getIsStanding()){
                    if (gardener.getIsRight() == true) g.drawImage(standRight[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);
                    else g.drawImage(standLeft[aniIndex],gardener.getX(), gardener.getY(), 128, 128, null);
                }
                else if (gardener.getIsRight()){
                    if (gardener.getDirection().equals("UP")) 
                    {   gardener.setY(gardener.getY()-8);
                     g.drawImage(runRight[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                 if (gardener.getDirection().equals("DOWN")) 
                    {   gardener.setY(gardener.getY()+8);
                        g.drawImage(runRight[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                    if (gardener.getDirection().equals("LEFT")) 
                    {   gardener.setX(gardener.getX()-8);
                        g.drawImage(runLeft[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                    if (gardener.getDirection().equals("RIGHT")) 
                    {   gardener.setX(gardener.getX()+8);
                        g.drawImage(runRight[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                }
                else {
                    if (gardener.getDirection().equals("UP")) 
                    {   gardener.setY(gardener.getY()-7);
                        g.drawImage(runLeft[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                    if (gardener.getDirection().equals("DOWN")) 
                    {   gardener.setY(gardener.getY()+7);
                        g.drawImage(runLeft[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                    if (gardener.getDirection().equals("LEFT")) 
                    {   gardener.setX(gardener.getX()-8);
                        g.drawImage(runLeft[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                    if (gardener.getDirection().equals("RIGHT")) 
                    {   gardener.setX(gardener.getX()+8);
                        g.drawImage(runRight[aniIndex], gardener.getX(), gardener.getY(), 128, 128, null);}
                }
                }
        }

         

    }

    static int progress = 0;

    private void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("PlantsVsZombies - GardenerVersion/src/audios/PVZ Loonboon 8bit Remix.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public static void setProgress(int num) {
        progress = progress + num;
        if (progress >= 70) {
            if (progress == 70) LevelData.zombieSpeed = 2;} 
        if (progress >= 110) {
            if (progress == 110) LevelData.zombieSpeed = 3; LevelData.LEVEL = 1;
        }
        if (progress == 160) {zombieProducer2.start();}
        if (progress == 300) {LevelData.LEVEL = 2;}
        if (progress >= 600) {
                JOptionPane.showMessageDialog(null, "LEVEL Completed !!!" + '\n' + "More Levels will come soon !!!" + '\n' + "Resetting data");
                System.exit(0);
        }
    }
    
    public GameWindow.PlantType getActivePlantingBrush() {
        return activePlantingBrush;
    }

    public void setActivePlantingBrush(GameWindow.PlantType activePlantingBrush) {
        this.activePlantingBrush = activePlantingBrush;
    }

    public ArrayList<ArrayList<Zombie>> getLaneZombies() {
        return laneZombies;
    }

    public void setLaneZombies(ArrayList<ArrayList<Zombie>> laneZombies) {
        this.laneZombies = laneZombies;
    }

    public ArrayList<ArrayList<Pea>> getLanePeas() {
        return lanePeas;
    }

    public void setLanePeas(ArrayList<ArrayList<Pea>> lanePeas) {
        this.lanePeas = lanePeas;
    }

    public ArrayList<Sun> getActiveSuns() {
        return activeSuns;
    }

    public void setActiveSuns(ArrayList<Sun> activeSuns) {
        this.activeSuns = activeSuns;
    }

    public Collider[] getColliders() {
        return colliders;
    }
    public Collider getCollider(int x, int y){
        return colliders[x + y*9];
    }
    public void setColliders(Collider[] colliders) {
        this.colliders = colliders;
    }

    public void resetgame(){
        clip.stop();
        redrawTimer.stop();
        advancerTimer.stop();
        sunProducer.stop();
    
    for (int i=0; i<45; ++i){
        if (colliders[i].assignedPlant != null) colliders[i].assignedPlant.stop();
    }
    for (int k = 0; k < 5; ++k){
        for (int i = 0; i < laneZombies.get(k).size(); i++) {
            if (laneZombies.get(k).get(i).getPosX() <=0) laneZombies.get(k).get(i).setPosX(1000);
        }
    keyBoardInput.setGamePanel(null);
    gardener = null;

    }}
    public Clip getClip() {
        return clip;
    }
    }

