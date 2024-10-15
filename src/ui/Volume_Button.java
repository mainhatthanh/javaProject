package ui;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.URMButtons.URM_DEFAULT_SIZE;
import static utilz.Constants.URMButtons.URM_SIZE;
import static utilz.Constants.Volume_Button.*;

public class Volume_Button extends PauseButton{
    private BufferedImage[] imgs;
    private  BufferedImage slider;
    private int index=0;
    private boolean mouseOver,mousePressed;
    private int buttonX,minX,maxX;

    public Volume_Button(int x, int y, int width, int height) {
        super(x+width/2, y, Volume_WIDTH, height);
        bounds.x-=Volume_WIDTH;
        buttonX = x+width/2;
        this.x=x;
        this.width = width;
        minX=x+Volume_WIDTH/2;
        maxX=x+width-Volume_WIDTH/2;
        loadImgs();

    }
    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTON);
        imgs = new BufferedImage[3];
        for(int i=0;i<imgs.length;i++)
            imgs[i]=temp.getSubimage(i*Volume_DEFAULT_WIDTH,0,Volume_DEFAULT_WIDTH,Volume_DEFAULT_HEIGHT);
        slider = temp.getSubimage(3*Volume_DEFAULT_WIDTH,0,SLIDER_DEFAULT_WIDTH,Volume_DEFAULT_HEIGHT);
    }

    public  void update(){
        index =0;
        if(mouseOver)
            index = 1;
        if(mousePressed)
            index = 2;

   bounds.x = buttonX-Volume_WIDTH/2;
    }
    public  void draw(Graphics g){
        g.drawImage(slider,x,y,width,height,null);
        g.drawImage(imgs[index],buttonX-Volume_WIDTH/2,y,Volume_WIDTH,height,null);


    }

    public void changeX(int x){
        if(x<minX)
            buttonX=minX;
        else if(x>maxX)
            buttonX=maxX;
        else
            buttonX = x;


    }

    public void resetBools(){
        mousePressed = false;
        mouseOver= false;

    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

}
