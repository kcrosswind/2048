public class Point_2048 {

	int x;
	int y;

	public Point_2048(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Point_2048 obj) {
		if (x == obj.x && y == obj.y) {
			return true;
		}
		return false;
	}

}
