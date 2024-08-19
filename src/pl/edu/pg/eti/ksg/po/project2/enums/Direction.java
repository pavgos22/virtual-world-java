package pl.edu.pg.eti.ksg.po.project2.enums;

public enum Direction {
    LEFT(0),
    RIGHT(1),
    UP(2),
    DOWN(3),
    NO_DIRECTION(4);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
