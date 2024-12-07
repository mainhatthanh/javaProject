package entities;

import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.ATTACK;
import static utilz.Constants.EnemyConstants.MONSTER_EYE1;
import static utilz.Constants.EnemyConstants.MONEYE1_HEIGHT;
import static utilz.Constants.EnemyConstants.MONEYE1_WIDTH;
import static utilz.Constants.EnemyConstants.HIT;
import static utilz.Constants.EnemyConstants.IDLE;
import static utilz.Constants.EnemyConstants.RUNNING;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Monster_Eye1 extends Enemy {

	private int attackBoxOffsetX;

	public Monster_Eye1(float x, float y) {
		super(x, y, MONEYE1_WIDTH, MONEYE1_HEIGHT, MONSTER_EYE1);
		initHitbox(17,25);
		initAttackBox();
	}
	private void initAttackBox(){
		attackBox=new Rectangle2D.Float(x,y,(int)(67*Game.SCALE),(int)(25*Game.SCALE));
		attackBoxOffsetX = (int)(Game.SCALE*30);
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
					if(aniIndex==10&&!attackChecked)
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
			return 0;
		else
			return  width;
	}
	public int flipY(){
		if(walkDir==RIGHT)
			return 1;
		else{
			return -1;
		}
	}


}


/*	public void update(){
		if(doAnimation)
			updateAnimationTick();
	}*/

	/*public int getTileY(){
		return tileY;
	}

	public void setAnimation(boolean doAnimation){
		this.doAnimation=doAnimation;
	}

	public void reset(){
		aniIndex = 0;
		aniTick = 0;
		active = true;


			doAnimation = true;

	}*/
