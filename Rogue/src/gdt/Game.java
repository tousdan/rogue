package gdt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Game implements ApplicationListener {
	Texture frostshock;
	Rectangle slider;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	public static final int WIDTH = 480;
	public static final int HEIGHT = 320;
	
	@Override
	public void create() {
		frostshock = new Texture(Gdx.files.internal("assets/frostshock.png"));
		// TODO Auto-generated method stub
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		
		batch = new SpriteBatch();
		
		slider = new Rectangle();
		slider.x = WIDTH / 2 - 32 / 2;
		slider.y = HEIGHT / 2;
		slider.width = 64;
		slider.height = 64;
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    
	    camera.update();
	    
	    batch.setProjectionMatrix(camera.combined);
	    batch.begin();
	    batch.draw(frostshock, slider.x, slider.y);
	    batch.end();
	    
	    slider.x += 1;
	    
	    if(slider.x > WIDTH) {
	    	slider.x = 0;
	    }
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
