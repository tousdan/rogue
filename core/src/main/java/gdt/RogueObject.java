package gdt;

import com.badlogic.gdx.graphics.Texture;

public interface RogueObject {
	public Texture draw();
	public boolean interact(Game game, GameEngine engine, Actor requestor, String intent);
}
