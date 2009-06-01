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

import java.net.URI;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

import pt.uc.dei.cms.incerto.utils.OWLStatistics;
import pt.uc.dei.cms.incerto.utils.OWLStatisticsVisitor;


public class TestStatistics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String location = "ontologias/Fulltarget.owl";
		
		OWLStatisticsVisitor vis = new OWLStatisticsVisitor();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		URI physicalURI = URI.create("file:/"+location);
		OWLOntology ontology = null;

		try {
			ontology = manager.loadOntologyFromPhysicalURI(physicalURI);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		
		
		OWLStatistics stat = vis.getOWLStatistics(ontology,location);
		System.out.println(OWLStatistics.header());
		System.out.println(stat.toString());
	}

}
