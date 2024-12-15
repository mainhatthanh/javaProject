package gameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import audio.AudioPlayer;
import entities.Enemy;
import entities.EnemyManager;
import entities.Player;

import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.*;
import utilz.LoadSave;

import static entities.Player.expThatChange;
import static entities.Player.levelUpTime;
import static utilz.Constants.PlayerConstants.*;

import static utilz.Constants.EnemyConstants.GetMessageEnemy;
import static utilz.Constants.EnemyConstants.GetNumberMessageBoss;;

public class Playing extends State implements Statemethods {

    private int exp ;
    private int textIndex = 0;

    private UI ui ;

    private Player player;
    private Enemy enemy;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;

    //private BulletManager bulletManager;
    private boolean paused = false;

    private int xLvlOffset;
    private int countRev = 0;

    private int leftBorder = (int) (0.5 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.5 * Game.GAME_WIDTH);

    private int maxLvlOffsetX;

    private BufferedImage backgroundImg, groundImg;

    private boolean touchFlag = false;
    private boolean gameOver;
    private boolean lvlCompleted = false;

    /*kiểm tra các trạng thái
     * fly1: xuất hiện cân đẩu vân
     * fly2: khỉ sử dụng cân đẩu vân
     */
    private boolean fly1 = false;
    private boolean fly2 = false ;

    //kiểm tra xem nhân vật đã đủ gần với boss chưa để hiện dialogue
    private int count  =0;

    private int intro = 0;

    private boolean playerDying;
    int[][] lvlData;


    //Tutorial paper

    private Tutorial tutorial;

    //Plot
    private Plot plot;


    /*private int DamageNormalAttack = 10;
    private int DamageUltiAttack = 40;*/


    public Playing(Game game) {
        super(game);
        initClasses();

        if ((getLevelManager().getLevelIndex()) == 0) {
            backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_3_BACKGROUND);
        }

