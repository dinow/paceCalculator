package be.dno.running.pacecalculator;

import java.util.HashMap;
import java.util.Map;

public class VMAPercentagesMap {
	private static Map<String,Integer[]> map = new HashMap<String,Integer[]>();

	static{
		map.put("30/30", new Integer[]{105,100});
		map.put("200", new Integer[]{105,100});
		map.put("300", new Integer[]{100});
		map.put("400", new Integer[]{100,95});
		map.put("500", new Integer[]{95});
		map.put("600", new Integer[]{95});
		map.put("800", new Integer[]{95});
		map.put("1000", new Integer[]{95});
		map.put("Endurance", new Integer[]{65,69});
	}

	public static Integer[] getPercentage(String vma){
		return map.get(vma);
	}
	
	
}
