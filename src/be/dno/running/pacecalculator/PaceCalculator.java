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
import android.widget.TextView;

public class PaceCalculator extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pace_calculator);
		

        MyPagerAdapter adapter = new MyPagerAdapter();
        ViewPager myPager = (ViewPager) findViewById(R.id.mypanelpager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);
     
		
	}
	
	private void loadVMACalc(){
		TextView helperText = 	(TextView)findViewById(R.id.helperText);
		EditText etVMA = 		(EditText)findViewById(R.id.txtVMA);
		try{ 
			SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
			etVMA.setText(sharedPref.getString("vma", ""));
		}catch(Exception ex){
			helperText.setText(ex.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pace_calculator, menu);
		return true;
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
		EditText etKms = 		(EditText)findViewById(R.id.txtKms);
		EditText etAllure = 	(EditText)findViewById(R.id.txtAllure);
		EditText etTemps = 		(EditText)findViewById(R.id.txtTemps);
		EditText etVitesse = 	(EditText)findViewById(R.id.txtVitesse);
		EditText etPourcVMA = 	(EditText)findViewById(R.id.txtPourcVMA);
		EditText etVMA = 		(EditText)findViewById(R.id.txtVMA);
		
		TextView helperText = 	(TextView)findViewById(R.id.helperText);
		
		//Get values
		String txtKms = 		etKms.getText().toString();
		String txtAllure = 		etAllure.getText().toString();
		String txtTemps = 		etTemps.getText().toString();
		String txtVitesse = 	etVitesse.getText().toString();
		String txtPourcVMA = 	etPourcVMA.getText().toString();
		String txtVMA = 		etVMA.getText().toString();
		
		//Store values in container class
		CalculationResult crInput = new CalculationResult();
		crInput.setAllure(txtAllure);
		crInput.setKms(txtKms);
		crInput.setTemps(txtTemps);
		crInput.setVitesse(txtVitesse);
		crInput.setVma(txtVMA);
		crInput.setPourcVMA(txtPourcVMA);
		
		//Save VMA
		try{ 
			SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("vma", txtVMA);
			editor.commit();
		}catch(Exception ex){
			helperText.setText(ex.getMessage());
		}
		
		
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
	
	public void resetVMA(View paramView){
		((EditText)findViewById(R.id.txtVMA)).setText("");
	}
	
	public void resetPourcVMA(View paramView){
		((EditText)findViewById(R.id.txtPourcVMA)).setText("");
	}
	
	private class MyPagerAdapter extends PagerAdapter {

        public int getCount() {
                return 4;
        }

        public Object instantiateItem(View collection, int position) {

                LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                int resId = 0;
                switch (position) {
	                case 0:
	                        resId = R.layout.pace_calc;
	                        
	                        break;
	                case 1:
	                        resId = R.layout.vma;
	                        break;
	                case 2:
	                        resId = R.layout.specifique;
	                        break;
	                case 3:
	                        resId = R.layout.previsions;
	                        break;
                }

                View view = inflater.inflate(resId, null);

                ((ViewPager) collection).addView(view, 0);
                if (resId == R.layout.pace_calc){
                	loadVMACalc();
                }
                
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