        caclcLvlOffset();
        loadStartLevel();
        resetAll();
    }

    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();

        if ((levelManager.getLevelIndex() ) == 1) {
            backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_1_BACKGROUND);
        }
        if ((levelManager.getLevelIndex() ) == 2) {
            backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_2_BACKGROUND);
        }
        if ((levelManager.getLevelIndex()) == 3) {
            backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_4_BACKGROUND);
        }
        if ((levelManager.getLevelIndex())== 4) {
            backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_5_BACKGROUND);
        }

        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());

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


        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());


        ui = new UI(this);
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        tutorial = new Tutorial();
        plot = new Plot();

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

        }else if(intro <7) {
       	//ui.u(intro);
        } else if(fly1) {
            player.update();
            objectManager.updateFlyWukong(player);
            if(objectManager.getFly()) {
                game.getAudioPlayer().lvlCompleted();
                fly2 = true;
                fly1 = false;
            }
        }else if(fly2) {
            objectManager.updateFlyWukong(player);
            if(objectManager.getXPos() >= Game.GAME_WIDTH+ 20) {
                lvlCompleted = true;
                levelCompletedOverlay.update();

                expThatChange = 0;
                levelUpTime = 0;
            }
        }
        else if (lvlCompleted) {
            levelCompletedOverlay.update();
            expThatChange = 0;
            levelUpTime = 0;
        }
        else if (gameOver) {
            gameOverOverlay.update();
            player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        }
        else if(check() && count ==0) {
            player.updateIDLE();
            enemyManager.updateIDLE();
            ui.setText(GetMessageEnemy(enemyManager.getEnemyCheck()), enemyManager.getEnemyCheck());
        }
        else if (playerDying) {
            player.update();
        }
        else if(tutorial.isShowTutorial()){
            pauseOverlay.update();
        }
        else if(plot.isShowPlot()){
            pauseOverlay.update();
        }
        else {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLvlData(), player);
            check();
            exp = enemyManager.getExpUp();
            if(exp != 0) {
                String a ="+"+ exp;
                ui.showMessage(a);
                enemyManager.setExpUp(0);
            }
            objectManager.update(levelManager.getCurrentLevel().getLvlData(), player);




            checkCloseToBorder();
        }

    }


    public LevelCompletedOverlay getLevelCompletedOverlay() {
        return levelCompletedOverlay;
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

        Graphics2D g2 = (Graphics2D) g;
        ui.draw(g2);

        levelManager.draw(g, xLvlOffset);

        //bulletManager.draw(g,xLvlOffset);

        //draw lvlUp khi len cap
        if(player.getIsShowLvlUp()){
            player.drawLvlUp(g,xLvlOffset);
        }


        if (paused) {
            player.stopStepSound();
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);

//            player.render(g, xLvlOffset);
//            player.drawSticks(g,xLvlOffset);
//            enemyManager.draw(g, xLvlOffset);
//            objectManager.draw(g, xLvlOffset);
        }else if(intro <7) {
            ui.drawIntro(g2, intro);
        }else if (gameOver) {
            player.stopStepSound();
            gameOverOverlay.draw(g);
        }
        else if(check() && count == 0) {
            if(textIndex >= 0 ) {
                ui.drawDialogueScreen(g2, textIndex,enemyManager.getEnemyCheck());
                player.renderIDLE(g, xLvlOffset);
                enemyManager.drawIDLE(g, xLvlOffset);
            }
        }
        else if(fly1) {
            player.render(g, xLvlOffset);
            player.drawSticks(g,xLvlOffset);
            objectManager.drawFlyWukong(g, xLvlOffset);

        }
        else if(fly2) {
            objectManager.drawFlyWukong(g, xLvlOffset);
            if(objectManager.getXPos() >= Game.GAME_WIDTH+ 20 + xLvlOffset) {
                levelCompletedOverlay.draw(g);
//        		expThatChange = 0;
                player.stopStepSound();
            }

        }
        else if (lvlCompleted){
            player.stopStepSound();
            levelCompletedOverlay.draw(g);
            expThatChange = 0;
            levelUpTime = 0;
        }
        else if(tutorial.isShowTutorial()) {
            tutorial.draw(g);
            player.stopStepSound();
        }
        else if(plot.isShowPlot()){
            plot.draw(g,levelManager.getLevelIndex());
            player.stopStepSound();
        }
