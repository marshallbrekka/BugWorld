import javax.swing.ImageIcon;


public class Rock extends Actor {
	
	
	
	public Rock(ImageIcon img) {
		super(img);
		zIndex = 1;
	}

	@Override
	public boolean canInhabitSpace(Actor existingActor) {
		Object theClass = existingActor.getClass();
		if(theClass.equals(this.getClass()) || theClass.equals(Flower.class)) {
			return false;
		}
		return true;
	}

}
