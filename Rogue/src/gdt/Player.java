package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Player implements Actor {
	private Texture character;
	
	public Player() {
		Pixmap map = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
		map.setColor(Color.RED);
		map.drawLine(0, 0, 8, 8);
		map.setColor(Color.ORANGE);
		map.drawLine(8, 0, 0, 8);
		
		character = new Texture(map);
	}
	@Override
	public Texture draw() {
		return character;
	}
	@Override
	public boolean interact(RogueObject requestor, String intent) {
		return false;
	}
	@Override
	public void setNewLocation(Cell location) {
		
	}

}
