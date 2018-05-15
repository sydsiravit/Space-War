import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip1 extends SpaceShip{
    
    int step = 8;

	public SpaceShip1(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.step = step;
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
		
	}

}
