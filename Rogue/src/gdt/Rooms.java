package gdt;

public class Rooms {
	private static Wall w = new Wall();
	private static GameObject _ = new GameObject();
	
	public static Room SQUARE = new Room(8, 8, new GameObject[] {
			w, w, w, w, w, w, w, w,
			w, _, _, _, _, _, _, w,
			w, _, _, _, _, _, _, w,
			w, _, _, _, _, _, _, w,
			w, _, _, _, _, _, _, w,
			w, _, _, _, _, _, _, w,
			w, _, _, _, _, _, _, w,
			w, w, w, w, w, w, w, w
	});
	
	public static Room anyRoom() {
		return SQUARE;
	}
}
