package levels;

import entities.Boss1;
import entities.Boss2;
import entities.Boss3;
import entities.Boss4;
import entities.Boss5;
import entities.BossFinal;
import entities.Crabby;
import entities.Minotaur;
import entities.Monster2;
import entities.Monster_Eye1;
import entities.Shark;
import entities.Spider;
import entities.Toro;
import main.Game;
import objects.FlyWukong;
import objects.GameContainer;
import objects.Potion;
import utilz.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.*;

public class Level {
    private BufferedImage img;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private int[][] lvlData;
    
    private ArrayList<GameContainer> containers;
    private ArrayList<Potion> potions;
    private ArrayList<FlyWukong> flyWukong ;
    
    private ArrayList<Crabby> crabs;
    private ArrayList<Shark> sharks;
    private ArrayList<Minotaur> minotaurs;
    private ArrayList<Monster_Eye1> monEye1;
    private ArrayList<Monster2> monster2;
    private ArrayList<Spider> spiders;
    private ArrayList<Boss1> boss1;
    private ArrayList<Boss5> boss5;
    
    private ArrayList<Toro> toros;
    private ArrayList<Boss2> boss2;
    private ArrayList<Boss3> boss3;
    private ArrayList<Boss4> boss4;
    private ArrayList<BossFinal> bossFinal;
   
    
    private Point playerSpawn;
    private Point playerMeet;

    
 public Level(BufferedImage img){
    this.img=img;
     createLevelData();
     createEnemies();
     createContainers();
     createPotions();   
     createFlyWukong();
     
     
     calcLvlOffsets();
     calcPlayerSpawn();
     calcPlayerMeet();
 }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }
    
    private void calcPlayerMeet() {
        playerMeet = GetPlayerMeet(img);
    }

    private void createFlyWukong() {
    	flyWukong = HelpMethods.GetFlyWukong(img);
    }
    
    private void createContainers() {
        containers = HelpMethods.GetContainers(img);
    }

    private void createPotions() {
        potions = HelpMethods.GetPotions(img);
    }

    private void createEnemies() {
     crabs = GetCrabs(img);
     sharks=GetSharks(img);
     minotaurs=GetMinotaurs(img);
     monEye1 = GetMonEye1(img);
     monster2 = GetMonster2(img);
     spiders = GetSpider(img);
     boss1 = GetBoss1(img);
     boss5 = GetBoss5(img);
     
     toros = GetToros(img);
     boss2 = GetBoss2(img);
     boss3 = GetBoss3(img);
     boss4 = GetBoss4(img);
     bossFinal = GetBossFinal(img);
     

    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];

    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }
    
    public ArrayList<FlyWukong> getFlyWukong() {
  		return flyWukong;
  	}

    
    public ArrayList<Shark> getSharks() {
        return sharks;
    }

    public ArrayList<Minotaur> getMinotaurs() {
        return minotaurs;
    }

    public ArrayList<Monster_Eye1> getMonEye1(){
    	return monEye1;
    }
    
    public ArrayList<Monster2> getMonster2(){
    	return monster2;
    }
    
    public ArrayList<Spider> getSpider(){
    	return spiders;
    }
    
    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }
    
    public ArrayList<Boss1> getBoss1(){
    	return boss1;
    }
    
    public ArrayList<Boss5> getBoss5(){
    	return boss5;
    }
    
    
    
    public ArrayList<Toro> getToros(){
    	return toros;
    }
    

    public ArrayList<Boss2> getBoss2(){
    	return boss2;
    }
    
    public ArrayList<Boss3> getBoss3(){
    	return boss3;
    }
    
    public ArrayList<Boss4> getBoss4(){
    	return boss4;
    }
    
    public ArrayList<BossFinal> getBossFinal(){
    	return bossFinal;
    }
    

  public Point getPlayerMeet() {
	  return playerMeet;
  }

public Point getPlayerSpawn(){
     return playerSpawn;
  }
    public int getMapHeight() {
        return lvlData.length; // Số hàng trong mảng 2D là chiều cao của bản đồ (theo số tile)
    }
}
