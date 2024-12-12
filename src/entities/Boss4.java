package entities;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.Directions.RIGHT;

import static utilz.Constants.EnemyConstants.ATTACK;
import static utilz.Constants.EnemyConstants.BOSS4;
import static utilz.Constants.EnemyConstants.BOSS4_HEIGHT;
import static utilz.Constants.EnemyConstants.BOSS4_WIDTH;
import static utilz.Constants.EnemyConstants.DEAD;
import static utilz.Constants.EnemyConstants.GetEnemyDmg;
import static utilz.Constants.EnemyConstants.GetSpriteAmount;
import static utilz.Constants.EnemyConstants.HIT;
import static utilz.Constants.EnemyConstants.IDLE;
import static utilz.Constants.EnemyConstants.RUNNING;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Boss4 extends Enemy {

	public Boss4(float x, float y) {
		 super(x, y, BOSS4_WIDTH, BOSS4_HEIGHT, BOSS4);
	        initHitbox(50,20);
	        this.enemyHealthBarWidth = (int)(50* Game.SCALE);
	        this.enemyHealthBarHeight = (int)(4* Game.SCALE);
	        this.enemyHealthWidth = enemyHealthBarWidth;
	        this.walkSpeed = 0.4f * Game.SCALE;
	        this.setDialogue();
	        initAttackBox();
		
	}
	 private void initAttackBox(){
	        attackBox=new Rectangle2D.Float(x,y,(int)(120*Game.SCALE),(int)(80*Game.SCALE));
	        attackBoxOffsetX = (int)(Game.SCALE*120);
	    }

	    public void update(int[][] lvlData,Player player){
	    	setDialogue();
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
	                    case ATTACK , HIT -> state = IDLE;
	                    case DEAD -> active = false;
	                }

	            }

	        }
	    }
	    
	    public void checkEnmyHit(Rectangle2D.Float attackBox, Player player) {
	        if (attackBox.intersects(player.hitbox)) {
	        	player.setJump(true);
	        	player.changeHealth(-GetEnemyDmg(enemyType));
	            attackChecked = true;
	            
	            
	        }
	        
	    }

	    protected void updateAttackBoxFlip() {
	        if (walkDir == RIGHT)
	            attackBox.x = hitbox.x + hitbox.width ;
	        else
	            attackBox.x = hitbox.x - attackBoxOffsetX ;

	        attackBox.y = hitbox.y - 20;
	    }
	    
	    protected boolean isPlayerCloseAttack(Player player) {
	        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
	        if(walkDir == RIGHT)
	        	return absValue <= attackDistance  * 4;
	        else 
	        	return absValue <= attackDistance  * 3;
	        
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
	                    	count ++;
	                        turnTowardsPlayer(player);
	                        if (isPlayerCloseAttack(player))
	                            newState(ATTACK);
	                    }
	                    move(lvlData);
	                    break;
	                case ATTACK:
	                    if(aniIndex==0)
	                        attackChecked = false;

	                    if(aniIndex== 9 &&!attackChecked)
	                    	checkEnmyHit(attackBox,player);
	                    if(aniIndex ==10)
	                    	player.setJump(false);
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
	    		return 15;
	    	else
	    		return -20;
	    }

	    public void setDialogue() {
	    	dialogue[0] = "Khá lắm khá lắm, cuối cùng ngươi cũng đã đến được đây";
	    	dialogue[1] = "Tôn Ngộ Không, người thật to gan, dám đến quậy phá nơi ở của ta,\n tội ngươi xứng đáng chết";
	    	dialogue[2] = "Hôm nay ta phải dạy cho ngươi một bài học";
	    }

	    public void drawAttackBox(Graphics g,int xLvlOffset){
	        g.setColor(Color.red);
	        g.drawRect((int)(attackBox.x-xLvlOffset),(int)(attackBox.y),(int)attackBox.width,(int)attackBox.height);

	    }
	    public int flipX(){
	        if(walkDir==RIGHT)
	            return width + 10;
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
	    
	    public String getDialogue(int textIndex) {
	    	return dialogue[textIndex];
	    }
}
