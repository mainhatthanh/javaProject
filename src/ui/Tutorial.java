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
    private Playing playing;
    private BufferedImage imgTutorial;
    private boolean iShowTutorial;
    private int tutorialX, tutorialY, tutorialW, tutorialH;

    public Tutorial(Playing playing) {
        this.playing = playing;
        initImg();

    }



    private void initImg() {
        imgTutorial = LoadSave.GetSpriteAtlas(LoadSave.TUTORIAL_PAPER);
        tutorialW = (int) (imgTutorial.getWidth() * Game.SCALE/1.5);
        tutorialH = (int) (imgTutorial.getHeight() * Game.SCALE/1.5);
        tutorialX = Game.GAME_WIDTH / 2 -tutorialX / 2-400;
        tutorialY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g) {

        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(imgTutorial, tutorialX, tutorialY, tutorialW, tutorialH, null);

    }
//    public void toggleShowTutorial(){
//        if(iShowTutorial){
//            iShowTutorial=false;
//        }
//        else if(!iShowTutorial){
//            iShowTutorial=true;
//
//        }
//    }

    public boolean isShowTutorial(){
        return iShowTutorial;
    }

    public void setTutorial(boolean isShow){
        this.iShowTutorial=isShow;
    }






}
