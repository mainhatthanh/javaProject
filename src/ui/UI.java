package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

import gameState.Playing;
import main.Game;
import main.GamePanel;

import static utilz.Constants.EnemyConstants.GetNumberMessageBoss;

public class UI {
	Playing playing;
	Font arial_40;
	Font vt323;
	
	public boolean messageOn = false;
	public String message;
	private int counter =0;
	
	
	private String[] text1;
	private int textLength;
	
	public UI(Playing playing) {
		this.playing = playing ;
		arial_40 = new Font("Arial", Font.BOLD, 25);
		text1 = new String[10];	
		
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/VT323.ttf");
			vt323 = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	
	//hien thi FPS va phan tang kinh nghiem
	public void draw(Graphics2D g2) {
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		g2.drawString("FPS: " + playing.getGame().getFPS(),  1050, 50);
		
		if(messageOn) {
			counter ++;
			g2.setFont(g2.getFont().deriveFont(20F));
			g2.setColor(Color.green);
			g2.drawString(message, Game.GAME_WIDTH/2 - 20, 40);
			
			if( counter > 120) {
				counter =0;
				messageOn = false;
			}
		}
	}
	
	public void setText(String[] text, int enemyCheck) {
		textLength = GetNumberMessageBoss(enemyCheck) ;
		for(int i=0; i < textLength; i++) {
			this.text1[i] = text[i];

		}
	}
	
	//hien thi khung tro chuyen giua nhan vat va boss
	public void drawDialogueScreen(Graphics2D g2, int textIndex) {
		
		int x = Game.GAME_WIDTH /12;
		int y = Game.GAME_HEIGHT/2  - 200;
		int width = Game.GAME_WIDTH - (Game.TILES_SIZE*4);
		int height = Game.TILES_SIZE * 4;
		
		drawSubWindow(x, y, width, height, g2, textIndex);
	}

	private void drawSubWindow(int x, int y, int width, int height, Graphics2D g2, int textIndex) {
		String intro1 = "Nhấn nút ENTER để tiếp tục";
		String intro2 = "Nhấn nút Q để chiến đấu";
		
		//nen cua dialogue
		Color c = new Color(0,0,0,100);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 50, 35);
		
		//viền cua dialogue
		c=new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y + 5, width -10, height -10, 40, 25);
		
		
		//chỉ dẫn người chơi
		if(textIndex >= textLength-1) {
			g2.setFont(vt323);
			g2.setColor(Color.red);
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(intro2, x + 700, y + 150);
		}else {
			g2.setFont(vt323);
			g2.setColor(Color.red);
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(intro1, x + 700, y + 150);
		}	
		
		
		//hiển thị cuộc trò chuyện
		if(textIndex >= textLength)
			textIndex = textLength -1;
		drawText(text1, textIndex, g2, x + 10, y + 50);
		
		
	}
	
	private void drawText(String[] text, int index, Graphics2D g2, int x, int y) {
		g2.setFont(vt323);
		g2.setFont(g2.getFont().deriveFont(40F));
		g2.setColor(Color.green);
		
		for(String line: text[index].split("\n")) {
			g2.drawString(line, x + 20 *Game.SCALE, y);
			y+= 40;
		}
	}

	public void drawIntro() {
		String intro1 = "Chào mừng bạn đến với BLACK MYTH WUKONG";
		
		
	}
}
