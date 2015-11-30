package alg.hybird;

import java.io.File;

import similarity.metric.Cosine;
import similarity.metric.Pearson;
import similarity.metric.HybridSim;
import similarity.metric.SimilarityMetric;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;
import alg.CFAlgorithm;
import alg.ib.ItemBasedCF;
import alg.ib.neighbourhood.Neighbourhood;
import alg.ib.neighbourhood.kNearestNeighbourhood;
import alg.ib.predictor.Predictor;
import alg.ib.predictor.Resnick;
import alg.ub.neighbourhood.ThresholdNeighbourhood;

public class ExecuteHybird {
	
	public static void main(String[] args)
	{
		
		// configure the user-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
		alg.ub.predictor.Predictor predictorUB = new alg.ub.predictor.Resnick();
		alg.ub.neighbourhood.Neighbourhood neighbourhoodUB = new ThresholdNeighbourhood(20,0);
		SimilarityMetric metricUB = new HybridSim(0.4,0.5,0.1);
		
		// configure the Item-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
		Predictor predictorIB = new Resnick();
		Neighbourhood neighbourhoodIB = new kNearestNeighbourhood(20);
		SimilarityMetric metricIB = new HybridSim(0.8,0.3,0.1);

		
		// set the paths and filenames of the item file, train file and test file ...
		String itemFile = "ML dataset" + File.separator + "u.item";
		String trainFile = "ML dataset" + File.separator + "u.train";
		String testFile = "ML dataset" + File.separator + "u.test";
		
		// set the path and filename of the output file ...
		String outputFile = "results" + File.separator + "predictions.txt";
		
		/////////////////////////////////////////////////////////////////////////////////
		// Evaluates the CF algorithm:
		// - the RMSE (if actual ratings are available) and coverage are output to screen
		// - the output file is created
		DatasetReader reader = new DatasetReader(itemFile, trainFile, testFile);
		
		CFAlgorithm hbcf = new HybirdBasedCF(predictorIB, predictorUB, neighbourhoodIB, neighbourhoodUB, metricIB, metricUB, reader);
		Evaluator eval = new Evaluator(hbcf, reader.getTestData());
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
