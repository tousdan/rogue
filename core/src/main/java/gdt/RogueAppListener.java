package gdt;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RogueAppListener extends InputAdapter implements ApplicationListener {
	
	private static final int SCREEN_WIDTH = 1024;
	private static final int SCREEN_HEIGHT = 768;
	
	private static final int CAMERA_WIDTH = 640;
	private static final int CAMERA_HEIGHT = 640;
	
	private Game game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@Override
	public void create() {
		game = new Game();

        game.generateLevel();;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		batch = new SpriteBatch();
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(true, width, height);
	}

	private Direction directionToMove = null;
	private boolean hasMoveInput = false;
    private boolean hasJumpMoveInput = false;
	
	static Map<Integer, Direction> movementKeys = new HashMap<Integer, Direction>();
	
	{
		movementKeys.put(Keys.UP, Direction.UP);
		movementKeys.put(Keys.DOWN, Direction.DOWN);
		movementKeys.put(Keys.RIGHT, Direction.RIGHT);
		movementKeys.put(Keys.LEFT, Direction.LEFT);
		
		movementKeys.put(Keys.NUM_1, Direction.DOWN_LEFT);
		movementKeys.put(Keys.NUM_2, Direction.DOWN);
		movementKeys.put(Keys.NUM_3, Direction.DOWN_RIGHT);
		movementKeys.put(Keys.NUM_4, Direction.LEFT);
		
		movementKeys.put(Keys.NUM_6, Direction.RIGHT);
		movementKeys.put(Keys.NUM_7, Direction.UP_LEFT);
		movementKeys.put(Keys.NUM_8, Direction.UP);
		movementKeys.put(Keys.NUM_9, Direction.UP_RIGHT);
	}
	
	@Override
	public boolean keyDown(int key) {
		if(movementKeys.containsKey(key)) {
            hasJumpMoveInput = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)
                            || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT);

			directionToMove = movementKeys.get(key);
			hasMoveInput = true;

			return true;
		} else if(Keys.SPACE == key) {
            game.generateLevel();
        }
		
		return false;
	}
	
	private void handleInput() {
		if(hasMoveInput) {
			game.moveActor(game.player, directionToMove, hasJumpMoveInput ? 5 : 1);
			game.runGameStep();
			hasMoveInput = false;
            hasJumpMoveInput = false;
            System.out.println(game.player.location());
		}
	}
	
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
}
