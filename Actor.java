import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public abstract class Actor implements Runnable {
	protected int zIndex;
	private JLabel image;
	public static World world;
	protected Cell cell;
	protected boolean alive = true;
	private int sleepTime;
	
	
	public Actor(ImageIcon img,  int sleep) {
		
		image = new JLabel(img);
		image.setSize(new Dimension(50,50));
		
		sleepTime = sleep;
	}
	
	public void setCell(Cell c) {
		cell = c;
	}
	
	public int getZIndex() {
		return zIndex;
	}
	
	public JLabel getImage() {
		return image;
	}
	
	public abstract Move move(MoveOptions options);
	
	protected void makeMove() {
		MoveOptions options = Actor.world.getMoveOptions(cell);
		boolean moved = false;
		
		while(!moved) {

			Move move = move(options);
			
			
			Cell lock1, lock2;
			
			Move.Direction direction = move.getMove();
			Cell center = options.getOption(Move.Direction.CENTER), 
				newCell = options.getOption(direction);
			
			switch(direction) {
			case DOWN:
				lock1 = center;
				lock2 = newCell;
				newCell = center;
				break;
			case CENTER:
				lock1 = center;
				lock2 = center;
			default:
				lock1 = newCell;
				lock2 = center;
			}
			
			synchronized(lock1) {
				synchronized(lock2) {
					if(alive) {
						// if the new location already has an object of the same class as us find another set of moves.
						if(newCell != center && newCell.hasClass(this.getClass())) {
							continue;
						}
						moved = true;
						Class<?> toKill = move.getClassToConsume();
						
						if(toKill != null) newCell.remove(toKill);
						
						Actor toCreate = move.getActorToCreate();
						center.remove(this.getClass());
						if(newCell != center) {
							newCell.add(this);
						}
						cell = newCell;
						
						if(toCreate != null) {
							center.add(toCreate);
							world.startActor(toCreate);
						}
						world.redrawCell(newCell);
						world.redrawCell(center);
						
						System.out.println(this.getClass().toString() + " moved " + move.getMove().toString() + " " + sleepTime);
						
					} else {
						moved = true;
					}
				}
			}
		}
	}
	
	public void kill() {
		alive = false;
	}
	
	public void run() {
		//Thread thisThread = Thread.currentThread();
		
		while(alive && world.running) {
			try {
				if(!world.paused) {
					makeMove();
				}
				System.out.println("start " + this.hashCode());
				Thread.currentThread().sleep(sleepTime);
				System.out.println("end " + this.hashCode());
	        } catch (InterruptedException e){}
		}
		System.out.println(this.getClass().toString() + " died");
	}

	
	

	
	public abstract boolean canInhabitSpace(Cell cell);
	
	
	
	
}
