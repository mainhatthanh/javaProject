package ui;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Plot {
    private BufferedImage imgPlot1;
    private BufferedImage imgPlot2;
    private BufferedImage imgPlot3;
    private BufferedImage imgPlot4;
    private BufferedImage imgPlot5;


    private int imgPlotX, imgPlotY, imgPlotW, imgPlotH;

    private boolean isShowPlot;

    public Plot() {
        initImg();
    }



    private void initImg() {
        imgPlot1 = LoadSave.GetSpriteAtlas(LoadSave.PLOT1);
        imgPlot2 = LoadSave.GetSpriteAtlas(LoadSave.PLOT2);
        imgPlot3 = LoadSave.GetSpriteAtlas(LoadSave.PLOT3);
        imgPlot4 = LoadSave.GetSpriteAtlas(LoadSave.PLOT4);
        imgPlot5 = LoadSave.GetSpriteAtlas(LoadSave.PLOT5);

        imgPlotW = (int) (imgPlot1.getWidth() * Game.SCALE/2.75);
        imgPlotH = (int) (imgPlot1.getHeight() * Game.SCALE/2.75);
        imgPlotX = Game.GAME_WIDTH / 2 -imgPlotX / 2-380;
        imgPlotY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g,int lvlIndex) {

        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);


        if(lvlIndex==0)
            g.drawImage(imgPlot1, imgPlotX, imgPlotY, imgPlotW, imgPlotH, null);

       else if(lvlIndex==1)
            g.drawImage(imgPlot2, imgPlotX, imgPlotY, imgPlotW, imgPlotH, null);

       else if(lvlIndex==2)
            g.drawImage(imgPlot3, imgPlotX, imgPlotY, imgPlotW, imgPlotH, null);

       else if(lvlIndex==3)
            g.drawImage(imgPlot4, imgPlotX, imgPlotY, imgPlotW, imgPlotH, null);

        else if(lvlIndex==4)
            g.drawImage(imgPlot5, imgPlotX, imgPlotY, imgPlotW, imgPlotH, null);

    }


    public boolean isShowPlot(){
        return isShowPlot;
    }

    public void setPlot(boolean isShow){
        this.isShowPlot=isShow;
    }
}
