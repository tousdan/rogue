package gdt.generators;

import gdt.Cell;
import gdt.Position;
import gdt.Rock;

public abstract class DungeonGeneratorBase {
    protected final int width;
    protected final int height;
    protected final Cell[] result;

    private boolean built = false;

    public DungeonGeneratorBase(int width, int height) {
        this.width = width;
        this.height = height;
        this.result = new Cell[width * height];
    }

    public Cell[] build() {
        if(!built) {
            generate();
        }

        return result;
    }

    protected boolean inBounds(int x, int y) {
        return x < width && x >= 0
                && y < height && y >= 0;
    }

    protected Cell cell(int x, int y) {
        if(!inBounds(x, y))
            return null;
        return result[x + y * width];
    }

    protected void setCell(Cell cell) {
        result[cell.x + cell.y * width] = cell;
    }

    protected boolean isZoneFree(int x, int y, int width, int height) {
        for(int i=x; i < x + width; i++) {
            for(int j=y; j < y + height; j++) {
                if(cell(i, j) != null) {
                    return false;
                }
            }
        }

        return true;
    }

    protected Position indexToPosition(int i) {
        if(i > this.result.length) {
            return null;
        }

        return new Position(i % width, i / width);
    }

    abstract void generate();
}
