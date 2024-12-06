package objects;

import main.Game;

public class Chest extends GameObjects {
	public Chest(int x, int y, int objType) {
		super(x, y, objType);
		initHitbox(32, 32);
		xDrawOffset = 0;
		yDrawOffset = (int) (32*Game.SCALE);
		hitbox.y += yDrawOffset + (int) (Game.SCALE * 4) + 4;
        
	}
	
	public void update() {
        updateAnimationTick();
    }

	

}
