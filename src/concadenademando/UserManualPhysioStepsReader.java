package concadenademando;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class UserManualPhysioStepsReader extends ChainOfRespElement{
	
	private static final String USERMANPHYSIOSTEP_TAGNAME = "userManualPhysioSteps";
	
	private static final String STEPTITLE_FIELD_TAGNAME = "stepTitle";
	private static final String STEPIMAGE_FIELD_TAGNAME = "stepImage";
	private static final String STEPTEXT_FIELD_TAGNAME = "stepText";
	private static final String PHYSIOREF_FIELD_TAGNAME = "physioRef";
	
	public UserManualPhysioStepsReader(ChainOfRespElement chainElement) {
		super(chainElement);
	}
	
	public StringBuffer readJSon(String name, JsonReader reader) throws IOException {
		StringBuffer userManualPhysioData = new StringBuffer();
		if (name.equals(USERMANPHYSIOSTEP_TAGNAME)) {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				userManualPhysioData.append(readEntry(reader)).append("\n");
				reader.endObject();
			}
			userManualPhysioData.append("\n");
			reader.endArray();
		}else {
			if (nextInChain != null) {
				userManualPhysioData = super.readJSon(name, reader);
			}else {
				reader.skipValue();
				System.err.println("Category "+name+" not processed");
			}
		}
		return userManualPhysioData;
	}

	private String readEntry(JsonReader reader) throws IOException {
		String stepTitle = null;
		String stepImage = null;
		String stepText = null;
		String physioRef = null;
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(STEPTITLE_FIELD_TAGNAME)) {
				stepTitle = reader.nextString();
			} else if (name.equals(STEPIMAGE_FIELD_TAGNAME)) {
				stepImage = reader.nextString();
			} else if (name.equals(STEPTEXT_FIELD_TAGNAME)) {
				stepText = reader.nextString();
			} else if (name.equals(PHYSIOREF_FIELD_TAGNAME)) {
				physioRef = reader.nextString();
			} else {
				reader.skipValue();
			}

		}
		return stepTitle + ";" + stepImage + ";" + stepText + ";" + physioRef;
	}

}
