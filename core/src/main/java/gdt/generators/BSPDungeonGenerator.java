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
        System.out.println(width + " " + height);
        boolean horizontal = rand.nextBoolean();

        //fill null with floor!
        for(int i=0; i< this.result.length; i++) {
            if(this.result[i] == null) {
                Position p = indexToPosition(i);
                this.result[i] = new Floor(p.x, p.y);
            }
        }

        SubDungeon a;
        SubDungeon b;

        if(horizontal) {
            int y = Math.max(rand.nextInt(height - 8), 8) ;

            a = new SubDungeon(0, width, 0, y);
            b = new SubDungeon(0, width, y, height - y);
        } else {
            int x = Math.max(rand.nextInt(width - 8), 8);

            a = new SubDungeon(0, x, 0, height);
            b = new SubDungeon(x, width - x, 0, height);
        }


        System.out.println(a);
        System.out.println(b);
        rockify(a);
        rockify(b);
    }

    void rockify(SubDungeon dung) {
        int topy = dung.y;
        int boty = dung.y + dung.height - 1;
        int leftx = dung.x;
        int rightx = dung.width + dung.x - 1;

        for(int x=leftx; x <= rightx; x++) {
            setCell(new Rock(x, topy, Color.RED));
            setCell(new Rock(x, boty, Color.RED));
        }

        for(int y=topy; y <= boty; y++) {
            setCell(new Rock(leftx, y, Color.RED));
            setCell(new Rock(rightx, y, Color.RED));
        }
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

        @Override
        public String toString() {
            return "SubDungeon{" +
                    "x=" + x +
                    ", width=" + width +
                    ", y=" + y +
                    ", height=" + height +
                    '}';
        }
    }
}
