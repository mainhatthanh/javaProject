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
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.getIntroText;


public class UI {
	
	//nhan vat -> true; boss ->false
	private boolean checkChat;
	
	Playing playing;
	Font arial_40;
	Font vt323;
	
	public boolean messageOn = false;
	public String message;
	private int counter =0;
	
	
	private String player;
	private String enemy;
	
	//dialogue
	private String[] text1;
	private int textLength;
	
	private String[] textIntro;
	
	public UI(Playing playing) {
		this.playing = playing ;
		arial_40 = new Font("Arial", Font.BOLD, 25);
		text1 = new String[20];	
		
		checkChat = true;
	
		player= "Tôn Ngộ Không";
		
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
		g2.drawString("FPS: " + playing.getGame().getFPS(), (23*Game.TILES_SIZE) + 30, 50);
		
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
	public void drawDialogueScreen(Graphics2D g2, int textIndex, int enemyType) {
		
		if(textIndex <0 )	
			textIndex =0;
		
		int x = Game.GAME_WIDTH /12;
		int y = Game.GAME_HEIGHT/2  - (int)(160 *Game.SCALE);
		int width = Game.GAME_WIDTH - (Game.TILES_SIZE*4);
		int height =(int)( Game.TILES_SIZE * 6) ;
		
		drawSubWindow(x, y, width, height, g2, textIndex, enemyType);
	}

	private void drawSubWindow(int x, int y, int width, int height, Graphics2D g2, int textIndex, int enemyType) {
		String intro1 = "Nhấn nút ENTER để tiếp tục";
		String intro2 = "Nhấn nút Q để chiến đấu";
		
		
		if(enemyType == TORO)
			enemy = "Ngưu Ma Vương";
		else if(enemyType == BOSS2)
			enemy = "Mộc Vân Kiếm Linh ";
		else if(enemyType == BOSS3)
			enemy = "Hắc Thuỷ Hành Giả";
		else if(enemyType == BOSS4)
			enemy = "Xích Diệm Ma Vương";
		else if(enemyType == BOSSFINAL) {
			enemy = "Kim Diện Hành Giả";
			checkChat = false;
		}

		
		//nen cua dialogue
		Color c = new Color(0,0,0,100);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 50, 35);
		
		//viền cua dialogue
		c=new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y + 5, width -10, height -10, 40, 25);
		
		//hiển thị hội thoại của ai
		if(checkChat) {
			g2.setFont(vt323);
			g2.setColor(Color.red);
			g2.setFont(g2.getFont().deriveFont(40F));
			g2.drawString(player, x + 12*Game.SCALE, y + 25*Game.SCALE);
		}else {
			g2.setFont(vt323);
			g2.setColor(Color.red);
			g2.setFont(g2.getFont().deriveFont(40F));
			g2.drawString(enemy, x + 12*Game.SCALE, y + 25*Game.SCALE);
		}			
		//chỉ dẫn người chơi
		if(textIndex >= textLength-1) {
			g2.setFont(vt323);
			g2.setColor(Color.pink);
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(intro2, x + 700, y + (int)(180*Game.SCALE));
		}else {
			g2.setFont(vt323);
			g2.setColor(Color.pink);
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(intro1, x + 700, y + (int)(180*Game.SCALE));
		}	
		
		
		//hiển thị cuộc trò chuyện
		if(textIndex >= textLength)
			textIndex = textLength -1;
		drawText(text1, textIndex, g2, x + 10, y + (int)(65*Game.SCALE));
		
		
	}
	
	private void drawText(String[] text, int index, Graphics2D g2, int x, int y) {
		g2.setFont(vt323);
		g2.setFont(g2.getFont().deriveFont(35F));
		g2.setColor(Color.white);
		
		for(String line: text[index].split("\n")) {
			g2.drawString(line, x + 12 *Game.SCALE, y);
			y+= 40;
		}
	}
	
	private void drawText1(int x, int y, int width, int height, Graphics2D g2, int index, String introText) {
		g2.setFont(vt323);
		g2.setFont(g2.getFont().deriveFont(60F));
		g2.setColor(Color.white);
		for(String text: introText.split("\n")) {
			g2.drawString(text, x + 12 *Game.SCALE,  y + (int)(40*Game.SCALE));
			y+= 50;
		}
		
		
	}

	public void drawIntro(Graphics2D g2, int index) {
		 String introText = getIntroText(index);
		 
		 String next1 = "Nhấn F để tiếp tục";
		 String next2 = "Nhấn F để bắt đầu";
		 
		 int x = Game.GAME_WIDTH /12 - (int)(5*Game.SCALE);
		 int y = Game.GAME_HEIGHT/2  - (int)(160 *Game.SCALE);
		 int width = Game.GAME_WIDTH - (Game.TILES_SIZE* 3);
		 int height =(int)( Game.TILES_SIZE * 7) ;
		 
		//nen cua dialogue
			Color c = new Color(0,0,0,100);
			g2.setColor(c);
			g2.fillRoundRect(x, y, width, height, 50, 35);
			
			//viền cua dialogue
			c=new Color(255, 255, 255);
			g2.setColor(c);
			g2.setStroke(new BasicStroke(5));
			g2.drawRoundRect(x+5, y + 5, width -10, height -10, 40, 25);
		 
		 drawText1(x, y + (int)(5*Game.SCALE), width, height, g2,index, introText);
		 
		 g2.setFont(vt323);
			g2.setColor(Color.pink);
			g2.setFont(g2.getFont().deriveFont(30F));
			if(index < 6)
				g2.drawString(next1, x +1100, y + (int)(200*Game.SCALE));
			else
				g2.drawString(next2, x +1100, y + (int)(200*Game.SCALE));
			
			
			
			
	}
	
	public boolean getCheckChat() {
		return checkChat;
	}
	
	public void setCheckChat(boolean check) {
		this.checkChat = check;
	}
	
	
}
