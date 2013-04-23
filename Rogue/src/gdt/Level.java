package gdt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Level {
	private final Cell[] level;
	private final int width;
	private final int height;
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		this.level = new Cell[width * height];
		
		for(int w=0; w < width / 16; w++) {
			for(int h=0; h < height / 16 ; h++) {
				Room room = Rooms.anyRoom();
				
				if(room != null) {
					for(int c=0;c<room.contents.length; c++) {
						Cell roomPart = room.contents[c];
						
						int y = c / room.width;
						int x = c % room.width;
						
						level[w * 16 + x + (h * 16 + y) * width] = roomPart;
					}
				}
			}
		}
	}
	
	public Cell findRandomCell(boolean includeSolid) {
		List<Cell> spawnableCells = new ArrayList<Cell>();
		
		for (Cell cell : level) {
			if(cell != null) {
				if(!cell.isSolid() || includeSolid) {
					spawnableCells.add(cell);
				}
			}
		}
		
		Random r = new Random();
		int cellIndex = r.nextInt(spawnableCells.size());
		
		return spawnableCells.get(cellIndex);
	}
	
	public Vector2 getCoords(Cell cell) {
		if(cell == null) return null;
		
		for(int i=0;i<level.length;i++) {
			if(cell == level[i]) {
				return indexToCoords(i);
			}
		}
		
		return null;
	}
	
	public void draw(SpriteBatch canvas) {
		for(int i=0;i<level.length;i++) {
			Cell c = level[i];
			
			if(c == null) {
				continue;
			}
			
			Texture tex = c.draw();
			
			if(tex != null) {
				Vector2 coords = indexToCoords(i);
				
			
				canvas.draw(tex, coords.x, coords.y);
			}
		}
	}
	
	private Vector2 indexToCoords(int i) {
		return new Vector2(i % width * 8 , i / width * 8);
	}
}
