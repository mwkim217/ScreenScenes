package object;

public class ViewLocation {
	private int locationNo;
	private String locationName;
	private String locationImgStr;
	private String posterImgStr;

	public ViewLocation(int locationNo, String locationName, String locationImgStr, String posterImgStr) {
		super();
		this.locationNo = locationNo;
		this.locationName = locationName;
		this.locationImgStr = locationImgStr;
		this.posterImgStr = posterImgStr;
	}

	public int getLocationNo() {
		return locationNo;
	}

	public void setLocationNo(int locationNo) {
		this.locationNo = locationNo;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationImgStr() {
		return locationImgStr;
	}

	public void setLocationImgStr(String locationImgStr) {
		this.locationImgStr = locationImgStr;
	}

	public String getPosterImgStr() {
		return posterImgStr;
	}

	public void setPosterImgStr(String posterImgStr) {
		this.posterImgStr = posterImgStr;
	}

	@Override
	public String toString() {
		return "ViewLocation [locationNo=" + locationNo + ", locationName=" + locationName + ", locationImgStr="
				+ locationImgStr + ", posterImgStr=" + posterImgStr + "]";
	}

}
