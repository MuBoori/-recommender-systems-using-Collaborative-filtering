/**
 * Used to evaluate the user-based CF algorithm
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package alg.ib;

import java.io.File;

import javax.swing.table.TableColumn;

import alg.ib.neighbourhood.*;
import alg.ib.predictor.*;
import similarity.metric.*;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteIB
{
	public static void main(String[] args)
	{
		// configure the user-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
		Predictor predictor = new Resnick();
		Neighbourhood neighbourhood = new kNearestNeighbourhood(30);
		//SimilarityMetric metric = new Cosine();
		
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
		SimilarityMetric metric = new Cosine();
		ItemBasedCF uicf = new ItemBasedCF(predictor, neighbourhood, metric, reader);
		Evaluator eval = new Evaluator(uicf, reader.getTestData());
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
	