package gdt.generators;

import gdt.*;

import java.util.List;
import java.util.Random;

/**
 * First implementation of the dungeon generator.
 *
 * It blows!
 */
public class MyDungeonGenerator extends DungeonGeneratorBase {

    public MyDungeonGenerator(int width, int height) {
        super(width, height);
    }

    @Override
    void generate() {
        Random rand = new Random();

        //try and find a decent amount of rooms to generate.
        int max = Math.max(width, height) / 5;
        int min = Math.abs(max / 2);

        int roomCount = rand.nextInt(max - min) + min;

        //generate rooms and position them
        for(int i=0; i < roomCount; i++) {
            Room room = Rooms.anyRoom();

            Position roomAssigned = null;
            for(int x=0; x < width - room.width && roomAssigned == null; x++) {
                for(int y=0; y < height - room.height && roomAssigned == null; y++) {
                    if(isZoneFree(x, y, room.width, room.height)) {
                        roomAssigned = new Position(x, y);
                    }
                }
            }

            if(roomAssigned != null) {
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
            }
        }

        //fill null with rocks!
        for(int i=0; i< this.result.length; i++) {
            if(this.result[i] == null) {
                Position p = indexToPosition(i);
                this.result[i] = new Rock(p.x, p.y);
            }
        }

        //connect walls of rooms at random
        for(int y=0;y < height; y += 4) {
            Cell start = null;
            Cell ahead = cell(0, y);
            Cell before = null;
            Cell current;
            for(int x=0; x<width; x++) {
                current = ahead;
                ahead = cell(x+1, y);
                before = cell(x-1, y);

                if(start != null) {
                    if(current instanceof Wall) {
                        //thick walls?
                        if(!(ahead instanceof Wall)) {
                            //done!
                            setCell(new Floor(start.x, start.y));

                            for(int i=start.x; i<=current.x; i++) {
                                setCell(new Wall(i, y - 1));
                                setCell(new Floor(i, y));
                                setCell(new Wall(i, y + 1));
                            }

                            setCell(new Floor(current.x, current.y));

                            start = null;
                        }
                    } else if(current != null && current.isSolid()) {
                        //continue!!
                    } else {
                        start = null;
                    }
                } else {
                    if(current instanceof Wall && (ahead != null && ahead.isSolid()) && (before != null && !before.isSolid())) {
                        //found a candidate.
                        start = current;
                    }
                }
            }
        }

        //connect walls of rooms at random
        for(int x=0;x < width; x += 4) {
            Cell start = null;
            Cell ahead = cell(x, 0);
            Cell before = null;
            Cell current;
            for(int y=0; y<height; y++) {
                current = ahead;
                ahead = cell(x, y+1);
                before = cell(x, y-1);

                if(start != null) {
                    if(current instanceof Wall) {
                        //thick walls?
                        if(!(ahead instanceof Wall)) {
                            //done!
                            setCell(new Floor(start.x, start.y));

                            for(int i=start.y; i<=current.y; i++) {
                                setCell(new Wall(x -1, i));
                                setCell(new Floor(x, i));
                                setCell(new Wall(x + 1, i));
                            }

                            setCell(new Floor(current.x, current.y));

                            start = null;
                        }
                    } else if(current != null && current.isSolid()) {
                        //continue!!
                    } else {
                        start = null;
                    }
                } else {
                    if(current instanceof Wall && (ahead != null && ahead.isSolid()) && (before != null && !before.isSolid())) {
                        //found a candidate.
                        start = current;
                    }
                }
            }
        }
    }
}
