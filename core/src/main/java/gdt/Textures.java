package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public final class  Textures {
    private static Textures instance;

    public final Texture character;
    public final Texture rock;
    public final Texture floor;

    private Textures() {
        Pixmap map = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
        map.setColor(Color.RED);
        map.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
        map.setColor(Color.ORANGE);
        map.drawLine(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
        map.drawLine(Constants.TILE_SIZE, 0, 0, Constants.TILE_SIZE);

        character = new Texture(map);


        map = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
        map.setColor(Color.DARK_GRAY);
        map.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);

        rock = new Texture(map);

        map = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
        map.setColor(Color.GRAY);
        map.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);

        floor = new Texture(map);
    }

    public static Textures i() {
        if(instance == null) {
            instance = new Textures();
        }

        return instance;
    }
}
