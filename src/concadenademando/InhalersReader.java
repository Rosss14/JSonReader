package concadenademando;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class InhalersReader extends ChainOfRespElement{
	
	private static final String INHALERS_TAGNAME = "inhalers";
	private static final String NAME_FIELD_TAGNAME = "name";
	private static final String IMAGE_FIELD_TAGNAME = "image";
	
	public InhalersReader(ChainOfRespElement chainElement) {
		super(chainElement);
	}
	
	public StringBuffer readJSon(String name, JsonReader reader) throws IOException {
		StringBuffer inhalerData = new StringBuffer();
		if (name.equals(INHALERS_TAGNAME)) {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				inhalerData.append(readInhalerEntry(reader)).append("\n");
				reader.endObject();
			}
			inhalerData.append("\n");
			reader.endArray();
		}else {
			if (nextInChain != null) {
				inhalerData=super.readJSon(name, reader);
			}else {
				reader.skipValue();
				System.err.println("Category "+name+" not processed");
			}
		}
		return inhalerData;
	}
	
	private String readInhalerEntry(JsonReader reader) throws IOException {
		String inhalerName = null;
		String inhalerImage = null;
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(NAME_FIELD_TAGNAME)) {
				inhalerName = reader.nextString();
			} else if (name.equals(IMAGE_FIELD_TAGNAME)) {
				inhalerImage = reader.nextString();
			} else {
				reader.skipValue();
			}

		}
		return inhalerName + ";" + inhalerImage;
	}
	
}
