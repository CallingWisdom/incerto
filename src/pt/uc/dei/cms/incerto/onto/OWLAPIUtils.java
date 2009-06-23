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
