package concadenademando;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GsonDatabaseClient {

	public static void main(String[] args) {
		try{
			UserManualPhysioStepsReader userManPhysioSteps = new UserManualPhysioStepsReader(null);
			RescueMedicinePresentationsReader rescMedPres = new RescueMedicinePresentationsReader(userManPhysioSteps);
			MedicinePresentationsReader medPres = new MedicinePresentationsReader(rescMedPres);
			PosologiesReader posologies = new PosologiesReader(medPres);
			InhalersReader inhalers = new InhalersReader(posologies);
			PhysiotherapiesReader physios = new PhysiotherapiesReader(inhalers);
			ActiveIngredientsReader actIngrs = new ActiveIngredientsReader(physios);
			MedicinesReader meds = new MedicinesReader(actIngrs);
			UserManualStepsReader userManSteps = new UserManualStepsReader(meds);
			
			DatabaseJSonReader dbjs = new DatabaseJSonReader(userManSteps);

			try {
				System.out.println(dbjs.parse("./datos.json"));
			} finally {
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}

	}

}