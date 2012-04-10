import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;


public class Bug extends Actor implements HasLife {
	private int cycle = -1;
	
	private static final int MAX_SEED_DROP_TIME = 4;
	private int lifeCount = 0;
	private int seedCount = 0;
	private LinkedList<Integer> seedDropTimes = new LinkedList<Integer>();

	public Bug(ImageIcon img) {
		super(img, World.BUG_SLEEP_TIME);
		zIndex = 2;
	}

	@Override
	public boolean canInhabitSpace(Cell cell) {
		return !cell.hasClass(Bug.class);
	}

	@Override
	public Move move(MoveOptions options) {
		Move move = null;
		if(lifeCount == World.BUG_FEEDING_TIME) {
			move = new Move(Move.Direction.CENTER, this.getClass());
		} else {
			lifeCount++;
			Move.Direction dir = chooseDirection(options);
			Cell newPlace = options.getOption(dir);
			Class<?> flower = null;
			Actor newActor = null;
			
			if(seedCount >= World.FLOWERS_FOR_NEW_BUG && dir != Move.Direction.CENTER) {
				newActor = new Bug(World.BUG_ICON);
				newActor.setCell(cell);
				seedCount = seedCount - World.FLOWERS_FOR_NEW_BUG;
			}
			
			
			if(!options.getOption(Move.Direction.CENTER).hasRock() && seedDropTimes.size() != 0 && seedDropTimes.peek() <= lifeCount && newActor == null) {
				seedDropTimes.poll();
				newActor = new Flower(World.FLOWER_ICON);
				newActor.setCell(cell);
			}
			move = new Move(dir, flower, newActor);
		}
		return move;
	}
	
	private void addSeed() {
		lifeCount = 0;
		seedCount++;
		Random rand = new Random();
		int drop = rand.nextInt(MAX_SEED_DROP_TIME - 1) + 1;
		seedDropTimes.offer(drop);
	}
	
	private Move.Direction chooseDirection(MoveOptions options) {
		//check for any critters
		ArrayList<Move.Direction> critterLocations = new ArrayList<Move.Direction>();
		ArrayList<Move.Direction> bugLocations = new ArrayList<Move.Direction>();
		ArrayList<Move.Direction> rockLocations = new ArrayList<Move.Direction>();
		ArrayList<Move.Direction> openLocations = new ArrayList<Move.Direction>();
		
		Cell temp;
		for (Move.Direction dir : Move.Direction.values()) {
			if(dir == Move.Direction.CENTER) break;
			temp = options.getOption(dir);
			boolean open = true;
			if(temp.hasBug()) {
				bugLocations.add(dir);
				open = false;
			}
			if(temp.hasCritter()) {
				critterLocations.add(dir);
				open = false;
			}
			if(temp.hasRock()) rockLocations.add(dir);
			if(open) openLocations.add(dir);
		}
		Move.Direction place = null;
		// run
		if(critterLocations.size() != 0) {
			if(rockLocations.size() != 0) {
				
				for(int i = 0; i < rockLocations.size(); i++) {
					if(!bugLocations.contains(rockLocations.get(i))) {
						place = rockLocations.get(i);
						break;
					}
				}
				if(place != null) return place;
			}
		}
		
		// move to uninhabited place
		if(openLocations.size() != 0) {
			Random rand = new Random();
			int index = rand.nextInt(openLocations.size());
			place = openLocations.get(index);
			return place;
		}
		
		// if there is a critter and no other open spaces move to the critter
		if(critterLocations.size() != 0) {
			place = critterLocations.get(0);
			return place;
		}
		
		// if surrounded by bugs, don't move anywhere;
		return Move.Direction.CENTER;
		
		
	}
	
	public Class<?> getClassToKill(Cell cell) {
		if(cell.hasFlower()) {
			addSeed();
			return Flower.class;
		}
		return null;
	}
	


}
