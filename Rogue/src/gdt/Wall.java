package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Wall extends GameObject {
	private Texture texture;
	
	public Wall() {
		Pixmap map = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
		map.setColor(Color.GRAY);
		map.fillRectangle(0, 0, 8, 8);
		
		texture = new Texture(map);
	}
	
	@Override
	public Texture draw() {
		return texture;
	}
	
}
