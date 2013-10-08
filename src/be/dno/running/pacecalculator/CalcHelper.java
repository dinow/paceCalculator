package be.dno.running.pacecalculator;


import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CalcHelper {

	private static String toDoubleDigit(int paramInt){
		String str = (paramInt+"");
		if (str.length() == 1){
			str = "0" + str;
		}
		return str;
	}

	private static String toDoubleDigit(double paramInt){
		NumberFormat nf = new DecimalFormat() ;
		nf.setMaximumFractionDigits(2); // la tu auras au plus 2 chiffres apres la virgule
		nf.setMinimumFractionDigits(2);  // maintenant tout tes nombres auront 2 chiffres après la virgule

		return nf.format(paramInt); // renvoie un String
	}




	public static CalculationResult calculate(CalculationResult crInput){
		CalculationResult crOutput = new CalculationResult();

		if(!crInput.getTxtKms().isEmpty()){//on a les kms
			crOutput.setTxtKms(crInput.getTxtKms());
			double kms = Double.valueOf(crInput.getTxtKms());

			if(!crInput.getTxtVitesse().isEmpty()){//Calcul du temps à mettre pour cette distance à cette vitesse
				double speed = Double.valueOf(crInput.getTxtVitesse());
				double secondsForOneKilo = 3600 / speed;
				double totalSecondForDistance = kms * secondsForOneKilo;
				crOutput.setTxtTemps(toTime(totalSecondForDistance));
			}
			if(!crInput.getTxtAllure().isEmpty()){//Calcul du temps à mettre pour cette distance à cette allure
				double secondsForOneKilo = getTotSecs(crInput.getTxtAllure());
				double totalSecondForDistance = kms * secondsForOneKilo;
				crOutput.setTxtTemps(toTime(totalSecondForDistance));
			}
			if(!crInput.getTxtTemps().isEmpty()){ //Calcul vitesse et allures pour cette distance et ce temps
				double secondsTotal = getTotSecs(crInput.getTxtTemps());
				double secondsForOneKilo = secondsTotal / kms;
				crOutput.setTxtVitesse(toDoubleDigit(3600/secondsForOneKilo));
				crOutput.setTxtAllure(toTime(secondsForOneKilo));
			}
		}

		if (!crInput.getTxtTemps().isEmpty()){//on a le temps
			crOutput.setTxtTemps(crInput.getTxtTemps());
			double totalTime = getTotSecs(crInput.getTxtTemps());
			if(!crInput.getTxtVitesse().isEmpty()){
				double speed = Double.valueOf(crInput.getTxtVitesse());
				double secondsForOneKilo = 3600 / speed;
				crOutput.setTxtKms(toDoubleDigit(totalTime/secondsForOneKilo));
			}

			if(!crInput.getTxtAllure().isEmpty()){//Calcul du temps à mettre pour cette distance à cette allure
				double secondsForOneKilo = getTotSecs(crInput.getTxtAllure());
				crOutput.setTxtKms(toDoubleDigit(totalTime/secondsForOneKilo));
			}
		}

		if(!crInput.getTxtAllure().isEmpty()){//on a l'allure
			crOutput.setTxtAllure(crInput.getTxtAllure());
			double totSeconds = getTotSecs(crInput.getTxtAllure());
			crOutput.setTxtVitesse(toDoubleDigit(3600/totSeconds));
		}

		if(!crInput.getTxtVitesse().isEmpty()){//on a la vitesse
			crOutput.setTxtVitesse(crInput.getTxtVitesse());
			double speed = Double.valueOf(crInput.getTxtVitesse());
			double secondsForOneKilo = 3600 / speed;
			crOutput.setTxtAllure(toTime(secondsForOneKilo));
		}


		return crOutput;
	}

	public static String toTime(double paramDouble){
		int hours = (int)(paramDouble / 3600.0D);
		int minutes = (int)(paramDouble % 3600) / 60;
		int seconds = (int)(paramDouble% 60.0D);
		return toDoubleDigit(hours) + ":" + toDoubleDigit(minutes) + ":" + toDoubleDigit(seconds);
	}

	private static double[] getTimeArray(String input){
		String[] list = input.split(":");
		double[] ret = new double[list.length];
		for (int i = 0; i < list.length; i++){
			ret[i] = Double.valueOf(list[i]);
		}

		return ret;
	}

	public static double getTotSecs(String time){
		String[] arrayOfString = time.split(":");
		double hour = 0d;
		double minutes = 0d;
		double seconds = 0d;
		if (arrayOfString.length == 3){
			hour = Double.valueOf(arrayOfString[0]);
			minutes = Double.valueOf(arrayOfString[1]);
			seconds = Double.valueOf(arrayOfString[2]);
		}
		if (arrayOfString.length == 2){
			minutes = Double.valueOf(arrayOfString[0]);
			seconds = Double.valueOf(arrayOfString[1]);
		}else if (arrayOfString.length == 1){
			seconds = Double.valueOf(arrayOfString[0]);
		}
		return Double.valueOf(60.0D * (60.0D * hour) + 60.0D * minutes + seconds);
	}

}
