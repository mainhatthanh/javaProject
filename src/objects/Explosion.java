package objects;

import main.Game;

public class Explosion extends GameObjects {
	public Explosion(int x, int y, int objType) {
		super(x, y, objType);
		initHitbox(50, 50);
		xDrawOffset = 0;
		yDrawOffset = (int) (5*Game.SCALE);
		hitbox.y += yDrawOffset;
	}
	
	public void update() {
		if(1 == 1) {
			updateAnimationTick();
		}
	}

}
