import javax.swing.ImageIcon;


public class Flower extends Actor implements HasLife {
	private int lifeCount = 0;
	private int cycle = -1;


	public Flower(ImageIcon img) {
		super(img);
		zIndex = 3;
	}

	@Override
	public boolean canInhabitSpace(Actor existingActor) {
		Object theClass = existingActor.getClass();
		if(theClass.equals(this.getClass()) || theClass.equals(Rock.class)) {
			return false;
		}
		return true;
	}



	@Override
	public Move move(MoveOptions options) {
		Move move = null;
		if(lifeCount == World.FLOWER_LIFE_TIME) {
			move = new Move(Move.Direction.CENTER, this);
			
		}
		lifeCount++;
		return move;
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
