package concadenademando;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class ActiveIngredientsReader extends ChainOfRespElement{
	
	private static final String ACTIVEINGREDIENTS_TAGNAME = "activeIngredients";
	private static final String NAME_FIELD_TAGNAME = "name";
	
	public ActiveIngredientsReader(ChainOfRespElement chainElement) {
		super(chainElement);
	}
	
	public StringBuffer readJSon(String name, JsonReader reader) throws IOException { 
		StringBuffer activeIngrData = new StringBuffer();
		if(name.equals(ACTIVEINGREDIENTS_TAGNAME)) {	
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				activeIngrData.append(readActiveIngredientEntry(reader)).append("\n");
				reader.endObject();
			}
			activeIngrData.append("\n");
			reader.endArray();
		}else {
			if (nextInChain != null) {
				activeIngrData = super.readJSon(name, reader);
			}else {
				reader.skipValue();
				System.err.println("Category "+name+" not processed");
			}
		}
		return activeIngrData;
	}
	
	private String readActiveIngredientEntry(JsonReader reader) throws IOException { 
		String actIngrName = null;
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(NAME_FIELD_TAGNAME)) {
				actIngrName = reader.nextString();
			} else {
				reader.skipValue();
			}
		}

		return actIngrName;
	}
	
}
