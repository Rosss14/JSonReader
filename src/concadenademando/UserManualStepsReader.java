package concadenademando;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class UserManualStepsReader extends ChainOfRespElement{
	
	private static final String USERMANUAL_TAGNAME = "userManualSteps";
	
	private static final String STEPTITLE_FIELD_TAGNAME = "stepTitle";
	private static final String STEPIMAGE_FIELD_TAGNAME = "stepImage";
	private static final String STEPTEXT_FIELD_TAGNAME = "stepText";
	private static final String INHREF_FIELD_TAGNAME = "inhalerRef";
	
	public UserManualStepsReader(ChainOfRespElement chainElement) {
		super(chainElement);
	}
	
	public StringBuffer readJSon(String name, JsonReader reader) throws IOException {
		StringBuffer userManualData = new StringBuffer();
		if (name.equals(USERMANUAL_TAGNAME)) {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				userManualData.append(readUserManualStepEntry(reader)).append("\n");
				reader.endObject();
			}
			userManualData.append("\n");
			reader.endArray();
		}else {
			if (nextInChain != null) {
				userManualData = super.readJSon(name, reader);
			}else {
				reader.skipValue();
				System.err.println("Category"+name+" not processed");
			}
		}
		return userManualData;
	}

	private String readUserManualStepEntry(JsonReader reader) throws IOException {
		String stepTitle = null;
		String stepImage = null;
		String stepText = null;
		String inhalerRef = null;
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(STEPTITLE_FIELD_TAGNAME)) {
				stepTitle = reader.nextString();
			} else if (name.equals(STEPIMAGE_FIELD_TAGNAME)) {
				stepImage = reader.nextString();
			} else if (name.equals(STEPTEXT_FIELD_TAGNAME)) {
				stepText = reader.nextString();
			} else if (name.equals(INHREF_FIELD_TAGNAME)) {
				inhalerRef = reader.nextString();
			} else {
				reader.skipValue();
			}

		}
		return stepTitle + ";" + stepImage + ";" + stepText + ";" + inhalerRef;
	}

}
