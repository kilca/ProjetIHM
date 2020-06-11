package climatechange.models;

public class Coord {

	public int latitude;
	public int longitude;
	
	public Coord (int lat, int lon) {
		this.latitude = lat;
		this.longitude = lon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + latitude;
		result = prime * result + longitude;
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
		Coord other = (Coord) obj;
		if (latitude != other.latitude)
			return false;
		if (longitude != other.longitude)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coord [lat=" + latitude + ", lon=" + longitude + "]";
	}
	
}
