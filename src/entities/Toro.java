package entities;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.Directions.*;


import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Toro extends Enemy {

	private boolean change = true;
	private boolean isAttacking;

	public Toro(float x, float y) {
		super(x , y, TORO_WIDTH, TORO_HEIGHT, TORO);
		initHitbox(40, 30);
		this.enemyHealthBarWidth = (int)(35* Game.SCALE);
        this.enemyHealthBarHeight = (int)(3* Game.SCALE);
        this.enemyHealthWidth = enemyHealthBarWidth;
        this.walkSpeed = 0.38f * Game.SCALE;
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x , y, (int)(Game.SCALE * 40),(int)(Game.SCALE * 30));
		this.attackBoxOffsetX = (int)(Game.SCALE *40);
	}


	public void update(int[][] lvlData, Player player) {
            	updateBehaviour(lvlData,player);
                updateAnimationTick();
                updateAttackBoxFlip();
                updateHealthBar();
                
                getCrazy(20, 0.45f);
	}
	
	
	protected void getCrazy(int speedUp, float walkUp) {
    	if(currentHealth <= 0.4 *maxHealth && currentHealth >0) {	
    		this.lowHealth = true;
        	this.speed = speedUp;
        	this.walkSpeed = walkUp * Game.SCALE;
        	initBox1();
    	}
    }
	
	

	private void initBox1() {
		if (walkDir == RIGHT)
            attackBox.x = hitbox.x - (int)(40*Game.SCALE);
        else
            attackBox.x = hitbox.x - attackBoxOffsetX;
		
		this.attackBox.width = (int)(120 * Game.SCALE);
		
	}

	protected void updateAttackBoxFlip() {
        if (walkDir == RIGHT)
            attackBox.x = hitbox.x + hitbox.width;
        else
            attackBox.x = hitbox.x - attackBoxOffsetX;

        attackBox.y = hitbox.y ;
    }
	private void updateBehaviour(int[][] lvlData, Player player) {
		if(firstUpdate) 
			firstUpdateCheck(lvlData);
		
		if(inAir) {
			updateInAir(lvlData);
		}else {
			switch(state) {
			case IDLE:
				if(canSeePlayer(lvlData,player)) {
            		newState(RUNNING);
            		turnTowardsPlayer(player);
            	}
				break;
				
			case IDLE_2 :
            	if(canSeePlayer(lvlData,player)) {
            		newState(RUNNING);
            		turnTowardsPlayer(player);
            	}        		
                break;
                
			case RUNNING:

				if(isPlayerCloseAttack(player)) {
					if(change && !lowHealth)
                		newState(ATTACK);
                	else
                		newState(ATTACK2);
                	change = !change; 
				}if(!canSeePlayer(lvlData, player))
					newState(IDLE_2);
				
				move(lvlData);
				break;
			case ATTACK:
				if(aniIndex ==0)
					attackChecked = false;		
				
				if( aniIndex == 3 &&!attackChecked)
					setAttacking(true);
				//Nhân vật trong tầm đánh mới tung chiêu
                	if(attackBox.intersects(player.getHitbox())) {
                		checkEnmyHit(attackBox,player);
                		
                	}else 
                		newState(RUNNING);

                	if(aniIndex==4)
    					setAttacking(false);
				break;
				
			case ATTACK2:
				if(aniIndex ==0)
					attackChecked = false;
				
				if(aniIndex == 0  ) {
					setAttacking(true);
				}
				
				if(aniIndex== 1 &&!attackChecked)
                	if(attackBox.intersects(player.getHitbox())) {
                		checkEnmyHit(attackBox,player);
                		
                	}else {
                		newState(RUNNING);
                		
                	}
				
				break;
			case HIT:
				break;
			}
		}
	}
	
	public void drawHealthBar(Graphics g, int xLvlOffset) {

        g.setColor(Color.red);
        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 - xLvlOffset + this.flipHealth()),
                (int) (hitbox.y + hitbox.height - attackBox.height - 12 * Game.SCALE), enemyHealthWidth,
                enemyHealthBarHeight);
        g.setColor(Color.WHITE);
        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 + enemyHealthWidth - xLvlOffset + this.flipHealth()),
                (int) (hitbox.y + hitbox.height - attackBox.height - 12 * Game.SCALE),
                enemyHealthBarWidth - enemyHealthWidth, enemyHealthBarHeight);
	
}
	private int flipHealth() {
    	if(walkDir == RIGHT)
    		return 5;
    	else
    		return -5;
    }
	
	public int flipX() {
		if(walkDir == RIGHT)
			return 0;
		else
			return width + 15;
	}
	
	public int flipY() {
		if(walkDir == RIGHT)
			return 1;
		else
			return -1;
		
	}
	public boolean isAttacking() {
        return isAttacking;
    }
    
    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
    
}
