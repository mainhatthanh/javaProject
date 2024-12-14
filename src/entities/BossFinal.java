package entities;


import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class BossFinal extends Enemy {
	

		private boolean recovery = true;
	
		private boolean change = true;
		private boolean isAttacking;


	    public BossFinal(float x, float y) {
	        super(x, y, BOSSFINAL_WIDTH, BOSSFINAL_HEIGHT, BOSSFINAL);
	        initHitbox(25,30);
	        this.enemyHealthBarWidth = (int)(40* Game.SCALE);
	        this.enemyHealthBarHeight = (int)(4* Game.SCALE);
	        this.enemyHealthWidth = enemyHealthBarWidth;
	        this.walkSpeed = 0.5f * Game.SCALE;
	        initAttackBox();
	    }
	    private void initAttackBox(){
	        attackBox=new Rectangle2D.Float(x,y,(int)(25*Game.SCALE),(int)(20*Game.SCALE));
	        attackBoxOffsetX = (int)(Game.SCALE*25);
	    }

	    public void update(int[][] lvlData,Player player){
	    	updateBehaviour(lvlData,player);
	        updateAnimationTick();
	        updateAttackBoxFlip();
	        updateHealthBar();
	        
	        getCrazy(20, 0.45f);

	    }
	    
	    
	    public void updateAnimaIDLE() {
	        aniTick++;
	        if (aniTick >= 30) {
	            aniTick = 0;
	            aniIndex++;
	            if (aniIndex >=  4)
	                aniIndex = 0;
	        }
	    }
	    
	    protected void updateAttackBoxFlip() {
	        if (walkDir == RIGHT)
	            attackBox.x = hitbox.x + hitbox.width;
	        else
	            attackBox.x = hitbox.x - attackBoxOffsetX;

	        attackBox.y = hitbox.y + 15;
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
	                    if (isPlayerCloseAttack(player)) {
	                        	if(change)
	                        		newState(ATTACK);
	                        	else
	                        		newState(ATTACK2);
	                        	change = !change;  
	                    }
	                    
	                    if(!canSeePlayer(lvlData,player)) 
	                		newState(IDLE_2);
	                		 
	                    move(lvlData);
	                    break;
	                case ATTACK:
	                    if(aniIndex==0)
	                        attackChecked = false;
	                    
	                    if(aniIndex== 1 &&!attackChecked)
	                    	if(attackBox.intersects(player.getHitbox())) {
	                    		checkEnmyHit(attackBox,player);
	                    		setAttacking(true);
	                    		
	                    	}else 
	                    		newState(RUNNING);
	                    if (aniIndex == 2) 
							setAttacking(false);
	                    break;
	                    
	                case ATTACK2:
	                	if(aniIndex==0)
	                        attackChecked = false;

	                    if( aniIndex == 4 &&!attackChecked)
	                    	//nhân vật trong tầm chiêu mới đánh
	                    	if(attackBox.intersects(player.getHitbox())) {
	                    		checkEnmyHit(attackBox,player);
	                    		
	                    	}else 
	                    		newState(RUNNING);

	                    if(aniIndex== 1 &&!attackChecked) {
	                        checkEnmyHit(attackBox,player);
							setAttacking(true);
						}
						if (aniIndex == 2) 
							setAttacking(false);
						
	                    break;
	                case HIT:
	                    break;
	            }

	        }

	    }
	    
	    
	    protected void getCrazy(int speedUp, float walkUp) {
	    	if(currentHealth <= 0.4 *maxHealth && currentHealth >0) {
	    		hitbox.width = 35*Game.SCALE;
	    		attackBox.width = 45 *Game.SCALE;
	    		attackBoxOffsetX = (int)(45 * Game.SCALE);
	    		this.lowHealth = true;
	        	this.speed = speedUp;
	        	this.walkSpeed = walkUp * Game.SCALE;
	        	if(recovery) {
	        		this.currentHealth = GetMaxHealth(BOSSFINAL);
	        		recovery = false;
	        	}

	    	}
	    }

		public void drawHealthBar(Graphics g, int xLvlOffset) {
	        g.setColor(Color.red);
	        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 - xLvlOffset + this.flipHealth()),
	                (int) (hitbox.y + hitbox.height - attackBox.height - flipflipp()), enemyHealthWidth,
	                enemyHealthBarHeight);
	        g.setColor(Color.WHITE);
	        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 + enemyHealthWidth - xLvlOffset + this.flipHealth()),
	                (int) (hitbox.y + hitbox.height - attackBox.height - flipflipp()),
	                enemyHealthBarWidth - enemyHealthWidth, enemyHealthBarHeight);
		}
		private int flipHealth() {
			if(lowHealth) {
		    	if(walkDir == RIGHT)
		    		return (int)(-30* Game.SCALE);
		    	else
		    		return (int)(30* Game.SCALE);
			}else {
				if(walkDir == RIGHT)
		    		return -5;
		    	else
		    		return 2;
			}
	    }
		
		private int flipflipp() {
			if(lowHealth) {
		    	return (int)(40 * Game.SCALE);
			}else {
				return (int)(25* Game.SCALE);
			}
		}


	    public void drawAttackBox(Graphics g,int xLvlOffset){
	        g.setColor(Color.red);
	        g.drawRect((int)(attackBox.x-xLvlOffset),(int)(attackBox.y),(int)attackBox.width,(int)attackBox.height);

	    }
	    public int flipX(){
	    	if(lowHealth) {
		    	if(walkDir == RIGHT)
		    		return (int)(-50*Game.SCALE);
		    	else
		    		return (int)(160*Game.SCALE);
			}else {
				if(walkDir==RIGHT)
		            return -10;
		        else
		            return  width + 69  ;
			}
	        
	    }
	    public int flipY(){
	          if(walkDir==RIGHT)
	              return 1;
	          else{
	              return -1;
	          }
	    }
	    
	    public int getSize() {
	    	if(lowHealth)
	    		return 3;
	    	else
	    		return 2;
	    
	    }
	    
	    public int getYPos() {
	    	if(lowHealth)
	    		return 100;
	    	else if(state != DEAD)
	    		return 35;
	    	
	    	else
	    		return 25;
	    }
	    
	    public void resetEnemy() {
	        hitbox.x = x;
	        hitbox.y = y;
	        firstUpdate = true;
	        currentHealth = maxHealth;
	        newState(IDLE);
	        active = true;
	        airSpeed = 0;
	        
	        lowHealth = false;
	        speed = ANI_SPEED;
	        dmg = GetEnemyDmg(enemyType);
	        recovery = true;
	    }
	    
	    public void checkEnmyHit(Rectangle2D.Float attackBox, Player player) {
	        if (attackBox.intersects(player.hitbox)) {
	        	if(enemyType == TORO || enemyType == BOSS2||enemyType == BOSS3||enemyType == BOSS4||enemyType == BOSSFINAL)
	        		if(lowHealth)
	        			dmg = (int)(1.35 * dmg);
	        	player.changeHealth(-dmg);
	            attackChecked = true;

	        }
	        
	    }

		public boolean isAttacking() {
			return isAttacking;
		}
		
		public void setAttacking(boolean isAttacking) {
			this.isAttacking = isAttacking;
		}

	}
	



