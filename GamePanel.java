import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	ArrayList<SpriteOval> spriteOvals = new ArrayList<SpriteOval>();

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.BLACK);
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		
		big.setColor(Color.WHITE);		
		big.drawString(String.format("Score: %08d", reporter.getScore()), 280, 20);
		big.drawString(String.format("Level: %02d", reporter.getLevel()), 280, 40);
		big.drawString(String.format("P1 Barier: %02d", reporter.getBarCount1()), 280, 60);
		big.drawString(String.format("P2 Barier: %02d", reporter.getBarCount2()), 280, 80);
		for(Sprite s : sprites){
			s.draw(big);
		}
		for(SpriteOval so : spriteOvals){
			so.drawOval(big);
		}
		

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Graphics2D g2d2 = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
		g2d2.drawImage(bi, null, 0, 0);
	}

}
