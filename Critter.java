import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;


public class Critter extends Actor implements HasLife {
	private int cycle = -1;
	private int lifeCount = 0;
	private int foodCount = 0;

	public Critter(ImageIcon img) {
		super(img, World.CRITTER_SLEEP_TIME);
		zIndex = 0;
	}

	@Override
	public boolean canInhabitSpace(Cell cell) {
		return !cell.hasClass(Critter.class);
	}

	@Override
	public Move move(MoveOptions options) {
		Move move = null;
		
		if(lifeCount == World.CRITTER_FEEDING_TIME) {
			move = new Move(Move.Direction.CENTER, this.getClass());
		} else if(options.getOption(Move.Direction.CENTER).hasBug() && !options.getOption(Move.Direction.CENTER).hasRock()) {
			move = new Move(Move.Direction.CENTER, Bug.class);
			foodCount++;
			lifeCount = 0;
		} else {
			lifeCount++;
			Move.Direction dir = chooseDirection(options);
			
			// happens when critter is killed by bugs
			if(dir == null) return move = new Move(Move.Direction.CENTER, this.getClass());
			Cell newPlace = options.getOption(dir);
			Class<?> killed = null;
			Actor newActor = null;
			
			if(foodCount >= World.BUGS_FOR_NEW_CRITTER && dir != Move.Direction.CENTER) {
				newActor = new Critter(World.CRITTER_ICON);
				newActor.setCell(cell);
				foodCount = foodCount - World.BUGS_FOR_NEW_CRITTER;
			}
			
			move = new Move(dir, killed, newActor);
		}
		return move;
	}
	
	
	
	/**
	 * 
	 * @param options
	 * @return Move.Direction if null it means critter gets killed by bugs
	 */
	private Move.Direction chooseDirection(MoveOptions options) {
		//check for any critters
		ArrayList<Move.Direction> bugLocations = new ArrayList<Move.Direction>();
		ArrayList<Move.Direction> openLocations = new ArrayList<Move.Direction>();
		
		Cell temp;
		for (Move.Direction dir : Move.Direction.values()) {
			if(dir == Move.Direction.CENTER) break;
			temp = options.getOption(dir);
			boolean open = true;
			if(temp.hasBug()) {
				bugLocations.add(dir);
			}
			if(temp.hasCritter()) {
				open = false;
			}
			if(open) openLocations.add(dir);
		}
		Move.Direction place = null;
		
		// bugs kill critter
		if(bugLocations.size() >= World.DEADLY_BUG_NEIGHBORS) {
			return null;
		} else if(bugLocations.size() != 0) {
			place = bugLocations.get(0);
			return place;
		}
		
		// move to uninhabited place
		if(openLocations.size() != 0) {
			Random rand = new Random();
			int index = rand.nextInt(openLocations.size());
			place = openLocations.get(index);
			return place;
		}

		
		// if surrounded by critters, don't move anywhere;
		return Move.Direction.CENTER;
		
		
	}
	
	public Class<?> getClassToKill(Cell cell) {
		if(!cell.hasRock() && cell.hasBug()) {
			lifeCount = 0;
			foodCount++;
			return Bug.class;
		} 
		if(cell.hasFlower()) {
			return Flower.class;
		}
		return null;
	}

}
