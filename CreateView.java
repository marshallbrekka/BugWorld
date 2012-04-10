
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* Marshall Brekka */
public class CreateView extends JPanel {
	private JTextField[] fields = new JTextField[12];
	private String[] labels = {"Rows", "Columns", "# Rocks", 
			"# Flowers", "# Bugs", "# Critters", "Flower Life Time", 
			"Bug Feeding Time", "Flowers for new Bug", "Deadly Bug Neighbors",
			"Bugs for new Critters","Critter Feeding Time"};
	
	private Main m;
	
	public CreateView(Main m) {
		super();
		this.m = m;

		//this.setSize(m.MIN_WIDTH, m.MIN_HEIGHT);
		this.setLayout(new GridLayout(13,2));
		for(int i = 0; i < labels.length; i++) {
			
			add(new JLabel(labels[i]));
			if(i < fields.length) {
				fields[i] = new JTextField();
				add(fields[i]);
			}
		}
	
		JButton add = new JButton("start");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				CreateView.this.start();
			}
		});
		
		add(add);
	}
	
	private void start() {
		int rocks = get(2),
			flowers = get(3),
			bugs = get(4),
			critters = get(5),
			rows = get(0),
			cols = get(1),
			bugFeed = get(7),
			newCritter = get(10),
			critterFeedTime = get(11),
			deadlyNeighbors = get(9),
			flowerLife = get(6),
			newBug = get(8);
		m.createWorld(rocks, flowers, bugs, critters, rows, cols, bugFeed, newCritter, critterFeedTime, deadlyNeighbors, flowerLife, newBug);
	}
	
	
	
	private int get(int index) {
		return new Integer(fields[index].getText().trim());
	}
	
	
}
