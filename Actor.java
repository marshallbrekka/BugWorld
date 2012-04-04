import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public abstract class Actor {
	protected int zIndex;
	private JLabel image;
	
	
	public Actor(ImageIcon img) {
		image = new JLabel(img);
		image.setSize(new Dimension(50,50));
		
	}
	

	
	public int getZIndex() {
		return zIndex;
	}
	
	public JLabel getImage() {
		return image;
	}

	
	

	
	public abstract boolean canInhabitSpace(Actor existingActor);
	
	
	
	
}
