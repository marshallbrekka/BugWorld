
public class Cell {
	private Actor[] actors = new Actor[4];
	protected static final int CRITTER = 0, BUG = 1, FLOWER = 2, ROCK = 3;
	private int row, col;
	
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void add(Actor a) {
		int loc = this.getLocation(a.getClass());
		
		
		if(loc < actors.length) {
			actors[loc] = a;
		}
	}
	
	public void kill(Object klass) {
		int start = klass.equals(Critter.class) ? 0 : klass.equals(Bug.class) ? 1 : 2;
		
		
		
		// minus 1 from length so that we don't remove rocks
		for(int i = start; i < actors.length - 1; i++) {
			if(actors[i] != null) {
				
				actors[i].kill();
				actors[i] = null;
				break;
				
			}
		}
	}
	
	public void remove(Object klass) {
		int loc = getLocation(klass);
	
		if(loc < actors.length) {
			actors[loc] = null;
		}
	}
	
	public Actor[] getActors() {
		int count = 0;
		for(int i = 0; i < actors.length; i++) {
			if(actors[i] != null) count++;
		}
		Actor[] temp = new Actor[count];
		for(int i = 0, y = 0; i < actors.length; i++) {
			if(actors[i] != null) {
				temp[y] = actors[i];
				y++;
			}
		}
		return temp;
	}
	
	private int getLocation(Object klass) {
		if(klass.equals(Rock.class)) return ROCK;
		if(klass.equals(Flower.class)) return FLOWER;
		if(klass.equals(Bug.class)) return BUG;
		if(klass.equals(Critter.class)) return CRITTER;
		return actors.length;
	
	}
	
	public boolean hasRock() {
		return actors[ROCK] != null;
	}
	
	public boolean hasFlower() {
		return actors[FLOWER] != null;
	}
	
	public boolean hasBug() {
		return actors[BUG] != null;
	}
	
	public boolean hasCritter() {
		return actors[CRITTER] != null;
	}
	
	public boolean hasClass(Object klass) {
		int loc = getLocation(klass);
		if(loc < actors.length) {
			if(actors[loc] != null) return true;
		}
		return false;
	}
	
	
	
	
}
