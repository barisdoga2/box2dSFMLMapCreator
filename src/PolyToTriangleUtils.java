import java.awt.Point;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class PolyToTriangleUtils {
	
	static void polygonToTriangles(List<Point> points, List<Triangle> triangles) {
		final boolean clockwise = isClockwise(points);
		final TreeSet<Link> set = new TreeSet<>();

        Link list = new Link();
    	{
       	 Link prev = list;
            for (int i = 0; i < points.size(); ++i) {
                int pred = (i == 0 ? points.size(): i) - 1;
                int succ = (i + 1) % points.size();

                Link next = new Link();
                prev.next = next;
                next.prev = prev;

                next.a = points.get(pred);
                next.b = points.get(i);
                next.c = points.get(succ);

                if(next.isClockwise() == clockwise) {
                    set.add(next);
                }
                prev = next;
            }
            list = prev.next =
            list.next;
            list.prev = prev;
        }

	outer:
		while (!set.isEmpty()) {
			Iterator<Link> i = set.iterator();
			boolean found = false;
			while (i.hasNext()) {
				Link link = i.next();
				Point a = link.a;
				Point b = link.b;
				Point c = link.c;
				if (isEmptyTriangle(a, b, c, link, clockwise)) {
					found = true;
					i.remove();
					Link prev = link.prev;
					Link next = link.next;
					set.remove(prev);
					set.remove(next);
					triangles.add(new Triangle(a, b, c));
					if (next.next == prev /*prev == next*/) {
						break outer;
					}
					prev.c = c; prev.distance = -1;
					next.a = a; next.distance = -1;
					prev.next = next;
					next.prev = prev;
					if (prev.isClockwise() == clockwise) set.add(prev);
					if (next.isClockwise() == clockwise) set.add(next);
					break;
				}
			}
			if (!found) {
				throw new IllegalStateException("found == false");
			}
		}
	}

	static boolean isEmptyTriangle(Point a, Point b, Point c, Link list, boolean clockwise) {
		Link link = list;
		do {
			final Point p = link.b;
			if (p != a && p != b && p != c) {
				if (isClockwise(b, a, p) != clockwise &&
				   isClockwise(a, c, p) != clockwise &&
				   isClockwise(c, b, p) != clockwise) {
					return false;
				}
		    }
		} while ((link = link.next) != list);
		return true;
	}
	
	static double polygonArea(java.util.List<Point> points) {
		double area = 0;
		for (int i = 0; i < points.size(); ++i) {
			area += exteriorProduct(
                    points.get(i),
                    points.get((i + 1) % points.size()));
		}
		return area * 0.5;
	}
	
	static double exteriorProduct(Point p0, Point p1) {
		return  p0.x * p1.y - p1.x * p0.y;
	}
	
	static boolean isClockwise(Point a, Point b, Point c) {
		return 0 <= (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
	}

	static boolean isClockwise(java.util.List<Point> points) {
		return 0 <= polygonArea(points);
	}
	
}
