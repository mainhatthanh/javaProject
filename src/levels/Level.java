package levels;

import entities.Crabby;
import entities.Minotaur;
import entities.Shark;
import entities.Toro;
import main.Game;
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
    private ArrayList<Crabby> crabs;
    private ArrayList<GameContainer> containers;
    private ArrayList<Potion> potions;
    private ArrayList<Shark> sharks;
    private ArrayList<Minotaur> minotaurs;
    private ArrayList<Toro> toros;

    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        createContainers();
        createPotions();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void createContainers() {
        containers = HelpMethods.GetContainers(img);
    }

    private void createPotions() {
        potions = HelpMethods.GetPotions(img);
    }

    private void createEnemies() {
        crabs = GetCrabs(img);
        sharks = GetSharks(img);
        minotaurs = GetMinotaurs(img);
        toros = GetToros(img);
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

    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<Shark> getSharks() {
        return sharks;
    }

    public ArrayList<Minotaur> getMinotaurs() {
        return minotaurs;
    }

    public ArrayList<Toro> getToros() {
        return toros;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public int getMapHeight() {
        return lvlData.length; // Số hàng trong mảng 2D là chiều cao của bản đồ (theo số tile)
    }
}
