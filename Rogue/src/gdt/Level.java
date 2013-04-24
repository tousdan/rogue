package gdt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level {
	private final Cell[] level;
	
	public final int width;
	public final int height;
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		this.level = new Cell[width * height];
		
		for(int w=0; w < width / 16; w++) {
			for(int h=0; h < height / 16 ; h++) {
				Room room = Rooms.anyRoom();
				
				if(room != null) {
					for(int c=0;c<room.contents.length; c++) {
						CellFactory factory = room.contents[c];
						
						if(factory == null) {
							continue;
						}
						
						int roomY = c / room.width;
						int roomX = c % room.width;
						
						int x = w * 16 + roomX;
						int y = h * 16 + roomY;
						
						setCell(level, factory.create(x, y));
					}
				}
			}
		}
	}
	
	private void setCell(Cell[] level, Cell cell) {
		level[cell.x + cell.y * width] = cell;
	}

	public Cell cell(int x, int y) {
		assert x < width && x > 0;
		assert y < height && y > 0;
		return level[x + y * width];
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
	
	public void draw(SpriteBatch canvas) {
		for(int i=0;i<level.length;i++) {
			Cell c = level[i];
			
			if(c == null) {
				continue;
			}
			
			Texture tex = c.draw();
			
			if(tex != null) {
				canvas.draw(tex, c.x * Constants.TILE_SIZE, c.y * Constants.TILE_SIZE);
			}
		}
	}
}
