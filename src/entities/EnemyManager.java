package entities;



import gameState.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.EnemyConstants.MINOTAUR_DRAWOFFSET_Y;

public class EnemyManager {
   private Playing playing;
   private BufferedImage[][] crabbyArr;
   private BufferedImage[][] sharkArr;
   private BufferedImage[][] minotaurArr;
   private ArrayList<Crabby> crabbies = new ArrayList<>();
   private ArrayList<Shark> sharks = new ArrayList<>();
   private ArrayList<Minotaur> minotaurs = new ArrayList<>();

  public EnemyManager(Playing playing){
      this.playing=playing;
      loadEnemyImgsCrabby();
      loadEnemyImgsShark();
      loadEnemyImgsMinotaur();


  }

    public void loadEnemies(Level level) {
      crabbies=level.getCrabs();
      sharks=level.getSharks();
      minotaurs=level.getMinotaurs();

    }

    public void update(int [][] lvlData,Player player){
      boolean isAnyActiveCrabby = false;
      boolean isAnyActiveShark = false;
      boolean isAnyActiveMinotaur = false;

        for(Shark sh:sharks)
            if(sh.isActive()) {
                sh.update(lvlData, player);
                isAnyActiveCrabby = true;
            }

      for(Crabby c:crabbies)
          if(c.isActive()) {
              c.update(lvlData, player);
              isAnyActiveShark = true;
          }

        for(Minotaur mino:minotaurs)
            if(mino.isActive()) {
                mino.update(lvlData, player);
                isAnyActiveMinotaur = true;
            }


      if(!isAnyActiveCrabby&&!isAnyActiveShark){
          playing.setLevelCompleted(true);

      }


  }
  public void draw(Graphics g,int xLvlOffset,int yLvlOffset){
      drawCrabs(g,xLvlOffset,yLvlOffset);
      drawSharks(g,xLvlOffset);
      drawMinotaurs(g,xLvlOffset);
  }

    private void drawCrabs(Graphics g,int xLvloffset,int yLvloffset) {
      for(Crabby c:crabbies) {
          if(c.isActive())
          g.drawImage(crabbyArr[c.getState()][c.getAniIndex()], (int) c.getHitBox().x - xLvloffset - CRABBY_DRAWOFFSET_X + c.flipX(), (int) c.getHitBox().y-yLvloffset - CRABBY_DRAWOFFSET_Y, CRABBY_WIDTH * c.flipY(), CRABBY_HEIGHT, null);
     // c.drawAttackBox(g,xLvloffset);
      }
  }
    private void drawSharks(Graphics g,int xLvloffset) {
        for(Shark sh:sharks) {
            if(sh.isActive())
                g.drawImage(sharkArr[sh.getState()][sh.getAniIndex()], (int) sh.getHitBox().x - xLvloffset - SHARK_DRAWOFFSET_X+ sh.flipX(), (int) sh.getHitBox().y - SHARK_DRAWOFFSET_Y, SHARK_WIDTH * sh.flipY(), SHARK_HEIGHT, null);
             //sh.drawAttackBox(g,xLvloffset);
        }
    }
    private void drawMinotaurs(Graphics g,int xLvloffset) {
        for(Minotaur mino:minotaurs) {
            if(mino.isActive())
                g.drawImage(minotaurArr[mino.getState()][mino.getAniIndex()], (int) mino.getHitBox().x- xLvloffset  - MINOTAUR_DRAWOFFSET_X+mino.flipX(), (int) mino.getHitBox().y - MINOTAUR_DRAWOFFSET_Y, MINOTAUR_WIDTH*mino.flipY() , MINOTAUR_HEIGHT, null);
            mino.drawHitbox(g,xLvloffset);
            mino.drawAttackBox(g,xLvloffset);
        }
    }


  public void checkEnemyHit(Rectangle2D.Float attackBox){
      for(Crabby c: crabbies)
          if(c.isActive())
              if(c.getCurrentHealth()>0)
          if(attackBox.intersects(c.getHitBox())){
              c.hurt(10);
              return;
          }

      for(Shark sh: sharks)
          if(sh.isActive())
              if(sh.getCurrentHealth()>0)
              if(attackBox.intersects(sh.getHitBox())){
                  sh.hurt(10);
                  return;
              }

      for(Minotaur mino: minotaurs)
          if(mino.isActive())
              if(mino.getCurrentHealth()>0)
                  if(attackBox.intersects(mino.getHitBox())){
                      mino.hurt(10);
                      return;
                  }

  }


    private void loadEnemyImgsCrabby() {
      crabbyArr = new BufferedImage[5][9];
      BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
      for(int j=0;j<crabbyArr.length;j++)
          for(int i=0;i<crabbyArr[j].length;i++)
              crabbyArr[j][i]=temp.getSubimage(i*CRABBY_WIDTH_DEFAULT,j*CRABBY_HEIGHT_DEFAULT,CRABBY_WIDTH_DEFAULT,CRABBY_HEIGHT_DEFAULT);
    }

    private void loadEnemyImgsShark() {
        sharkArr = new BufferedImage[5][8];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SHARK_ATLAS);
        for(int j=0;j<sharkArr.length;j++)
            for(int i=0;i<sharkArr[j].length;i++)
                sharkArr[j][i]=temp.getSubimage(i*SHARK_WIDTH_DEFAULT,j*SHARK_HEIGHT_DEFAULT,SHARK_WIDTH_DEFAULT,SHARK_HEIGHT_DEFAULT);
    }

    private void loadEnemyImgsMinotaur() {
        minotaurArr = new BufferedImage[5][23];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MINOTAUR_ATLAS);
        for(int j=0;j<minotaurArr.length;j++)
            for(int i=0;i<minotaurArr[j].length;i++)
                minotaurArr[j][i]=temp.getSubimage(i*MINOTAUR_WIDTH_DEFAULT,j*MINOTAUR_HEIGHT_DEFAULT,MINOTAUR_WIDTH_DEFAULT,MINOTAUR_HEIGHT_DEFAULT);
    }


    public void resetAllEnemies(){
      for(Crabby c:crabbies)
          c.resetEnemy();
      for (Shark sh:sharks)
          sh.resetEnemy();
      for(Minotaur mino:minotaurs)
          mino.resetEnemy();
    }
}
