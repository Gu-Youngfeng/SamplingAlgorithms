package algorithms;

import java.util.List;

import utils.SATer;

/***
 * <p><b>All-most-enabled-disabled</b> collects 2 kinds of configurations 
 * which either has the most enabled features or has the most disabled features.</p>
 * <p>For features A, B, C and D we can get 2 samples like that, </p>
 * <pre> [!A, !B, !C, !D]
 * [A, B, C, D]
 * </pre>
 *
 */
public class AllMostEnabledDisabledSampling {
	
	List<List<String>> configs;
	
	public AllMostEnabledDisabledSampling(String path){
		configs = SATer.getAllMostEnabledDisabledConfig(path);
	}
	
	public List<List<String>> getSamples(){
		return configs;
	}

}
