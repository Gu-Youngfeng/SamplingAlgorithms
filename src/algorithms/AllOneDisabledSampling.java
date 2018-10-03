package algorithms;

import java.util.List;
import utils.SATer;

/***
 * <p><b>All-one-disabled</b> sampling strategy disables one features at each time
 * and enables the other features with the constraints. </p>
 * <p>Note: <b>All-one-disabled</b> strategy differs from <b>One-disabled</b> strategy that the former
 * collects all valid configurations while the latter only obtains the first satisfied one.
 * </p>
 */
public class AllOneDisabledSampling {

	List<List<String>> configs;
	
	public AllOneDisabledSampling(String path){
		
		configs = SATer.getAllOneDisabledConfig(path);
	}
	
	public List<List<String>> getSamples(){
		return configs;
	}
}
