package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.Timer;

import noise.FastNoise;
public class MainPanel extends JPanel{
	
	private BufferedImage terrain = new BufferedImage(MainWindow.WIDTH, MainWindow.HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
	//NOISE GENERATOR
	private FastNoise fastNoise = new FastNoise();
	//COLOURS
	private Color DEEP_WATER = new Color(54, 107, 193);
	private Color MEDIUM_WATER = new Color(64, 132, 226);
	private Color SHALLOW_WATER = new Color(107, 190, 242);	
	private Color SAND = new Color(255, 253, 165);	
	private Color FOREST = new Color(90, 123, 49);
	private Color WOODS = new Color(106, 146, 57);
	private Color PLAINS = new Color(129, 183, 71);	
	private Color TALL_MOUNTAIN = new Color(69, 69, 69);
	private Color MEDIUM_MOUNTAIN = new Color(87, 87, 87);
	private Color SNOW = new Color(250, 250, 255);
	
	
	private double[][] terrainArr; //NOISE VALUES HELD INSIDE ARRAY.
	private double[] heightConditions = {-1,-0.15,-0.1,-0.05,0,0.1,0.2,0.3,0.4, 0.5};

	
	
	private Timer timer = new Timer(30, e -> repaint()); //TIMER FOR ANIMATING THE SHORELINE
	
	private int tick = 0;
	private int animationLength = 120;
	private float shoreGrowth = 0.0002f;
	
	private int noiseX;
	private int noiseY;
	
	private JButton reGenerate = new JButton("ReGenerate Terrain");
	private JCheckBox moveTerrain = new JCheckBox("Move Terrain", true);
	
	public MainPanel(){
		timer.start();
		terrainArr = new double[MainWindow.WIDTH][MainWindow.HEIGHT];
		
		createTerrainMap();
		createTerrainImage(terrainArr);
		this.add(reGenerate);
		this.add(moveTerrain);
		this.reGenerate.addActionListener(new ButtonListener(this));
		
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//SHORE ANIMATION
		if(tick == animationLength) {
			tick = 0;
		}			
		
		if(tick < animationLength/2) {
			heightConditions[2] = heightConditions[2] - shoreGrowth;
		} else {
			heightConditions[2] = heightConditions[2] + shoreGrowth;
		}
		//END ANIMATION
		
		//MOVE TERRAIN
		if(moveTerrain.isSelected()) {
			moveTerrain(noiseX, noiseY);
			noiseX = noiseX + 10;
			noiseY = noiseY + 10;
		}		
		//END MOVE TERRAIN
		
		createTerrainImage(terrainArr);
		g.drawImage(terrain, 0, 0, this);
		tick++;
	}
	
	public void createTerrainMap() {
		
		Random rand = new Random();
		fastNoise.SetSeed(rand.nextInt(1000000));
		
		for(int y = 0; y < MainWindow.HEIGHT; y++) {
			for(int x = 0; x < MainWindow.WIDTH; x++) {
				terrainArr[y][x] = fastNoise.GetPerlinFractal(x, y);
			}
		}
	}
	
	public void moveTerrain(int noiseX, int noiseY) {
		for(int y = 0; y < MainWindow.HEIGHT; y++) {
			for(int x = 0; x < MainWindow.WIDTH; x++) {
				terrainArr[y][x] = fastNoise.GetPerlinFractal(x + noiseX, y + noiseY);
			}
		}
	}
	
	public void createTerrainImage(double[][] terrainArr) {
		for(int x = 0; x < MainWindow.WIDTH; x++) {
			for(int y = 0; y < MainWindow.HEIGHT; y++) {
				if(terrainArr[x][y] <= heightConditions[0]) {
					terrain.setRGB(x, y, DEEP_WATER.getRGB());
				} else if (terrainArr[x][y] >  heightConditions[0] && terrainArr[x][y] <=  heightConditions[1]) {
					terrain.setRGB(x, y, MEDIUM_WATER.getRGB());
				} else if (terrainArr[x][y] >  heightConditions[1] && terrainArr[x][y] <=  heightConditions[2]) {
					terrain.setRGB(x, y, SHALLOW_WATER.getRGB());
				} else if (terrainArr[x][y] >  heightConditions[2] && terrainArr[x][y] <=  heightConditions[3]) {
					terrain.setRGB(x, y, SAND.getRGB());
				} else if (terrainArr[x][y] >  heightConditions[3] && terrainArr[x][y] <=  heightConditions[4]) {
					terrain.setRGB(x, y, FOREST.getRGB());
				} else if (terrainArr[x][y] >  heightConditions[4] && terrainArr[x][y] <=  heightConditions[5]) {
					terrain.setRGB(x, y, WOODS.getRGB());
				} else if (terrainArr[x][y] >  heightConditions[5] && terrainArr[x][y] <=  heightConditions[6]) {
					terrain.setRGB(x, y, PLAINS.getRGB());
				} else if (terrainArr[x][y] >  heightConditions[6] && terrainArr[x][y] <=  heightConditions[7]) {
					terrain.setRGB(x, y, MEDIUM_MOUNTAIN.getRGB());
				} else if (terrainArr[x][y] >  heightConditions[7] && terrainArr[x][y] <=  heightConditions[8]) {
					terrain.setRGB(x, y, TALL_MOUNTAIN.getRGB());
				} else if (terrainArr[x][y] >  heightConditions[8] && terrainArr[x][y] <=  heightConditions[9]) {
					terrain.setRGB(x, y, SNOW.getRGB());
				}
			}
		}
	}
	
	public void resetNoiseCoordinates() {
		this.noiseX = 0;
		this.noiseY = 0;
	}
	
	private class ButtonListener implements ActionListener {
	    
		MainPanel mainPanel;
		
	    public ButtonListener(MainPanel mainPanel) {
	    	this.mainPanel = mainPanel;
	    }
	    
	    public void actionPerformed(ActionEvent e) {
	      if (e.getSource() == reGenerate) {
	        mainPanel.createTerrainMap();
	        mainPanel.resetNoiseCoordinates();
	      }
	    }
	  }
}
