package be.dno.running.pacecalculator;

import java.util.HashMap;
import java.util.Map;

public class SpecificPercentagesMap {
	private static Map<String,Integer[]> map = new HashMap<String,Integer[]>();

	static{
		map.put("800", new Integer[]{120,125});
		map.put("1000", new Integer[]{105,115});
		map.put("1500", new Integer[]{101,111});
		map.put("2000", new Integer[]{98,102});
		map.put("3000", new Integer[]{95,100});
		map.put("5000", new Integer[]{86,95});
		map.put("10000", new Integer[]{85,90});
		map.put("20000", new Integer[]{78,85});
		map.put("21100", new Integer[]{78,85});
		map.put("42195", new Integer[]{72,80});
	}

	public static Integer[] getPercentage(String vma){
		return map.get(vma);
	}
	
	
}
