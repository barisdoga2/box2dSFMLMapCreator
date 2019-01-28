public class Link extends Triangle implements Comparable<Link> {
	
	Link prev;
	Link next;
	int distance = -1;
	
	@Override
	public int compareTo(Link that) {
		if (this == that) {
			return 0;
		}
		int delta = getDistance() - that.getDistance();
		return delta != 0 ? delta: hashCode() - that.hashCode(); // <(^_^;)
	}
	
	int getDistance() {
		if (distance == -1) {
			distance = (int) a.distanceSq(c);
		}
		return distance;
	}
	
	boolean isClockwise() { return PolyToTriangleUtils.isClockwise(a, b, c); }
}