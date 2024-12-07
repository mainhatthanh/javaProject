/*package entities;

import gameState.Playing;
import levels.Level;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.Bullet.BALL_1EYE_DEFAULT_WIDTH;
import static utilz.Constants.Bullet.BALL_1EYE_HEIGHT;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.HelpMethods.*;

public class BulletManager {
    private Playing playing;
    private BufferedImage bulletImg;
    private ArrayList<Bullet> bullets=new ArrayList<>();
    private ArrayList<Monster_Eye1> me=new ArrayList<>();


    private Level currentLevel;

    public BulletManager(Playing playing) {
        this.playing = playing;
        currentLevel=playing.getLevelManager().getCurrentLevel();
        loadImgs();
    }

    public void loadBullets(Level newLevel){
        currentLevel = newLevel;
        bullets.clear();
    }

    private void loadImgs() {
          bulletImg= LoadSave.GetSpriteAtlas(LoadSave.BALL1EYE);
    }

    public void update(int[][] lvlData, Player player){
          updateBullet(lvlData,player);
          updateME(lvlData,player);
    }

    private void updateBullet(int[][] lvlData, Player player) {
        for (Bullet b : bullets)
            if (b.isActive()) {
                b.updatePos();
                if (b.getHitbox().intersects(player.getHitbox())) {
                    player.changeHealth(-25);
                    b.setActive(false);
                } else if (IsBulletHittingLevel(b, lvlData))
                    b.setActive(false);
            }
    }

    private boolean isPlayerInRange(Monster_Eye1 me, Player player) {
        int absValue = (int) Math.abs(player.getHitbox().x - me.getHitbox().x);
        return absValue <= Game.TILES_SIZE * 5;
    }

    private boolean isPlayerInfrontOfMe(Monster_Eye1 me, Player player) {

        if (me.walkDir == LEFT && me.getHitbox().x > player.getHitbox().x) {
            return true;
        } else if (me.walkDir == RIGHT && me.getHitbox().x < player.getHitbox().x) {
            return true;
        }

        return false;
    }

    protected void updateME(int[][] lvlData, Player player) {
        for (Monster_Eye1 me : currentLevel.getMonEye1()) {
            if(!me.doAnimation)
      if(isPlayerInRange(me,player))
       if(isPlayerInfrontOfMe(me,player))
            if (CanMESeePlayer(lvlData, player.getHitbox(), me.getHitbox(), me.getTileY()))
                me.setAnimation(true);



        me.update();
        if (me.getAniIndex() == 4 && me.getAniTick() == 0) {
            shootME(me);
        }
    }
    }




    private void shootME(Monster_Eye1 me){
      int dir=1;
      int vecto=1;
        if(me.walkDir==LEFT) {
            vecto = -2;
        }
        else if(me.walkDir==RIGHT)
            vecto=2;

        bullets.add(new Bullet((int)me.getHitbox().x,(int)me.getHitBox().y,dir*vecto));
    }
    public void draw(Graphics g,int xLvlOffset){
        drawBullets(g,xLvlOffset);
    }

    private void drawBullets(Graphics g,int xLvlOffset){
        for (Bullet b : bullets)
            if (b.isActive())
                g.drawImage(bulletImg, (int) (b.getHitbox().x - xLvlOffset), (int) (b.getHitbox().y), BALL_1EYE_DEFAULT_WIDTH, BALL_1EYE_HEIGHT, null);

    }

    public void resetBullets(){
        loadBullets(playing.getBulletManager().currentLevel);
        for(Monster_Eye1 me: currentLevel.getMonEye1())
            me.reset();
    }


}*/
