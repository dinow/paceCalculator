package be.dno.running.pacecalculator;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class CalcHelper {
	private static DecimalFormat df = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance());
	/**
	 * 
	 * @param paramInt
	 * @return
	 */
	private static String toDoubleDigit(int paramInt){
		if (Math.abs(paramInt) < 10){
			return ("0" + paramInt);
		}
		return ""+paramInt;
	}

	/**
	 * 
	 * @param paramInt
	 * @return
	 */
	public static String toDoubleDecimal(double paramInt){
		return df.format(paramInt);
	}
	
	public static Double toDouble(String input){
		try {
			return df.parse(input).doubleValue();
		} catch (ParseException e) {
			return Double.valueOf(input);
		}
	}

	/**
	 * 
	 * @param crInput
	 * @return a filled {@link CalculationResult} object
	 */
	public static CalculationResult calculate(CalculationResult crInput){
		CalculationResult crOutput = new CalculationResult();
		//Check if vma and % vma is filled in
		if(!crInput.getVma().isEmpty()){
			if(!crInput.getPourcVMA().isEmpty()){
				double vma = toDouble(crInput.getVma());
				double pVMA = toDouble(crInput.getPourcVMA());
				if (crInput.getVitesse().isEmpty()){
					double speed = vma * (pVMA/100);
					crInput.setVitesse(toDoubleDecimal(speed));
				}
			}
		}
		if(!crInput.getKms().isEmpty()){//on a les kms
			crOutput.setKms(crInput.getKms());
			double kms = toDouble(crInput.getKms());

			if(!crInput.getVitesse().isEmpty()){//Calcul du temps à mettre pour cette distance à cette vitesse
				double speed = toDouble(crInput.getVitesse());
				double secondsForOneKilo = 3600 / speed;
				double totalSecondForDistance = kms * secondsForOneKilo;
				crOutput.setTemps(toTime(totalSecondForDistance));
			}
			if(!crInput.getAllure().isEmpty()){//Calcul du temps à mettre pour cette distance à cette allure
				double secondsForOneKilo = getTotSecs(crInput.getAllure());
				double totalSecondForDistance = kms * secondsForOneKilo;
				crOutput.setTemps(toTime(totalSecondForDistance));
			}
			if(!crInput.getTemps().isEmpty()){ //Calcul vitesse et allures pour cette distance et ce temps
				double secondsTotal = getTotSecs(crInput.getTemps());
				double secondsForOneKilo = secondsTotal / kms;
				crOutput.setVitesse(toDoubleDecimal(3600/secondsForOneKilo));
				crOutput.setAllure(toTime(secondsForOneKilo));
			}
		}
		if (!crInput.getTemps().isEmpty()){//on a le temps
			crOutput.setTemps(crInput.getTemps());
			double totalTime = getTotSecs(crInput.getTemps());
			if(!crInput.getVitesse().isEmpty()){
				double speed = toDouble(crInput.getVitesse());
				double secondsForOneKilo = 3600 / speed;
				crOutput.setKms(toDoubleDecimal(totalTime/secondsForOneKilo));
			}

			if(!crInput.getAllure().isEmpty()){//Calcul du temps à mettre pour cette distance à cette allure
				double secondsForOneKilo = getTotSecs(crInput.getAllure());
				crOutput.setKms(toDoubleDecimal(totalTime/secondsForOneKilo));
			}
		}
		if(!crInput.getAllure().isEmpty()){//on a l'allure
			crOutput.setAllure(crInput.getAllure());
			double totSeconds = getTotSecs(crInput.getAllure());
			crOutput.setVitesse(toDoubleDecimal(3600/totSeconds));
		}
		if(!crInput.getVitesse().isEmpty()){//on a la vitesse
			crOutput.setVitesse(crInput.getVitesse());
			double speed = toDouble(crInput.getVitesse());
			double secondsForOneKilo = 3600 / speed;
			crOutput.setAllure(toTime(secondsForOneKilo));
		}
		if(!crInput.getVma().isEmpty()){//calcul % VMA
			if (!crOutput.getVitesse().isEmpty()){
				double vma = toDouble(crInput.getVma());
				double speed = toDouble(crOutput.getVitesse());
				double pourcVMA = (speed / vma)*100;
				crOutput.setPourcVMA(toDoubleDecimal(pourcVMA));
			}
		}
		return crOutput;
	}
	
	

	/**
	 * 
	 * @param totalSeconds
	 * @return a human readeable time
	 */
	public static String toTime(double totalSeconds){
		int hours = (int)(totalSeconds / 3600.0D);
		int minutes = (int)(totalSeconds % 3600) / 60;
		int seconds = (int)(totalSeconds% 60.0D);
		return toDoubleDigit(hours) + ":" + toDoubleDigit(minutes) + ":" + toDoubleDigit(seconds);
	}

	/**
	 * 
	 * @param time in format hh:mm:ss or mm:ss or ss
	 * @return the seconds in the given time
	 */
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
