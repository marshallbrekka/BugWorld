import java.util.ArrayList;


public class Cell {
	private ArrayList<Actor> actors = new ArrayList<Actor>(3);
	
	public void add(Actor a) {
		actors.add(a);
	}
	
	public void remove(Actor a) {
		actors.remove(a);
	}
	
	public int size() {
		return actors.size();
	}
	
	public Actor get(int index) {
		return actors.get(index);
	}
	
	public ArrayList<Actor> getAllActors() {
		return actors;
	}
	
	public boolean hasRock() {
		return hasClass(Rock.class);
	}
	
	public boolean hasFlower() {
		return hasClass(Flower.class);
	}
	
	public boolean hasBug() {
		return hasClass(Bug.class);
	}
	
	public boolean hasCritter() {
		return hasClass(Critter.class);
	}
	
	public Flower getFlower() {
		return (Flower) getActor(Flower.class);
	}
	
	public Bug getBug() {
		return (Bug) getActor(Bug.class);
	}
	
	public Critter getCritter() {
		return (Critter) getActor(Critter.class);
	}
	
	private boolean hasClass(Object theClass) {
		if(getActor(theClass) != null) return true;
		return false;
	}
	
	private Actor getActor(Object theClass) {
		for(int i = 0; i < actors.size(); i++) {
			if(actors.get(i).getClass().equals(theClass)) {
				return actors.get(i);
			}
		}
		return null;
	}
}
