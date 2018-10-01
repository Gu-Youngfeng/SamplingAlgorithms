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

	public static void main(String[] args) {
		
		List<String> config_1st = obtainFirstConfiguration("file/jhipster-3.6.1.dimacs");
		System.out.println(config_1st);
		System.out.println("------------");
		
//		List<List<String>> configs = obtainAllConfigurations("file/jhipster-3.6.1.dimacs");
//		for(List<String> config: configs) System.out.println(config);
//		System.out.println("------------");
//		System.out.println("size:" + configs.size());
		
		List<String> config_sel = obtainRandomConfiguration("file/jhipster-3.6.1.dimacs");
		System.out.println(config_sel);
		System.out.println("------------");
		
		List<List<String>> configs = getOneEnabledConfig("file/jhipster-3.6.1.dimacs");
		for(List<String> config: configs) System.out.println(config);
		System.out.println("------------");
		System.out.println("size:" + configs.size());
		
	}
	
	
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
				System.out.print("[n]:" + n + "[v]:" + v.size());
				
				try{
					solver.addAtMost(v, (n+1)); // at most 2
					solver.addClause(new VecInt(clause)); // let the i-th features to be enabled
				}catch(Exception e){
					continue;
				}
				if(problem.isSatisfiable()){
					model = problem.model();
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
}
