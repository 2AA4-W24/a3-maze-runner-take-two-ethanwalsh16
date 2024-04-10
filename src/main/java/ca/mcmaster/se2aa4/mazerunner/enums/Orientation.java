package ca.mcmaster.se2aa4.mazerunner.enums;

// Enum to track orientation of the solver as it traverses the maze.
public enum Orientation {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    /*
      Credit to Alexandre Lachance of 2AA4 teaching team for the turnRight and 
      turnLeft methods (a1-solution repo in 2AA4 organization on GitHub).
    */
    // Returns direction immediately to left of current direction.
	public Orientation turnRight() {
        switch (this) {
            case UP -> {
                return RIGHT;
            }
            case DOWN -> {
                return LEFT;
            }
            case LEFT -> {
                return UP;
            }
            case RIGHT -> {
                return DOWN;
            }
        }
        throw new IllegalStateException("Unexpected value: " + this);
    }
    // Returns direction immediately to right of current direction.
    public Orientation turnLeft() {
        switch (this) {
            case UP -> {
                return LEFT;
            }
            case DOWN -> {
                return RIGHT;
            }
            case LEFT -> {
                return DOWN;
            }
            case RIGHT -> {
                return UP;
            }
        }
        throw new IllegalStateException("Unexpected value: " + this);
    }
    // Returns opposite direction of current direction.
	public Orientation opposite() {
		switch (this) {
            case UP -> {
                return DOWN;
            }
            case DOWN -> {
                return UP;
            }
            case LEFT -> {
                return RIGHT;
            }
            case RIGHT -> {
                return LEFT;
            }
        }
        throw new IllegalStateException("Unexpected value: " + this);
	}
}