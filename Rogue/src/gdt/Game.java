package gdt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Game implements ApplicationListener {
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;
	
	public static final int CAMERA_WIDTH = 640;
	public static final int CAMERA_HEIGHT = 460;
	
	private static final int LEVEL_WIDTH = 256;
	private static final int LEVEL_HEIGHT = 256;
	
	GameObject[] level;
	
	Texture frostshock;
	Rectangle frostshock_loc;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	
	
	@Override
	public void create() {
		generateLevel();
		
		frostshock = new Texture(Gdx.files.internal("assets/frostshock.png"));
		
		frostshock_loc = new Rectangle(10, 10, 64, 64);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		batch = new SpriteBatch();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	boolean middlePressed = false;
	@Override
	public void render() {
		handleInput();
		handleCameraInput();
		
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    	    
	    camera.update();
	    
	    batch.setProjectionMatrix(camera.combined);
	    batch.begin();
	    for (int i = 0; i < level.length; i++) {
	    	GameObject o = level[i];
	    	
	    	if(o == null) {
	    		continue;
	    	}
			Texture tex = level[i].draw();
			
			if(tex != null) {
				int x = i % LEVEL_WIDTH * 8;
				int y = i / LEVEL_WIDTH * 8;
				
			
				batch.draw(tex, x, y);
			}
		}
	    batch.draw(frostshock, frostshock_loc.x, frostshock_loc.y);
	    batch.end();
	    
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private void handleInput() {
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			frostshock_loc.x -= 5;
		} 
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			frostshock_loc.x += 5;
		} 
		
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			frostshock_loc.y += 5;
		} 
		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			frostshock_loc.y -= 5;
		}
		
		if(frostshock_loc.x <= 0) {
			frostshock_loc.x = 0;
		} else if(frostshock_loc.x >= SCREEN_WIDTH - frostshock_loc.width) {
			frostshock_loc.x = SCREEN_WIDTH - frostshock_loc.width;
		}
		
		if(frostshock_loc.y <= 0) {
			frostshock_loc.y = 0;
		} else if(frostshock_loc.y >= SCREEN_HEIGHT - frostshock_loc.height) {
			frostshock_loc.y = SCREEN_HEIGHT - frostshock_loc.height;
		}
	}
	
	private void handleCameraInput() {
		if(Gdx.input.isKeyPressed(Keys.X)) {
			camera.zoom += 0.2;
		}
		
		if(Gdx.input.isKeyPressed(Keys.Z)) {
			camera.zoom -= 0.2;
		}
		
		camera.zoom = Math.max(camera.zoom, 1);
	}
	
	private void generateLevel() {
		level = new GameObject[LEVEL_WIDTH * LEVEL_HEIGHT];
		
		for(int w=0; w < LEVEL_WIDTH / 16; w++) {
			for(int h=0; h < LEVEL_HEIGHT / 16 ; h++) {
				Room room = Rooms.anyRoom();
				
				if(room != null) {
					for(int c=0;c<room.contents.length; c++) {
						GameObject roomPart = room.contents[c];
						
						int y = c / room.width;
						int x = c % room.width;
						
						level[w * 16 + x + (h * 16 + y) * LEVEL_WIDTH] = roomPart;
					}
				}
			}
		}
	}

}
