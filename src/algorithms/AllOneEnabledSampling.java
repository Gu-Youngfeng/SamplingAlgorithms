package algorithms;

import java.util.List;
import utils.SATer;

/***
 * <p><b>All-one-enabled</b> sampling strategy enables one features at each time
 * and disables the other features with the constraints. </p>
 * <p>Note: <b>All-one-enabled</b> strategy differs from <b>One-enabled</b> strategy that the former
 * collects all valid configurations while the latter only obtains the first satisfied one.
 * </p>
 */
public class AllOneEnabledSampling {

	List<List<String>> configs;
	
	public AllOneEnabledSampling(String path){
		
		configs = SATer.getAllOneEnabledConfig(path);
	}
	
	public List<List<String>> getSamples(){
		return configs;
	}
}
