package object;

import java.util.List;

public class ViewPath {
    private int viewPathNo; // 1, 2, 3
    private int userNo;
    private List<ViewLocation> location;

    public ViewPath(int viewPathNo, int userNo, List<ViewLocation> location) {
	super();
	this.viewPathNo = viewPathNo;
	this.userNo = userNo;
	this.location = location;
    }

    public int getViewPathNo() {
	return viewPathNo;
    }

    public void setViewPathNo(int viewPathNo) {
	this.viewPathNo = viewPathNo;
    }

    public int getuserNo() {
	return userNo;
    }

    public void setuserNo(int userNo) {
	this.userNo = userNo;
    }

    public List<ViewLocation> getLocation() {
	return location;
    }

    public void setLocation(List<ViewLocation> location) {
	this.location = location;
    }

    @Override
    public String toString() {
	return "ViewPath [viewPathNo=" + viewPathNo + ", userNo=" + userNo + ", location=" + location + "]";
    }

}
