package mangobits.startupkit.core.util;


import com.mangobits.startupkit.core.address.AddressInfo;

public class AddressUtils {

	public static String textualAddress(AddressInfo addressInfo){
		
		StringBuilder en = new StringBuilder();
		
		if(addressInfo != null){

			if (addressInfo.getZipCode().equals("")){

				en.append(addressInfo.getStreet());
				en.append(" n.");
				en.append(addressInfo.getNumber());
				en.append(", ");
				en.append(addressInfo.getDistrict());
				en.append(", ");
				en.append(addressInfo.getCity());

			}else{

				en.append(addressInfo.getStreet());
				en.append(" n.");
				en.append(addressInfo.getNumber());
				en.append(", ");
				en.append(addressInfo.getDistrict());
				en.append(", ");
				en.append(addressInfo.getCity());
				en.append(", CEP:");
				en.append(addressInfo.getZipCode());

			}

		}
		
		return en.toString();
	}
}
