package concadenademando;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class MedicinePresentationsReader extends ChainOfRespElement{
	
	private static final String MED_PRES_TAGNAME = "medicinePresentations";
	private static final String MEDREF_FIELD_TAGNAME = "medicineRef";
	private static final String ACTINGREF_FIELD_TAGNAME = "activeIngRef";
	private static final String INHREF_FIELD_TAGNAME = "inhalerRef";
	private static final String DOSE_FIELD_TAGNAME = "dose";
	private static final String POSREF_FIELD_TAGNAME = "posologyRef";
	
	public MedicinePresentationsReader(ChainOfRespElement chainElement) {
		super(chainElement);
	}
	
	public StringBuffer readJSon(String name, JsonReader reader) throws IOException {
		StringBuffer medicinePresentationData = new StringBuffer();
		if (name.equals(MED_PRES_TAGNAME)) {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				medicinePresentationData.append(readEntry(reader)).append("\n");
				reader.endObject();
			}
			medicinePresentationData.append("\n");
			reader.endArray();
	    }else {
	    	if (nextInChain != null) {
	    		medicinePresentationData = super.readJSon(name, reader);
	    	}else {
	    		reader.skipValue();
	    		System.err.println("Category "+name+" not processed");
	    	}
	    }
		return medicinePresentationData;
	}
	
	private String readEntry(JsonReader reader) throws IOException {
		String medRef = null;
		String aiRef = null;
		String inhRef = null;
		String dose = null;
		String posRef = null;
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(MEDREF_FIELD_TAGNAME)) {
				medRef = reader.nextString();
			} else if (name.equals(ACTINGREF_FIELD_TAGNAME)) {
				aiRef = reader.nextString();
			} else if (name.equals(INHREF_FIELD_TAGNAME)) {
				StringBuffer res = new StringBuffer();
				reader.beginArray();
				while (reader.hasNext()) {
					res.append(reader.nextString()).append(",");
				}
				reader.endArray();
				res.deleteCharAt(res.length() - 1);
				inhRef = new String(res);
			} else if (name.equals(DOSE_FIELD_TAGNAME)) {
				StringBuffer res = new StringBuffer();
				reader.beginArray();
				while (reader.hasNext()) {
					res.append(reader.nextString()).append(",");
				}
				reader.endArray();
				res.deleteCharAt(res.length() - 1);
				dose = new String(res);
			} else if (name.equals(POSREF_FIELD_TAGNAME)) {
				StringBuffer res = new StringBuffer();
				reader.beginArray();
				while (reader.hasNext()) {
					res.append(reader.nextString()).append(",");
				}
				reader.endArray();
				res.deleteCharAt(res.length() - 1);
				posRef = new String(res);
			}else {
				reader.skipValue();
			}

		}
		return medRef + ";" + aiRef + ";" + inhRef + ";" + dose + ";" + posRef;
	}
}
