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
	
	private int cycle = 0;
	
	public static ImageIcon FLOWER_ICON, ROCK_ICON, BUG_ICON, CRITTER_ICON;
	
	private Cell[][] actors;
	
	private VisibleWorld visible;


	
	
	public World(int rows, int cols, VisibleWorld visual) {
		visible = visual;
		actors = new Cell[rows][cols];
		for(int y = 0; y < actors.length; y++) {
			for(int x = 0; x < actors[y].length; x++) {
				actors[y][x] = new Cell();
			}
		}
		
	}
	
	public void runRound() {
		cycle++;
		Actor a;
		HasLife hl;
		for(int row = 0; row < actors.length; row++) {
			Cell cell;
			for(int col = 0; col < actors[0].length; col++) {
				cell = actors[row][col];
				for(int i = 0; i < cell.size(); i++) {
					a = cell.get(i);
					if(a instanceof HasLife) {
						hl = (HasLife) a;
						if(hl.getCycle() != cycle) {
							hl.setCycle(cycle);
							Move move = hl.move(buildMoveOptions(row, col));
							if(move != null) {
								Cell newPlace = getCellFromMove(row, col, move.getMove());
								Actor toKill = move.getActorToConsume();
								if(toKill != null) newPlace.remove(toKill);
								
								Actor toCreate = move.getActorToCreate();
								if(toCreate != null) {
									cell.add(toCreate);
								}
								cell.remove((Actor) hl);
								if(newPlace != cell) {
									newPlace.add((Actor) hl);
								}
								
								boolean t = false;
							}
						}
						
						
						
						
					}
					
				}
			}
		}
		
		updateView();
	}
	
	public void updateView() {
		for(int row = 0; row < actors.length; row++) {
			for(int col = 0; col < actors[0].length; col++) {
				visible.updatePane(row, col, actors[row][col]);
			}
		}
	}
	
	private Cell getCellFromMove(int row, int col, Move.Direction move) {
		if(move == Move.Direction.UP) row--;
		if(move == Move.Direction.DOWN) row++;
		if(move == Move.Direction.LEFT) col--;
		if(move == Move.Direction.RIGHT) col++;
		return getCell(row, col);
	}
	
	private Cell getCell(int row, int col) {
		if(row < 0) {
			row = actors.length + row;
		} else if(row >= actors.length) {
			row = row - actors.length;
		}
		
		if(col < 0) {
			col = actors[0].length + col;
		} else if(col >= actors[0].length) {
			col = col - actors[0].length;
		}
		
		return actors[row][col];
	}
	
	private MoveOptions buildMoveOptions(int row, int col) {
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
		ArrayList<Integer> emptyRows = new ArrayList<Integer>(actors.length);
		ArrayList<ArrayList<Integer>> emptyCells = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp;
		for(int i = 0; i < actors.length; i++) {
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
		
		actors[row][col].add(a);
		//allActors.add(a);
		return true;
		
	}
	
	
	// i don't think i ever use this
	public boolean addActor(Actor a, int row, int col) {
		Cell existing = actors[row][col];
		if(isCellEmpty(a, existing)) {
			existing.add(a);
			//allActors.add(a);
			return true;
		}
		return false;
	}
	
	private ArrayList<Integer> getEmptyCells(int row, Actor a) {
		ArrayList<Integer> spaces = new ArrayList<Integer>(actors[0].length);
		Cell temp;
		boolean empty;
		
		for(int i = 0; i < actors[row].length; i++) {
			temp = actors[row][i];
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
		boolean empty = true;
		for(int y = 0; y < cell.size(); y++) {
			Actor tempActor = cell.get(y);
			if(!a.canInhabitSpace(tempActor)) {
				empty = false;
				break;
			}
		}
		return empty;
	}
}
