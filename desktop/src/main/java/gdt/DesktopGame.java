package gdt;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopGame {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Rogue";
		config.useGL20 = true;
		config.width = 1024;
		config.height = 768;
		
		new LwjglApplication(new RogueAppListener(), config);
	}
}
