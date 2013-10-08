package gdt;

import java.util.*;

/**
 * Attempt at A* resolver for dungeon levels
 */
public class CellPathResolver {
    private final Level level;
    private final Cell goal;

    private PriorityQueue<Path> paths;
    public HashMap<Cell, Double> mindists;
    private Double lastCost;
    private int expandedCounter;

    public CellPathResolver(Level level, Cell goal) {
        this.level = level;
        this.goal = goal;

        this.paths = new PriorityQueue<Path>();
        this.mindists = new HashMap<Cell, Double>();
        this.expandedCounter = 0;
        this.lastCost = 0.0;
    }

    protected boolean isGoal(Cell node) {
        return node == goal;
    }

    //Movement cost from -> to
    protected Double g(Cell from, Cell to) {
        return from.distanceTo(to);
    }

    //Estimated cost from -> to
    protected Double h(Cell from, Cell to) {
        return from.distanceTo(to);
    }

    protected Double f(Path p, Cell from, Cell to) {
        Double g = g(from, to) + ((p.parent != null) ? p.parent.g : 0.0);
        Double h = h(from, to);

        p.g = g;
        p.f = g +h;

        return p.f;
    }

    private void expand(Path path) {
        Cell p = path.cell;
        Double min = mindists.get(p);

        //A better path already exists? Dont expand then.
        if(min == null || min.doubleValue() > path.f.doubleValue()) {
            mindists.put(p, path.f);
        } else {
            return;
        }

        List<Cell> succesors = generateSuccesors(p);

        for(Cell succ : succesors) {
            Path n = new Path(path);
            n.cell = succ;
            f(n, p, succ);
            paths.offer(n);
        }

        expandedCounter++;
    }

    public List<Cell> compute(Cell start) {
        try {
            Path root = new Path();
            root.cell = start;

            f(root, start, start);

            expand(root);

            for(;;) {
                Path p = paths.poll();

                if(p == null) {
                    lastCost = Double.MAX_VALUE;
                    return null;
                }

                Cell last = p.cell;

                lastCost = p.g;

                if(isGoal(last)) {
                    LinkedList<Cell> retPath = new LinkedList<Cell>();

                    for(Path i = p; i != null; i = i.parent) {
                        retPath.addFirst(i.cell);
                    }

                    return retPath;
                }

                expand(p);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public int getExpandedCounter() {
        return expandedCounter;
    }

    protected List<Cell> generateSuccesors(Cell node) {
        List<Cell> result = level.getAdjacentCells(node, 1, false);

        for(Iterator<Cell> it = result.iterator(); it.hasNext();) {
            Cell candidate = it.next();

            if(candidate.isSolid()) {
                it.remove();
            }
        }

        return result;
    }

    private class Path implements Comparable<Path> {
        public Cell cell;
        public Double f;
        public Double g;
        public Path parent;

        public Path() {
            parent = null;
            cell = null;
            f = g = 0.0;
        }

        public Path(Path p) {
            this();

            parent = p;
            g = p.g;
            f = p.f;
        }

        @Override
        public int compareTo(Path o) {
            return (int) (f - o.f);
        }
    }
}
