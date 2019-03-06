import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repeatition {
	
	static String sourceMessage ="PID|0001|8539187|851735^^^050^MR~1105143^^^050^AI~851735^^^050^PI||HHMOCKTWO^EDIPCPSTWO^^^||19440726|M||W^WHITE^99TRA^C^White^MCKOMB|125"; 
	
	static Map<String, List<String>> m_Map1 = new HashMap<String, List<String>>(); 
	public static void main(String args[]) {
		
		List messageList = new ArrayList();
		
		//Map<String, List<String>> m_Map1 = new HashMap<String, List<String>>(); 
		
		messageList.add(sourceMessage);
		
		m_Map1.put("PID",messageList);
		
		
		
		
		
		String TotWord = "851735^^^050^MR~1105143^^^050^AI~851735^^^050^PI";
		//String TotWord = "^PRN^PH^^1^516^7000000";
		
		String Segment = "PID";
		String SegmentPosition = "3";
		String ConcatSegment = "6";
		String transformation = "Loop through all repetition of PID:3,"
				+ " Copy PID:3.1 from the repetition that contains \"MR\", "
				+ "Copy output padded with \"0\" to make it \"5\" digits";
		RepeatitionFunc(TotWord,transformation);
	}

	private static void RepeatitionFunc(String totWord, String transformation) {
		
		String SourceMessage = "";
		String requiredword = ""; 
		
		String[] getDetails = transformation.split(",");
		
		for(int varloop= 0; varloop<getDetails.length;varloop++) {
			
			if(getDetails[varloop].startsWith("Loop through all")) {
				
				String[] transformationArray = getDetails[varloop].split(" ");
				String sourceSegmentInfo[] = transformationArray[transformationArray.length - 1]
						.split(":");
				String sourceSegment = sourceSegmentInfo[0];
				String sourcePosition = sourceSegmentInfo[1];
				System.out.println(sourcePosition);
				
				SourceMessage = fetchSegmentValueFromSourceMessage
						(m_Map1,sourceSegment,sourcePosition,-1,0);
				
				System.out.println(SourceMessage);
			}
			
			if(getDetails[varloop].contains("repetition that contains")) {
				
				String[] transformationArray = getDetails[varloop].split(" ");
				String sourceSegmentInfoCondition[] = transformationArray[transformationArray.length - 1]
						.split("\"");
				//String sourceSegment = sourceSegmentInfo[0];
				String FilterCondition = sourceSegmentInfoCondition[1];
				System.out.println(FilterCondition);
				
				String sourceSegmentInfoValue[] = transformationArray[2]
						.split(":");
				
				String sourceSegmentValue = sourceSegmentInfoValue[0];
				String FilterConditionValue = sourceSegmentInfoValue[1];
				String[] Value = FilterConditionValue.toString().split("\\.");
				System.out.println(sourceSegmentValue);
				
				
								
				
				
				String[] splitword = SourceMessage.split("~");

				// System.out.println(splitword.length);

				int flag = 0;

				for (int varloopsplit = 0; varloopsplit < splitword.length; varloopsplit++) {

					// System.out.println(splitword[varloopsplit]);

					if (splitword[varloopsplit].contains(FilterCondition)) {

						flag = 1;
					}

					if (flag == 1) {

						String[] splitwordinner = splitword[varloopsplit].split("\\^");
						//System.out.println(splitwordinner.length);

						for (int varloopsplitinner = 0; varloopsplitinner < splitwordinner.length; varloopsplitinner++) {

												
							if (varloopsplitinner==(Integer.parseInt(Value[1]))-1) {

								requiredword = splitwordinner[varloopsplitinner];
								break;

							}
							
							
						}
						
						flag=0;

					}
				
				}
				
				
			}
			
			
			if(getDetails[varloop].contains("padded with")) {
				
				
				String PaddedValue = TargetValuePadding(null,null,getDetails[varloop],requiredword,null,null,-2,0);
				
				System.out.println(PaddedValue);
				
			}
			
			
			
		}
		
		

		//call the Set to value method.
		

	}
	
	
	

	public static String  TargetValuePadding(Map<String,List<String>> sourceMessage,Map<String,List<String>> targetMessage
			,String transformation, String requiredValue, String position,
			String segment, int repetition, int segmentIteration) {
		
		int tolength=0;
		
		if(repetition==-2) {
			
			String[] transformationArray = transformation.trim().split(" ");
			
			
			String sourceSegmentInfoCondition[] = transformationArray[transformationArray.length - 1]
					.split("\"");
			
			if(transformationArray[1].trim().equals("output")) {
				
				System.out.println(requiredValue);
				
				tolength = Integer.parseInt(transformationArray[8].replace("\"", "").trim());
				System.out.println(transformationArray[4].toString());
				System.out.println(transformationArray[8].toString());
				
			}
			
			if(requiredValue.length()<tolength) {
				
				
				String paddedzero ="0";
				for(int zeropadded = 1;zeropadded<(tolength-requiredValue.length());zeropadded++) {
					
					paddedzero = paddedzero + "0";
					
				}
				
				requiredValue = paddedzero+requiredValue;
				System.out.println(paddedzero);
				System.out.println(paddedzero+requiredValue);
				
				return requiredValue;
				
			}
					
		/*	
			
			String[] transformationArray = getDetails[varloop].split(" ");
			String sourceSegmentInfo[] = transformationArray[transformationArray.length - 1]
					.split(":");
			String sourceSegment = sourceSegmentInfo[0];
			String sourcePosition = sourceSegmentInfo[1];
			System.out.println(sourcePosition);
			
			*/
		}
		
/*
		if(requiredword.length()<9) {
			
			
			String paddedzero ="0";
			for(int zeropadded = 1;zeropadded<(9-requiredword.length());zeropadded++) {
				
				paddedzero = paddedzero + "0";
				
			}
			
			System.out.println(paddedzero);
			System.out.println(paddedzero+requiredword);
			
		}
		*/
		return requiredValue;
		
	}
	
	
	
	
	
	public static String fetchSegmentValueFromSourceMessage(Map<String,List<String>> sourceMessage,String segmentName,
			String position, int repetition, int segmentIteration) {

		String segmentValue = sourceMessage.get(segmentName.trim().toUpperCase()).get(segmentIteration);
		repetition = repetition - 1;
		int sourceMainIndex = 0;
		int sourceChildIndex = 0;
		// fetching value from target segment
		System.out.println("Inside fetch segment value method");
		String sourceSegmentArray[] = segmentValue.split("\\|");
		String sourceValue = "";
		// checking for sub index in source position.
		if (position.contains(".")) {
			String positionArray[] = position.split("\\.");
			if (segmentName.equals("MSH")) {
				sourceMainIndex = Integer.parseInt(positionArray[0]) - 1;
				sourceChildIndex = Integer.parseInt(positionArray[1]) - 1;
			} else {
				sourceMainIndex = Integer.parseInt(positionArray[0]);
				sourceChildIndex = Integer.parseInt(positionArray[1]) - 1;
			}

			if (sourceSegmentArray[sourceMainIndex].contains("~")) {
				String x[] = sourceSegmentArray[sourceMainIndex].split("~");
				if (x[repetition].contains("^")) {
					String insidesourceSegmentArray[] = x[repetition]
							.split("\\^");
					sourceValue = insidesourceSegmentArray[sourceChildIndex];
				} else {
					if(sourceChildIndex!=0){
					sourceValue = "";
					}else{
						sourceValue = x[repetition];
					}
				}
			} else {
				if (sourceSegmentArray[sourceMainIndex].contains("^")) {
					String insidesourceSegmentArray[] = sourceSegmentArray[sourceMainIndex]
							.split("\\^",-1);
					sourceValue = insidesourceSegmentArray[sourceChildIndex];
				} else {
					if(sourceChildIndex==0){
						sourceValue = sourceSegmentArray[sourceMainIndex];
					}else{
					sourceValue = "";
					}
				}
			}
		} else {
			if (segmentName.equals("MSH")) {
				sourceMainIndex = Integer.parseInt(position) - 1;
			} else {
				sourceMainIndex = Integer.parseInt(position);
			}
			
			if(repetition==-2) {
				
				sourceValue = sourceSegmentArray[sourceMainIndex];
				
				return sourceValue;
				
				
			}
			if (!sourceSegmentArray[sourceMainIndex].contains("~")) {
				String insidesourceSegmentArray[] = sourceSegmentArray[sourceMainIndex]
						.split("\\^");
				sourceValue = insidesourceSegmentArray[0];
			} else {
				String x[] = sourceSegmentArray[sourceMainIndex].split("~");
				String insidesourceSegmentArray[] = x[repetition].split("\\^");
				sourceValue = insidesourceSegmentArray[0];
			}
		}
		System.out.println("Exiting fetch segment value method");

		return sourceValue;
	}
	
	
	
}	


