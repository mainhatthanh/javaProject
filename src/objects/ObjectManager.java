package objects;

import gameState.Playing;
import levels.Level;
import main.Game;
import utilz.Constants.ObjectsConstants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import entities.Entity;
import entities.Player;

import static utilz.Constants.ObjectsConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Projectiles.*;

public class ObjectManager {

    Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage trap1Img, scrollImg, swordImg, cannonBallImg;
    private BufferedImage[] chestImgs, cannonImgs;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Trap1> trap1;
    private ArrayList<Chest> chests;
    private ArrayList<Scroll> scrolls;
    private ArrayList<Sword> swords;
    private ArrayList<Cannon> cannons;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
        

    }
    
    public void checkTrapTouched(Player p) {
    	for(Trap1 t1 : trap1)
    		if(t1.getHitbox().intersects(p.getHitBox()))
    			p.kill(TRAP1_VALUE);
    		
    }
    
    public void checkObjectTouched(Rectangle2D.Float hitbox) {
		for (Potion p : potions)
			if (p.isActive()) {
				if (hitbox.intersects(p.getHitbox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
		for (Scroll s : scrolls) {
			if (s.isActive()) {
				if (hitbox.intersects(s.getHitbox())) {
					s.setActive(false);
					applyEffectToPlayer(s);
				}
			}
		}
		
	}
    
    
    public void applyEffectToPlayer(GameObjects go) {
		if (go.getObjType() == RED_POTION)
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
		if (go.getObjType() == BLUE_POTION)
			playing.getPlayer().changeStamina(BLUE_POTION_VALUE);
	}

	public void checkObjectHit(Rectangle2D.Float attackbox) {
		for (GameContainer gc : containers)
			if (gc.isActive() && !gc.doAnimation) {
				if (gc.getHitbox().intersects(attackbox)) {
					gc.setAnimation(true);
					int type = (int) (Math.random() * 10);
					if ((type % 2) == 0) type = 1;
					else type =0;
					
					potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
					return;
				}
			}
	}
	
	public void checkChestsOpen(Rectangle2D.Float hitbox) {
		for (Chest c : chests)
			if (c.isActive()){
				if (hitbox.intersects(c.getHitbox())) {
					c.setAnimation(true);
					int type = (int) (Math.random() * 10);
					if ((type % 2) == 0) {
						type = 1;
						potions.add(new Potion((int) (c.getHitbox().x + c.getHitbox().width / 2), (int) (c.getHitbox().y - c.getHitbox().height / 2), type));
					}
					else swords.add(new Sword((int) (c.getHitbox().x + c.getHitbox().width / 2), (int) (c.getHitbox().y - c.getHitbox().height / 2), 7));
				}
			}
		}




    public void loadObjects(Level newLevel){
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        trap1 = new ArrayList<>(newLevel.getTrap1());
        chests = new ArrayList<>(newLevel.getChests());
        scrolls = new ArrayList<>(newLevel.getScrolls());
        swords = new ArrayList<>(newLevel.getSwords());
        cannons = new ArrayList<>(newLevel.getCannons());
        projectiles.clear();
        
    }

    public void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTIONS_ATLAS);
        potionImgs = new BufferedImage[2][7];
        for (int i = 0; i < potionImgs.length; i++) {
            for (int j = 0; j < potionImgs[i].length; j++) {
                potionImgs[i][j] = potionSprite.getSubimage(j * 12, i * 16, 12, 16);
            }
        }
        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];
        for (int i = 0; i < containerImgs.length; i++) {
            for (int j = 0; j < containerImgs[i].length; j++) {
                containerImgs[i][j] = containerSprite.getSubimage(j * 40, i * 30, 40, 30);
            }
        }
        
        cannonImgs = new BufferedImage[7];
		BufferedImage cannonSprite = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);

		for (int i = 0; i < cannonImgs.length; i++)
			cannonImgs[i] = cannonSprite.getSubimage(i * 40, 0, 40, 26);

		cannonBallImg = LoadSave.GetSpriteAtlas(LoadSave.CANNON_BALL);
        
        
        
        BufferedImage chestSprite = LoadSave.GetSpriteAtlas(LoadSave.CHEST_ATLAS);
        chestImgs = new BufferedImage[3];
        for (int i = 0; i < chestImgs.length; i++) {
        	chestImgs[i] = chestSprite.getSubimage(i * 48, 0, 48, 48);
        }
        
        swordImg = LoadSave.GetSpriteAtlas(LoadSave.SWORD_ATLAS);
        
        scrollImg = LoadSave.GetSpriteAtlas(LoadSave.SCROLL_ATLAS);
        
        trap1Img = LoadSave.GetSpriteAtlas(LoadSave.TRAP1_ATLAS);
    }

    public void update(int[][] lvlData, Player player){
        for (Potion p : potions) {
            if (p.active){
                p.update();
            }
        }
        for (GameContainer gc : containers) {
            if (gc.active){
                gc.update();
            }
        }
        
        for (Cannon c : cannons) {
            
                c.update();
            
        }
        
        
        updateCannons(lvlData, player);
        updateProjectiles(lvlData, player);
        
        for (Chest c : chests) {
        	if(c.active) {
        		c.update();
        	}
        }
        for (Scroll s : scrolls) {
        	if(s.active)
        		s.update();
        }
        
        for (Sword sw : swords) { 
        	if(sw.active)
        		sw.update();
        }
        
        
    }
    
    private void updateProjectiles(int[][] lvlData, Player player) {
		for (Projectile p : projectiles)
			if (p.isActive()) {
				p.updatePos();
				if (p.getHitbox().intersects(player.getHitBox())) {
					player.changeHealth(-25);
					p.setActive(false);
				} else if (IsProjectileHittingLevel(p, lvlData))
					p.setActive(false);
			}
	}
    
    private boolean isPlayerInRange(Cannon c, Player player) {
		int absValue = (int) Math.abs(player.getHitBox().x - c.getHitbox().x);
		return absValue <= Game.TILES_SIZE * 5;
	}

	private boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
		if (c.getObjType() == CANNON_LEFT) {
			if (c.getHitbox().x > player.getHitBox().x)
				return true;

		} else if (c.getHitbox().x < player.getHitBox().x)
			return true;
		return false;
	}

	private void updateCannons(int[][] lvlData, Player player) {
		for (Cannon c : cannons) {
			if (!c.doAnimation)
				if (c.getTileY() == player.getTileY())
					if (isPlayerInRange(c, player))
						if (isPlayerInfrontOfCannon(c, player))
							if (CanCannonSeePlayer(lvlData, player.getHitBox(), c.getHitbox(), c.getTileY()))
								c.setAnimation(true);

			c.update();
			if (c.getAniIndex() == 4 && c.getAniTick() == 0)
				shootCannon(c);
		}
	}
	
	private void shootCannon(Cannon c) {
		int dir = 1;
		if (c.getObjType() == CANNON_LEFT)
			dir = -1;

		projectiles.add(new Projectile((int) c.getHitbox().x, (int) c.getHitbox().y, dir));

	}


    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainer(g, xLvlOffset);
        drawTrap1(g, xLvlOffset);
        drawChests(g, xLvlOffset);
        drawScrolls(g, xLvlOffset);
        drawSwords(g, xLvlOffset);
        drawCannons(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }
    
    private void drawProjectiles(Graphics g, int xLvlOffset) {
		for (Projectile p : projectiles)
			if (p.isActive())
				g.drawImage(cannonBallImg, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y), CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);

	}

    
    
    private void drawCannons(Graphics g, int xLvlOffset) {
		for (Cannon c : cannons) {
			int x = (int) (c.getHitbox().x - xLvlOffset);
			int width = CANNON_WIDTH;

			if (c.getObjType() == CANNON_RIGHT) {
				x += width;
				width *= -1;
			}

			g.drawImage(cannonImgs[c.getAniIndex()], x, (int) (c.getHitbox().y), width, CANNON_HEIGHT, null);
			c.drawHitbox(g, xLvlOffset);
		}

	}

    
    

    private void drawSwords(Graphics g, int xLvlOffset) {
    	for (Sword sw : swords) {
    		if (sw.isActive()) {
			g.drawImage(swordImg,
					    (int) (sw.getHitbox().x - xLvlOffset),
					    (int) (sw.getHitbox().y - sw.getyDrawOffset()),
						SWORD_WIDTH,
						SWORD_HEIGHT, null);
			sw.drawHitbox(g, xLvlOffset);
    		}
		
	}
}

	private void drawScrolls(Graphics g, int xLvlOffset) {
    	for (Scroll s : scrolls) {
    		if (s.isActive()) {
			g.drawImage(scrollImg,
					    (int) (s.getHitbox().x - xLvlOffset),
					    (int) (s.getHitbox().y - s.getyDrawOffset()),
						SCROLL_WIDTH,
						SCROLL_HEIGHT, null);
			s.drawHitbox(g, xLvlOffset);
    		}
    	}
		
	}

	private void drawChests(Graphics g, int xLvlOffset) {
    	for (Chest c : chests) {
			g.drawImage(chestImgs[c.getAniIndex()],
					    (int) (c.getHitbox().x - c.getxDrawOffset() - xLvlOffset),
					    (int) (c.getHitbox().y - c.getyDrawOffset()),
						CHEST_WIDTH,
						CHEST_HEIGHT, null);
    	c.drawHitbox(g, xLvlOffset);
    	}
		
	}
		
	


	private void drawTrap1(Graphics g, int xLvlOffset) {
		for (Trap1 t1 : trap1)
			g.drawImage(trap1Img,
					    (int) (t1.getHitbox().x - xLvlOffset),
					    (int) (t1.getHitbox().y - t1.getyDrawOffset()),
						TRAP1_WIDTH,
						TRAP1_HEIGHT, null);
		
	}

	private void drawContainer(Graphics g, int xLvlOffset) {
        for (GameContainer gc : containers){
            if (gc.active){
                int type = 0;
                if (gc.getObjType() == BARREL){
                    type = 1;
                }
                    g.drawImage(containerImgs[type][gc.getAniIndex()],
                            (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset),
                            (int) (gc.getHitbox().y - gc.getyDrawOffset()),
                            CONTAINER_WIDTH,
                            CONTAINER_HEIGHT,
                            null
                            );
            }
        }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion p : potions){
            if (p.active){
                int type = 0;
                if (p.getObjType() == RED_POTION){
                    type = 1;
                }
                g.drawImage(potionImgs[type][p.getAniIndex()],
                        (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                        (int) (p.getHitbox().y - p.getyDrawOffset()),
                        POTION_WIDTH,
                        POTION_HEIGHT,
                        null
                );
            }
        }
    }
    
    public void resetAllObjects() {
    	
    	loadObjects(playing.getLevelManager().getCurrentLevel());
    	
    	for (Chest c : chests) {
    		c.reset();
    	}
    	
    	for (Scroll s : scrolls) {
    		s.reset();
    	}
    	
    	for (Sword sw : swords) {
    		sw.reset();
    	}
    	
    	
    	for (Potion p : potions)
    		p.reset();
    	for (GameContainer gc : containers)
    		gc.reset();
    	for (Cannon c : cannons)
			c.reset();
    }
    
    


}
