package be.dno.running.pacecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PaceCalculator extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pace_calculator);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pace_calculator, menu);
		return true;
	}

	public void closeAppli(View paramView){
	    super.finish();
	}
	
	public void startCalcul(View paramView){
		EditText etKms = (EditText)findViewById(R.id.txtKms);
		EditText etAllure = (EditText)findViewById(R.id.txtAllure);
		EditText etTemps = (EditText)findViewById(R.id.txtTemps);
		EditText etVitesse = (EditText)findViewById(R.id.txtVitesse);
		
		TextView helperText = (TextView)findViewById(R.id.helperText);
		
		String txtKms = etKms.getText().toString();
		String txtAllure = etAllure.getText().toString();
		String txtTemps = etTemps.getText().toString();
		String txtVitesse = etVitesse.getText().toString();
		
		CalculationResult crInput = new CalculationResult();
		crInput.setTxtAllure(txtAllure);
		crInput.setTxtKms(txtKms);
		crInput.setTxtTemps(txtTemps);
		crInput.setTxtVitesse(txtVitesse);
		
		
		CalculationResult crOutput = crInput;
		
		try{
			crOutput = CalcHelper.calculate(crInput);
		}catch(Exception ex){
			helperText.setText(ex.getMessage());
		}
		
		etAllure.setText(crOutput.getTxtAllure());
		etKms.setText(crOutput.getTxtKms());
		etTemps.setText(crOutput.getTxtTemps());
		etVitesse.setText(crOutput.getTxtVitesse());
	}

	public void resetAllure(View paramView){
		((EditText)findViewById(R.id.txtAllure)).setText("");
	}

	public void resetKms(View paramView){
		((EditText)findViewById(R.id.txtKms)).setText("");
	}

	public void resetTemps(View paramView){
		((EditText)findViewById(R.id.txtTemps)).setText("");
	}

	public void resetVitesse(View paramView){
		((EditText)findViewById(R.id.txtVitesse)).setText("");
	}
}
