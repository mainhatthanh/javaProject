package input;

import gameState.Gamestate;
import main.GamePanel;
import main.Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static utilz.Constants.Directions.*;

public class KeyBoardInputs implements KeyListener {
    private GamePanel gamePanel;
    private Game game;
public KeyBoardInputs(GamePanel gamePanel){
    this.gamePanel=gamePanel;
}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(Gamestate.state){
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
     switch(Gamestate.state){
         case MENU:
             gamePanel.getGame().getMenu().keyReleased(e);
             break;
         case PLAYING:
             gamePanel.getGame().getPlaying().keyReleased(e);
         default:
             break;
     }
    }
}
