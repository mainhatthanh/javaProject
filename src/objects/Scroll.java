package objects;

import main.Game;

public class Scroll extends GameObjects {
	public Scroll(int x, int y, int objType) {
		super(x, y, objType);
		
		initHitbox(20, 20);
		xDrawOffset = 0;
		yDrawOffset = (int) (0*Game.SCALE);
		this.hitbox.y = yDrawOffset + (int) (16*Game.SCALE);
	}
	public void update() {
		updateAnimationTick();
	}

}
