package algorithms;

import java.util.List;
import utils.SATer;

/***
 * <p><b>T-wise</b> sampling strategy generates configurations
 *  to cover all the combination among <b>T</b> features.</p>
 *  <p>Note: Due to the limitation of computation, we only choose <b>T=1, 2, 3, 4</b>.</p>
 */
public class TWiseSampling {

	List<List<String>> configs;
	
	public TWiseSampling(String path, int strength){
		configs = SATer.getTWiseConfig(path, strength);
	}
	
	public List<List<String>> getSamples(){
		return configs;
	}
}
