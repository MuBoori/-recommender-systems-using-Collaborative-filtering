/**
 * Used to evaluate the user-based CF algorithm
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package alg.ub;

import java.io.File;

import javax.swing.table.TableColumn;

import alg.ub.neighbourhood.*;
import alg.ub.predictor.*;
import similarity.metric.*;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteUB
{
	public static void main(String[] args)
	{
		// configure the user-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
		Predictor predictor = new WeightedAverage();
		Neighbourhood neighbourhood = new ThresholdNeighbourhood(20,0.01);

		
		// set the paths and filenames of the item file, train file and test file ...
		String itemFile = "ML dataset" + File.separator + "u.item";
		String trainFile = "ML dataset" + File.separator + "u.train";
		String testFile = "ML dataset" + File.separator + "u.probe";
		
		// set the path and filename of the output file ...
		String outputFile = "results" + File.separator + "predictions.txt";
		
		/////////////////////////////////////////////////////////////////////////////////
		// Evaluates the CF algorithm:
		// - the RMSE (if actual ratings are available) and coverage are output to screen
		// - the output file is created
		DatasetReader reader = new DatasetReader(itemFile, trainFile, testFile);
		SimilarityMetric metric = new Pearson();
		UserBasedCF ubcf = new UserBasedCF(predictor, neighbourhood, metric, reader);
		Evaluator eval = new Evaluator(ubcf, reader.getTestData());
		eval.writeResults(outputFile);
		
		Double RMSE = eval.getRMSE();
		if(RMSE != null) System.out.println("RMSE: " + RMSE);
		
		for(int i = 1; i <= 5; i++)
		{
			RMSE = eval.getRMSE(i);
			if(RMSE != null) System.out.println("RMSE (true rating = " + i + "): " + RMSE);
		}
		
		double coverage = eval.getCoverage();
		System.out.println("coverage: " + coverage + "%");
	}
}
	