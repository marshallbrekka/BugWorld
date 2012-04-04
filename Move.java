
public class Move {
	public enum Direction { UP, RIGHT, DOWN, LEFT, CENTER };
	
	private Direction move;
	private Actor toConsumeOnMove;
	private Actor toCreate;
	
	public Move(Direction move, Actor consume) {
		this(move, consume, null);
	}
	
	public Move(Direction move, Actor consume, Actor create) {
		this.move = move;
		toConsumeOnMove = consume;
		toCreate = create;
	}
	
	public Direction getMove() {
		return move;
	}
	
	public Actor getActorToConsume() {
		return toConsumeOnMove;
	}
	
	public Actor getActorToCreate() {
		return toCreate;
	}
	

}
