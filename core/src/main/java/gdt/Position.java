package gdt;

public class Position implements Locatable {
	public final int x;
	public final int y;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Position location() {
		return this;
	}
	public Position translated(int dx, int dy) {
		return new Position(x + dx, y + dy);
	}
	public double distanceTo(Position other) {
		return Math.sqrt(square(other.x - x) + square(other.y - y));
	}
	public double distanceTo(Locatable locatable) {
		return distanceTo(locatable.location());
	}
	private int square(int x) { return x * x; }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
