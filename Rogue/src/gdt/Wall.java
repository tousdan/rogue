package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Wall implements Cell{
	private Texture texture;
	
	public Wall() {
		Pixmap map = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
		map.setColor(Color.DARK_GRAY);
		map.fillRectangle(0, 0, 8, 8);
		
		texture = new Texture(map);
	}
	
	@Override
	public Texture draw() {
		return texture;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public boolean interact(RogueObject requestor, String intent) {
		return false;
	}
	
	
	
}
