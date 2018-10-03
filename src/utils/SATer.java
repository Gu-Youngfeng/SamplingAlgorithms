package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.InstanceReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

public class SATer {	
	
	/***
	 * <p>To obtain the 1-st valid configuration that satisfied the constraints from the cnf file. </p>
	 * @param path cnf file
	 * @return valid configuration
	 */
	public static List<String> obtainFirstConfiguration(String path){	
		
		/** STEP 1: Find the model */
		int[] model = null;	// sat model	
		ISolver solver = SolverFactory.newDefault();
		solver.setTimeout(3600);
		Reader reader = new DimacsReader(solver);
		try {
			IProblem problem = reader.parseInstance(path);			
			if(problem.isSatisfiable()){
				model = problem.model();
			}			
		} catch (ParseFormatException e) {
			System.out.println("CNF convertion Failed! " + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Open CNF Failed! " + path);
			e.printStackTrace();
		} catch (ContradictionException e) {
			System.out.println("Contradiction Exception! " + path);
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("SAT Timeout! " + path);
			e.printStackTrace();
		}
		
		if(model == null){ // no solution
			System.out.println("SAT4J CANNOT find such solution. " + path);
			return null;
		}
		
		/** STEP 2: Parse to configuration */
		List<String> features = obtainFeature(path); // feature name list
		List<String> config = new ArrayList<String>(); // valid configuration
		if(features.size() != model.length){
			System.out.println("Feature inconsistent. " + path);
			return null;
		}		
		for(int i=0; i<model.length; i++){ // each configuration
			if(model[i] < 0){ 
				config.add("!"+features.get(i));
			}
			else{
				config.add(features.get(i));
			}
		}
				
		return config;
	}
	
	
	/***
	 * <p>To obtain the all valid configurations that satisfied the constraints from the cnf file. </p>
	 * @param path cnf file
	 * @return valid configurations list
	 */
	public static List<List<String>> obtainAllConfigurations(String path){
		
		/** STEP 1: Find the model */
		List<int[]> models = new ArrayList<int[]>(); // sat model list	
		ISolver solver = SolverFactory.newDefault();
		ModelIterator mi =  new ModelIterator(solver);
		Reader reader = new InstanceReader(mi);
		solver.setTimeout(3600);
		try {
			IProblem problem = reader.parseInstance(path);			
			while(problem.isSatisfiable()){
				int[] model = problem.model();
				models.add(model);
			}			
		} catch (ParseFormatException e) {
			System.out.println("CNF convertion Failed! " + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Open CNF Failed! " + path);
			e.printStackTrace();
		} catch (ContradictionException e) {
			System.out.println("Contradiction Exception! " + path);
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("SAT Timeout! " + path);
			e.printStackTrace();
		}
		
		if(models.size() == 0){ // no solution
			System.out.println("SAT4J CANNOT find such solution. " + path);
			return null;
		}
		
		/** STEP 2: Parse to configurations */
		List<String> features = obtainFeature(path); // feature name list
		List<List<String>> configs = new ArrayList<>(); // valid configurations
		if(features.size() != models.get(0).length){
			System.out.println("Feature inconsistent. " + path);
			return null;
		}	
		for(int[] model: models){ // for each model
			List<String> config = new ArrayList<String>(); // each configuration
			for(int i=0; i<model.length; i++){
				if(model[i] < 0){ 
					config.add("!"+features.get(i));
				}
				else{
					config.add(features.get(i));
				}
			}
			configs.add(config);
		}
				
		return configs;
	}
	
	
	/***
	 * <p>To obtain a random valid configuration that satisfied the constraints from the cnf file. </p>
	 * @param path cnf file
	 * @return valid configuration
	 */
	public static List<String> obtainRandomConfiguration(String path){
		
		/** STEP 1: Find the models */
		List<int[]> models = new ArrayList<int[]>();	// sat model list
		ISolver solver = SolverFactory.newDefault();
		ModelIterator mi =  new ModelIterator(solver);
		Reader reader = new InstanceReader(mi);
		solver.setTimeout(3600);
		try {
			IProblem problem = reader.parseInstance(path);			
			while(problem.isSatisfiable()){
				int[] model = problem.model();
				models.add(model);
			}		
		} catch (ParseFormatException e) {
			System.out.println("CNF convertion Failed! " + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Open CNF Failed! " + path);
			e.printStackTrace();
		} catch (ContradictionException e) {
			System.out.println("Contradiction Exception! " + path);
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("SAT Timeout! " + path);
			e.printStackTrace();
		}
		
		if(models.size() == 0){ // no solution
			System.out.println("SAT4J CANNOT find such solution. " + path);
			return null;
		}
		
		/** STEP 2: Randomly select one model */
		int index = (int)(Math.random()*models.size());
		int[] model_sel = models.get(index);
		
		/** STEP 3: Parse to configuration */
		List<String> features = obtainFeature(path); // feature name list
		List<String> config = new ArrayList<String>(); // valid configuration
		if(features.size() != model_sel.length){
			System.out.println("Feature inconsistent. " + path);
			return null;
		}		
		for(int i=0; i<model_sel.length; i++){ // each configuration
			if(model_sel[i] < 0){ 
				config.add("!"+features.get(i));
			}
			else{
				config.add(features.get(i));
			}
		}
				
		return config;
	}

		
	/***
	 * <p>To obtain total feature names list from the cnf file. </p>
	 * @param path cnf file
	 * @return name list
	 */
	public static List<String> obtainFeature(String path){
		
		List<String> features = new ArrayList<String>();	
		File file = new File(path);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line=br.readLine())!=null){
				if(line.startsWith("p cnf")) break; // break
				String[] partition = line.split(" ");
				features.add(partition[2]); // obtain feature name
			}
			br.close(); // close input stream
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return features;
	}

	
	/***
	 * <p>To obtain the one-enabled samples with the constraints. <br>
	 * Note that, for each feature we disable the most of other features.</p>
	 * @param path cnf file
	 * @return samples list
	 */
	public static List<List<String>> getOneEnabledConfig(String path){
		
		List<List<String>> configs = new ArrayList<>();
		
		List<String> features = obtainFeature(path);
		int nVars = features.size(); // number of features
		
		for(int i=2; i<=nVars; i++){
			int[] clause = {i}; // from the 2nd feature
			
			int[] model = getMostDisabled(path, clause);
			
			if(model == null){
				System.out.println("The feature " + features.get(i-1) + " cannot be enabled!");
			}else{
				List<String> config = new ArrayList<String>(); // valid configuration
				if(features.size() != model.length){
					System.out.println("Feature inconsistent. " + path);
					return null;
				}		
				for(int k=0; k<model.length; k++){ // each configuration
					if(model[k] < 0){ 
						config.add("!"+features.get(k));
					}
					else{
						config.add(features.get(k));
					}
				}
				configs.add(config);
			}
		}
		
		return configs;
	}
	
	
	/***
	 * <p>enable one feature and disable the most of other features. </p>
	 * @param path cnf file
	 * @param clause enabled feature
	 * @return sat model
	 */
	public static int[] getMostDisabled(String path, int[] clause){
		
		int[] model = null;	// sat model	
		ISolver solver = SolverFactory.newDefault();
		solver.setTimeout(3600);
		Reader reader = new DimacsReader(solver);	
		
		try {		
			IProblem problem = reader.parseInstance(path);
			for(int n=1; n<problem.nVars(); n++){
				
				solver = SolverFactory.newDefault();
				reader = new DimacsReader(solver);
				problem = reader.parseInstance(path);
			
				VecInt v = new VecInt();
				for(int i=1; i<=problem.nVars(); i++){
					v.push(i);
				}
//				System.out.print("[n]:" + n + "[v]:" + v.size());
				
				try{
					solver.addAtMost(v, (n+1)); // at most 2
					solver.addClause(new VecInt(clause)); // let the i-th features to be enabled
				}catch(Exception e){
					continue;
				}
				
				if(problem.isSatisfiable()){
					model = problem.model();
//					System.out.println("[n]:" + n);
					break;
				}else{
					continue;
				}			
			}
		} catch (ParseFormatException e) {
			System.out.println("CNF convertion Failed! " + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Open CNF Failed! " + path);
			e.printStackTrace();
		} catch (ContradictionException e) {
			System.out.println("Contradiction Exception! " + path);
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("SAT Timeout! " + path);
			e.printStackTrace();
		}
		
		return model;
		
	}
	
	
	/***
	 * <p>To obtain the one-disabled samples with the constraints. <br>
	 * Note that, for each feature we enable the most of other features.</p>
	 * @param path cnf file
	 * @return samples list
	 */
	public static List<List<String>> getOneDisabledConfig(String path){
		
		List<List<String>> configs = new ArrayList<>();
		
		List<String> features = obtainFeature(path);
		int nVars = features.size(); // number of features
		
		for(int i=2; i<=nVars; i++){
			int[] clause = {-i}; // from the 2nd feature
			
			int[] model = getMostEnabled(path, clause);
			
			if(model == null){
				System.out.println("The feature " + features.get(i-1) + " cannot be disabled!");
			}else{
				List<String> config = new ArrayList<String>(); // valid configuration
				if(features.size() != model.length){
					System.out.println("Feature inconsistent. " + path);
					return null;
				}		
				for(int k=0; k<model.length; k++){ // each configuration
					if(model[k] < 0){ 
						config.add("!"+features.get(k));
					}
					else{
						config.add(features.get(k));
					}
				}
				configs.add(config);
			}
			
		}
		
		return configs;
	}
	
	
	/***
	 * <p>disable one feature and enable the most of other features. </p>
	 * @param path cnf file
	 * @param clause disabled feature
	 * @return sat model
	 */
	public static int[] getMostEnabled(String path, int[] clause){
		
		int[] model = null;	// sat model	
		ISolver solver = SolverFactory.newDefault();
		solver.setTimeout(3600);
		Reader reader = new DimacsReader(solver);
		
		try {		
			IProblem problem = reader.parseInstance(path);
			for(int n=problem.nVars(); n>0; n--){
				
				solver = SolverFactory.newDefault();
				reader = new DimacsReader(solver); 
				problem = reader.parseInstance(path);
			
				VecInt v = new VecInt();
				for(int i=1; i<=problem.nVars(); i++){
					v.push(i);
				}
//				System.out.println("[n]:" + n + "[v]:" + v.size());
				
				try{
					solver.addAtLeast(v, (n-1)); // at most 2
					solver.addClause(new VecInt(clause)); // let the i-th features to be enabled
				}catch(Exception e){
					continue;
				}
				
				if(problem.isSatisfiable()){
					model = problem.model();
//					System.out.println("[n]:" + n);
					break;
				}else{
					continue;
				}			
			}
		} catch (ParseFormatException e) {
			System.out.println("CNF convertion Failed! " + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Open CNF Failed! " + path);
			e.printStackTrace();
		} catch (ContradictionException e) {
			System.out.println("Contradiction Exception! " + path);
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("SAT Timeout! " + path);
			e.printStackTrace();
		}
		
		return model;	
	}

	
	/***
	 * <p>To obtain the all-one-disabled samples with the constraints. <br>
	 * Note that, for each feature we enable the most of other features and return all the possible configurations. </p>
	 * @param path cnf file
	 * @return samples list
	 */
	public static List<List<String>> getAllOneDisabledConfig(String path){
		
		List<List<String>> configs = new ArrayList<>();
		
		List<String> features = obtainFeature(path);
		int nVars = features.size(); // number of features
		
		for(int i=2; i<=nVars; i++){
			int[] clause = {-i}; // from the 2nd feature
//			System.out.println("[disabled]:" + features.get(i-1));
			List<int[]> models = getAllMostEnabled(path, clause); 
			
			if(models.size() == 0){
				System.out.println("The feature " + features.get(i-1) + " cannot be disabled!");
			}else{
				
				if(features.size() != models.get(0).length){
					System.out.println("Feature inconsistent. " + path);
					return null;
				}	
				for(int[] model: models){
//					int count = 0;
					List<String> config = new ArrayList<String>(); // valid configuration
					for(int k=0; k<model.length; k++){ // each configuration
						if(model[k] < 0){ 
							config.add("!"+features.get(k));
//							count++;
						}
						else{
							config.add(features.get(k));
						}
					}
					configs.add(config);
//					System.out.println("[disabled]:" + count);
				}
			}
			
//			break;
		}
		
		return configs;
	}
	
	
	/***
	 * <p>disable one feature and enable the most of other features. </p>
	 * @param path cnf file
	 * @param clause disabled feature
	 * @return sat model
	 */
	public static List<int[]> getAllMostEnabled(String path, int[] clause){
		
		List<int[]> models = new ArrayList<int[]>();	// sat model	
		ISolver solver = SolverFactory.newDefault();
		solver.setTimeout(3600);		
		ModelIterator mi =  new ModelIterator(solver); ///
		Reader reader = new InstanceReader(mi); ///
		
		try {		
			IProblem problem = reader.parseInstance(path);
			for(int n=problem.nVars(); n>0; n--){
				
				solver = SolverFactory.newDefault();
				mi =  new ModelIterator(solver); ///
				reader = new InstanceReader(mi); ///
				problem = reader.parseInstance(path);
			
				VecInt v = new VecInt();
				for(int i=1; i<=problem.nVars(); i++){
					v.push(i);
				}
//				System.out.println("[n]:" + n + "[v]:" + v.size());
				
				try{
					solver.addAtLeast(v, (n-1)); // at most 2
					solver.addClause(new VecInt(clause)); // let the i-th features to be disabled
				}catch(Exception e){
					continue;
				}
				
				int[] model = null;
				while(problem.isSatisfiable()){
					model = problem.model();
					models.add(model);
//					System.out.println("[n]:" + n);
//					break;
				}
				
				if(model != null){
					break;
				}
				
			}
		} catch (ParseFormatException e) {
			System.out.println("CNF convertion Failed! " + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Open CNF Failed! " + path);
			e.printStackTrace();
		} catch (ContradictionException e) {
			System.out.println("Contradiction Exception! " + path);
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("SAT Timeout! " + path);
			e.printStackTrace();
		}
		
		return models;	
	}
	
	
	/***
	 * <p>To obtain the all-one-enabled samples with the constraints. <br>
	 * Note that, for each feature we disable the most of other features and return all the possible configurations. </p>
	 * @param path cnf file
	 * @return samples list
	 */
	public static List<List<String>> getAllOneEnabledConfig(String path){
		
		List<List<String>> configs = new ArrayList<>();
		
		List<String> features = obtainFeature(path);
		int nVars = features.size(); // number of features
		
		for(int i=2; i<=nVars; i++){
			int[] clause = {i}; // from the 2nd feature
//			System.out.println("[disabled]:" + features.get(i-1));
			List<int[]> models = getAllMostDisabled(path, clause); 
			
			if(models.size() == 0){
				System.out.println("The feature " + features.get(i-1) + " cannot be enabled!");
			}else{
				
				if(features.size() != models.get(0).length){
					System.out.println("Feature inconsistent. " + path);
					return null;
				}	
				for(int[] model: models){
//					int count = 0;
					List<String> config = new ArrayList<String>(); // valid configuration
					for(int k=0; k<model.length; k++){ // each configuration
						if(model[k] < 0){ 
							config.add("!"+features.get(k));
//							count++;
						}
						else{
							config.add(features.get(k));
						}
					}
					configs.add(config);
//					System.out.println("[disabled]:" + count);
				}
			}
			
//			break;
		}
		
		return configs;
	}
	
	
	/***
	 * <p>enable one feature and disable the most of other features. </p>
	 * @param path cnf file
	 * @param clause enabled feature
	 * @return sat model
	 */
	public static List<int[]> getAllMostDisabled(String path, int[] clause){
		
		List<int[]> models = new ArrayList<int[]>();	// sat model	
		ISolver solver = SolverFactory.newDefault();
		solver.setTimeout(3600);		
		ModelIterator mi =  new ModelIterator(solver); ///
		Reader reader = new InstanceReader(mi); ///
		
		try {		
			IProblem problem = reader.parseInstance(path);
			for(int n=1; n<problem.nVars(); n++){
				
				solver = SolverFactory.newDefault();
				mi =  new ModelIterator(solver); ///
				reader = new InstanceReader(mi); ///
				problem = reader.parseInstance(path);
			
				VecInt v = new VecInt();
				for(int i=1; i<=problem.nVars(); i++){
					v.push(i);
				}
//				System.out.println("[n]:" + n + "[v]:" + v.size());
				
				try{
					solver.addAtMost(v, (n+1)); // at most 2
					solver.addClause(new VecInt(clause)); // let the i-th features to be enabled
				}catch(Exception e){
					continue;
				}
				
				int[] model = null;
				while(problem.isSatisfiable()){
					model = problem.model();
					models.add(model);
//					System.out.println("[n]:" + n);
//					break;
				}
				
				if(model != null){
					break;
				}
				
			}
		} catch (ParseFormatException e) {
			System.out.println("CNF convertion Failed! " + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Open CNF Failed! " + path);
			e.printStackTrace();
		} catch (ContradictionException e) {
			System.out.println("Contradiction Exception! " + path);
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("SAT Timeout! " + path);
			e.printStackTrace();
		}
		
		return models;	
	}
	
	
	/***
	 * <p>To obtain all the configurations satisfy the most-enabled-disabled constraints. </p>
	 * @param path cnf file
	 * @return configurations list
	 */
	public static List<List<String>> getAllMostEnabledDisabledConfig(String path){
		List<List<String>> configs = obtainAllConfigurations(path);
		List<List<String>> configs_sel = new ArrayList<>();
		
		int count_enabled;
		int most_enabled = 0;
		int least_enabled = 0;
		
		for(int i=0; i<configs.size(); i++){ // for each configuration
			count_enabled = countEnabled(configs.get(i));
			
			if(count_enabled > most_enabled){ // configurations with the most disabled features
				most_enabled = count_enabled;
			}
		}
		
		least_enabled = configs.get(0).size();
		
		for(int i=0; i<configs.size(); i++){ // for each configuration
			count_enabled = countEnabled(configs.get(i));
			
			if(count_enabled < least_enabled){ // configurations with the most enabled features
				least_enabled = count_enabled;
			}
		}
		
//		System.out.println("[most_enabled]:" + most_enabled);
//		System.out.println("[least_enabled]:" + least_enabled);
		
		for(int i=0; i<configs.size(); i++){ // for each configuration
			count_enabled = countEnabled(configs.get(i));
			if(count_enabled == most_enabled || count_enabled == least_enabled){
				configs_sel.add(configs.get(i));
			}
		}
		
		return configs_sel;
	}	
	
	
	/***
	 * <p>To obtain the number of enabled features of the configuration. </p>
	 * @param config configuration
	 * @return number of enabled features
	 */
	public static int countEnabled(List<String> config){
		int num_enabled = 0;
		
		for(String option: config){
			if(!option.startsWith("!")){
				num_enabled++;				
			}
		}
		
		return num_enabled;
			
	}
	
	
	/***
	 * <p>To read configurations based on {@link CSVReader}, which reads configuration from csv result. </p>
	 * @param path cnf file
	 * @param strength value of T
	 * @return configurations list
	 */
	public static List<List<String>> getTWiseConfig(String path, int strength){
	
		String csv_result = "";
		//TODO: complete the T-wise csv results 
		switch(strength){
		case 1:
			csv_result = ""; // 1-wise
			break;
		case 2:
			csv_result = ""; // 2-wise
			break;
		case 3:
			csv_result = ""; // 3-wise
			break;
		case 4:
			csv_result = ""; // 4-wise
			break;
		default:
			csv_result = ""; // 2-wise
			System.out.print("T=1,2,3,4");
			break;
			
		}
		
		CSVReader reader = new CSVReader(csv_result); // read from csv result
		List<List<String>> configs_sel = reader.configs;
		
		return configs_sel;

	}
	
}
