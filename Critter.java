import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;


public class Critter extends Actor implements HasLife {
	private int cycle = -1;
	private int lifeCount = 0;
	private int foodCount = 0;

	public Critter(ImageIcon img) {
		super(img);
		zIndex = 0;
	}

	@Override
	public boolean canInhabitSpace(Actor existingActor) {
		return !existingActor.getClass().equals(Critter.class);
	}

	@Override
	public Move move(MoveOptions options) {
		Move move = null;
		
		if(lifeCount == World.BUG_FEEDING_TIME) {
			move = new Move(Move.Direction.CENTER, this);
		} else if(options.getOption(Move.Direction.CENTER).hasBug()) {
			move = new Move(Move.Direction.CENTER, options.getOption(Move.Direction.CENTER).getBug());
			foodCount++;
			lifeCount = 0;
		} else {
			lifeCount++;
			Move.Direction dir = chooseDirection(options);
			Cell newPlace = options.getOption(dir);
			Actor killed = null;
			Actor newActor = null;
			
			if(foodCount >= World.BUGS_FOR_NEW_CRITTER && dir != Move.Direction.CENTER) {
				newActor = new Critter(World.CRITTER_ICON);
				foodCount = foodCount - World.BUGS_FOR_NEW_CRITTER;
			}
			
			if(!newPlace.hasRock() && newPlace.hasBug()) {
				lifeCount = 0;
				foodCount++;
				killed = newPlace.getBug();
			} 
			if(newPlace.hasFlower()) {
				killed = newPlace.getFlower();
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
	
	@Override
	public int getCycle() {
		return cycle;
	}


	@Override
	public void setCycle(int val) {
		
		cycle = val;
	}

}
