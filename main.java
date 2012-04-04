import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class main {
	private Runner runner;
	private JFrame frame;
	private JMenuBar menuBar = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		main m = new main();
		m.createWorld(5, 10, 6, 3, 25, 25, 30, 4, 35, 2, 50, 3);


	}
	
	public main() {
		frame = new JFrame("World");
		World.BUG_ICON = loadImage("/img/bug_s.png");
		World.FLOWER_ICON = loadImage("/img/flower_s.png");
		World.CRITTER_ICON = loadImage("/img/critter_s.png");
		World.ROCK_ICON = loadImage("/img/rock_s.png");
	}
	
	public void stop() {
		runner.stop();
	}
	
	public void start() {
		runner.start();
	}
	
	public void startNew() {
		runner.stop();
		runner = null;
		showMenu();
	}
	
	public void showMenu() {
		menuBar.setVisible(false);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new CreateView(this));
		frame.pack();
		frame.setVisible(true);
		frame.repaint();
	}
	
	public void createWorld(int rocks, int flowers, int bugs, int critters, int rows, int cols, int bugFeed, int newCritter, int critterFeedTime, int deadlyNeighbors, int flowerLife, int newBug) {
		VisibleWorld w = new VisibleWorld(rows, cols);
		World world = new World(rows,cols, w);
		World.BUG_FEEDING_TIME = bugFeed;
		World.BUGS_FOR_NEW_CRITTER = newCritter;
		World.CRITTER_FEEDING_TIME = critterFeedTime;
		World.DEADLY_BUG_NEIGHBORS = deadlyNeighbors;
		World.FLOWER_LIFE_TIME = flowerLife;
		World.FLOWERS_FOR_NEW_BUG = newBug;
		
		Actor[] actors = new Actor[rocks + flowers + bugs + critters];
		int i = 0;
		for(int y = 0; y < rocks; y++) {
			actors[i] = new Rock(World.ROCK_ICON);
			i++;
		}
		for(int y = 0; y < flowers; y++) {
			actors[i] = new Flower(World.FLOWER_ICON);
			i++;
		}
		
		for(int y = 0; y < bugs; y++) {
			actors[i] = new Bug(World.BUG_ICON);
			i++;
		}
		
		for(int y = 0; y < critters; y++) {
			actors[i] = new Critter(World.CRITTER_ICON);
			i++;
		}
		
		for(int y = 0; y < actors.length; y++) {
			world.addActor(actors[y]);
		}
		world.updateView();
		
		
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(w, BorderLayout.CENTER);
		
		if(menuBar == null) {
			menuBar = new JMenuBar();

			// Create a menu
			JMenu menu = new JMenu("Controls");
			
			menuBar.add(menu);

			// Create a menu item
			JMenuItem item = new JMenuItem("Stop");
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					main.this.stop();
				}
			});
			menu.add(item);
			
			item = new JMenuItem("Start");
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					main.this.start();
				}
			});
			menu.add(item);
			
			item = new JMenuItem("New");
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					main.this.startNew();
				}
			});
			menu.add(item);

			// Install the menu bar in the frame
			frame.setJMenuBar(menuBar);
			
		}
		
		
		menuBar.setVisible(true);
		
		
		frame.pack();
		frame.setVisible(true);
		frame.repaint();
		runner = new Runner(world);
		runner.start();
		
		
	}
	
	
	
	private  ImageIcon loadImage(String filename) {
		
        try {
            return new ImageIcon(ImageIO.read(getClass().getResource(filename)));
        } catch (IOException e) {
            System.out.println("Error loading " + filename);
            return null;
        }
		
		
	}

}
