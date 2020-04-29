package concadenademando;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GsonDatabaseClient {

	public static void main(String[] args) {
		try{
			UserManualStepsReader userManSteps = new UserManualStepsReader(null);
			UserManualPhysioStepsReader userManPhysioSteps = new UserManualPhysioStepsReader(userManSteps);
			RescueMedicinePresentationsReader rescMedPres = new RescueMedicinePresentationsReader(userManPhysioSteps);
			MedicinePresentationsReader medPres = new MedicinePresentationsReader(rescMedPres);
			PosologiesReader posologies = new PosologiesReader(medPres);
			InhalersReader inhalers = new InhalersReader(posologies);
			PhysiotherapiesReader physios = new PhysiotherapiesReader(inhalers);
			ActiveIngredientsReader actIngrs = new ActiveIngredientsReader(physios);
			MedicinesReader meds = new MedicinesReader(actIngrs);
			DatabaseJSonReader dbjs = new DatabaseJSonReader(meds);

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