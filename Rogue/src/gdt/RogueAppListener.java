package gdt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class RogueAppListener implements ApplicationListener {
	
	private static final int SCREEN_WIDTH = 1024;
	private static final int SCREEN_HEIGHT = 768;
	
	private static final int CAMERA_WIDTH = 640;
	private static final int CAMERA_HEIGHT = 460;
	
	private Game game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@Override
	public void create() {
		game = new Game();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		batch = new SpriteBatch();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(true, width, height);
	}

	boolean middlePressed = false;
	@Override
	public void render() {
		/*handleInput();
		handleCameraInput();*/
		
		handleInput();
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    	    
	    camera.update();
	    
	    batch.setProjectionMatrix(camera.combined);
	    batch.begin();
	    	
	    game.draw(batch);
		
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

		Player pl = game.player;
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			game.moveActor(pl, game.getAdjacentCell(pl, Direction.UP));
		} 
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			game.moveActor(pl, game.getAdjacentCell(pl, Direction.DOWN));
		} 
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			game.moveActor(pl, game.getAdjacentCell(pl, Direction.RIGHT));
		} 
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			game.moveActor(pl, game.getAdjacentCell(pl, Direction.LEFT));
		} 
	}	
	
	
	/*private void handleInput() {
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
	}*/
}
