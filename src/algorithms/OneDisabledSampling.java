package algorithms;

import java.util.List;
import utils.SATer;

/***
 * <p><b>One-disabled</b> sampling strategy disables one features at each time
 * and enables the other features with the constraints. </p>
 * <p>For features A, B, C and D we can get 4 samples like that, </p>
 * <pre> [!A, B, C, D]
 * [A, !B, C, D]
 * [A, B, !C, D]
 * [A, B, C, !D]
 * </pre>
 */
public class OneDisabledSampling {

	List<List<String>> configs;
	
	public OneDisabledSampling(String path){
		
		configs = SATer.getOneDisabledConfig(path);
	}
	
	public List<List<String>> getSamples(){
		return configs;
	}
}
