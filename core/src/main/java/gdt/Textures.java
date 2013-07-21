package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class  Textures {
    private static Textures instance;

    public final Texture character;
    public final Texture rock;
    public final Texture floor;
    public final Texture wall;

    private final Map<Color, Texture> coloredfloors;

    public Texture coloredFloor(Color color) {
        Texture result = coloredfloors.get(color);

        if(result == null) {
            Pixmap map = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
            map.setColor(color);
            map.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);

            result = new Texture(map);
            coloredfloors.put(color, result);
        }

        return result;
    }


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
        coloredfloors = new HashMap<Color, Texture>();

        floor = coloredFloor(Color.GRAY);

        map = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
        map.setColor(Color.BLACK);
        map.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);

        wall = new Texture(map);
    }

    public static Textures i() {
        if(instance == null) {
            instance = new Textures();
        }

        return instance;
    }
}
