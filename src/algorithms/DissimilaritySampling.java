package algorithms;

import java.util.List;
import utils.SATer;

/***
 * <p><b>Dissimilarity</b> sampling strategy uses evolutionary algorithm to generate an optimal configurations 
 * to cover the feature interaction of <b>T-wise</b> strategy. </p>
 * <p>The biggest advantage of <b>Dissimilarity</b> strategy is that 
 * it generates configurations in a heuristic way which can avoid the expensive computation in <b>T-wise</b> strategy. </p> 
 */
public class DissimilaritySampling {

	List<List<String>> configs;
	
	public DissimilaritySampling(String path, int num){
		configs = SATer.getDissimilarityConfig(path, num);
	}
	
	public List<List<String>> getSamples(){
		return configs;
	}
}
