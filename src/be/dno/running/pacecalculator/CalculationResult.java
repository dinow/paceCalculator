package be.dno.running.pacecalculator;

public class CalculationResult {
	private String txtKms = "";
	private String txtAllure = "";
	private String txtTemps = "";
	private String txtVitesse = "";
	public String getTxtKms() {
		return txtKms;
	}
	public void setTxtKms(String txtKms) {
		this.txtKms = txtKms;
	}
	public String getTxtAllure() {
		return txtAllure;
	}
	public void setTxtAllure(String txtAllure) {
		this.txtAllure = txtAllure;
	}
	public String getTxtTemps() {
		return txtTemps;
	}
	public void setTxtTemps(String txtTemps) {
		this.txtTemps = txtTemps;
	}
	public String getTxtVitesse() {
		return txtVitesse;
	}
	public void setTxtVitesse(String txtVitesse) {
		this.txtVitesse = txtVitesse;
	}
	@Override
	public String toString() {
		return "CalculationResult [txtKms=" + txtKms + ", txtAllure="
				+ txtAllure + ", txtTemps=" + txtTemps + ", txtVitesse="
				+ txtVitesse + "]";
	}
	
	
}
