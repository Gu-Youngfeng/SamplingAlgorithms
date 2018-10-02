package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utils.SATer;

/***
 * <p><b>Random</b> sampling strategy randomly selects several configurations that satisfied constraints, 
 * which can be considered as a robust baseline strategy. </p>
 * <p>Note: this strategy needs the variable <b>num</b> (configurations to be selected)</p>
 */
public class RandomSampling {

	List<List<String>> configs;
	
	public RandomSampling(String path, int num){
		
		configs = new ArrayList<>();
		List<List<String>> configs_all = SATer.obtainAllConfigurations(path); // all valid configurations
		System.out.println("[size]" + configs_all.size());
		int[] lsIDs = returnRandList(configs_all.size(), num);
		
		for(int i=0; i<lsIDs.length; i++){
			configs.add(configs_all.get(lsIDs[i])); // add configurations according to the lsIDs
		}
	}
	
	public List<List<String>> getSamples(){
		return configs;
	}
	
	/***
	 * <p>Randomly select <b>num</b> numbers from <b>size</b> numbers. </p>
	 * @param size number of selection pool
	 * @param num number to be selected
	 * @return
	 */
	private int[] returnRandList(int size, int num){
		
		/** shuffle the array */
		int[] lsRDs = new int[size];
		Random rd = new Random();	
		for(int i=0; i<size; i++){
			lsRDs[i] = i;
		}
		for(int i=0; i<size; i++){
			int temp_id = rd.nextInt(size);
			int temp = lsRDs[temp_id];
			lsRDs[temp_id] = lsRDs[i];
			lsRDs[i] = temp;	
		}
		
		/** select the top-num*/
		int[] sels = new int[num];
		for(int i=0; i<num; i++){
			sels[i] = lsRDs[i];
		}
		
		return sels; // return the top-num of lsRDs
	}
}
