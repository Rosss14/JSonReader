package concadenademando;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class PhysiotherapiesReader extends ChainOfRespElement{
	
	private static final String PHYSIOTHERAPIES_TAGNAME = "physiotherapies";
	private static final String NAME_FIELD_TAGNAME = "name";
	private static final String IMAGE_FIELD_TAGNAME = "image";
	
	public PhysiotherapiesReader(ChainOfRespElement chainElement) {
		super(chainElement);
	}
	
	public StringBuffer readJSon(String name, JsonReader reader) throws IOException {
		StringBuffer physiotherapyData = new StringBuffer();
		if(name.equals(PHYSIOTHERAPIES_TAGNAME)) {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				physiotherapyData.append(readPhysiotherapyEntry(reader)).append("\n");
				reader.endObject();
			}
			physiotherapyData.append("\n");
			reader.endArray();
		}else {
			if (nextInChain != null) {
				physiotherapyData = super.readJSon(name, reader);
			}else {
				reader.skipValue();
				System.err.println("Category "+name+" not processed");
			}
		}
		return physiotherapyData;
	}
	
	private String readPhysiotherapyEntry(JsonReader reader) throws IOException {
		String physioName = null;
		String physioImage = null;
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(NAME_FIELD_TAGNAME)) {
				physioName = reader.nextString();
			} else if (name.equals(IMAGE_FIELD_TAGNAME)) {
				physioImage = reader.nextString();
			} else {
				reader.skipValue();
			}

		}
		return physioName + ";" + physioImage;
	}
	
}
