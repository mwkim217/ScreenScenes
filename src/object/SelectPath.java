package object;

public class SelectPath {
	private int userno;
	private int location1;
	private int location2;
	private int location3;
	private int location4;
	private String pathMapImage;

	public SelectPath(int userno, int location1, int location2, int location3, int location4, String pathMapImage) {
		this.userno = userno;
		this.location1 = location1;
		this.location2 = location2;
		this.location3 = location3;
		this.location4 = location4;
		this.pathMapImage = pathMapImage;
	}

	public int getUserno() {
		return userno;
	}

	public void setUserno(int userno) {
		this.userno = userno;
	}

	public int getLocation1() {
		return location1;
	}

	public void setLocation1(int location1) {
		this.location1 = location1;
	}

	public int getLocation2() {
		return location2;
	}

	public void setLocation2(int location2) {
		this.location2 = location2;
	}

	public int getLocation3() {
		return location3;
	}

	public void setLocation3(int location3) {
		this.location3 = location3;
	}

	public int getLocation4() {
		return location4;
	}

	public void setLocation4(int location4) {
		this.location4 = location4;
	}

	public String getPathMapImage() {
		return pathMapImage;
	}

	public void setPathMapImage(String pathMapImage) {
		this.pathMapImage = pathMapImage;
	}

	@Override
	public String toString() {
		return "SelectPath [userno=" + userno + ", location1=" + location1 + ", location2=" + location2 + ", location3="
				+ location3 + ", location4=" + location4 + ", pathMapImage=" + pathMapImage + "]";
	}

}
