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

package pt.uc.dei.cms.incerto.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import pt.uc.dei.cms.incerto.utils.OWLStatisticsVisitor;


public class TestStatisticsBatch {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		TestStatisticsBatch tsb = new TestStatisticsBatch();
		tsb.starter();
	}

	public void starter() throws IOException
	{
		File folder = new File("d:/ontologias/tobe/ontoselect/5");
		BufferedWriter out = new BufferedWriter(new FileWriter("stats5.csv",true));
		OWLStatisticsVisitor vis = new OWLStatisticsVisitor();
		//out.write(OWLStatistics.header());
		//out.newLine();
		openFolder(folder, vis, out);

		out.close();
	}

	public void openFolder(File folder, OWLStatisticsVisitor vis, BufferedWriter out) throws IOException
	{
		int i = 1;
		System.out.println(folder.getName().toUpperCase());
		for(File fi: folder.listFiles())
		{
			out.flush();
			System.out.print("("+i+++"/"+folder.list().length+") ");
			if(fi.isFile())
			{
				URI physicalURI = null;
				OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
				OWLOntology ontology = null;
				try {
					physicalURI = URI.create("file:/"+fi.getAbsolutePath().replace('\\', '/'));
					ontology = manager.loadOntologyFromPhysicalURI(physicalURI);
				} catch (Exception e) {				
					//e.printStackTrace();
					System.out.println("Exception");
					continue;
				}
				out.write(vis.getOWLStatistics(ontology, physicalURI.toString()).toString());
				out.newLine();		
				System.out.println(fi.getAbsolutePath());
				//System.out.println(vis.getOWLStatistics(ontology, physicalURI.toString()).toString());
			}
			else if (fi.isDirectory())
				openFolder(fi,vis,out);
		}
		out.flush();
	}

}
