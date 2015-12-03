import java.util.ArrayList;
import java.util.HashMap;

public class Catalog {
	HashMap<String, Integer> regions = new HashMap(300);
	
	public void addval(String key){
		
		int valEdit;
		if (regions.containsKey(key) == true){
			valEdit = (int) regions.get(key);
			valEdit = valEdit + 1;
			regions.put(key, valEdit);
		} else {
			regions.put(key, 1);
		}
	}
	public int rtnval(String key){
		int rtnval = 0;
		if (regions.containsKey(key) == true){
			rtnval = regions.get(key);
		}
		return rtnval;
	}

}
