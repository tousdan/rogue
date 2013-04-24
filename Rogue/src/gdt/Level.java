package gdt;

import java.util.ArrayList;
import java.util.Iterator;
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
	
	public Cell getAdjacentCell(Locatable locatable, Direction direction) {
		Position location = locatable.location();
		Position adjacent = location.translated(direction.dx, direction.dy);
		
		if(inBounds(adjacent))
			return cell(adjacent);
		else
			return null;
	}
	
	public List<Cell> getAdjacentCells(Locatable locatable, Direction direction, int count) {
		List<Cell> result = new ArrayList<>();
		
		if(count < 1) {
			return result;
		}
		
		Position initial = locatable.location();
		Position current = initial.translated(direction.dx, direction.dy);
		while(count --> 0 && inBounds(current)) {
			result.add(cell(current));
			current = current.translated(direction.dx, direction.dy);
		}
		
		return result;
	}
	
	public List<Cell> getAdjacentCells(Locatable locatable, int radius, boolean round) {
		List<Cell> result = new ArrayList<>();
		
		if(radius < 1) {
			return result;
		}
		
		Position initial = locatable.location();
		Position topLeft = initial.translated(-radius, -radius);
		Position bottomRight = initial.translated(radius, radius);
		
		for(int x=topLeft.x; x < bottomRight.x; x++) {
			for(int y = topLeft.y; y < bottomRight.y; y++) {
				Position current = new Position(x, y);
				if(inBounds(current)) {
					result.add(cell(current));
				}
			}
		}
		
		if(round) {
			Cell current = null;
			for(Iterator<Cell> it=result.iterator(); it.hasNext(); current=it.next()) {
				if(current.distanceTo(initial) > radius)
					it.remove();
			}
		}
		
		return result;
		
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
