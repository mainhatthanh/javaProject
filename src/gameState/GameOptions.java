package gameState;

import main.Game;
import ui.AudioOptions;
import ui.UrmButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GameOptions extends State implements Statemethods  {
    private AudioOptions audioOptions;
    private BufferedImage backGroundImg,optionBackgroundImg;
    private int bgX,bgY,bgW,bgH;
    private UrmButton menuB;


    public GameOptions(Game game) {
        super(game);
        loadImgs();
        loadButtons();
        audioOptions= game.getAudioOptions();
    }

    private void loadButtons() {
    }

    private void loadImgs() {
        backGroundImg= LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backGroundImg,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
