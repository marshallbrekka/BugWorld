
public class Move {
	public enum Direction { UP, RIGHT, DOWN, LEFT, CENTER };
	
	private Direction move;
	private Class<?> toConsumeOnMove;
	private Actor toCreate;
	
	public Move(Direction move, Class<?> consume) {
		this(move, consume, null);
	}
	
	public Move(Direction move, Class<?> consume, Actor create) {
		this.move = move;
		toConsumeOnMove = consume;
		toCreate = create;
	}
	
	public Direction getMove() {
		return move;
	}
	
	public Class<?> getClassToConsume() {
		return toConsumeOnMove;
	}
	
	public Actor getActorToCreate() {
		return toCreate;
	}
	

}
