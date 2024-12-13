package objects;

import static utilz.Constants.ANI_SPEED;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import entities.Player;
import gameState.Playing;
import main.Game;

public class FlyWukong extends GameObjects{
	protected boolean doAnimation, active = true;
	private boolean check;
	private int rowIndex =1;
	
	public FlyWukong(int x, int y, int objType) {
		super(x, y, objType);
		initHitbox(150, 40);
	}

	public void update(int n) {
		updateAnimationTick();
		updatePos(n);
	}
	
	private void updatePos(int n) {
		if(n ==0) {
			if (hitbox.y > Game.GAME_HEIGHT/2 -150 )
					hitbox.y -= 0.9f * Game.SCALE;
			if (hitbox.y <= Game.GAME_HEIGHT/2 -150)
				hitbox.x += 1.0f*Game.SCALE;
			//hitbox.x += 1.0f*Game.SCALE;
			
			
		}
		
	}
	
	public void checkTouched(Player player) {
		if (hitbox.x <= player.getHitbox().x && player.getHitbox().x <= hitbox.x+ hitbox.width) {
			rowIndex = 0;
			check = true;
		}
		
		else {
			rowIndex = 1;
			check = false;
		}
	}

	protected void updateAnimationTick(){
        aniTick++;
        if(aniTick>= 50){
            aniTick =0;
            aniIndex++;
            if(aniIndex >= 3)
                aniIndex = 0;

        }
    }
	
	public void reset(){
    	hitbox.x = x;
    	hitbox.y = y;
        aniTick = 0;
        aniIndex = 0;
        active = true;
        
        rowIndex = 1;
        check = false;

    }
	
	public boolean getCheck() {
		return check;
	}
	
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
}
