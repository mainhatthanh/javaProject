package objects;

import main.Game;

public class Potion extends GameObjects {

    public Potion(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(7, 14);
        xDrawOffset = (int) (5* Game.SCALE);
        yDrawOffset = (int) (12* Game.SCALE);
        hitbox.y += yDrawOffset + (int) (Game.SCALE * 4) ;
    }
    
   
    
    public void update() {
        updateAnimationTick();
    }


}
