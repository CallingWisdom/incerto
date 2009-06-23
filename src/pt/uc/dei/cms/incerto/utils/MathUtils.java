package pt.uc.dei.cms.incerto.utils;

import java.util.ArrayList;
import java.util.List;

public class MathUtils {

	public static double mean(List<Double> values)
	{
		double sum=0;		
		for(Double d: values)
			sum+=d;
		return sum/values.size();
	}

	public static double stdeviation(List<Double> values, double mean)
	{
		ArrayList<Double> dif = new ArrayList<Double>(values.size());		
		for(Double d: values)
			dif.add(Math.pow(d-mean,2));	
		return Math.sqrt(MathUtils.mean(dif));
	}

	public static double stdeviation(List<Double> values)
	{
		return stdeviation(values, mean(values));
	}


	/**
	 * value' = (value-mean)/stdeviation
	 * @param value
	 * @param mean
	 * @param stdeviation
	 * @return
	 */
	public static double scalling(Double value, Double mean, Double stdeviation)
	{
		return (value-mean)/stdeviation;
	}

	/**
	 * value' = (value-min)/(max-min)
	 * @param value
	 * @param max
	 * @param min
	 * @return
	 */
	public static double normalize01(Double value, Double max, Double min)
	{
		return (value-min)/(max-min);
	}


	/**
	 * value' = value/max
	 * @param value
	 * @param max
	 * @return
	 */
	public static double normalize01Max(Double value, Double max)
	{
		return value/max;
	}


	/**
	 * value' = ((value-min)/(max-min))*(new_max-new_min)+new_min
	 * @param value
	 * @param max
	 * @param min
	 * @param new_max
	 * @param new_min
	 * @return
	 */
	public static double normalize(Double value, Double max, Double min, Double new_max, Double new_min)
	{
		return ((value-min)/(max-min))*((new_max-new_min)+new_min);
	}

	public static double log2(double num)
	{
		return (Math.log(num)/LOG2);
	} 

	public static double getPearsonCorrelation(double[] scores1,double[] scores2){
		double result = 0;
		double sum_sq_x = 0;
		double sum_sq_y = 0;
		double sum_coproduct = 0;
		double mean_x = scores1[0];
		double mean_y = scores2[0];
		for(int i=2;i<scores1.length+1;i+=1){
			double sweep =Double.valueOf(i-1)/i;
			double delta_x = scores1[i-1]-mean_x;
			double delta_y = scores2[i-1]-mean_y;
			sum_sq_x += delta_x * delta_x * sweep;
			sum_sq_y += delta_y * delta_y * sweep;
			sum_coproduct += delta_x * delta_y * sweep;
			mean_x += delta_x / i;
			mean_y += delta_y / i;
		}
		double pop_sd_x = (double) Math.sqrt(sum_sq_x/scores1.length);
		double pop_sd_y = (double) Math.sqrt(sum_sq_y/scores1.length);
		double cov_x_y = sum_coproduct / scores1.length;
		result = cov_x_y / (pop_sd_x*pop_sd_y);
		return result;
	}
	
	private static double LOG2 = Math.log(2);
}
