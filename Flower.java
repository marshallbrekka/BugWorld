import javax.swing.ImageIcon;


public class Flower extends Actor implements HasLife, Runnable {
	private int lifeCount = 0;



	public Flower(ImageIcon img) {
		super(img, World.FLOWER_SLEEP_TIME);
		zIndex = 3;
	}

	@Override
	public boolean canInhabitSpace(Cell cell) {
		boolean can = !(cell.hasClass(Flower.class) || cell.hasClass(Rock.class));
		return can;
	}



	@Override
	public Move move(MoveOptions options) {
		Move move = null;
		if(lifeCount == World.FLOWER_LIFE_TIME) {
			move = new Move(Move.Direction.CENTER, this.getClass());
			
		}
		lifeCount++;
		return move;
	}
	
	public Class<?> getClassToKill(Cell cell) {
		return null;
	}

	

	

}
