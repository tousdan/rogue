package gdt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rooms {
	private static List<Room> rooms = new ArrayList<Room>();
	private static Random rand = new Random();
	
	public static Room SQUARE = buildRoom(8, 8,
			"wwwwwwww" +
			"w______w" +
			"w______w" +
			"w______w" +
			"w______w" +
			"w______w" +
			"w______w" +
			"wwwwwwww"
	);
	
	public static Room CAVE = buildRoom(8, 8, 
		   "wwwwwwww" +
		   "w______w" +
		   "w______w" +
		   " ww____w" +
		   "   ww__w" +
		   "    w_w " +
		   "    w_w " +
		   "     w  " 
	);

    public static Room roomFor(int width, int height) {
        if(width < 5 || height < 5) {
            return null;
        }

        CellFactory cf = new CellFactory() {
            @Override
            public Cell create(int x, int y) {
                return new Floor(x, y);
            }
        };

        CellFactory[] contents = new CellFactory[(width - 2) * (height - 2)];

        for(int i=0;i<contents.length;i++) {
            contents[i] = cf;
        }

        return new Room(width - 2, height - 2, contents);
    }
	
	public static Room anyRoom() {		
		return rooms.get(rand.nextInt(rooms.size()));
	}
	
	private static Room buildRoom(int width, int height, String contentsAsString) {
		TextToCellFactory fact = new TextToCellFactory();
		
		CellFactory[] contents = new CellFactory[width * height];
		char[] asChars = contentsAsString.toCharArray();
		
		if(contents.length != asChars.length) {
			throw new RuntimeException("Room width and height does not match the description of the room");
		}
		
		for (int i = 0; i < asChars.length; i++) {
			contents[i] = fact.build(asChars[i]);
		}
		
		Room r = new Room(width, height, contents);
		
		rooms.add(r);
		
		return r;
	}
	
	
	static class TextToCellFactory {
		public CellFactory build(char c) {
			switch(c) {
				case 'w':
					return new CellFactory() {
						public Cell create(int x, int y) {
							return new Wall(x, y);
						}
					};
				case '_':
					return new CellFactory() {
						public Cell create(int x, int y) {
							return new Floor(x, y);
						}
					};
				default:
					return null;
				 
			}
		}
	}
	
}
