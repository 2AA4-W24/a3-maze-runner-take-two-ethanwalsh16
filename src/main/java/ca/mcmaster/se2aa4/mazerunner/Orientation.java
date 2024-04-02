package ca.mcmaster.se2aa4.mazerunner;

public enum Orientation {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    // Credit to Alexandre Lachance of 2AA4 teaching team for the turnRight and turnLeft methods.
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