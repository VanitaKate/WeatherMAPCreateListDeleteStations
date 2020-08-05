package WeatherMapAPI;

public class StationPOJO {

	String external_id;
	 String name;
		double latitude;
		 double longitude;
		 int altitude;
	 public StationPOJO(String external_id, String name, double d, double e, int altitude) {
		this.external_id = external_id;
		this.name = name;
		this.latitude = d;
		this.longitude = e;
		this.altitude = altitude;
	}

	 public String getExternal_id() {
		return external_id;
	}
	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getAltitude() {
		return altitude;
	}
	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	
	@Override
	public String toString() {
		return "StationPOJO [external_id=" + this.external_id + ", name=" + this.name + ", latitude=" + this.latitude + ", longitude="
				+ this.longitude + ", altitude=" + this.altitude + "]";
	}
}
