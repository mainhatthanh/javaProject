package gameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;


import entities.EnemyManager;
import entities.Player;

import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay; 
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

import static utilz.Constants.PlayerConstants.ATTACK;
import static utilz.Constants.PlayerConstants.GetStamina;
import static utilz.Constants.PlayerConstants.JUMP;
import static entities.Player.expThatChange;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    //private BulletManager bulletManager;
    private boolean paused = false;

    private int xLvlOffset;

    private int leftBorder = (int) (0.5 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.5 * Game.GAME_WIDTH);

    private int maxLvlOffsetX;

    private BufferedImage backgroundImg, groundImg;

    private boolean gameOver;
    private boolean lvlCompleted = false;
    private boolean playerDying;

    int[][] lvlData;


    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG);


        caclcLvlOffset();
        loadStartLevel();
        resetAll();
    }

    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
       // bulletManager.loadBullets(levelManager.getCurrentLevel());

    }

    private void caclcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();

        // Đảm bảo không có giá trị âm
        if (maxLvlOffsetX < 0)
            maxLvlOffsetX = 0;
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        //bulletManager = new BulletManager(this);

        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay = new PauseOverlay(this); 
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        // Căn chỉnh camera theo vị trí nhân vật
        caclcLvlOffset(); // Tính maxLvlOffsetX và maxLvlOffsetY trước

        int playerX = (int) player.getHitBox().x;
        xLvlOffset = Math.max(0, Math.min(maxLvlOffsetX, playerX - Game.GAME_WIDTH / 2));
    }

    @Override
    public void update() {
        // Nếu nhân vật chạm đáy màn hình, nhân vật DEAD
        if ((int) (player.getHitBox().y + player.getHitBox().height + 2) >= Game.GAME_HEIGHT) {
            player.changeHealth(-player.getMaxHealth());
            // gameOver = true;
        }

        if (paused) {
            pauseOverlay.update();
        } else if (lvlCompleted) {
            levelCompletedOverlay.update();
            expThatChange = 0;
        } else if (gameOver) {
            gameOverOverlay.update();
        } else if (playerDying) {
            player.update();
        } else {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLvlData(), player);
            objectManager.update();
            //bulletManager.update(levelManager.getCurrentLevel().getLvlData(), player);
            checkCloseToBorder();
        }

    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitBox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(groundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);
        //bulletManager.draw(g,xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver)
            gameOverOverlay.draw(g);
        else if (lvlCompleted){
            levelCompletedOverlay.draw(g);
            expThatChange = 0;
        }
    }



    public void resetAll() {
        gameOver = false;
        paused = false;
        if(lvlCompleted == true){
            expThatChange = 0;
        }
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
       // bulletManager.resetBullets();

    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox , player);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver){
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (player.getCurrentStamina() >= GetStamina(ATTACK)) {
                    player.setAttacking(true);
                    player.changeStamina(-GetStamina(ATTACK));
                }
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
                    player.powerAttack();
                }


                else{ 
                    System.out.println("Khong du mana");
                }
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.keyPressed(e);
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_LEFT:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    if(player.getCurrentStamina() >= GetStamina(JUMP)){
                        player.setJump(true);
                        player.changeStamina(-GetStamina(JUMP));
                    }
                    else{ 
                        System.out.println("Khong du mana");
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
                case KeyEvent.VK_F:
                    if(player.getCurrentStamina() >= GetStamina(ATTACK)){
                        player.setAttacking(true);
                        player.changeStamina(-GetStamina(ATTACK));
                    }else{ 
                        System.out.println("Khong du mana");
                    }
                    break;
            }
            // player.changeStamina(-GetStamina(GetAniFromKey(e)));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_LEFT:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
                // case KeyEvent.VK_F:
                // player.setAttacking(false);
                // break;
            }

    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mousePressed(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mousePressed(e);
        } else {
            gameOverOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseReleased(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);
        } else {
            gameOverOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseMoved(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mouseMoved(e);
        } else
            gameOverOverlay.mouseMoved(e);
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
        if (levelCompleted)
            game.getAudioPlayer().lvlCompleted();
    }

    public boolean getLevelCompleted(){
        return lvlCompleted;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unpauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }

    /*public BulletManager getBulletManager(){
        return bulletManager;

    }*/

    public boolean enoughStamina(int player_action){
        if(player.getCurrentStamina() >= GetStamina(player_action))
            return true;
        else   
            return false;
    }

    public void restoreStaminaDefault(){
        player.changeStamina(10);
        // if(player.getCurrentStamina()<player.getMaxStamina())
        //     player.setCurrentStamina( 3 + player.getCurrentStamina() );
    }

}