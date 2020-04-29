package concadenademando;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class PosologiesReader extends ChainOfRespElement{
	
	private static final String POSOLOGIES_TAGNAME = "posologies";
	private static final String DESCRIPTION_FIELD_TAGNAME = "description";
	
	public PosologiesReader(ChainOfRespElement chainElement) {
		super(chainElement);
		
	}
	
	public StringBuffer readJSon(String name, JsonReader reader) throws IOException {
		StringBuffer posologyData = new StringBuffer();
		if (name.equals(POSOLOGIES_TAGNAME)) {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				posologyData.append(readEntry(reader)).append("\n");
				reader.endObject();
			}
			posologyData.append("\n");
			reader.endArray();
		}else {
			if(nextInChain != null) {
				posologyData = super.readJSon(name, reader);
			}else {
				reader.skipValue();
				System.err.println("Category "+name+" not processed");
			}
		}
		return posologyData;
	}
	
	private String readEntry(JsonReader reader) throws IOException {
		String posName = null;
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(DESCRIPTION_FIELD_TAGNAME)) {
				posName = reader.nextString();
			} else {
				reader.skipValue();
			}
		}

		return posName;
	}
}
