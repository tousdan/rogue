package gdt.generators;

import com.badlogic.gdx.graphics.Color;
import gdt.*;

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

        all.split(4 , 4);
        all.populate();
        all.link();

        //outline(all);
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

        private boolean isLeaf() { return subsec_a == null && subsec_b == null; }

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

        public void split(int iter, int min_size) {
            if(iter <= 0) {
                return;
            }

            boolean horizontal = rand.nextBoolean();
            double split_percent = 0.1 + 0.8 * rand.nextFloat();

            System.out.println(split_percent);

            if(this.height > 2 * min_size && this.width > 2 * min_size) {

                int y = (int) Math.round((height - min_size * 2) * split_percent + min_size);
                int x = (int) Math.round((width - min_size * 2) * split_percent + min_size);

                if(horizontal && y >= min_size)  {
                    subsec_a = new SubDungeon(this.x, width, this.y, y);
                    subsec_b = new SubDungeon(this.x, width, this.y + y, height - y);
                } else if(!horizontal && x >= min_size) {
                    subsec_a = new SubDungeon(this.x, x, this.y, height);
                    subsec_b = new SubDungeon(this.x + x, width - x, this.y, height);
                }

                if(subsec_a != null) {
                    subsec_a.split(iter - 1, min_size);
                    subsec_b.split(iter - 1, min_size);
                }
            }


        }

        public void populate() {
            if(isLeaf()) {
                Room room = Rooms.roomFor(width -2 , height -2);

                if(room == null) {
                    return;
                }

                Position roomAssigned = new Position(x+1, y+1);


                for(int c=0;c<room.contents.length; c++) {
                    CellFactory factory = room.contents[c];

                    if(factory == null) {
                        continue;
                    }

                    int roomY = c / room.width;
                    int roomX = c % room.width;

                    int x = roomAssigned.x + roomX;
                    int y = roomAssigned.y + roomY;

                    Cell cell = factory.create(x, y);
                    setCell(factory.create(x, y));
                }
            } else {
                this.subsec_a.populate();
                this.subsec_b.populate();
            }
        }

        public void link() {
            if(subsec_a != null && subsec_b != null) {
                if(subsec_a.isLeaf() && subsec_b.isLeaf()) {
                    //How are the sections related to each other?
                    if(subsec_a.width == subsec_b.width) {
                        //Horizontal split!

                        //Temporaire..
                        setCell(new Floor(x + width / 2, y + subsec_a.height-2));
                        setCell(new Floor(x + width / 2, y + subsec_a.height+1));


                    } else {
                        //Vertical

                    }
                } else {
                    subsec_a.link();
                    subsec_b.link();
                }
            }

        }
    }
}
