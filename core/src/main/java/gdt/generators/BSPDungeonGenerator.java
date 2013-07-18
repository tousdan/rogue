package gdt.generators;

import com.badlogic.gdx.graphics.Color;
import gdt.Cell;
import gdt.Floor;
import gdt.Position;
import gdt.Rock;

import java.util.Random;

public class BSPDungeonGenerator extends DungeonGeneratorBase {
    private final Random rand;

    public BSPDungeonGenerator(int width, int height) {
        super(width, height);

        this.rand = new Random();
    }

    @Override
    void generate() {

        boolean horizontal = rand.nextBoolean();

        //fill null with rocks!
        for(int i=0; i< this.result.length; i++) {
            if(this.result[i] == null) {
                Position p = indexToPosition(i);
                this.result[i] = new Rock(p.x, p.y);
            }
        }

        SubDungeon a;
        SubDungeon b;

        if(horizontal) {
            int y = rand.nextInt(height -1);

            a = new SubDungeon(0, width, 0, y);
            b = new SubDungeon(0, width, y+1, height - y);
        } else {
            int x = rand.nextInt(width - 1);

            a = new SubDungeon(0, x, 0, height);
            b = new SubDungeon(x+1, width - x, 0, height);
        }

        for(int x=a.x; x < a.width; x++) {
            for(int y=a.y; y < a.height; y++) {
                setCell(new Rock(x, y, Color.RED));
            }
        }

        setCell(new Floor(0,0));
    }

    class SubDungeon {
        int x;
        int width;

        int y;
        int height;

        SubDungeon(int x, int width, int y, int height) {
            this.x = x;
            this.width = width;
            this.y = y;
            this.height = height;
        }
    }
}
