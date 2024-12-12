package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.Curve.*;

public class Stick {
    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active = true;


    public Stick(int x, int y, int dir) {
        int xOffset = (int) (-40 * Game.SCALE);
        int yOffset = (int) (16 * Game.SCALE);

        if (dir == 1)
            xOffset = (int) (18 * Game.SCALE);

        hitbox = new Rectangle2D.Float(x+xOffset, y + yOffset, STICK_WIDTH, STICK_HEIGHT);
        this.dir = dir;
    }

    public void updatePos() {
        hitbox.x += dir * SPEED;
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
    public void drawStickHitbox(Graphics g,int xLvlOffset){
        g.setColor(Color.WHITE);
        g.drawRect((int) (this.getHitbox().x - xLvlOffset), (int) (this.getHitbox().y), STICK_WIDTH, STICK_HEIGHT);

    }
}
