package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Floor implements Cell {

	private Texture texture;
	private Actor content;
	
	public Floor() {
		Pixmap map = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
		map.setColor(Color.GRAY);
		map.fillRectangle(0, 0, 8, 8);
		
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
