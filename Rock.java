import javax.swing.ImageIcon;


public class Rock extends Actor {
	
	
	
	public Rock(ImageIcon img) {
		super(img,  0);
		zIndex = 1;
	}

	@Override
	public boolean canInhabitSpace(Cell cell) {
		return !(cell.hasClass(Rock.class) || cell.hasClass(Flower.class));
	}

	@Override
	public Move move(MoveOptions options) {
		return null;
	}

}
