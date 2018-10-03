package algorithms;

import java.util.List;

import utils.SATer;

/***
 * <p><b>Most-enabled-disabled</b> collects 2 configurations 
 * which either has the most enabled features or has the most disabled features.</p>
 * <p>For features A, B, C and D we can get 2 samples like that, </p>
 * <pre> [!A, !B, !C, !D]
 * [A, B, C, D]
 * </pre>
 *
 */
public class MostEnabledDisabledSampling {
	
	List<List<String>> configs;
	
	public MostEnabledDisabledSampling(String path){
		configs = SATer.getMostEnabledDisabledConfig(path);
	}
	
	public List<List<String>> getSamples(){
		return configs;
	}

}
