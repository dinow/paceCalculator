package be.dno.running.pacecalculator;

public class CalculationResult {
	private String kms = "";
	private String allure = "";
	private String temps = "";
	private String vitesse = "";
	private String vma; 
	private String pourcVMA;
	public String getKms() {
		return kms;
	}
	public void setKms(String kms) {
		this.kms = kms;
	}
	public String getAllure() { 
		return allure;
	}
	public void setAllure(String allure) {
		this.allure = allure;
	}
	public String getTemps() {
		return temps;
	}
	public void setTemps(String temps) {
		this.temps = temps;
	}
	public String getVitesse() {
		return vitesse;
	}
	public void setVitesse(String vitesse) {
		this.vitesse = vitesse;
	}
	public String getVma() {
		return vma;
	}
	public void setVma(String vma) {
		this.vma = vma;
	}
	public String getPourcVMA() {
		return pourcVMA;
	}
	public void setPourcVMA(String pourcVMA) {
		this.pourcVMA = pourcVMA;
	}
	@Override
	public String toString() {
		return "CalculationResult [kms=" + kms + ", allure=" + allure
				+ ", temps=" + temps + ", vitesse=" + vitesse + ", vma=" + vma
				+ ", pourcVMA=" + pourcVMA + "]";
	}
	
	
	
	
}
