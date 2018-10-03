package algorithms;

import java.util.List;

import utils.SATer;

/***
 * <p><b>All-most-enabled-disabled</b> collects 2 kinds of configurations 
 * which either has the most enabled features or has the most disabled features.</p>
 * <p>Note: **All-most-enabled-disabled** strategy differs from **Most-enabled-disabled** strategy that 
 * the former collects all valid configurations while the latter only obtains the first satisfied one.</p>
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
