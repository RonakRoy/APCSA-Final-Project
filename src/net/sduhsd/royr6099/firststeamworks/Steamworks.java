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
	
	private List<Hopper> hoppers;
	private List<Ball> groundBalls, shotBalls;
	
	private HighGoal goal;
	
	private Image field, logo;
	private double heightMultiplier;
	
	private int top, bottom, left, right;
	
	private boolean starting_up = true;
	
	private int game_time = 135;
	private int score = 40;
	
	private int ball_count = 0;
	
	private int rotor = 1;
	private int scored_balls = 0;
	
	private long last_shot = 0;
	
	public Steamworks() {
		top = 80;
		bottom = Constants.WINDOW_HEIGHT - 160;
		
		left = 70;
		right = 445;
		
		setBackground(Color.WHITE);
		
		misfire = new Misfire((Constants.BOARD_WIDTH - 80) / 2, bottom, 2);

		this.addKeyListener(this);
		new Thread(this).start();
		
		keys = new boolean[7];
		
		field = ImageLoader.loadImage("field.png");
		logo = ImageLoader.loadImage("steamworks.png");
				
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
		
		hoppers = new ArrayList<Hopper>();
		hoppers.add(new Hopper(66, 215, true));
		hoppers.add(new Hopper(66, 462, true));
		hoppers.add(new Hopper(66, 709, true));
		
		hoppers.add(new Hopper(513, 320, false));
		hoppers.add(new Hopper(513, 605, false));
		
		groundBalls = new ArrayList<Ball>();
		shotBalls = new ArrayList<Ball>();
		
		goal = new HighGoal(64, 852);
		
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

		if (starting_up) {
			backgroundGraphics.drawImage(logo, 30, 60, 330, 255, null);
			
			backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
			
			backgroundGraphics.drawString("Artisinally crafted by Ronak Roy. Welcome to the2018 FIRST Robotics", 30, 340);
			backgroundGraphics.drawString("Competition and this year's game, FIRST STEAMWORKS!", 30, 360);
			
			backgroundGraphics.drawString("One objective of this game is to retrieve gears from the loading station at", 30, 400);
			backgroundGraphics.drawString("the top right of the field and drop them off at the blue alliance airship.", 30, 420);
			backgroundGraphics.drawString("There are three pegs, located at the bottom, bottom-left, and bottom-right", 30, 440);
			backgroundGraphics.drawString("of the airship. Be careful! Sometimes, the robot will drop a gear or miss", 30, 460);
			backgroundGraphics.drawString("the airship! The second objective of this game is to score fuel into", 30, 480);
			backgroundGraphics.drawString("the high boiler to build steam pressure. Fuel can be collected from the", 30, 500);
			backgroundGraphics.drawString("hoppers located on the sides of the field.", 30, 520);
			
			backgroundGraphics.drawString("You can control Team 254: The Cheesy Poof's 2017 robot, Misfire, using", 30, 560);
			backgroundGraphics.drawString("the WASD keys. Gears can be picked up and dropped off on the ground", 30, 580);
			backgroundGraphics.drawString("or dropped off at the airship using the space bar. Fuel can be fired", 30, 600);
			backgroundGraphics.drawString("down using the e key and to the left using the q key. Good luck!", 30, 620);
			
			backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
			backgroundGraphics.drawString("Press the space bar to begin.", 100, 660);
			
			if (keys[4]) {
				starting_up = false;
				
				new Thread(() -> {
					while (this.game_time > 0) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						this.game_time -= 1;
					}
				}).start();
			}
		}
		else {
			backgroundGraphics.drawImage(field, 30, 50, Constants.BOARD_WIDTH - 80, (int) (field.getWidth(null) * heightMultiplier), null);
			
			backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
			backgroundGraphics.drawString("Time Remaining:", Constants.BOARD_WIDTH + 10, Constants.WINDOW_HEIGHT - 100);
			
			backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
			backgroundGraphics.drawString("" + game_time, Constants.BOARD_WIDTH + 10, Constants.WINDOW_HEIGHT - 70);
			
			
			backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
			backgroundGraphics.drawString("Score:", Constants.BOARD_WIDTH + 10, Constants.WINDOW_HEIGHT - 200);
			
			backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
			backgroundGraphics.drawString("" + score, Constants.BOARD_WIDTH + 10, Constants.WINDOW_HEIGHT - 170);
			
			
			backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
			backgroundGraphics.drawString("Pressure:", Constants.BOARD_WIDTH + 10, Constants.WINDOW_HEIGHT - 260);
			
			backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
			backgroundGraphics.drawString((scored_balls / 3) + " kPa", Constants.BOARD_WIDTH + 10, Constants.WINDOW_HEIGHT - 230);
			
			for (GearIndicator gi : gearIndicators) {
				gi.draw(backgroundGraphics);
			}
			
//			loadingZone.draw(backgroundGraphics);
//			for (FixedBlock fb : fixedBlocks) {
//				fb.draw(backgroundGraphics);
//			}
//			for (GearPegZone peg : pegs) {
//				peg.draw(backgroundGraphics);
//			}
//			
//			goal.draw(backgroundGraphics);
			
			for (Hopper h : hoppers) {
				h.draw(backgroundGraphics);
			}
			
			if (game_time > 0) {
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
					
					if (inPegZone && Math.random() < 0.6) {
						incrementGear();
					}
					else {
						gears.add(new Gear(misfire));
					}
				}
			}
			
			if (misfire.hasGear() && Math.random() < 0.005) {
				gears.add(new Gear(misfire));
				misfire.depositGear();
			}
			
			for (Hopper h : hoppers) {
				if (misfire.isIn(h) && h.getFilled()) {
					h.dump();
					
					for (int i = 0; i < 100; i++) {
						groundBalls.add(new Ball(h));
					}
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
			
			for (Ball b : groundBalls) {
				if (misfire.isIn(b) && ball_count < 50) {
					ball_count += 1;
					
					groundBalls.remove(b);
				}
				else {
					b.draw(backgroundGraphics);
				}
			}
			
			if (keys[5] || keys[6]) {
				if (!(misfire.getX() < 210 && misfire.getY() > 700) && ball_count > 0 && System.currentTimeMillis() - last_shot >= 50) {
					ball_count--;
					
					shotBalls.add(new Ball(misfire, keys[5]));
					
					last_shot = System.currentTimeMillis();
				}
			}
			
			for (Ball b : shotBalls) {
				b.move();
				b.draw(backgroundGraphics);
				
				if (b.getX() < 0) {
					shotBalls.remove(b);
				}
				
				if (goal.isIn(b)) {
					shotBalls.remove(b);
					scoreBall();
				}
			}
			
			misfire.draw(backgroundGraphics);
			
			if (misfire.isIn(loadingZone)) {
				misfire.pickUpGear();
			}
			
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
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			keys[5] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			keys[6] = true;
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
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			keys[5] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			keys[6] = false;
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
		for (int i = 0; i < gearIndicators.size(); i++) {
			GearIndicator gi = gearIndicators.get(i);
			
			if (!gi.isActive()) {
				gi.activate();
				
				if (i < gearIndicators.size() - 1 && gearIndicators.get(i+1).getRotor() != rotor) {
					rotor++;
					
					score += 40;
				}
				else if (i == gearIndicators.size() - 1) {
					score += 40;
				}
				
				break;
			}
		}
	}
	
	public void scoreBall() {
		scored_balls++;
		
		if (scored_balls % 3 == 0) {			
			score++;
		}
	}
}
