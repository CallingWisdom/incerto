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

package pt.uc.dei.cms.incerto.onto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLOntology;

import pt.uc.dei.cms.incerto.utils.LoggerUtils;


public class OWLAPIUtils {
	
	private static URI label;
	
	public static URI getLabelURI()
	{
		try
		{
			if(label == null)
				label = new URI("http://www.w3.org/2000/01/rdf-schema#label");	//rdfs:label
		}catch(URISyntaxException e)
		{
			LoggerUtils.addError("Fatal Error: (rdfs:label is not a valid URI?)",e);
			System.exit(-1);
		}
		return label;
	}
	
	public static String getLabel(OWLEntity entity, OWLOntology ontology)
	{
		Set<OWLAnnotation> labels = entity.getAnnotations(ontology,getLabelURI());
		if(labels.size()>0)
			return labels.iterator().next().getAnnotationValueAsConstant().toString();
		return null;		
	}

}
