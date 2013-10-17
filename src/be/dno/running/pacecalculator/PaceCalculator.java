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
			return sharedPref.getString("vma", "");
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
			SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("vma", vma);
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
		
		//Store values in container class
		CalculationResult crInput = new CalculationResult();
		crInput.setAllure(txtAllure);
		crInput.setKms(txtKms);
		crInput.setTemps(txtTemps);
		crInput.setVitesse(txtVitesse);
		crInput.setVma(txtVMA);
		crInput.setPourcVMA(txtPourcVMA);
		
		//Save VMA
		saveVMA(txtVMA);				
		
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
	
	private class MyPagerAdapter extends PagerAdapter {

        public int getCount() {
                return 4;
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
	                case 3:
	                        resId = R.layout.previsions;
	                        vmaId = R.id.prevtxtvma;
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
