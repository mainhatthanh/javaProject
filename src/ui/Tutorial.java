package ui;

import audio.AudioPlayer;
import gameState.Gamestate;
import gameState.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.URMButtons.URM_SIZE;

public class Tutorial {

    private BufferedImage imgTutorial;
    private boolean iShowTutorial;
    private int tutorialX, tutorialY, tutorialW, tutorialH;

    public Tutorial() {
        initImg();
    }



    private void initImg() {
        imgTutorial = LoadSave.GetSpriteAtlas(LoadSave.TUTORIAL_PAPER);
        tutorialW = (int) (imgTutorial.getWidth() * Game.SCALE/1.5);
        tutorialH = (int) (imgTutorial.getHeight() * Game.SCALE/1.5);
        tutorialX = Game.GAME_WIDTH / 2 -tutorialX / 2-380;
        tutorialY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g) {

        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(imgTutorial, tutorialX, tutorialY, tutorialW, tutorialH, null);

    }


    public boolean isShowTutorial(){
        return iShowTutorial;
    }

    public void setTutorial(boolean isShow){
        this.iShowTutorial=isShow;
    }






}
