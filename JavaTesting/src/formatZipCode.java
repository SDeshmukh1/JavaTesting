
public class formatZipCode {
	static String Zip = "876341";
	public static void main(String args[]) {
		
		//String formattedZip = "";
		
		if(Zip.length()>5) {
			Zip = Zip.substring(0, 4)+"-"+Zip.substring(5);
		}
		
		else {
			System.out.println(Zip);
		}
		
		System.out.println(Zip);
		
	}

}
