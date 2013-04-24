package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Floor extends Cell {

	private Texture texture;
	private Actor content;
	
	public Floor(int x, int y) {
		super(x, y);
		
		Pixmap map = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
		map.setColor(Color.GRAY);
		map.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
		
		texture = new Texture(map);
	}
	
	@Override
	public Texture draw() {
		return texture;
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public boolean interact(RogueObject requestor, String intent) {
		if(Intents.MOVE.equals(intent)) {
			if(requestor instanceof Actor) {
				if(content == null) {
					content = (Actor) requestor;
					return true;
				}
			}
			
			return false;
		}
		
		return false;
	}

}
