package Implementierung;

import org.pi4.locutil.GeoPosition;
import org.pi4.locutil.MACAddress;

public class AccessPoint {
	
	private MACAddress address;
	private GeoPosition position;
	
	
	
	public AccessPoint(String address,double x,double y,double z) {
		super();
		this.address = MACAddress.parse(address);
		this.position = new GeoPosition(x,y,z);
	}

	public MACAddress getAddress() {
		return address;
	}
	
	public void setAddress(MACAddress address) {
		this.address = address;
	}
	public GeoPosition getPosition() {
		return position;
	}
	public void setPosition(GeoPosition position) {
		this.position = position;
	}
	
	
}
