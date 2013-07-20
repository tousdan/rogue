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


        //fill null with floor!
        for(int i=0; i< this.result.length; i++) {
            if(this.result[i] == null) {
                Position p = indexToPosition(i);
                this.result[i] = new Floor(p.x, p.y);
            }
        }

        SubDungeon all = new SubDungeon(0, width, 0, height);

        all.split(8);

        outline(all);
    }

    void outline(SubDungeon dung) {
        if(dung.subsec_a != null) {
            outline(dung.subsec_a);
            outline(dung.subsec_b);
        }  else {
            int topy = dung.y;
            int boty = dung.y + dung.height - 1;
            int leftx = dung.x;
            int rightx = dung.width + dung.x - 1;

            for(int x=leftx; x <= rightx; x++) {
                setCell(new Floor(x, topy, dung.color));
                setCell(new Floor(x, boty, dung.color));
            }

            for(int y=topy; y <= boty; y++) {
                setCell(new Floor(leftx, y, dung.color));
                setCell(new Floor(rightx, y, dung.color));
            }
        }
    }

    class SubDungeon {
        int x;
        int width;

        int y;
        int height;

        Color color;

        SubDungeon subsec_a;
        SubDungeon subsec_b;


        SubDungeon(int x, int width, int y, int height) {
            this.x = x;
            this.width = width;
            this.y = y;
            this.height = height;
            this.color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1);
        }

        @Override
        public String toString() {
            return "SubDungeon{" +
                    "x=" + x +
                    ", width=" + width +
                    ", y=" + y +
                    ", height=" + height +
                    ", has_a=" + (subsec_a != null) +
                    ", has_b=" + (subsec_b != null) +
                    '}';
        }

        public void split(int min_size) {

            boolean horizontal = rand.nextBoolean();
            double split_percent = rand.nextFloat();

            System.out.println(split_percent);
            int y = (int) Math.round(height * split_percent);
            int x = (int) Math.round(width * split_percent);

            if(horizontal && this.height > min_size && y > min_size && y < height)  {
                subsec_a = new SubDungeon(this.x, width, this.y, y);
                subsec_b = new SubDungeon(this.x, width, this.y + y, height - y);
            } else if(!horizontal && this.width > min_size && x > min_size && x < height) {
                subsec_a = new SubDungeon(this.x, x, this.y, height);
                subsec_b = new SubDungeon(this.x + x, width - x, this.y, height);
            }

            if(subsec_a != null) {
                subsec_a.split(min_size);
                subsec_b.split(min_size);
            }


        }
    }
}
