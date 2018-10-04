package experiments;

import java.util.List;

import algorithms.AllMostEnabledDisabledSampling;
import algorithms.AllOneDisabledSampling;
import algorithms.AllOneEnabledSampling;
import algorithms.DissimilaritySampling;
import algorithms.MostEnabledDisabledSampling;
import algorithms.OneDisabledSampling;
import algorithms.OneEnabledSampling;
import algorithms.RandomSampling;
import algorithms.TWiseSampling;

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
		
		/** Most-enabled-disabled sampling */
		MostEnabledDisabledSampling meds = new MostEnabledDisabledSampling("file/jhipster-3.6.1.dimacs");
		List<List<String>> config_meds = meds.getSamples();
		for(List<String> config: config_meds) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + config_meds.size());
		
		/** all-one-enabled sampling */
		AllOneEnabledSampling aoes = new AllOneEnabledSampling("file/jhipster-3.6.1.dimacs");
		List<List<String>> configs_aoes = aoes.getSamples();
		for(List<String> config: configs_aoes) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + configs_aoes.size());
		
		/** all-one-disabled sampling */
		AllOneDisabledSampling aods = new AllOneDisabledSampling("file/jhipster-3.6.1.dimacs");
		List<List<String>> configs_aods = aods.getSamples();
		for(List<String> config: configs_aods) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + configs_aods.size());
				
		/** All-most-enabled-disabled sampling */
		AllMostEnabledDisabledSampling ameds = new AllMostEnabledDisabledSampling("file/jhipster-3.6.1.dimacs");
		List<List<String>> config_ameds = ameds.getSamples();
		for(List<String> config: config_ameds) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + config_ameds.size());
		
		/** random sampling */
		RandomSampling rs = new RandomSampling("file/jhipster-3.6.1.dimacs", 8); // randomly select 8 valid configurations
		List<List<String>> config_rd = rs.getSamples();
		for(List<String> config: config_rd) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + config_rd.size());
		
		/** T-wise sampling */
		TWiseSampling tws = new TWiseSampling("file/jhipster-3.6.1.dimacs", 2); // conduct pair-wise testing
		List<List<String>> config_tws = tws.getSamples();
		for(List<String> config: config_tws) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + config_tws.size());
		
		/** T-wise sampling */
		DissimilaritySampling ds = new DissimilaritySampling("file/jhipster.pledge.40", 40); // conduct dissimilarity sampling
		List<List<String>> config_ds = ds.getSamples();
		for(List<String> config: config_ds) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + config_ds.size());

	}

}
