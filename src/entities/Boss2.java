package entities;

import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.ATTACK;
import static utilz.Constants.EnemyConstants.BOSS2;
import static utilz.Constants.EnemyConstants.BOSS2_HEIGHT;
import static utilz.Constants.EnemyConstants.BOSS2_WIDTH;
import static utilz.Constants.EnemyConstants.HIT;
import static utilz.Constants.EnemyConstants.IDLE;
import static utilz.Constants.EnemyConstants.RUNNING;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Boss2 extends Enemy {
	private int attackBoxOffsetX;

    public Boss2(float x, float y) {
        super(x, y, BOSS2_WIDTH, BOSS2_HEIGHT, BOSS2);
        initHitbox(15,15);
        initAttackBox();
    }
    private void initAttackBox(){
        attackBox=new Rectangle2D.Float(x,y,(int)(55*Game.SCALE),(int)(15*Game.SCALE));
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

                    if(aniIndex== 4 &&!attackChecked)
                        checkEnmyHit(attackBox,player);
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
            return -30;
        else
            return  width + 30;
    }
    public int flipY(){
          if(walkDir==RIGHT)
              return 1;
          else{
              return -1;
          }
    }
}
