package gdt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
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
		if(!inBounds(x, y))
			return null;
		return level[x + y * width];
	}
	public Cell cell(Position location) {
		return cell(location.x, location.y);
	}
	public Cell cell(Locatable locatable) {
		return cell(locatable.location());
	}
	
	public boolean inBounds(int x, int y) {
		return x < width && x >= 0
			&& y < height && y >= 0;
	}
	public boolean inBounds(Position location) {
		return inBounds(location.x, location.y);
	}
	public boolean inBounds(Locatable locatable) {
		return inBounds(locatable.location());
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
	
	public void draw(Cell playerLocation, SpriteBatch canvas) {
		for(int i=0;i<level.length;i++) {
			Cell c = level[i];
			
			if(c == null) {
				continue;
			}
			
			
			
			
			Texture tex = c.draw();
			
			
			if(tex != null) {
				
				int xDistance = Math.abs(c.x - playerLocation.x);
				int yDistance = Math.abs(c.y - playerLocation.y);
				
				int closestDistance = Math.max(xDistance, yDistance);
				
				Color color = canvas.getColor();
				
				//light radius = 10
				Color adjusted = adjustColorForRadius(color, closestDistance, 10);
				
				canvas.setColor(adjusted);
				
				canvas.draw(tex, c.x * Constants.TILE_SIZE, c.y * Constants.TILE_SIZE);
				
				canvas.setColor(color);
			}
		}
	}
	
	private Color adjustColorForRadius(Color c, int distance, int treshold) {
		Color adjusted = new Color(c);
		
		if(distance < treshold) {
			adjusted.a = 1 - (distance * 0.04f);
		} else {
			adjusted.a = 0.5f;
		}
		
		return adjusted;
	}
}


