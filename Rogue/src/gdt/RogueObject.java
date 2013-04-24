package gdt;

import com.badlogic.gdx.graphics.Texture;

public interface RogueObject {
	public Texture draw();
	public boolean interact(GameEngine engine, Actor requestor, String intent);
}
