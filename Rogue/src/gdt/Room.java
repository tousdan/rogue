package gdt;

public class Room {

	public int width;
	public int height;
	public Cell[] contents;
	
	public Room(int width, int height, Cell[] contents) {
		this.width = width;
		this.height = height;
		this.contents = contents;
		
		assert this.contents.length == width * height;
	}
}
