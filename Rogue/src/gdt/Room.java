package gdt;

public class Room {

	public int width;
	public int height;
	public GameObject[] contents;
	
	public Room(int width, int height, GameObject[] contents) {
		this.width = width;
		this.height = height;
		this.contents = contents;
		
		assert this.contents.length == width * height;
	}
}
