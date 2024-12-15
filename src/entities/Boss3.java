package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Boss3 extends Enemy {
	
	private boolean change = true;
	private boolean isAttacking;
	
	public Boss3(float x, float y) {
		 super(x, y, BOSS3_WIDTH, BOSS3_HEIGHT, BOSS3);
	        initHitbox(20,30);
	        this.enemyHealthBarWidth = (int)(35* Game.SCALE);
	        this.enemyHealthBarHeight = (int)(3* Game.SCALE);
	        this.enemyHealthWidth = enemyHealthBarWidth;
	        this.walkSpeed = 0.4f * Game.SCALE;
	        initAttackBox();
	        
	        
		
	}
	 private void initAttackBox(){
	        attackBox=new Rectangle2D.Float(x,y,(int)(35*Game.SCALE),(int)(30*Game.SCALE));
	        attackBoxOffsetX = (int)(Game.SCALE*35);
	    }

	    public void update(int[][] lvlData,Player player){
	    	updateBehaviour(lvlData,player);
	        updateAnimationTick();
	        updateAttackBoxFlip();
	        updateHealthBar();
	        
	        getCrazy(15,  0.6f);
	    }

	    protected void updateAttackBoxFlip() {
	        if (walkDir == RIGHT)
	            attackBox.x = hitbox.x + hitbox.width;
	        else
	            attackBox.x = hitbox.x - attackBoxOffsetX;

	        attackBox.y = hitbox.y + 5;
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

	                case RUNNING:
	                        if (isPlayerCloseAttack(player)) {
	                            if(change)
	                            	newState(ATTACK);
	                            else
	                            	newState(ATTACK2);
	                            change = !change;
	                        	
	                    }
	                        if(!canSeePlayer(lvlData,player)) 
		                		newState(IDLE);
	                    move(lvlData);
	                    break;
	                case ATTACK:
	                    if(aniIndex==0){   
							attackChecked = false;
						}
	                    if(aniIndex== 2 &&!attackChecked) {
							setAttacking(true);
	                        checkEnmyHit(attackBox,player);
						}
						if(aniIndex == 3) {
							setAttacking(false);
						}
						
	                    break;
	                case ATTACK2:
	                	if(aniIndex==0){   
							attackChecked = false;
						}
	                    if(aniIndex== 2 &&!attackChecked) {
							setAttacking(false);
	                        checkEnmyHit(attackBox,player);
						}
						if(aniIndex == 1) {
							setAttacking(true);
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
	                (int) (hitbox.y + hitbox.height - attackBox.height - 15 * Game.SCALE), enemyHealthWidth,
	                enemyHealthBarHeight);
	        g.setColor(Color.WHITE);
	        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 + enemyHealthWidth - xLvlOffset + this.flipHealth()),
	                (int) (hitbox.y + hitbox.height - attackBox.height - 15 * Game.SCALE),
	                enemyHealthBarWidth - enemyHealthWidth, enemyHealthBarHeight);
		}
		private int flipHealth() {
	    	if(walkDir == RIGHT)
	    		return 2;
	    	else
	    		return -2;
	    }

	    public int flipX(){
	        if(walkDir==RIGHT)
	            return -30;
	        else
	            return  width - 20;
	    }
	    public int flipY(){
	          if(walkDir==RIGHT)
	              return 1;
	          else{
	              return -1;
	          }
	    }
		public boolean isAttacking() {
			return isAttacking;
		}
		
		public void setAttacking(boolean isAttacking) {
			this.isAttacking = isAttacking;
		}
}
