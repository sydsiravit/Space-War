import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Powerup> powerups = new ArrayList<Powerup>();	
	private SpaceShip v1;
	private SpaceShip1 v2;	
	
	private Timer timer;
	
	private int level = 1;
	private long score = 0;
	private double difficulty = 0.1;
	private int barierStack1 = 0;
	private int barierStack2 = 0;

	public GameEngine(GamePanel gp, SpaceShip v1, SpaceShip1 v2) {
		this.gp = gp;
		this.v1 = v1;		
		this.v2 = v2;

		gp.sprites.add(v1);
		gp.sprites.add(v2);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generatePowerup(){
		Powerup p = new Powerup((int)(Math.random()*390), 30);
		gp.spriteOvals.add(p);
		powerups.add(p);
	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
			generatePowerup();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 10;
				if (score % 250 == 0){
					difficulty += 0.1;
					level += 1;				}
			}
		}

		Iterator<Powerup> p_iter = powerups.iterator();
		while(p_iter.hasNext()){
			Powerup p = p_iter.next();
			p.proceed();
			
			if(!p.isAlive()){
				p_iter.remove();
				gp.spriteOvals.remove(p);
			}

		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr1 = v1.getRectangle();
		Rectangle2D.Double vr2 = v2.getRectangle();
		Rectangle2D.Double er;
		Ellipse2D.Double e1;


		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr1)){
				if(barierStack1 == 0)
					die();
				else if (barierStack1 > 0)
					barierStack1 -=1;
				return;
			}
			if(er.intersects(vr2)){
				if(barierStack2 == 0)
					die();
				else if (barierStack2 > 0)
					barierStack2 -= 1;
				return;
			}
		}

		for(Powerup p : powerups){
			
			e1 = p.getOvaDouble();
			if(e1.intersects(vr1)){
				barierStack1 += 1;
			}
			if(e1.intersects(vr2)){
				barierStack2 += 1;
			}
		}
	}
	
	public void die(){
		timer.stop();
	}
	
	void controlVehicle1(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v1.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v1.move(1);
			break;
		}
	}

	void controlVehicle2(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_Z:
			v2.move(-1);
			break;
		case KeyEvent.VK_C:
			v2.move(1);
			break;		
		}
	}

	public long getScore(){
		return score;
	}

	public int getLevel() {
		return level;
	}


	public int getBarCount1() {
		return barierStack1;
	}

	public int getBarCount2() {
		return barierStack2;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle1(e);
		controlVehicle2(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
