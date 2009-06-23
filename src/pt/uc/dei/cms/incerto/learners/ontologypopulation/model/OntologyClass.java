package pt.uc.dei.cms.incerto.learners.ontologypopulation.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import pt.uc.dei.cms.incerto.utils.LinguisticUtils;

public class OntologyClass implements Comparable<String>{
	
	private TextualEntity originalName;
	private TextualEntity normalizedName;
	private TextualEntity label;
	
	public OntologyClass(String name)
	{
		this.originalName = new TextualEntity(name);
		this.normalizedName = new TextualEntity(LinguisticUtils.normalizeOntologyEntity(name));
		this.label = this.originalName;
	}
	
	public OntologyClass(TextualEntity name)
	{
		this.originalName = name;
		this.normalizedName = new TextualEntity(LinguisticUtils.normalizeOntologyEntity(name.getOriginalName()));
		this.label = this.originalName;
	}
	
	public OntologyClass(String name, String label)
	{
		this.originalName = new TextualEntity(name);
		this.normalizedName = new TextualEntity(LinguisticUtils.normalizeOntologyEntity(name));
		this.label = new TextualEntity(label);
	}

	public TextualEntity getOriginalName() {
		return originalName;
	}

	public void setOriginalName(TextualEntity originalName) {
		this.originalName = originalName;
	}

	public TextualEntity getNormalizedName() {
		return normalizedName;
	}

	public void setNormalizedName(TextualEntity normalizedName) {
		this.normalizedName = normalizedName;
	}

	public TextualEntity getLabel() {
		return label;
	}

	public void setLabel(TextualEntity label) {
		this.label = label;
	}

	//Generate all the possible search engine query names for this ontology class, using the name, normalized name, and label of the class 
	public List<String> generatePossibleQueries()
	{
		HashSet<String> possibleQueries = new HashSet<String>();
		possibleQueries.addAll(originalName.generatePossibleQueries());
		possibleQueries.addAll(normalizedName.generatePossibleQueries());
		if(label!=null)
			possibleQueries.addAll(label.generatePossibleQueries());
		return new ArrayList<String>(possibleQueries);
	}

	//@Override
	public int compareTo(String o) {
		if(originalName.compareTo(o)==0 || normalizedName.compareTo(o)==0 || (label!=null && label.compareTo(o)==0))
			return 0;
		return -1;
	}
	
	//@Override
	public boolean equals(Object other)
	{
		if(other==null)
			return false;
		if(other==this)
			return true;
		if (!(other instanceof OntologyClass))
			return false;
		OntologyClass o = (OntologyClass)other;
		if(this.normalizedName.compareTo(o.getNormalizedName())== 0)
			return true;
		return false;
	}
	
	//TODO: Improve HashCode!
	public int hashCode() {
		return this.normalizedName.hashCode();
	}
	
	public String toString()
	{
		return "Class(OName("+originalName.toString()+")NormName("+normalizedName.toString()+")Label("+label.toString()+"))";
	}
}
