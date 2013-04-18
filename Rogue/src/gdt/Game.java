package gdt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Game implements ApplicationListener {
	Texture background;
	Texture frostshock;
	Rectangle frostshock_loc;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	public static final int CAMERA_WIDTH = 480;
	public static final int CAMERA_HEIGHT = 320;
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;
	
	@Override
	public void create() {
		background = new Texture(Gdx.files.internal("assets/raglan01.jpg"));
		frostshock = new Texture(Gdx.files.internal("assets/frostshock.png"));
		
		frostshock_loc = new Rectangle(CAMERA_WIDTH / 2 , CAMERA_HEIGHT / 2, 64, 64);
		
		// TODO Auto-generated method stub
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		
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
		
		centerCamera();
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    	    
	    camera.update();
	    
	    batch.setProjectionMatrix(camera.combined);
	    batch.begin();
	    batch.draw(background, 0, 0);
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
		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			frostshock_loc.y += 5;
		} 
		
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
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
	
	private void centerCamera() {
		float new_x = frostshock_loc.x;
		if(new_x < CAMERA_WIDTH / 2) {
			new_x = CAMERA_WIDTH / 2;
		} else if(new_x > SCREEN_WIDTH - CAMERA_WIDTH / 2) {
			new_x = SCREEN_WIDTH - CAMERA_WIDTH / 2;
		}
		
		float new_y = frostshock_loc.y;
		if(new_y < CAMERA_HEIGHT / 2) {
			new_y = CAMERA_HEIGHT / 2;
		} else if(new_y > SCREEN_HEIGHT - CAMERA_HEIGHT / 2) {
			new_y = SCREEN_HEIGHT - CAMERA_HEIGHT / 2;
		}
		
		camera.position.set(new_x, new_y, 1); 
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

}
