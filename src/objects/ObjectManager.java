package objects;

import audio.AudioPlayer;
import gameState.Playing;
import levels.Level;
import main.Game;

import ui.Tutorial;
import utilz.Constants.ObjectsConstants;

import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import entities.Entity;
import entities.Player;

import entities.Player;

import static utilz.Constants.ObjectsConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Projectiles.*;
import static utilz.Constants.Arrows.*;

public class ObjectManager {
	
	public boolean fly ;

    Playing playing;
    
    private BufferedImage trap1Img, scrollImg, swordImg, cannonBallImg, arrowImg, peachImg, bananaImg;
    private BufferedImage[] chestImgs, cannonImgs, arrowTrapImgs, trap2Imgs, flagImgs, exploImgs;
    private BufferedImage[][] potionImgs, containerImgs, flyImgs;

    
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Flag> flags;
    private ArrayList<Chest> chests;
    private ArrayList<Scroll> scrolls;
	private ArrayList<Peach> peaches;
	private ArrayList<Explosion> explos;
    private ArrayList<Banana> bananas;
    private ArrayList<Trap1> trap1;
    private ArrayList<Sword> swords;
    private ArrayList<Cannon> cannons;
    private ArrayList<ArrowTrap> arrowTraps;
    private ArrayList<Trap2> trap2;

    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Arrow> arrows = new ArrayList<>();
    
