package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import gameState.Playing;
import main.Game;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;

public class Minotaur extends Enemy {

    public Minotaur(float x, float y) {
        super(x , y, MINOTAUR_WIDTH, MINOTAUR_HEIGHT, MINOTAUR);
        initHitbox(30, 30);
        initAttackBox();
        
        this.enemyHealthBarWidth = (int)(30* Game.SCALE);
        this.enemyHealthBarHeight = (int)(1.5* Game.SCALE);
        this.enemyHealthWidth = enemyHealthBarWidth;
        this.walkSpeed = 0.4f * Game.SCALE;
    }
    
    private void initAttackBox(){
        attackBox=new Rectangle2D.Float(x,y,(int)(20*Game.SCALE),(int)(30*Game.SCALE));
        this.attackBoxOffsetX = (int)(Game.SCALE*20);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehaviour(lvlData, player);
        updateAnimationTick(player);
        updateAttackBoxFlip();
        updateHealthBar();
    }
    
    protected void updateAttackBoxFlip() {
        if (walkDir == RIGHT)
            attackBox.x = hitbox.x + hitbox.width;
        else
            attackBox.x = hitbox.x - 30;

        attackBox.y = hitbox.y ;
    }
    
    protected void updateAnimationTick(Player player) {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if(state == DEAD && aniIndex == 15) {
            	if(walkDir ==RIGHT)
            		checkEnmyHit(new Rectangle2D.Float(hitbox.x - attackBox.width, attackBox.y, (attackBox.width * 2 + hitbox.width), hitbox.height), player);
            	else if(walkDir == LEFT)
            		checkEnmyHit(new Rectangle2D.Float(attackBox.x - attackBox.width, attackBox.y, 60, hitbox.height), player);
            }
            	
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                aniIndex = 0;
                switch (state) {
                    case ATTACK, HIT -> state = IDLE;
                    case DEAD -> active = false;   
                }
        }
        }
    }


    private void updateBehaviour(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            updateInAir(lvlData);
        } else {
            switch (state) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseAttack(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 8 && !attackChecked)
                        checkEnmyHit(attackBox, player);
                    break;
                case HIT:
                    break;
            }

        }
    }
    
    public void drawHealthBar(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 - xLvlOffset + this.flipHealth()),
                (int) (hitbox.y + hitbox.height - attackBox.height - 5 * Game.SCALE), enemyHealthWidth,
                enemyHealthBarHeight);
        g.setColor(Color.WHITE);
        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 + enemyHealthWidth - xLvlOffset + this.flipHealth()),
                (int) (hitbox.y + hitbox.height - attackBox.height - 5 * Game.SCALE),
                enemyHealthBarWidth - enemyHealthWidth, enemyHealthBarHeight);
	}
	private int flipHealth() {
    	if(walkDir == RIGHT)
    		return -10;
    	else
    		return 10;
    }
    
	
    public int flipX(){
        if(walkDir==RIGHT)
            return width-260;
        else
            return  30;
    }
    public int flipY(){
        if(walkDir==RIGHT)
            return 1;
        else{
            return -1;
        }
    }

}
