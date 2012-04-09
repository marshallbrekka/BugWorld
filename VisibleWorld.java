import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class VisibleWorld extends JPanel {
	private JLayeredPane[][] panes;
	
	
	public VisibleWorld(int rows, int cols) {
		panes = new JLayeredPane[rows][cols];
		GridLayout layout = new GridLayout(rows, cols);
		
		setLayout(layout);
		for(int x = 0; x < rows; x++) {
			for(int y = 0; y < cols; y++) {
				JLayeredPane pane = createPane(null);
				this.add(pane);
				panes[x][y] = pane;
			}
		}
		
	}
	
	public void updatePane(Cell cell) {
		updatePane(panes[cell.getRow()][cell.getCol()], cell);
	}
	

	
	private JLayeredPane createPane(Cell cell) {
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(50, 50));
		
		if(cell != null) updatePane(layeredPane, cell);
		
		return layeredPane;
		
	}
	
	private void updatePane(JLayeredPane pane, Cell cell) {
		pane.removeAll();
		JLabel label;
		Actor[] temp = cell.getActors();
		for(int i = 0; i < temp.length; i++) {
			label = temp[i].getImage();
			pane.add(label, temp[i].getZIndex());
		}
		pane.repaint();
		this.repaint();
		this.setVisible(true);
	}
	
}
