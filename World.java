import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;


public class World {
	
	public static int FLOWER_LIFE_TIME;
	public static int BUG_FEEDING_TIME;
	public static int FLOWERS_FOR_NEW_BUG;
	public static int DEADLY_BUG_NEIGHBORS;
	public static int BUGS_FOR_NEW_CRITTER;
	public static int CRITTER_FEEDING_TIME;
	public static int FLOWER_SLEEP_TIME;
	public static int BUG_SLEEP_TIME;
	public static int CRITTER_SLEEP_TIME;
	
	private int cycle = 0;
	
	public static ImageIcon FLOWER_ICON, ROCK_ICON, BUG_ICON, CRITTER_ICON;
	private ArrayList<HasLife> actors = new ArrayList<HasLife>();
	
	private Cell[][] cells;
	
	private VisibleWorld visible;
	public boolean running = false;
	public boolean paused = false;


	
	
	public World(int rows, int cols, VisibleWorld visual) {
		running = true;
		visible = visual;
		cells = new Cell[rows][cols];
		for(int row = 0; row < cells.length; row++) {
			for(int col = 0; col < cells[row].length; col++) {
				cells[row][col] = new Cell(row, col);
			}
		}
		
	}
	
	public void startActor(Actor a) {
		if(a instanceof HasLife) {
			new Thread(a).start();
		}
	}
	
	public void startAll() {
		for(int i = 0; i < actors.size(); i++) {
			new Thread((Actor) actors.get(i)).start();
		}
	}
	
	
	public void updateView() {
		for(int row = 0; row < cells.length; row++) {
			for(int col = 0; col < cells[0].length; col++) {
				visible.updatePane(cells[row][col]);
			}
		}
	}
	
	
	private Cell getCell(int row, int col) {
		if(row < 0) {
			row = cells.length + row;
		} else if(row >= cells.length) {
			row = row - cells.length;
		}
		
		if(col < 0) {
			col = cells[0].length + col;
		} else if(col >= cells[0].length) {
			col = col - cells[0].length;
		}
		
		return cells[row][col];
	}
	
	public MoveOptions getMoveOptions(Cell cell) {
		int row = cell.getRow(), col = cell.getCol();
		Cell up, down, left, right, center;
		up = getCell(row - 1, col);
		down = getCell(row + 1, col);
		left = getCell(row, col - 1);
		right = getCell(row, col + 1);
		center = getCell(row, col);
		return new MoveOptions(up, right, down, left, center);
	}
	
	
	/** 
	 * returns false if there is no more room for this type of actor
	 * @param a
	 * @return
	 */
	public boolean addActor(Actor a) {
		ArrayList<Integer> emptyRows = new ArrayList<Integer>(cells.length);
		ArrayList<ArrayList<Integer>> emptyCells = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp;
		for(int i = 0; i < cells.length; i++) {
			temp = getEmptyCells(i, a);
			if(temp != null) {
				emptyRows.add(i);
				emptyCells.add(temp);
			}
		}
		
		if(emptyRows.size() == 0) return false;
		Random rand = new Random();
		int max = emptyRows.size();
		int row = rand.nextInt(max);
		
		rand = new Random();
		max = emptyCells.get(row).size();
		int col = rand.nextInt(max);
		
		cells[row][col].add(a);
		a.setCell(cells[row][col]);
		if(a instanceof HasLife) {
			actors.add((HasLife) a);
		}
		
		return true;
		
	}
	
	
	// i don't think i ever use this
	public boolean addActor(Actor a, int row, int col) {
		Cell existing = cells[row][col];
		if(isCellEmpty(a, existing)) {
			existing.add(a);
			//allActors.add(a);
			return true;
		}
		return false;
	}
	
	public void redrawCell(Cell cell) {
		visible.updatePane(cell);
	}
	
	private ArrayList<Integer> getEmptyCells(int row, Actor a) {
		ArrayList<Integer> spaces = new ArrayList<Integer>(cells[0].length);
		Cell temp;
		boolean empty;
		
		for(int i = 0; i < cells[row].length; i++) {
			temp = cells[row][i];
			empty = isCellEmpty(a, temp);
			
			if (empty) {
				spaces.add(i);
			}
		}
		
		if(spaces.size() == 0) {
			return null;
		} else {
			return spaces;
		}
	}
	
	
	
	private boolean isCellEmpty(Actor a, Cell cell) {
		return a.canInhabitSpace(cell);
	}
}
