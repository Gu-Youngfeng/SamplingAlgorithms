package experiments;

import java.util.List;

import algorithms.AllOneDisabledSampling;
import algorithms.AllOneEnabledSampling;
import algorithms.OneDisabledSampling;
import algorithms.OneEnabledSampling;
import algorithms.RandomSampling;

public class comparison {

	public static void main(String[] args) {
		
		/** one-enabled sampling */
		OneEnabledSampling oes = new OneEnabledSampling("file/jhipster-3.6.1.dimacs");
		List<List<String>> configs_oes = oes.getSamples();
		for(List<String> config: configs_oes) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + configs_oes.size());
		
		/** one-disabled sampling */
		OneDisabledSampling ods = new OneDisabledSampling("file/jhipster-3.6.1.dimacs");
		List<List<String>> configs_ods = ods.getSamples();
		for(List<String> config: configs_ods) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + configs_ods.size());
		
		/** all-one-disabled sampling */
		AllOneDisabledSampling aods = new AllOneDisabledSampling("file/jhipster-3.6.1.dimacs");
		List<List<String>> configs_aods = aods.getSamples();
		for(List<String> config: configs_aods) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + configs_aods.size());
		
		/** all-one-enabled sampling */
		AllOneEnabledSampling aoes = new AllOneEnabledSampling("file/jhipster-3.6.1.dimacs");
		List<List<String>> configs_aoes = aoes.getSamples();
		for(List<String> config: configs_aoes) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + configs_aoes.size());
		
		/** random sampling */
		RandomSampling rs = new RandomSampling("file/jhipster-3.6.1.dimacs", 5);
		List<List<String>> config_rd = rs.getSamples();
		for(List<String> config: config_rd) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + config_rd.size());

	}

}
