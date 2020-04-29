package concadenademando;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class MedicinesReader extends ChainOfRespElement{
	
	private static final String MEDICINES_TAGNAME = "medicines";
	private static final String NAME_FIELD_TAGNAME = "name";
	
	public MedicinesReader(ChainOfRespElement chainElement) {
		super(chainElement);
		
	}
	
	public StringBuffer readJSon(String name, JsonReader reader) throws IOException {
		StringBuffer medicineData = new StringBuffer();
		if (name.equals(MEDICINES_TAGNAME)) {	
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				medicineData.append(readMedicineEntry(reader)).append("\n");
				reader.endObject();
			}
			medicineData.append("\n");
			reader.endArray();	
		}else {
			if (nextInChain != null) {
				medicineData = super.readJSon(name, reader);
			}else {
				reader.skipValue();
				System.err.println("Category "+name+" not processed");
			}
		}
		return medicineData;
	}
	
	private String readMedicineEntry(JsonReader reader) throws IOException {
		String medName = null;
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(NAME_FIELD_TAGNAME)) {
				medName = reader.nextString();
			} else {
				reader.skipValue();
			}
		}

		return medName;
	}
}
