package Implementierung;

import org.pi4.locutil.GeoPosition;
import org.pi4.locutil.MACAddress;

public class AccessPoint {
	
	private MACAddress address;
	private GeoPosition position;
	private String name;
	
	
	
	public AccessPoint(String address,double x,double y,double z) {
		super();
		this.address = MACAddress.parse(address);
		this.position = new GeoPosition(x,y,z);
		this.name = convertMacToName(MACAddress.parse(address));
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
	public String getName() {
		return name;
	}
	
	public String convertMacToName(MACAddress address) {
		String apName = "";

        switch (address.toString()) {
            case "00:14:BF:B1:7C:54": 	apName = "AP1";
                     					break;
            case "00:16:B6:B7:5D:8F":  	apName = "AP2";
                    					break;
            case "00:14:BF:B1:7C:57":  	apName = "AP3";
										break;
            case "00:14:BF:B1:97:8D": 	apName = "AP4";
										break;
			case "00:16:B6:B7:5D:9B":  	apName = "AP5";
										break;
			case "00:14:6C:62:CA:A4": 	apName = "AP6";
										break;
			case "00:14:BF:3B:C7:C6":  	apName = "AP7";
										break;
			case "00:14:BF:B1:97:8A": 	apName = "AP8";
										break;
			case "00:14:BF:B1:97:81":  	apName = "AP9";
										break;
			case "00:16:B6:B7:5D:8C": 	apName = "AP10";
										break;
			case "00:11:88:28:5E:E0":  	apName = "AP11";
										break;
			default:					apName = "Other AP";
										break;
        }
		return apName;	
	}

}
