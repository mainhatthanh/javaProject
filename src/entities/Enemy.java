package entities;

import main.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.EnemyConstants.*;

import static utilz.Constants.GRAVITY;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;
import static entities.Player.expThatChange;

public abstract class Enemy extends Entity {
    protected int enemyType;
    protected boolean firstUpdate = true;
    protected float walkSpeed = 0.35f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected boolean active = true;
    protected boolean attackChecked;
    protected int attackBoxOffsetX;

    private int enemyHealthBarWidth = (int) (64 * Game.SCALE);
    private int enemyHealthBarHeight = (int) (2 * Game.SCALE);

    private int enemyHealthWidth = enemyHealthBarWidth;

    public Enemy(float x, float y, int width, int height, int enemyState) {
        super(x, y, width, height);
        this.enemyType = enemyState;
        this.maxHealth = GetMaxHealth(enemyState);
        this.currentHealth = maxHealth;
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
        walkSpeed = Game.SCALE * 0.35f;
    }

    protected void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    protected void updateAttackBoxFlip() {
        if (walkDir == RIGHT)
            attackBox.x = hitbox.x + hitbox.width;
        else
            attackBox.x = hitbox.x - attackBoxOffsetX;

        attackBox.y = hitbox.y;
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void initAttackBox(int w, int h, int attackBoxOffsetX) {
        attackBox = new Rectangle2D.Float(x, y, (int) (w * Game.SCALE), (int) (h * Game.SCALE));
        this.attackBoxOffsetX = (int) (Game.SCALE * attackBoxOffsetX);
    }

    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = 0;
        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        changeWalkDir();
    }


    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitBox().y / Game.TILES_SIZE);
        if (playerTileY == tileY)
            if (isPlayerInRange(player)) {
                if (IsSightClear(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;

    }

    protected boolean isPlayerCloseAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    protected void newState(int enemyState) {
        this.state = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    public void hurt(int amount, Player player) {
        currentHealth -= amount;
        if (currentHealth <= 0){
            newState(DEAD);
            player.changeExp(GetExperience(enemyType));
            expThatChange += GetExperience(enemyType);
        }
        else
            newState(HIT);
    }

    protected void checkEnmyHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox)) {
        }
        player.changeHealth(-GetEnemyDmg(enemyType));
        attackChecked = true;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                aniIndex = 0;

                switch (state) {
                    case ATTACK, HIT -> state = IDLE;
                    case DEAD -> active = false;
                }

            }

        }
    }

    public void update(int[][] lvlData) {
        updateMove(lvlData);
        updateAnimationTick();

        updateHealthBar();

    }

    public void updateHealthBar() {
        if (currentHealth < 0) {
            currentHealth = 0;
        }
        enemyHealthWidth = (int) ((currentHealth / (float) maxHealth) * enemyHealthBarWidth);
    }

    public void drawHealthBar(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        
        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 - xLvlOffset),
                (int) (hitbox.y + hitbox.height - attackBox.height - 4 * Game.SCALE), enemyHealthWidth,
                enemyHealthBarHeight);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 + enemyHealthWidth - xLvlOffset),
                (int) (hitbox.y + hitbox.height - attackBox.height - 4 * Game.SCALE),
                enemyHealthBarWidth - enemyHealthWidth, enemyHealthBarHeight);
    }

    private void updateMove(int[][] lvlData) {
        if (firstUpdate) {
            if (!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;
            firstUpdate = false;

        }
        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
            } else {
                inAir = false;
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            }
        } else {
            switch (state) {
                case IDLE:
                    state = RUNNING;
                    break;
                case RUNNING:
                    float xSpeed = 0;
                    if (walkDir == LEFT)
                        xSpeed = -walkSpeed;
                    else
                        xSpeed = walkSpeed;

                    if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
                        if (IsFloor(hitbox, xSpeed, lvlData)) {
                            hitbox.x += xSpeed;
                            return;
                        }
                    changeWalkDir();

                    break;
            }

        }

    }

    protected void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;

    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        airSpeed = 0;
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }
    public int getAniTick(){
        return this.aniTick;
    }

}
