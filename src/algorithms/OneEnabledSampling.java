package algorithms;

import java.util.List;
import utils.SATer;

/***
 * <p><b>One-enabled</b> sampling strategy enables one features at each time
 * and disables the other features with the constraints. </p>
 * <p>For features A, B, C and D we can get 4 samples like that, </p>
 * <pre> [A, !B, !C, !D]
 * [!A, B, !C, !D]
 * [!A, !B, C, !D]
 * [!A, !B, !C, D]
 * </pre>
 */
public class OneEnabledSampling {
	
	List<List<String>> configs;
	
	public OneEnabledSampling(String path){
		
		configs = SATer.getOneEnabledConfig(path);
	}
	
	public List<List<String>> getSamples(){
		return configs;
	}
	
}
