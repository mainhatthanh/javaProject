package entities;

import static utilz.Constants.Directions.*;


import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.EnemyConstants.MINOTAUR;
import static utilz.Constants.EnemyConstants.MINOTAUR_HEIGHT;
import static utilz.Constants.EnemyConstants.MINOTAUR_WIDTH;
import static utilz.Constants.EnemyConstants.HIT;
import static utilz.Constants.EnemyConstants.IDLE;
import static utilz.Constants.EnemyConstants.RUNNING;

import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.IsFloor;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Toro extends Enemy {
	
	private int attackBoxOffsetX;

	public Toro(float x, float y) {
		super(x , y, TORO_WIDTH, TORO_HEIGHT, TORO);
		initHitbox(20, 20);

		initAttackBox();
	}
	
	//khai bao attackbox
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x , y, (int)(Game.SCALE * 50),(int)(Game.SCALE * 30));
		attackBoxOffsetX = (int)(Game.SCALE *10);
		
	}

	public void update(int[][] lvlData, Player player) {
		updateBehaviour(lvlData,player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	protected void updateAttackBox() {
        attackBox.x= hitbox.x-attackBoxOffsetX;
        attackBox.y=hitbox.y;
    }

	private void updateBehaviour(int[][] lvlData, Player player) {
		if(firstUpdate) 
			firstUpdateCheck(lvlData);
		
		if(inAir) {
			updateInAir(lvlData);
		}else {
			switch(state) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				if(canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
				if(isPlayerCloseAttack(player))
					newState(ATTACK);
				}
				move(lvlData);
				break;
			case ATTACK:
				if(aniIndex ==0)
					attackChecked = false;
				
				if(aniIndex == 3  && !attackChecked)
					checkEnmyHit(attackBox, player);
				break;
			case HIT:
				break;
			}
		}
	}
	
	
	public int flipX() {
		if(walkDir == RIGHT)
			return 0;
		else
			return width;
	}
	
	public int flipY() {
		if(walkDir == RIGHT)
			return 1;
		else
			return -1;
		
	}
}
