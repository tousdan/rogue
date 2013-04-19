package gdt;

import java.util.Random;

public class Rooms {
	private static Random rand = new Random();
	
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
	
	public static Room CAVE = new Room(8, 8, new GameObject[] {
			w, w, w, w, w, w, w, w,
			w, _, _, _, _, _, _, w,
			w, _, _, _, _, _, _, w,
			_, w, w, _, _, _, _, w,
			_, _, _, w, w, _, _, w,
			_, _, _, _, w, _, w, _,
			_, _, _, _, w, _, w, _,
			_, _, _, _, _, w, _, _
	});
	
	public static Room anyRoom() {
		Room[] rooms = new Room[] {
			SQUARE, CAVE
		};
		
		return rooms[rand.nextInt(rooms.length)];
	}
}