//=======
//            player.stopStepSound();
//            gameOverOverlay.draw(g);
//        }
//        else if (lvlCompleted){
//            player.stopStepSound();
//            levelCompletedOverlay.draw(g);
//            expThatChange = 0;
//            levelUpTime = 0;
//
//>>>>>>> 0eef73b5a3ef415fe1f14ff5c396ef6284a5a68e

        else {
            player.render(g, xLvlOffset);
            enemyManager.draw(g, xLvlOffset);
            objectManager.draw(g, xLvlOffset);
            player.drawSticks(g,xLvlOffset);

        }

    }



    public void resetAll() {
        gameOver = false;
        paused = false;

        if(lvlCompleted == true){
            expThatChange = 0;
            levelUpTime = 0;
        }

        touchFlag = false;
        countRev = 0;
        textIndex =0;

        lvlCompleted = false;
        playerDying = false;
        player.resetAll();

        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();

        fly2 = false;
        fly1 = false;

        count = 0;
        textIndex =0;
        // bulletManager.resetBullets();

         /*if(player.isCurving())
            curveManager.resetCurve(player);*/

    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }


    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }

    public void checkTrapTouched(Player p) {
        objectManager.checkTrapTouched(p);
    }

    public void checkChestsOpen(Rectangle2D.Float hitbox) {
        objectManager.checkChestsOpen(hitbox);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox , player);
    }

    public boolean isStickHitEnemy(Rectangle2D.Float curveHitBox){
        return  enemyManager.isStickHitEnemy(curveHitBox,player);

    }




    @Override
    public void mouseClicked(MouseEvent e) {
     /*   if (!gameOver){
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (player.getCurrentStamina() >= GetStamina(ATTACK)) {
                    player.setAttacking(true);
                    player.setUltiSkill(false);
                    player.setPlayerDamage(GetPlayerDamage(ATTACK));
                    player.changeStamina(-GetStamina(ATTACK));
                }
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
                    player.powerAttack();
                }
                else{
                    System.out.println("Khong du mana");
                }
            }*/
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            gameOverOverlay.keyPressed(e);
        }
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_W:
                    player.shootStick();
                    break;
                case KeyEvent.VK_LEFT:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_ENTER:
                    textIndex += 1;
                    this. getGame().getAudioPlayer().playEffect(AudioPlayer.CLICK);

                    if(textIndex < GetNumberMessageBoss(enemyManager.getEnemyCheck())) {
                        ui.setCheckChat(!ui.getCheckChat());
                    }

                    break;
                case KeyEvent.VK_F:
                    intro+=1;
                    break;
                case KeyEvent.VK_Q:
                    this. getGame().getAudioPlayer().playEffect(AudioPlayer.CLICK);
                    if(textIndex >= GetNumberMessageBoss(enemyManager.getEnemyCheck())-1)
                        count = 1;
                    break;
                case KeyEvent.VK_L:
                    player.powerAttack();
                    break;
                case KeyEvent.VK_R:
                    tutorial.setTutorial(!tutorial.isShowTutorial());
                    break;

                case KeyEvent.VK_B:
                    plot.setPlot(false);
                    this.getGame().getAudioPlayer().playEffect(AudioPlayer.PAPER);
                    break;
                case KeyEvent.VK_E:
                    objectManager.checkChestsOpen(player.getHitBox());
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
                    getGame().getAudioPlayer().playEffect(AudioPlayer.CLICK);
                    paused = !paused;
                    break;
                case KeyEvent.VK_J:
                    if(e.getKeyCode()!=KeyEvent.VK_G)
                        if(player.getCurrentStamina() >= GetStamina(ATTACK)){
                            player.setAttacking(true);
                            player.setPlayerDamage(GetPlayerDamage(ATTACK));
                            player.setUltiSkill(false);
                            player.changeStamina(-GetStamina(ATTACK));
                        }else{
                            System.out.println("Khong du mana");
                        }
                    break;
                case KeyEvent.VK_K:
                    if(e.getKeyCode()!=KeyEvent.VK_F)
                        if(player.getCurrentStamina() >= GetStamina(ULTI)){
                            player.setUltiSkill(true);
                            player.setPlayerDamage(GetPlayerDamage(ULTI));
                            player.setAttacking(false);
                            player.changeStamina(-GetStamina(ULTI));
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

    public boolean check() {
        if(player.getHitbox().x >= levelManager.getCurrentLevel().getPlayerMeet().x)
            return true;
        else
            return false;
    }

    public void setFlyWukong(boolean flyWukong) {
        this.fly1 = flyWukong;

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

    public UI getUi() {
        return ui;
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
    public void setCountRev(int countRev) {
        this.countRev = countRev;
    }
    public void setTouchFlag(boolean touchFlag) {
        this.touchFlag = touchFlag;
    }

    public int CountRev() {
        return countRev;
    }
    public boolean TouchFlag() {
        return touchFlag;
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
        player.changeStamina(5);
        // if(player.getCurrentStamina()<player.getMaxStamina())
        //     player.setCurrentStamina( 3 + player.getCurrentStamina() );
    }
    public void setSpawn() {
        player.setSpawn(levelManager.getCurrentLevel().getFlag1());
    }

    public void setIntro(int intro) {
        this.intro = intro;
    }

    public Tutorial getTutorial(){
        return tutorial;
    }
    public Plot getPlot(){
        return plot;
    }




}