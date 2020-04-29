package concadenademando;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class ChainOfRespElement {
	
	protected ChainOfRespElement nextInChain;
	
	public ChainOfRespElement(ChainOfRespElement elem) {
		nextInChain = elem;
	}
	
	public StringBuffer readJSon(String name, JsonReader reader) throws IOException {
		return nextInChain.readJSon(name, reader);
	}

}
