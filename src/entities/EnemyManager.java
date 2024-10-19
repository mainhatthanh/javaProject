package entities;


import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
   private gamestates.Playing playing;
   private BufferedImage[][] crabbyArr;
   private ArrayList<Crabby> crabbies = new ArrayList<>();

  public EnemyManager(gamestates.Playing playing){
      this.playing=playing;
      loadEnemyImgs();
      addEnemies();
  }

    private void addEnemies() {
      crabbies=LoadSave.GetCrabs();

    }

    public void update(int [][] lvlData,Player player){
      for(Crabby c:crabbies)
          if(c.isActive())
          c.update(lvlData,player);
  }
  public void draw(Graphics g,int xLvlOffset){
      drawCrabs(g,xLvlOffset);
  }

    private void drawCrabs(Graphics g,int xLvloffset) {
      for(Crabby c:crabbies) {
          if(c.isActive())
          g.drawImage(crabbyArr[c.getEnemyState()][c.getAniIndex()], (int) c.getHitBox().x - xLvloffset - CRABBY_DRAWOFFSET_X + c.flipX(), (int) c.getHitBox().y - CRABBY_DRAWOFFSET_Y, CRABBY_WIDTH * c.flipY(), CRABBY_HEIGHT, null);
     // c.drawAttackBox(g,xLvloffset);
      }
  }

  public void checkEnemyHit(Rectangle2D.Float attackBox){
      for(Crabby c: crabbies)
          if(c.isActive())
          if(attackBox.intersects(c.getHitBox())){
              c.hurt(10);
              return;
          }

  }


    private void loadEnemyImgs() {
      crabbyArr = new BufferedImage[5][9];
      BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
      for(int j=0;j<crabbyArr.length;j++)
          for(int i=0;i<crabbyArr[j].length;i++)
              crabbyArr[j][i]=temp.getSubimage(i*CRABBY_WIDTH_DEFAULT,j*CRABBY_HEIGHT_DEFAULT,CRABBY_WIDTH_DEFAULT,CRABBY_HEIGHT_DEFAULT);
    }
    public void resetAllEnemies(){
      for(Crabby c:crabbies)
          c.resetEnemy();
    }
}
