/*
 * Copyright 2009 Pedro Oliveira
 * This file is part of Incerto.
 * 
 * Incerto is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Incerto is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Incerto.  If not, see <http://www.gnu.org/licenses/>.
 */

package pt.uc.dei.cms.incerto.experiments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.StringUtils;

public class ClusterSimilarity {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {	
		String path = "C:/Documents and Settings/Pedro/Ambiente de trabalho/Software/cluto-2.1.1/Win32/";
		//new ClusterSimilarity().clusterSimilarity(path+"diseases.res.graph.clustering.5_graph", path+"diseases.res.graph.clustering.5_normal", 1);
		new ClusterSimilarity().printCluster(path+"diseases.res.graph.clustering.5_normal", path+"diseases.res.graph.rlabel");
	}
	
	//Type = 1 {correlation} | 2 {Rand Index} | 3 {Adjusted Rand Index}
	public void clusterSimilarity(String cl1, String cl2, int type) throws IOException
	{
		HashMap<Integer,HashSet<Integer>> c1 = readClusterFile(cl1);
		HashMap<Integer,HashSet<Integer>> c2 = readClusterFile(cl2);
		clusterCorrelation(c1, c2);
	}
	
	public void printCluster(String cl, String labels) throws IOException
	{
		List<String> l = InOutUtils.readListFromFile(labels);
		HashMap<Integer,HashSet<Integer>> c = readClusterFile(cl);
		for(Integer i: c.keySet())
		{
			HashSet<Integer> els1 = c.get(i);
			System.out.print(i+" ("+els1.size()+") [");		
			for(Integer i2: els1)
				System.out.print(l.get(i2)+",");
			System.out.println("]");
		}
	}
	
	private void clusterCorrelation(HashMap<Integer,HashSet<Integer>> c1, HashMap<Integer,HashSet<Integer>> c2)
	{
		int i=0, j, corr;
		
		System.out.print("\t");
		for(Integer i2: c2.keySet())
			System.out.print("["+c2.get(i2).size()+"]\t");
		System.out.println();
		for(Integer i1: c1.keySet())
		{
			j=0;
			HashSet<Integer> els1 = c1.get(i1);
			System.out.print("["+els1.size()+"]\t");
			for(Integer i2: c2.keySet())
			{
				corr = 0;
				for(Integer el: c2.get(i2))
				{
					if(els1.contains(el))
						corr++;
				}				
				j++;
				//System.out.print("["+i+","+j+"] "+corr+"\t");
				System.out.print(corr+"\t");
			}
			System.out.println();
			i++;
		}
	}
	
	
	private HashMap<Integer,HashSet<Integer>> readClusterFile(String filename) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		HashMap<Integer,HashSet<Integer>> clusters = new LinkedHashMap<Integer, HashSet<Integer>>();
		
		String line;
		int clusterN, count = 0;
		while((line=br.readLine()) != null)
		{
			if(line.trim().isEmpty())
				continue;
			clusterN = Integer.parseInt(line);
			HashSet<Integer> elems = clusters.get(clusterN);
			if(elems == null)
				elems = new HashSet<Integer>();
			elems.add(count++);
			clusters.put(clusterN, elems);
		}
		br.close();
		return clusters;
	}

}
