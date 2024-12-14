package objects;

import main.Game;

public class Peach extends GameObjects {
	public Peach(int x, int y, int objType) {
		super(x, y, objType);
		initHitbox(15, 15);
		xDrawOffset = 0;
		yDrawOffset =(int) (5* Game.SCALE);
	}

    public void update() {
            updateAnimationTick();

	}

}
