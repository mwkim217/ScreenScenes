package object;

import java.util.ArrayList;
import java.util.List;

public class ImgLocationObject {
	private List<String> imageData = new ArrayList<>();
	private List<String> addressData = new ArrayList<>();
	private List<Integer> location_no = new ArrayList<>();

	public ImgLocationObject(List<String> imageData, List<String> addressData, List<Integer> location_no) {
		super();
		this.imageData = imageData;
		this.addressData = addressData;
		this.location_no = location_no;
	}

	public List<String> getImageData() {
		return imageData;
	}

	public void setImageData(List<String> imageData) {
		this.imageData = imageData;
	}

	public List<String> getAddressData() {
		return addressData;
	}

	public void setAddressData(List<String> addressData) {
		this.addressData = addressData;
	}

	public List<Integer> getLocation_no() {
		return location_no;
	}

	public void setLocation_no(List<Integer> location_no) {
		this.location_no = location_no;
	}

	@Override
	public String toString() {
		return "imgLocationObject [imageData=" + imageData + ", addressData=" + addressData + ", location_no="
				+ location_no + "]";
	}

}