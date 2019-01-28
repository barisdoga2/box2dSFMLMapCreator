import java.awt.Point;
import java.util.ArrayList;

public class MapObject {
	
	private String name;
	private ArrayList<Point> vertices = new ArrayList<Point>();
	private ArrayList<Triangle> triangles;
	
	public MapObject(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Map Obj: " + name;
	}
	
	public String getName() {
		return name;
	}

	public void pushVertex(int x, int y) {
		this.vertices.add(new Point(x, y));
	}

	public ArrayList<Point> getAllVertices() {
		return vertices;
	}
	
	public ArrayList<Triangle> getAllTriangles() {
		if(triangles == null && vertices.size() >= 3) {
			triangles = new ArrayList<Triangle>();
			for(int i = 0 ; i < vertices.size() ; i++) 
				System.out.println("\t X:" + vertices.get(i).x + " \t Y:" + vertices.get(i).y);
			
			long start = System.nanoTime();
			PolyToTriangleUtils.polygonToTriangles(vertices, triangles);
			System.err.println("Elapsed time:"+ (System.nanoTime() - start));
			System.err.println("number of triangles:"+ triangles.size());
		}
		
		return triangles;
	}

	public void removeLastVertex() {
		if(vertices.size() > 0) {
			vertices.remove(vertices.size() - 1);
		}
	}
	
}
