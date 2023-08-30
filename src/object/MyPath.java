package object;

public class MyPath {
	int pathNo;
	Location locationAddress1;
	Location locationAddress2;
	Location locationAddress3;
	Location locationAddress4;
	String rootName;

	public MyPath(int pathNo, Location locationAddress1, Location locationAddress2, Location locationAddress3,
			Location locationAddress4, String rootName) {
		super();
		this.pathNo = pathNo;
		this.locationAddress1 = locationAddress1;
		this.locationAddress2 = locationAddress2;
		this.locationAddress3 = locationAddress3;
		this.locationAddress4 = locationAddress4;
		this.rootName = rootName;
	}

	public int getPathNo() {
		return pathNo;
	}

	public void setPathNo(int pathNo) {
		this.pathNo = pathNo;
	}

	public Location getLocationAddress1() {
		return locationAddress1;
	}

	public void setLocationAddress1(Location locationAddress1) {
		this.locationAddress1 = locationAddress1;
	}

	public Location getLocationAddress2() {
		return locationAddress2;
	}

	public void setLocationAddress2(Location locationAddress2) {
		this.locationAddress2 = locationAddress2;
	}

	public Location getLocationAddress3() {
		return locationAddress3;
	}

	public void setLocationAddress3(Location locationAddress3) {
		this.locationAddress3 = locationAddress3;
	}

	public Location getLocationAddress4() {
		return locationAddress4;
	}

	public void setLocationAddress4(Location locationAddress4) {
		this.locationAddress4 = locationAddress4;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
}
