import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class VisibleWorld extends JPanel {
	private JLayeredPane[][] panes;
	
	
	public VisibleWorld(int row, int col) {
		panes = new JLayeredPane[row][col];
		GridLayout layout = new GridLayout(row, col);
		
		setLayout(layout);
		for(int x = 0; x < row; x++) {
			for(int y = 0; y < col; y++) {
				JLayeredPane pane = createPane(null);
				this.add(pane);
				panes[x][y] = pane;
			}
		}
		
	}
	
	public void updatePane(int row, int col, Cell cell) {
		updatePane(panes[row][col], cell);
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
		for(int i = 0; i < cell.size(); i++) {
			label = cell.get(i).getImage();
			pane.add(label, cell.get(i).getZIndex());
		}
		pane.repaint();
		this.repaint();
		this.setVisible(true);
	}
	
}
