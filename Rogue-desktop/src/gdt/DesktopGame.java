package gdt;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopGame {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Rogue";
		config.useGL20 = true;
		config.width = 480;
		config.height = 320;
		
		new LwjglApplication(new Game(), config);
	}
}
