
public class MoveOptions {
	public Cell[] options = new Cell[5];
	public MoveOptions(Cell top, Cell right, Cell bottom, Cell left, Cell center ) {
		options[Move.Direction.UP.ordinal()] = top;
		options[Move.Direction.RIGHT.ordinal()] = right;
		options[Move.Direction.DOWN.ordinal()] = bottom;
		options[Move.Direction.LEFT.ordinal()] = left;
		options[Move.Direction.CENTER.ordinal()] = center;
	}
	
	public Cell getOption(Move.Direction d) {
		return options[d.ordinal()];
	}
}
