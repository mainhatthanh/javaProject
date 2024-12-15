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
    
    protected int enemyHealthBarWidth = (int) (64 * Game.SCALE);
    protected int enemyHealthBarHeight = (int) (1.5 * Game.SCALE);
	protected int enemyHealthWidth = enemyHealthBarWidth;
	
	protected int expUpdate;
	
	protected int dmg ;
	protected int speed;
	protected boolean lowHealth;

    public Enemy(float x, float y, int width, int height, int enemyState) {
        super(x, y, width, height);
        this.enemyType = enemyState;
        this.maxHealth = GetMaxHealth(enemyState);
        this.currentHealth = maxHealth;
        this.dmg = GetEnemyDmg(enemyType);
        this.speed =ANI_SPEED;
    }
    
    protected void initAttackBox(int w, int h, int attackBoxOffsetX) {
        attackBox = new Rectangle2D.Float(x, y, (int) (w * Game.SCALE), (int) (h * Game.SCALE));
        this.attackBoxOffsetX = (int) (Game.SCALE * attackBoxOffsetX);
    }
    
    //Cập nhật vị trí của attackBox
    protected void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }
    
    //Thay đổi attackBox khi quái thay đổi hướng di chuyển
    protected void updateAttackBoxFlip() {
        if (walkDir == RIGHT)
            attackBox.x = hitbox.x + hitbox.width;
        else
            attackBox.x = hitbox.x - attackBoxOffsetX;

        attackBox.y = hitbox.y + 20;
    }
    
    //Thiết lập cơ chế rơi xuống cho quái nếu vị trí ban đầu không ở mặt đất
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
    
    //kiểm tra xem quái có ở mặt đất tại vị trí ban đầu không
    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }
    
    
    

    //Cập nhật ảnh của quái khi hiển thị dialogue
    public void updateAnimaIDLE() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, IDLE)) 
                aniIndex = 0;
        }
    }
    
    //Cập nhật ảnh của quái trong quá trình chơi
    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= speed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                aniIndex = 0;
                switch (state) {
                    case ATTACK, ATTACK2, HIT -> state = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
    }

    //kích hoạt cơ chế di chuyển tự động cho quái 
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
    
    //Thay đổi hướng cho quái
    protected void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    //di chuyển theo nhân vật khi nhân vật trong tầm nhìn thấy của quái
    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    //kiểm tra xem nhân vật có ở trong tầm nhìn thấy của quái không
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitBox().y / Game.TILES_SIZE);
        if (playerTileY == tileY)
            if (isPlayerInRange(player)) {
                if (IsSightClear(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }
        return false;
    }

    //kiểm tra tầm nhìn giữa quái và nhân vật
    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;

    }

    //kiểm tra nhân vật đã vào tầm tấn công của quái chưa
    protected boolean isPlayerCloseAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    //chuyển trạng thái cho quái
    protected void newState(int enemyState) {
        this.state = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    //Cơ chế mất máu của quái
    public void hurt(int amount, Player player) {
    	
    	if(lowHealth)
    		amount = (int)(0.7*amount);
    	expUpdate = 0;
    	currentHealth -= amount;
        
        if (currentHealth <= 0){
        	lowHealth = false;
            newState(DEAD);
            player.changeExp(GetExperience(enemyType));
            expThatChange += GetExperience(enemyType);
            expUpdate = GetExperience(enemyType);
        }
        else
            newState(HIT);
    }

    //Cơ chế mất máu của nhân vật
    public void checkEnmyHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox)) {
        	if(enemyType == TORO || enemyType == BOSS2||enemyType == BOSS3||enemyType == BOSS4||enemyType == BOSSFINAL)
        		if(lowHealth)
        			dmg = (int)(1.2 * dmg);
        	player.changeHealth(-dmg);
            attackChecked = true;
        }
        
    }
    
    //cơ chế cuồng nộ của BOSS khi lượng máu dưới 40% so với ban đầu
    protected void getCrazy(int speedUp, float walkUp) {
    	if(currentHealth <= 0.4 *maxHealth && currentHealth >0) { 		
    		this.lowHealth = true;
        	this.speed = speedUp;
        	this.walkSpeed = walkUp * Game.SCALE;
    	}
    }


    //Cập nhật lại máu của quái
    public void updateHealthBar() {
        if (currentHealth < 0) {
            currentHealth = 0;
        }
        enemyHealthWidth = (int) ((currentHealth / (float) maxHealth) * enemyHealthBarWidth);
    }

    
    //Hiển thị thanh máu cho quái
    public void drawHealthBar(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        
        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 - xLvlOffset),
                (int) (hitbox.y + hitbox.height - hitbox.height - 4 * Game.SCALE), enemyHealthWidth,
                enemyHealthBarHeight);
        g.setColor(Color.white);
        g.fillRect((int) (hitbox.x + hitbox.width / 2 - enemyHealthBarWidth / 2 + enemyHealthWidth - xLvlOffset),
                (int) (hitbox.y + hitbox.height - hitbox.height - 4 * Game.SCALE),
                enemyHealthBarWidth - enemyHealthWidth, enemyHealthBarHeight);

    }
    
    
//    private void updateMove(int[][] lvlData) {
//        if (firstUpdate) {
//            if (!IsEntityOnFloor(hitbox, lvlData))
//                inAir = true;
//            firstUpdate = false;
//
//        }
//        if (inAir) {
//            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
//                hitbox.y += airSpeed;
//                airSpeed += GRAVITY;
//            } else {
//                inAir = false;
//                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
//            }
//        } else {
//            switch (state) {
//                case IDLE:
//                    state = RUNNING;
//                    break;
//                case RUNNING:
//                    float xSpeed = 0;
//                    if (walkDir == LEFT)
//                        xSpeed = -walkSpeed;
//                    else
//                        xSpeed = walkSpeed;
//
//                    if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
//                        if (IsFloor(hitbox, xSpeed, lvlData)) {
//                            hitbox.x += xSpeed;
//                            return;
//                        }
//                    changeWalkDir();
//
//                    break;
//            }
//
//        }
//
//    }


    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        airSpeed = 0;
        
        lowHealth = false;
        speed = ANI_SPEED;
        dmg = GetEnemyDmg(enemyType);
    }

    public int getExpUpdate() {
    	return expUpdate;
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
