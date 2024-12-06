package objects;

import gameState.Playing;
import levels.Level;
import utilz.Constants.ObjectsConstants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;

import static utilz.Constants.ObjectsConstants.*;

public class ObjectManager {

    Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage trap1Img;
    private BufferedImage[] chestImgs;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Trap1> trap1;
    private ArrayList<Chest> chests;

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
	}
    
    public void applyEffectToPlayer(Potion p) {
		if (p.getObjType() == RED_POTION)
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
		else
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



    public void loadObjects(Level newLevel){
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        trap1 = new ArrayList<>(newLevel.getTrap1());
        chests = new ArrayList<>(newLevel.getChests());
        
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
        
        BufferedImage chestSprite = LoadSave.GetSpriteAtlas(LoadSave.CHEST_ATLAS);
        chestImgs = new BufferedImage[3];
        for (int i = 0; i < chestImgs.length; i++) {
        	chestImgs[i] = chestSprite.getSubimage(i * 48, 0, 48, 48);
        }
        
        trap1Img = LoadSave.GetSpriteAtlas(LoadSave.TRAP1_ATLAS);
    }

    public void update(){
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
        for (Chest c : chests) {
        	if(c.active) {
        		c.update();
        	}
        }
        
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainer(g, xLvlOffset);
        drawTrap1(g, xLvlOffset);
        drawChests(g, xLvlOffset);
    }

    private void drawChests(Graphics g, int xLvlOffset) {
    	for (Chest c : chests)
			g.drawImage(chestImgs[c.getAniIndex()],
					    (int) (c.getHitbox().x - xLvlOffset),
					    (int) (c.getHitbox().y - c.getyDrawOffset()),
						CHEST_WIDTH,
						CHEST_HEIGHT, null);
		
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
    	
    	for (Potion p : potions)
    		p.reset();
    	for (GameContainer gc : containers)
    		gc.reset();
    }
    
    


}
