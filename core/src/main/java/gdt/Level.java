package gdt;

import java.util.ArrayList;
import java.util.Iterator;
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
		
		Random rand = new Random();
		
		//try and find a decent amount of rooms to generate.
		int max = Math.max(width, height) / 5;
		int min = Math.abs(max / 2);
		
		int roomCount = rand.nextInt(max - min) + min;
		
		//generate rooms and position them
		for(int i=0; i < roomCount; i++) {
			Room room = Rooms.anyRoom();
			
			Position roomAssigned = null;
			for(int x=0; x < width - room.width && roomAssigned == null; x++) {
				for(int y=0; y < height - room.height && roomAssigned == null; y++) {
					if(isZoneFree(x, y, room.width, room.height)) {
						roomAssigned = new Position(x, y);
					}
				}
			}
			
			if(roomAssigned != null) {
				for(int c=0;c<room.contents.length; c++) {
					CellFactory factory = room.contents[c];
					
					if(factory == null) {
						continue;
					}
					
					int roomY = c / room.width;
					int roomX = c % room.width;
					
					int x = roomAssigned.x + roomX;
					int y = roomAssigned.y + roomY;
					
					Cell cell = factory.create(x, y);
					setCell(level, factory.create(x, y));
				}
			}
		}
		
		//fill null with rocks!
		for(int i=0; i< this.level.length; i++) {
			if(this.level[i] == null) {
				Position p = indexToPosition(i);
				this.level[i] = new Rock(p.x, p.y);
			}
		}
		
		//connect walls of rooms at random
		for(int y=0;y < height; y += 4) {
			Cell start = null;
			Cell ahead = cell(0, y);
			Cell before = null;
			Cell current;
			for(int x=0; x<width; x++) {
				current = ahead;
				ahead = cell(x+1, y);
				before = cell(x-1, y);
				
				if(start != null) {
					if(current instanceof Wall) {
						//thick walls?
						if(!(ahead instanceof Wall)) {
							//done!
							setCell(level, new Floor(start.x, start.y));
							
							for(int i=start.x; i<=current.x; i++) {							
								setCell(level, new Wall(i, y - 1));
								setCell(level, new Floor(i, y));
								setCell(level, new Wall(i, y + 1));
							}
							
							setCell(level, new Floor(current.x, current.y));
							
							start = null;
						}
					} else if(current != null && current.isSolid()) {
						//continue!!
					} else {
						start = null;
					}
				} else {
					if(current instanceof Wall && (ahead != null && ahead.isSolid()) && (before != null && !before.isSolid())) {
						//found a candidate.
						start = current;
					}
				}
			}
		}
		
		//connect walls of rooms at random
		for(int x=0;x < width; x += 4) {
			Cell start = null;
			Cell ahead = cell(x, 0);
			Cell before = null;
			Cell current;
			for(int y=0; y<height; y++) {
				current = ahead;
				ahead = cell(x, y+1);
				before = cell(x, y-1);
				
				if(start != null) {
					if(current instanceof Wall) {
						//thick walls?
						if(!(ahead instanceof Wall)) {
							//done!
							setCell(level, new Floor(start.x, start.y));
							
							for(int i=start.y; i<=current.y; i++) {							
								setCell(level, new Wall(x -1, i));
								setCell(level, new Floor(x, i));
								setCell(level, new Wall(x + 1, i));
							}
							
							setCell(level, new Floor(current.x, current.y));
							
							start = null;
						}
					} else if(current != null && current.isSolid()) {
						//continue!!
					} else {
						start = null;
					}
				} else {
					if(current instanceof Wall && (ahead != null && ahead.isSolid()) && (before != null && !before.isSolid())) {
						//found a candidate.
						start = current;
					}
				}
			}
		}
	}
	
	private boolean isZoneFree(int x, int y, int width, int height) {
		for(int i=x; i < x + width; i++) {
			for(int j=y; j < y + height; j++) {
				if(cell(i, j) != null) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private Position indexToPosition(int i) {
		if(i > this.level.length) {
			return null;
		}
		
		return new Position(i % width, i / width);
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
		List<Cell> result = new ArrayList<Cell>();
		
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
		List<Cell> result = new ArrayList<Cell>();
		
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
				if(!cell.isSolid() || includeSolid && cell.actor() == null) {
					spawnableCells.add(cell);
				}
			}
		}
		
		Random r = new Random();
		int cellIndex = r.nextInt(spawnableCells.size());
		
		return spawnableCells.get(cellIndex);
	}
	
	public void draw(Game game, SpriteBatch canvas) {
		for(int i=0;i<level.length;i++) {
			Cell c = level[i];
			
			if(c == null) {
				continue;
			}
			
			Texture tex = c.draw();
			
			if(tex != null) {
				
				Color color = canvas.getColor();
				
				//light radius = 10
				Color adjusted = adjustColorForRadius(color, c.distanceTo(game.player), 10);
				
				canvas.setColor(adjusted);
				canvas.draw(tex, c.x * Constants.TILE_SIZE, c.y * Constants.TILE_SIZE);
				canvas.setColor(color);
			}
		}
	}
	
	private Color adjustColorForRadius(Color c, double d, int treshold) {
		Color adjusted = new Color(c);
		
		if(d < treshold) {
			adjusted.a = (float) (1 - (d * 0.04f));
		} else {
			adjusted.a = 0.5f;
		}
		
		return adjusted;
	}
}


