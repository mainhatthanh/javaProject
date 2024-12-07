package entities;

import gameState.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] crabbyArr;
    private BufferedImage[][] sharkArr;
    private BufferedImage[][] minotaurArr;
    private BufferedImage[][] toroArr; 

    private ArrayList<Crabby> crabbies = new ArrayList<>();
    private ArrayList<Shark> sharks = new ArrayList<>();

    private ArrayList<Minotaur> minotaurs = new ArrayList<>();
    private ArrayList<Toro> toros = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgsCrabby();
        loadEnemyImgsShark();
        loadEnemyImgsMinotaur();
        loadEnemyImgsToro();

    }

    public void loadEnemies(Level level) {
        crabbies = level.getCrabs();
        sharks = level.getSharks();
        minotaurs = level.getMinotaurs();
        toros = level.getToros();
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActiveCrabby = false;
        boolean isAnyActiveShark = false;
        boolean isAnyActiveMinotaur = false;
        boolean isAnyActiveToro = false;

        for (Shark sh : sharks){
            if (sh.isActive()) {
                sh.updateHealthBar();
                sh.update(lvlData, player);
                isAnyActiveCrabby = true;
            }
            // } else {
            //     player.changeExp(GetExperience(SHARK));
            // }
        }
            

        for (Crabby c : crabbies){
            if (c.isActive()) {
                c.updateHealthBar();
                c.update(lvlData, player);
                isAnyActiveShark = true;
            }
            // } else {
            //     player.changeExp(GetExperience(CRABBY));
            // }
        }

        for (Minotaur mino : minotaurs){
            if (mino.isActive()) {
                mino.updateHealthBar();
                mino.update(lvlData, player);
                isAnyActiveMinotaur = true;
            }
            // } else {
            //     player.changeExp(GetExperience(MINOTAUR));
            // }
        }

        for (Toro t : toros){
            if (t.isActive()) {
                t.updateHealthBar();
                t.update(lvlData, player);
                isAnyActiveToro = true;
            }
            // } else {
            //     player.changeExp(GetExperience(TORO));
            // }
        }

        if (!isAnyActiveCrabby && !isAnyActiveShark && !isAnyActiveMinotaur && !isAnyActiveToro) {
            playing.setLevelCompleted(true);
        }

    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
        drawSharks(g, xLvlOffset);
        drawMinotaurs(g, xLvlOffset);
        drawToros(g, xLvlOffset);
    }

    private void drawToros(Graphics g, int xLvlOffset) {
        for (Toro t : toros) {
            if (t.isActive()) {
                g.drawImage(toroArr[t.getState()][t.getAniIndex()],
                        (int) (t.getHitBox().x - xLvlOffset - TORO_DRAWOFFSET_X + t.flipX()),
                        (int) (t.getHitBox().y - TORO_DRAWOFFSET_Y), TORO_WIDTH * t.flipW(), TORO_HEIGHT, null);
                t.drawHealthBar(g, xLvlOffset);
            }
            // t.drawAttackHitbox(g,xLvlOffset);
            // t.drawHitbox(g,xLvlOffset);
        }
    }

    private void drawCrabs(Graphics g, int xLvloffset) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                g.drawImage(crabbyArr[c.getState()][c.getAniIndex()],
                        (int) c.getHitBox().x - xLvloffset - CRABBY_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitBox().y - CRABBY_DRAWOFFSET_Y, CRABBY_WIDTH * c.flipY(), CRABBY_HEIGHT, null);
                c.drawHealthBar(g, xLvloffset);
            }
            // c.drawAttackBox(g,xLvloffset);
            // c.drawHitbox(g,xLvloffset);
        }
    }

    private void drawSharks(Graphics g, int xLvloffset) {
        for (Shark sh : sharks) {
            if (sh.isActive()) {
                g.drawImage(sharkArr[sh.getState()][sh.getAniIndex()],
                        (int) sh.getHitBox().x - xLvloffset - SHARK_DRAWOFFSET_X + sh.flipX(),
                        (int) sh.getHitBox().y - SHARK_DRAWOFFSET_Y, SHARK_WIDTH * sh.flipY(), SHARK_HEIGHT, null);
                sh.drawHealthBar(g, xLvloffset);
            }
            // sh.drawAttackBox(g,xLvloffset);
            // sh.drawHitbox(g,xLvloffset);
        }
    }

    private void drawMinotaurs(Graphics g, int xLvloffset) {
        for (Minotaur mino : minotaurs) {
            if (mino.isActive()) {
                g.drawImage(minotaurArr[mino.getState()][mino.getAniIndex()],
                        (int) mino.getHitBox().x - xLvloffset - MINOTAUR_DRAWOFFSET_X + mino.flipX(),
                        (int) mino.getHitBox().y - MINOTAUR_DRAWOFFSET_Y, MINOTAUR_WIDTH * mino.flipY(),
                        MINOTAUR_HEIGHT, null);
                mino.drawHealthBar(g, xLvloffset);
            }
            // mino.drawHitbox(g,xLvloffset);
            // mino.drawAttackBox(g,xLvloffset);
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
        for (Crabby c : crabbies)
            if (c.isActive())
                if (c.getCurrentHealth() > 0)
                    if (attackBox.intersects(c.getHitBox())) {
                        c.hurt(10, player);
                        return;
                    }

        for (Shark sh : sharks)
            if (sh.isActive())
                if (sh.getCurrentHealth() > 0)
                    if (attackBox.intersects(sh.getHitBox())) {
                        sh.hurt(10, player);
                        return;
                    }

        for (Minotaur mino : minotaurs)
            if (mino.isActive())
                if (mino.getCurrentHealth() > 0)
                    if (attackBox.intersects(mino.getHitBox())) {
                        mino.hurt(10, player);
                        return;
                    }

        for (Toro t : toros)
            if (t.isActive())
                if (t.getCurrentHealth() > 0)
                    if (attackBox.intersects(t.getHitBox())) {
                        t.hurt(10, player);
                        return;
                    }

    }

    private void loadEnemyImgsCrabby() {
        crabbyArr = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
        for (int j = 0; j < crabbyArr.length; j++)
            for (int i = 0; i < crabbyArr[j].length; i++)
                crabbyArr[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT,
                        CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
    }

    private void loadEnemyImgsShark() {
        sharkArr = new BufferedImage[5][8];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SHARK_ATLAS);
        for (int j = 0; j < sharkArr.length; j++)
            for (int i = 0; i < sharkArr[j].length; i++)
                sharkArr[j][i] = temp.getSubimage(i * SHARK_WIDTH_DEFAULT, j * SHARK_HEIGHT_DEFAULT,
                        SHARK_WIDTH_DEFAULT, SHARK_HEIGHT_DEFAULT);
    }

    private void loadEnemyImgsMinotaur() {
        minotaurArr = new BufferedImage[5][23];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MINOTAUR_ATLAS);
        for (int j = 0; j < minotaurArr.length; j++)
            for (int i = 0; i < minotaurArr[j].length; i++)
                minotaurArr[j][i] = temp.getSubimage(i * MINOTAUR_WIDTH_DEFAULT, j * MINOTAUR_HEIGHT_DEFAULT,
                        MINOTAUR_WIDTH_DEFAULT, MINOTAUR_HEIGHT_DEFAULT);
    }

    private void loadEnemyImgsToro() {
        toroArr = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.TORO_ATLAS);
        for (int j = 0; j < toroArr.length; j++)
            for (int i = 0; i < toroArr[j].length; i++)
                toroArr[j][i] = temp.getSubimage(i * TORO_WIDTH_DEFAULT, j * TORO_HEIGHT_DEFAULT, TORO_WIDTH_DEFAULT,
                        TORO_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies() {
        for (Crabby c : crabbies)
            c.resetEnemy();
        for (Shark sh : sharks)
            sh.resetEnemy();
        for (Minotaur mino : minotaurs)
            mino.resetEnemy();
        for (Toro t : toros)
            t.resetEnemy();
    }
}
