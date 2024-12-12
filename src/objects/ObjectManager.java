package objects;

import gameState.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;

import static utilz.Constants.ObjectsConstants.*;

public class ObjectManager {
	
	public boolean fly ;

    Playing playing;
    private BufferedImage[][] potionImgs, containerImgs, flyImgs;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<FlyWukong> flyWukong ;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();

    }


    public void loadObjects(Level newLevel){
        potions =  newLevel.getPotions();
        containers = new ArrayList<>(newLevel.getContainers());
        flyWukong = new  ArrayList<>(newLevel.getFlyWukong());
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
        
    }
    
    public void updateFlyWukong(Player player) {
    	for(FlyWukong fw: flyWukong) 
        	if(fw.isActive()) {
        		fly = fw.getCheck();
        		if(!fly)
        			fw.checkTouched(player);
        		
        		fw.update(fw.getRowIndex());
//        		if(fw.getCheck()) {
//        			fw.setRowIndex(0);
//        			fw.update(fw.getRowIndex());
//        		}
        		//System.out.println(fw.getRowIndex());
        	}

    }
    

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainer(g, xLvlOffset);
        
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
                            CONTAINER_HEIGHT, null
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
                        POTION_HEIGHT, null
                );
            }
        }
    }
    
    public void resetAllObjects() {

		
		loadObjects(playing.getLevelManager().getCurrentLevel());
		for(Potion p: potions)
			p.reset();
		
		for(GameContainer gc: containers)
			gc.reset();
		
		for(FlyWukong fwk : flyWukong)
			fwk.reset();

	}

	public boolean getFly() {
		return fly;
	}


}
