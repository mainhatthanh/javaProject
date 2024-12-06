package entities;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;


public class Spider extends Enemy{


    private int attackBoxOffsetX;

    public Spider(float x, float y) {

        super(x, y, SPIDER_WIDTH, SPIDER_HEIGHT, SPIDER);
        initHitbox(30,9);
        initAttackBox();
    }
    private void initAttackBox(){
        attackBox=new Rectangle2D.Float(x,y,(int)(70*Game.SCALE),(int)(9*Game.SCALE));
        attackBoxOffsetX = (int)(Game.SCALE*20);
    }

    
    public void update(int[][] lvlData,Player player){
        updateBehaviour(lvlData,player);
        updateAnimationTick();
        updateAttackBox();

    }

    protected void updateAttackBox() {
        attackBox.x= hitbox.x-attackBoxOffsetX;
        attackBox.y=hitbox.y;
    }

    private void updateBehaviour(int[][] lvlData,Player player) {
        if (firstUpdate){
         firstUpdateCheck(lvlData);

        }
        if(inAir) {
           updateInAir(lvlData);
        }else{
            switch (state){
                case IDLE :
                    newState(RUNNING);
                    break;

                case RUNNING:
                    if(canSeePlayer(lvlData,player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseAttack(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if(aniIndex==0)
                        attackChecked = false;

                    if(aniIndex==3&&!attackChecked) {
                        checkEnmyHit(attackBox,player);
                    }
                    break;
                case HIT:
                    break;
            }

        }

    }


	public void drawAttackBox(Graphics g,int xLvlOffset){
        g.setColor(Color.red);
        g.drawRect((int)(attackBox.x-xLvlOffset),(int)(attackBox.y),(int)attackBox.width,(int)attackBox.height);

    }
    public int flipX(){
        if(walkDir==RIGHT)
            return width  + 60;
        else
            return  -30;
    }
    public int flipY(){
          if(walkDir==RIGHT)
              return -1;
          else{
              return 1;
          }
    }

}