    private ArrayList<FlyWukong> flyWukong ;
	private Tutorial tutorial;


    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }
    
    public void checkTrapTouched(Player p) {
    	for(Trap1 t1 : trap1)
    		if(t1.getHitbox().intersects(p.getHitBox()))
    			p.kill(TRAP1_VALUE);
    		
    }
	/*public void checkScrollTouched(Player player) {
		for(Scroll sc : scrolls)
			if(sc.isActive())
			if(sc.getHitbox().intersects(player.getHitBox())) {
				System.out.println("cham");
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.PAPER);
				playing.getPlot().setPlot(true);
				sc.setActive(false);

			}

	}*/

	/*public void checkSwordtouched(Player player) {
		for(Sword sw : swords)
			if(sw.isActive())
				if(sw.getHitbox().intersects(player.getHitBox())) {
					System.out.println("cham");
					player.setGiantUp(6);
					sw.setActive(false);
				}

	}*/


	public ArrayList<Scroll> getScrolls() {
		return scrolls;
	}


    
    public void checkObjectTouched(Rectangle2D.Float hitbox) {
    	boolean touchObj = false;
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
					playing.getGame().getAudioPlayer().playEffect(AudioPlayer.PAPER);
					playing.getPlot().setPlot(true);
					s.setActive(false);
					//applyEffectToPlayer(s);
				}
			}
		}
		for (Flag f : flags) {
			if (f.isActive()) {
				if (hitbox.intersects(f.getHitbox())) {
					if (playing.TouchFlag() ==  false) {
						playing.setTouchFlag(true);
						playing.setCountRev(3);
					}
				}
			}
		}
		for (Peach p : peaches)
			if (p.isActive()) {
				if (hitbox.intersects(p.getHitbox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
		for (Banana b : bananas)
			if (b.isActive()) {
				if (hitbox.intersects(b.getHitbox())) {
					b.setActive(false);
					applyEffectToPlayer(b);
				}
			}
		for (Sword s : swords)
			if (s.isActive()) {
				if (hitbox.intersects(s.getHitbox())) {
					s.setActive(false);
					applyEffectToPlayer(s);
				}
			}
		for (Explosion e : explos)
			if (e.isActive()) {
				if (hitbox.intersects(e.getHitbox())) {
					
					applyEffectToPlayer(e);
					if (e.getAniIndex() == 11) e.setActive(false);
			}
		
	}
    }
    public void applyEffectToPlayer(GameObjects go) {
		if (go.getObjType() == RED_POTION) 
			playing.getPlayer().changeHealth(RED_POTION_VALUE);


		if (go.getObjType() == BLUE_POTION) {
			playing.getPlayer().changeStamina(BLUE_POTION_VALUE);
			playing.getGame().getAudioPlayer().playEffect(AudioPlayer.HEAL_MANA);
		}

		if (go.getObjType() == EXPLOSION) {
			playing.getPlayer().kill(EXPLOSION_VALUE);
			playing.getGame().getAudioPlayer().playEffect(AudioPlayer.EXPLOSION);
		}
		if (go.getObjType() == PEACH)
			playing.getPlayer().changeExp(PEACH_VALUE);
			playing.getGame().getAudioPlayer().playEffect(AudioPlayer.HEAL_MANA);
		if (go.getObjType() == SWORD) {
			playing.getGame().getAudioPlayer().playEffect(AudioPlayer.SWORD);
			playing.getPlayer().setPlayerDamage(playing.getPlayer().getPlayerDamage() + SWORD_VALUE);
		}

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
					int type = (int) (Math.random() * 20);
					if (type <= 5)
						explos.add(new Explosion((int) (c.getHitbox().x - 12*Game.SCALE ), (int) (c.getHitbox().y - 16*Game.SCALE  ), 15));
					if ((type > 5) && (type <=13))
						peaches.add(new Peach((int) (c.getHitbox().x + 10*Game.SCALE ), (int) (c.getHitbox().y  ), 13));
					if (type > 13)
						swords.add(new Sword((int) (c.getHitbox().x + 10*Game.SCALE ), (int) (c.getHitbox().y  ), 7));
			}
		}
	}




    public void loadObjects(Level newLevel){

		flyWukong = new  ArrayList<>(newLevel.getFlyWukong());
		
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        chests = new ArrayList<>(newLevel.getChests());
        scrolls = new ArrayList<>(newLevel.getScrolls());
        flags = new ArrayList<>(newLevel.getFlag());
        
        trap1 = new ArrayList<>(newLevel.getTrap1());
        swords = new ArrayList<>(newLevel.getSwords());
        cannons = new ArrayList<>(newLevel.getCannons());
        arrowTraps = new ArrayList<>(newLevel.getArrowTraps());
        trap2 = new ArrayList<>(newLevel.getTrap2());
		peaches = new ArrayList<>(newLevel.getPeaches());
		explos = new ArrayList<>(newLevel.getExplos());
		bananas = new ArrayList<>(newLevel.getBananas());

        projectiles.clear();
        arrows.clear();

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
        

        BufferedImage flySprite = LoadSave.GetSpriteAtlas(LoadSave.WUKONG_FLY);
        flyImgs = new BufferedImage[2][4];
        for (int i = 0; i < flyImgs.length; i++) 
            for (int j = 0; j < 4; j++) 
            	flyImgs[i][j] = flySprite.getSubimage(j * 128, i * 40, 128, 40);
        

        cannonImgs = new BufferedImage[7];
		BufferedImage cannonSprite = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);

		for (int i = 0; i < cannonImgs.length; i++)
			cannonImgs[i] = cannonSprite.getSubimage(i * 40, 0, 40, 26);
		
		exploImgs = new BufferedImage[12];
		BufferedImage exploSprite = LoadSave.GetSpriteAtlas(LoadSave.EXPLO_ATLAS);

		for (int i = 0; i < exploImgs.length; i++) {
			if (i == 0)
				exploImgs[i] = exploSprite.getSubimage(i * 40, 0, 40, 26);
			else exploImgs[i] = exploSprite.getSubimage((i + 6) * 48, 0, 48, 48);
		}
		flagImgs = new BufferedImage[5];
		BufferedImage flagSprite = LoadSave.GetSpriteAtlas(LoadSave.FLAG_ATLAS);

		for (int i = 0; i < flagImgs.length; i++)
			flagImgs[i] = flagSprite.getSubimage(i * 60, 0, 60, 56);
		
		arrowTrapImgs = new BufferedImage[15];
		BufferedImage arrowTrapSprite = LoadSave.GetSpriteAtlas(LoadSave.ARROWTRAP_ATLAS);

		for (int i = 0; i < arrowTrapImgs.length; i++)
			if (i < 8)
			arrowTrapImgs[i] = arrowTrapSprite.getSubimage(i * 95, 0, 95, 30);
			else arrowTrapImgs[i] = arrowTrapSprite.getSubimage((i+2) * 95, 0, 95, 30);

		cannonBallImg = LoadSave.GetSpriteAtlas(LoadSave.CANNON_BALL);
		arrowImg = LoadSave.GetSpriteAtlas(LoadSave.ARROW_ATLAS);
        
        
        
        BufferedImage chestSprite = LoadSave.GetSpriteAtlas(LoadSave.CHEST_ATLAS);
        chestImgs = new BufferedImage[3];
        for (int i = 0; i < chestImgs.length; i++) {
        	chestImgs[i] = chestSprite.getSubimage(i * 48, 0, 48, 48);
        }
        
        swordImg = LoadSave.GetSpriteAtlas(LoadSave.SWORD_ATLAS);

		peachImg = LoadSave.GetSpriteAtlas(LoadSave.PEACH_ATLAS);
		bananaImg = LoadSave.GetSpriteAtlas(LoadSave.BANANA_ATLAS);
        
        scrollImg = LoadSave.GetSpriteAtlas(LoadSave.SCROLL_ATLAS);
        
        trap1Img = LoadSave.GetSpriteAtlas(LoadSave.TRAP1_ATLAS);
        BufferedImage trap2Sprite = LoadSave.GetSpriteAtlas(LoadSave.TRAP2_ATLAS);
        trap2Imgs = new BufferedImage[10];
        for (int i = 0; i < 10 ; i++) {
        	trap2Imgs[i] = trap2Sprite;
        }

    }

    public void update(int[][] lvlData, Player player){
        for (Potion p : potions) {
            if (p.active){
                p.update();
            }
        }
        
        for (Explosion e : explos) {
        	if (e.active) {
        		e.update();
        	}
        }
        for (Banana b : bananas) {
        	if(b.active) {
        		b.update();
        	}
        }

		for (Peach p : peaches) {
			if (p.active) {
				p.update();
			}
		}
        for (GameContainer gc : containers) {
            if (gc.active){
                gc.update();
            }
        }
        updateArrowTrap();
        updateArrows(lvlData, player);
        updateTrap2(lvlData, player);
        
        updateCannons(lvlData, player);
        updateProjectiles(lvlData, player);

		//checkSwordtouched(player);

        
        for (Flag f : flags) {
        	if (f.active) {
        		f.update();
        	}
        }
        
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
    
	public void updateFlyWukong(Player player) {
		for(FlyWukong fw: flyWukong) 
	    	if(fw.isActive()) {
	    		fly = fw.getCheck();
	    		if(!fly)
	    			fw.checkTouched(player);
	    		fw.update(fw.getRowIndex());
	//    		if(fw.getCheck()) {
	//    			fw.setRowIndex(0);
	//    			fw.update(fw.getRowIndex());
	//    		}
	    		//System.out.println(fw.getRowIndex());
	    	}
	
	}
	
    private void updateTrap2(int[][] lvlData, Player player) {
    	for (Trap2 t2 : trap2) {
			if (!t2.doAnimation)
				if (t2.getTileY() == player.getTileY())
					if (isPlayerInRange(t2, player))
						if (isPlayerInfrontOfCannon(t2, player))
							if (CanCannonSeePlayer(lvlData, player.getHitBox(), t2.getHitbox(), t2.getTileY()))
								t2.setAnimation(true);
			t2.update();
			if (t2.getAniTick() == 0 && t2.getAniIndex() == 1)
				shootArrow2(t2);

		}
		
	}

	private void shootArrow2(Trap2 t2) {
		int dir = 1;
		if (t2.getObjType() == TRAP2_LEFT)
			dir = -1;

		arrows.add(new Arrow((int) t2.getHitbox().x, (int) t2.getHitbox().y, dir));
		
	}

	private void updateArrows(int[][] lvlData, Player player) {
    	for (Arrow a : arrows)
			if (a.isActive()) {
				a.updatePos();
				if (a.getHitbox().intersects(player.getHitBox())) {
					player.changeHealth(-25);
					a.setActive(false);
				} else if (IsArrowHittingLevel(a, lvlData))
					a.setActive(false);
			}
		
	}

	private void updateArrowTrap() {
		for (ArrowTrap at : arrowTraps) {
			if (at.active) {
				at.update();
				if (at.getAniIndex() == 8 && at.getAniTick() == 0)
					shootArrow(at);
			}
		}
		
	}

	private void shootArrow(ArrowTrap at) {
		int dir = -1;

		arrows.add(new Arrow((int) at.getHitbox().x, (int) at.getHitbox().y, dir));
		
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
    
    private boolean isPlayerInRange(GameObjects c, Player player) {
		int absValue = (int) Math.abs(player.getHitBox().x - c.getHitbox().x);
		return absValue <= Game.TILES_SIZE * 5;
	}

	private boolean isPlayerInfrontOfCannon(GameObjects c, Player player) {
		if (c.getObjType() == CANNON_LEFT || c.getObjType() == TRAP2_LEFT) {
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

    public void drawFlyWukong(Graphics g, int xLvlOffset) {
    	for(FlyWukong fwk : flyWukong)
    		if(fwk.active) {
    			g.drawImage(flyImgs[fwk.getRowIndex()][fwk.getAniIndex()], 
    					(int)(fwk.getHitbox().x - xLvlOffset), 
    					(int)(fwk.getHitbox().y -20), 
    					(int)(128  * Game.SCALE  * 1.5),(int) (40 * Game.SCALE * 1.5 ), null);
    			//System.out.println();
    			//fwk.drawHitbox(g, xLvlOffset);
    		}
    }
    
    public float getXPos() {
    	for(FlyWukong fw: flyWukong) 
        	if(fw.isActive()) {
        		return fw.getHitbox().x ;
        	}
    	return 0;
    }
    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainer(g, xLvlOffset);
        drawTrap1(g, xLvlOffset);
        drawChests(g, xLvlOffset);
        drawScrolls(g, xLvlOffset);
        drawSwords(g, xLvlOffset);
        drawCannons(g, xLvlOffset);
        drawArrowTraps(g, xLvlOffset);
        drawArrow(g, xLvlOffset);
        drawTrap2(g, xLvlOffset);
        drawFlag(g, xLvlOffset);
		drawPeach(g, xLvlOffset);
		drawBanana(g, xLvlOffset);
		drawExplo(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }

	private void drawBanana(Graphics g, int xLvlOffset) {
		for (Banana b : bananas) {
			if (b.isActive()) {
				g.drawImage(bananaImg,
						(int) (b.getHitbox().x - xLvlOffset),
						(int) (b.getHitbox().y - b.getyDrawOffset()),
						BANANA_WIDTH,
						BANANA_HEIGHT, null);
			}
		
	}
	}
	private void drawPeach(Graphics g, int xLvlOffset) {
		for (Peach p : peaches) {
			if (p.isActive()) {
				g.drawImage(peachImg,
						(int) (p.getHitbox().x - xLvlOffset),
						(int) (p.getHitbox().y - p.getyDrawOffset()),
						PEACH_WIDTH,
						PEACH_HEIGHT, null);
			}

		}
	}

	private void drawFlag(Graphics g, int xLvlOffset) {
    	for (Flag f : flags) {
    		if (f.isActive()) {
			g.drawImage(flagImgs[f.getAniIndex()],
					    (int) (f.getHitbox().x - xLvlOffset),
					    (int) (f.getHitbox().y - f.getyDrawOffset() + 5 *Game.SCALE),
						FLAG_WIDTH,
						FLAG_HEIGHT, null);
    		}
		
    	}
    }

	private void drawTrap2(Graphics g, int xLvlOffset) {
    	for (Trap2 t2 : trap2) {
			int x = (int) (t2.getHitbox().x - xLvlOffset - (int) (15*Game.SCALE));
			int width = TRAP2_WIDTH;

			if (t2.getObjType() == TRAP2_RIGHT) {
				x += width;
				width *= -1;
			}

			g.drawImage(trap2Imgs[t2.getAniIndex()], x, (int) (t2.getHitbox().y), width, TRAP2_HEIGHT, null);
		}
	}

	private void drawArrow(Graphics g, int xLvlOffset) {
    	for (Arrow a : arrows)
			if (a.isActive())
				g.drawImage(arrowImg, (int) (a.getHitbox().x - xLvlOffset), (int) (a.getHitbox().y), ARROW_WIDTH, ARROW_HEIGHT, null);

		
	}

	private void drawArrowTraps(Graphics g, int xLvlOffset) {
    	for (ArrowTrap at : arrowTraps) {
			int x = (int) (at.getHitbox().x - at.xDrawOffset - xLvlOffset - (20*Game.SCALE));
			int width = ARROW_TRAP_WIDTH;

			g.drawImage(arrowTrapImgs[at.getAniIndex()], x, (int) (at.getHitbox().y - at.yDrawOffset + (int) (5*Game.SCALE)), width, ARROW_TRAP_HEIGHT, null);
		}
		
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
		}

	}
    
    private void drawExplo(Graphics g, int xLvlOffset) {
		for (Explosion e : explos) {
			

			g.drawImage(exploImgs[e.getAniIndex()], (int) (e.getHitbox().x - xLvlOffset), (int) (e.getHitbox().y), EXPLOSION_WIDTH, EXPLOSION_HEIGHT, null);
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
    		}
    	}
		
	}

	private void drawChests(Graphics g, int xLvlOffset) {
    	for (Chest c : chests) {
			g.drawImage(chestImgs[c.getAniIndex()],
					    (int) (c.getHitbox().x - c.getxDrawOffset() - xLvlOffset),
					    (int) (c.getHitbox().y - c.getyDrawOffset() + (8*Game.SCALE)),
						CHEST_WIDTH,
						CHEST_HEIGHT, null);
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
  
    	
		for(FlyWukong fwk : flyWukong)
			fwk.reset();

		for (Peach p : peaches)
			p.reset();
		for (Banana b : bananas)
			b.reset();

    	for (Flag f : flags) {
    		f.reset();
    	}
    	
    	for (Chest c : chests) {
    		c.reset();
    	}
    	
    	for (Scroll s : scrolls) {
    		s.reset();
    	}
    	for (Explosion e : explos) {
    		e.reset();
    	}
    	
    	for (Sword sw : swords) {
    		sw.reset();
    	}
    	
    	for (Trap2 t2 : trap2) {
    		t2.reset();
    	}
    	
    	for (Potion p : potions)
    		p.reset();
    	for (GameContainer gc : containers)
    		gc.reset();
    	for (Cannon c : cannons)
			c.reset();
    	for (ArrowTrap at : arrowTraps)
    		at.reset();
    }
	public boolean getFly() {
		return fly;
	}
    



}
