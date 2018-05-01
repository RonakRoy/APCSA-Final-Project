package net.sduhsd.royr6099.firststeamworks;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Steamworks extends Canvas implements KeyListener, Runnable {
	private boolean[] keys;
	private BufferedImage back;
	
	private Misfire misfire;
	private LoadingZone loadingZone;
	
	private List<Gear> gears;
	private List<GearIndicator> gearIndicators;
	
	private List<FixedBlock> fixedBlocks;
	private List<GearPegZone> pegs;
	
	private Image field;
	private double heightMultiplier;
	
	private int top, bottom, left, right;
	
	public Steamworks() {
		top = 80;
		bottom = Constants.WINDOW_HEIGHT - 160;
		
		left = 70;
		right = 445;
		
		setBackground(Color.WHITE);
		
		misfire = new Misfire((Constants.BOARD_WIDTH - 80) / 2, bottom, 2);

		this.addKeyListener(this);
		new Thread(this).start();
		
		keys = new boolean[5];
		
		field = ImageLoader.loadImage("field.png");
		heightMultiplier = 1.0 * field.getWidth(null) / (Constants.BOARD_WIDTH - 80);
		
		loadingZone = new LoadingZone();
		
		gears = new ArrayList<Gear>();
		gearIndicators = new ArrayList<GearIndicator>();
		
		int[] counts = {1, 2, 5, 8};
		
		for (int i = 0; i < counts.length; i++) {
			for (int j = 0; j < counts[i]; j++) {
				gearIndicators.add(new GearIndicator(i, j));
			}
		}
		
		gearIndicators.get(0).activate();
		gearIndicators.get(3).activate();
		gearIndicators.get(8).activate();
		gearIndicators.get(9).activate();
		
		fixedBlocks = new ArrayList<FixedBlock>();
		
		fixedBlocks.add(new FixedBlock(240, 665, 110, 65));
		fixedBlocks.add(new FixedBlock(260, 650, 70, 100));
		
		fixedBlocks.add(new FixedBlock(240, 665 - 430, 110, 65));
		fixedBlocks.add(new FixedBlock(260, 650 - 430, 70, 100));
		
		pegs = new ArrayList<GearPegZone>();
		
		pegs.add(new GearPegZone(280, 740));
		pegs.add(new GearPegZone(335, 715));
		pegs.add(new GearPegZone(225, 715));
		
		setVisible(true);
	}

	public void update(Graphics window) {
		paint(window);
	}

	public void paint(Graphics window) {
		Graphics2D graphics = (Graphics2D) window;

		if (back == null)
			back = (BufferedImage) (createImage(getWidth(), getHeight()));

		Graphics backgroundGraphics = back.createGraphics();

		backgroundGraphics.setColor(Color.BLACK);
		backgroundGraphics.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		
		//backgroundGraphics.setColor(new Color(3, 155, 50));
		backgroundGraphics.fillRect(0, 0, Constants.BOARD_WIDTH, Constants.WINDOW_HEIGHT);
		
		
		
		backgroundGraphics.setColor(Color.WHITE);
		backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		backgroundGraphics.drawString("FIRST STEAMWORKS", 10, 34);
		
		backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		backgroundGraphics.drawString(misfire.getX() + "," + misfire.getY(), Constants.BOARD_WIDTH + 10, 20);

		backgroundGraphics.drawImage(field, 30, 50, Constants.BOARD_WIDTH - 80, (int) (field.getWidth(null) * heightMultiplier), null);
		
		for (GearIndicator gi : gearIndicators) {
			gi.draw(backgroundGraphics);
		}
		
//		loadingZone.draw(backgroundGraphics);
//		for (FixedBlock fb : fixedBlocks) {
//			fb.draw(backgroundGraphics);
//		}
//		for (GearPegZone peg : pegs) {
//			peg.draw(backgroundGraphics);
//		}
		
		boolean canMoveUp = misfire.getY() > top;
		if (canMoveUp) {
			for (FixedBlock fb : fixedBlocks) {
				if (misfire.didCollideTop(fb)) {
					canMoveUp = false;
					break;
				}
				
			}
		}
		boolean canMoveDown = misfire.getY() < bottom;
		if (canMoveDown) {
			for (FixedBlock fb : fixedBlocks) {
				if (misfire.didCollideBottom(fb)) {
					canMoveDown = false;
					break;
				}
			}
		}
				
		if (keys[0] && canMoveUp) {
			misfire.setDirection(-90);
			misfire.move();
		}
		else if (keys[2] && canMoveDown) {
			misfire.setDirection(+90);
			misfire.move();
		}
		
		
		
		boolean canMoveLeft = misfire.getX() > left;
		if (canMoveLeft) {
			for (FixedBlock fb : fixedBlocks) {
				if (misfire.didCollideLeft(fb)) {
					canMoveLeft = false;
					break;
				}
			}
		}
		boolean canMoveRight = misfire.getX() < right;
		if (canMoveRight) {
			for (FixedBlock fb : fixedBlocks) {
				if (misfire.didCollideRight(fb)) {
					canMoveRight = false;
					break;
				}
			}
		}
		
		if (keys[1] && canMoveLeft) {
			misfire.setDirection(180);
			misfire.move();
		}
		else if (keys[3] && canMoveRight) {
			misfire.setDirection(0);
			misfire.move();
		}
		
		if (keys[4] && misfire.hasGear()) {
			misfire.depositGear();
			
			boolean inPegZone = false;
			for (GearPegZone peg : pegs) {
				if (misfire.isIn(peg)) {
					inPegZone = true;
					break;
				}
			}
			
			if (inPegZone) {
				incrementGear();
			}
			else {
				gears.add(new Gear(misfire));
			}
		}
		
		for (Gear g : gears) {
			if (!misfire.hasGear() && misfire.isIn(g)) {
				gears.remove(g);
				
				misfire.pickUpGear();
			}
			else {
				g.draw(backgroundGraphics);
			}
		}
		
		misfire.draw(backgroundGraphics);
		
		if (misfire.isIn(loadingZone)) {
			misfire.pickUpGear();
		}
		
		graphics.drawImage(back, null, 0, 0);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			keys[0] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			keys[1] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			keys[2] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keys[3] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = true;
		}
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			keys[2] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keys[3] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = false;
		}
		repaint();
	}

	public void keyTyped(KeyEvent e) {
		// Ignore Event
	}

	public void run() {
		try {
			while (true) {
				Thread.sleep(5);
				repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void incrementGear() {
		for (GearIndicator gi : gearIndicators) {
			if (!gi.isActive()) {
				gi.activate();
				break;
			}
		}
	}
}
