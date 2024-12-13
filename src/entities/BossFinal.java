package entities;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.ATTACK;
import static utilz.Constants.EnemyConstants.BOSSFINAL;
import static utilz.Constants.EnemyConstants.BOSSFINAL_HEIGHT;
import static utilz.Constants.EnemyConstants.BOSSFINAL_WIDTH;
import static utilz.Constants.EnemyConstants.DEAD;
import static utilz.Constants.EnemyConstants.GetSpriteAmount;
import static utilz.Constants.EnemyConstants.HIT;
import static utilz.Constants.EnemyConstants.IDLE;
import static utilz.Constants.EnemyConstants.RUNNING;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class BossFinal extends Enemy {
	
		private int attackBoxOffsetX;
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

	    }
	    
	    protected void updateAnimationTick() {
	        aniTick++;
	        if (aniTick >= ANI_SPEED) {
	            aniTick = 0;
	            aniIndex++;
	            
	            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
	                aniIndex = 0;
	                switch (state) {
	                    case ATTACK, HIT -> state = IDLE;
	                    case DEAD -> active = false;
	                }

	            }

	        }
	    }
	    
	    public void updateAnimaIDLE() {
	        aniTick++;
	        if (aniTick >= 40) {
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
	                    if(aniIndex==0) {
	                        attackChecked = false;
							
						}
	                    if(aniIndex== 1 &&!attackChecked) {
	                        checkEnmyHit(attackBox,player);
							setAttacking(true);
						}
						if (aniIndex == 2) {
							setAttacking(false);
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
	                (int) (hitbox.y + hitbox.height - attackBox.height - 25 * Game.SCALE), enemyHealthWidth,
	                enemyHealthBarHeight);
	        g.setColor(Color.WHITE);
	        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 + enemyHealthWidth - xLvlOffset + this.flipHealth()),
	                (int) (hitbox.y + hitbox.height - attackBox.height - 25 * Game.SCALE),
	                enemyHealthBarWidth - enemyHealthWidth, enemyHealthBarHeight);
		}
		private int flipHealth() {
	    	if(walkDir == RIGHT)
	    		return -5;
	    	else
	    		return 2;
	    }


	    public void drawAttackBox(Graphics g,int xLvlOffset){
	        g.setColor(Color.red);
	        g.drawRect((int)(attackBox.x-xLvlOffset),(int)(attackBox.y),(int)attackBox.width,(int)attackBox.height);

	    }
	    public int flipX(){
	        if(walkDir==RIGHT)
	            return -10;
	        else
	            return  width + 69  ;
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
	



