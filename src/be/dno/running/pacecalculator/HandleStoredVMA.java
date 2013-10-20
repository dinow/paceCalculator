package be.dno.running.pacecalculator;

import android.widget.EditText;

public class HandleStoredVMA extends Thread {

	private EditText etVMA;

	public int SET = 1;
	public int GET = 2;

	public void run(int type, int etID) {
		if (type == SET){
			
		}else{

		}
	}

	public EditText getEtVMA() {
		return etVMA;
	}

	public void setEtVMA(EditText etVMA) {
		this.etVMA = etVMA;
	}

	
}
