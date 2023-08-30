package object;

import java.sql.Blob;

public class Location {
    private int location_no;
    private int movie_no;
    private String address;
    private double latitude;
    private double longitude;
    private Blob image;

    public Location(int location_no, int movie_no, String address, double latitude, double longitude, Blob image) {
	super();
	this.location_no = location_no;
	this.movie_no = movie_no;
	this.address = address;
	this.latitude = latitude;
	this.longitude = longitude;
	this.image = image;
    }

    public Location(int location_no, String address, double latitude, double longitude) {
	super();
	this.location_no = location_no;
	this.address = address;
	this.latitude = latitude;
	this.longitude = longitude;
    }

    public Location(double latitude, double longitude, int movie_no, int location_no) {
	super();
	this.location_no = location_no;
	this.movie_no = movie_no;
	this.latitude = latitude;
	this.longitude = longitude;
    }

    public Location(double latitude, double longitude) {
	super();
	this.latitude = latitude;
	this.longitude = longitude;
    }

    public Location(int location_no, int movie_no, double latitude, double longitude) {
	super();
	this.location_no = location_no;
	this.movie_no = movie_no;
	this.latitude = latitude;
	this.longitude = longitude;
    }

    public int getLocation_no() {
	return location_no;
    }

    public void setLocation_no(int location_no) {
	this.location_no = location_no;
    }

    public int getMovie_no() {
	return movie_no;
    }

    public void setMovie_no(int movie_no) {
	this.movie_no = movie_no;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public double getLatitude() {
	return latitude;
    }

    public void setLatitude(double latitude) {
	this.latitude = latitude;
    }

    public double getLongitude() {
	return longitude;
    }

    public void setLongitude(double longitude) {
	this.longitude = longitude;
    }

    public Blob getImage() {
	return image;
    }

    public void setImage(Blob image) {
	this.image = image;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((image == null) ? 0 : image.hashCode());
	long temp;
	temp = Double.doubleToLongBits(latitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + location_no;
	temp = Double.doubleToLongBits(longitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + movie_no;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Location other = (Location) obj;
	if (address == null) {
	    if (other.address != null)
		return false;
	} else if (!address.equals(other.address))
	    return false;
	if (image == null) {
	    if (other.image != null)
		return false;
	} else if (!image.equals(other.image))
	    return false;
	if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
	    return false;
	if (location_no != other.location_no)
	    return false;
	if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
	    return false;
	if (movie_no != other.movie_no)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Location [location_no=" + location_no + ", movie_no=" + movie_no + ", address=" + address
		+ ", latitude=" + latitude + ", longitude=" + longitude + ", image=" + image + "]";
    }

}
