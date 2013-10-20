package be.dno.running.pacecalculator;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PaceCalculator extends Activity {

	private String activeVMA;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pace_calculator);
        MyPagerAdapter adapter = new MyPagerAdapter();
        ViewPager myPager = (ViewPager) findViewById(R.id.mypanelpager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pace_calculator, menu);
		return true;
	}
	
	
	/**
	 * Persistance VMA
	 * @return
	 */
	private String getVMA(){
		try{ 
			SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
			activeVMA = sharedPref.getString("vma", "");
			return activeVMA;
		}catch(Exception ex){
			return "";
		}
	}
	
	/**
	 * Persistance VMA
	 * @param vma
	 */
	private void saveVMA(String vma){
		try{ 
			activeVMA = vma;
			SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("vma", activeVMA);
			editor.commit();
		}catch(Exception ex){
			
		}
	}
	
	/**
	 * USed in the pace_calc.xml
	 */
	private void loadVMA(int id){
		if (id != -1){
			EditText etVMA = (EditText)findViewById(id);
			etVMA.setText(getVMA());
		}
	}

	

	/**
	 * To close the application
	 * @param paramView
	 */
	public void closeAppli(View paramView){
	    super.finish();
	}
	
	/**
	 * Triggered by onClic on "calcul" button
	 * @param paramView
	 */
	public void startCalcul(View paramView){
		//Get ediatables fields
		EditText etKms = 		(EditText)findViewById(R.id.pctxtkms);
		EditText etAllure = 	(EditText)findViewById(R.id.pctxtallure);
		EditText etTemps = 		(EditText)findViewById(R.id.pctxttemps);
		EditText etVitesse = 	(EditText)findViewById(R.id.pctxtvitesse);
		EditText etPourcVMA = 	(EditText)findViewById(R.id.pctxtpourcvma);
		EditText etVMA = 		(EditText)findViewById(R.id.pctxtvma);
		TextView helperText = 	(TextView)findViewById(R.id.pctxthelper);
		
		//Get values
		String txtKms = 		etKms.getText().toString();
		String txtAllure = 		etAllure.getText().toString();
		String txtTemps = 		etTemps.getText().toString();
		String txtVitesse = 	etVitesse.getText().toString();
		String txtPourcVMA = 	etPourcVMA.getText().toString();
		String txtVMA = 		etVMA.getText().toString();
		
		//Save VMA
		saveVMA(txtVMA);
		
		//Store values in container class
		CalculationResult crInput = new CalculationResult();
		crInput.setAllure(txtAllure);
		crInput.setKms(txtKms);
		crInput.setTemps(txtTemps);
		crInput.setVitesse(txtVitesse);
		crInput.setVma(txtVMA);
		crInput.setPourcVMA(txtPourcVMA);
		
						
		
		//Calculate values
		CalculationResult crOutput = crInput;	
		try{
			crOutput = CalcHelper.calculate(crInput);
		}catch(Exception ex){
			helperText.setText(ex.getMessage());
		}
		
		//Display values
		etAllure.setText(crOutput.getAllure());
		etKms.setText(crOutput.getKms());
		etTemps.setText(crOutput.getTemps());
		etVitesse.setText(crOutput.getVitesse());
		etPourcVMA.setText(crOutput.getPourcVMA());
	}

	public void resetAllure(View paramView){
		((EditText)findViewById(R.id.pctxtallure)).setText("");
	}

	public void resetKms(View paramView){
		((EditText)findViewById(R.id.pctxtkms)).setText("");
	}

	public void resetTemps(View paramView){
		((EditText)findViewById(R.id.pctxttemps)).setText("");
	}

	public void resetVitesse(View paramView){
		((EditText)findViewById(R.id.pctxtvitesse)).setText("");
	}
	
	public void resetVMA(View paramView){
		((EditText)findViewById(R.id.pctxtvma)).setText("");
	}
	
	public void resetPourcVMA(View paramView){
		((EditText)findViewById(R.id.pctxtpourcvma)).setText("");
	}
	
	public void startCalculSpecifique(View paramView){
		EditText etVMA = (EditText)findViewById(R.id.spectxtvma);
		
		
		TextView[] etPerc = {(TextView)findViewById(R.id.specpercentage1),(TextView)findViewById(R.id.specpercentage2)};
		
		TextView[] etTime = {(TextView)findViewById(R.id.spectemps1),(TextView)findViewById(R.id.spectemps2)};
		
		TextView[] etSpeed = {(TextView)findViewById(R.id.specspeed1),(TextView)findViewById(R.id.specspeed2)};
		
		TextView[] etPace = {(TextView)findViewById(R.id.specpace1),(TextView)findViewById(R.id.specpace2)};
	
		String vma = etVMA.getText().toString();
		saveVMA(vma);
		
		if (!vma.isEmpty()){
			Spinner specdistdropdown = (Spinner) findViewById(R.id.specdistdropdown);
			String selectedVMA = specdistdropdown.getSelectedItem().toString();
			Integer[] percentages = SpecificPercentagesMap.getPercentage(selectedVMA);
			
			Double lVMA = CalcHelper.toDouble(vma);
			int i = 0;
			for (Integer percentage : percentages){
				double speed = (lVMA*percentage)/100;
				double secondsForOneKilo = 3600 / speed;
				double secondsForDistance = 0l;
				String secondsForDistanceStr = "";
				String minperkm = CalcHelper.toTime(secondsForOneKilo);

				Double currentDistance = CalcHelper.toDouble(selectedVMA)/1000;
				secondsForDistance = currentDistance * (3600/speed);
				secondsForDistanceStr = CalcHelper.toTime(secondsForDistance);

				etPerc[i].setText(percentage+"%");
				etTime[i].setText(secondsForDistanceStr+"");
				etSpeed[i].setText(CalcHelper.toDoubleDecimal(speed));
				etPace[i].setText(minperkm);
				i++;	
			}
			
			if (i == 1){
				etPerc[i].setText("");
				etTime[i].setText("");
				etSpeed[i].setText("");
				etPace[i].setText("");
			}
		}
		
	}

	public void startCalculVMA(View paramView){
		EditText etVMA = (EditText)findViewById(R.id.vmatxtvma);
		
		
		TextView[] etPerc = {(TextView)findViewById(R.id.vmapercentage1),(TextView)findViewById(R.id.vmapercentage2)};
		
		TextView[] etTime = {(TextView)findViewById(R.id.vmatemps1),(TextView)findViewById(R.id.vmatemps2)};
		
		TextView[] etSpeed = {(TextView)findViewById(R.id.vmaspeed1),(TextView)findViewById(R.id.vmaspeed2)};
		
		TextView[] etPace = {(TextView)findViewById(R.id.vmapace1),(TextView)findViewById(R.id.vmapace2)};
	
		String vma = etVMA.getText().toString();
		saveVMA(vma);
		
		if (!vma.isEmpty()){
			Spinner vmadistdropdown = (Spinner) findViewById(R.id.vmadistdropdown);
			String selectedVMA = vmadistdropdown.getSelectedItem().toString();
			Integer[] percentages = VMAPercentagesMap.getPercentage(selectedVMA);
			
			Double lVMA = CalcHelper.toDouble(vma);
			int i = 0;
			for (Integer percentage : percentages){
				double speed = (lVMA*percentage)/100;
				double secondsForOneKilo = 3600 / speed;
				double secondsForDistance = 0l;
				String secondsForDistanceStr = "";
				String minperkm = CalcHelper.toTime(secondsForOneKilo);
				
				if (selectedVMA.equals("30/30")){
					secondsForDistance = (speed*1000/3600)*30;
					secondsForDistanceStr = CalcHelper.toDoubleDecimal(secondsForDistance);
				}else if (selectedVMA.equals("Endurance")){
					Integer currentDistance = 1;
					secondsForDistance = currentDistance * (3600/speed);
					secondsForDistanceStr = CalcHelper.toTime(secondsForDistance);
				}else{
					Double currentDistance = CalcHelper.toDouble(selectedVMA)/1000;
					secondsForDistance = currentDistance * (3600/speed);
					secondsForDistanceStr = CalcHelper.toTime(secondsForDistance);
				}
				
				etPerc[i].setText(percentage+"%");
				etTime[i].setText(secondsForDistanceStr+"");
				etSpeed[i].setText(CalcHelper.toDoubleDecimal(speed));
				etPace[i].setText(minperkm);
				i++;	
			}
			
			if (i == 1){
				etPerc[i].setText("");
				etTime[i].setText("");
				etSpeed[i].setText("");
				etPace[i].setText("");
			}
		}
		
	}
	
	private class MyPagerAdapter extends PagerAdapter {

        public int getCount() {
                return 3;
        }

        public Object instantiateItem(View collection, int position) {

                LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                int resId = 0;
                int vmaId = -1;
                switch (position) {
	                case 0:
	                        resId = R.layout.pace_calc;
	                        vmaId = R.id.pctxtvma;
	                        break;
	                case 1:
	                        resId = R.layout.vma;
	                        vmaId = R.id.vmatxtvma;
	                        break;
	                case 2:
	                        resId = R.layout.specifique;
	                        vmaId = R.id.spectxtvma;
	                        break;
                }

                View view = inflater.inflate(resId, null);
                ((ViewPager) collection).addView(view, 0);
                loadVMA(vmaId);   
                return view;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView((View) arg2);

        }

        @Override
        public void finishUpdate(View arg0) {
                // TODO Auto-generated method stub

        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == ((View) arg1);

        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
                // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
                // TODO Auto-generated method stub
                return null;
        }

        @Override
        public void startUpdate(View arg0) {
                // TODO Auto-generated method stub

        }

}
}
