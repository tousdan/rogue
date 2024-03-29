package gdt;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Rat extends Monster {

	private Texture rat;
	private Texture deadrat;
	

	public Rat() {
		
		Pixmap map = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
		
		map.setColor(Color.MAGENTA);
		map.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
		map.setColor(Color.PINK);
		map.drawLine(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
		
		rat = new Texture(map);
		
		Pixmap deadmap = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
		
		deadmap.setColor(Color.BLACK);
		deadmap.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
		deadmap.setColor(Color.PINK);
		deadmap.drawLine(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
		deadmap.drawLine(0, Constants.TILE_SIZE, Constants.TILE_SIZE, 0);
		
		deadrat = new Texture(deadmap);
	}
	
	@Override
	public void step(Game game) {
		if(!alive()) return;
		
		game.moveActor(this, Direction.values()[new Random().nextInt(Direction.values().length)]);
	}

	@Override
	public Texture draw() {
		return alive() ? rat : deadrat;
	}

	@Override
	public boolean interact(Game game, GameEngine engine, Actor requestor, String intent) {
		if(!alive()) return false;
		
		if(requestor == game.player) {
			engine.performAttack(requestor, this);
			return true;
		}
		
		return false;
		
	}
}
