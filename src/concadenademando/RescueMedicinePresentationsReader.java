package concadenademando;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class RescueMedicinePresentationsReader extends ChainOfRespElement{
	
	private static final String RESCUEMEDPRES_TAGNAME = "rescueMedicinePresentations";
	private static final String MEDREF_FIELD_TAGNAME = "medicineRef";
	private static final String ACTINGREF_FIELD_TAGNAME = "activeIngRef";
	private static final String INHREF_FIELD_TAGNAME = "inhalerRef";
	private static final String DOSE_FIELD_TAGNAME = "dose";
	
	public RescueMedicinePresentationsReader(ChainOfRespElement chainElement) {
		super(chainElement);
	}
	
	public StringBuffer readJSon(String name, JsonReader reader) throws IOException {
		StringBuffer rescueMedicinePresentationData = new StringBuffer();
		if (name.equals(RESCUEMEDPRES_TAGNAME)) {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				rescueMedicinePresentationData.append(readRescueMedicinePresentationEntry(reader)).append("\n");
				reader.endObject();
			}
			rescueMedicinePresentationData.append("\n");
			reader.endArray();
		}else {
			if (nextInChain != null) {
				rescueMedicinePresentationData = super.readJSon(name, reader);
			}else {
				reader.skipValue();
				System.err.println("Category "+name+" not processed");
			}
		}
		return rescueMedicinePresentationData;
	}

	private String readRescueMedicinePresentationEntry(JsonReader reader) throws IOException {
		String medRef = null;
		String aiRef = null;
		String inhRef = null;
		String dose = null;
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
			} else {
				reader.skipValue();
			}

		}
		return medRef + ";" + aiRef + ";" + inhRef + ";" + dose;
	}
	
}
