package pt.uc.dei.cms.incerto.learners.ontologypopulation.model;

public class PropertyAssertion {
	
	private NounPhrase sourceIndividual;
	private NounPhrase property;
	private NounPhrase targetIndividual;
	
	public PropertyAssertion(NounPhrase sourceIndividual, NounPhrase property, NounPhrase targetIndividual) {
		this.sourceIndividual = sourceIndividual;
		this.property = property;
		this.targetIndividual = targetIndividual;
	}
	public NounPhrase getSourceIndividual() {
		return sourceIndividual;
	}
	public void setSourceIndividual(NounPhrase sourceIndividual) {
		this.sourceIndividual = sourceIndividual;
	}
	public NounPhrase getProperty() {
		return property;
	}
	public void setProperty(NounPhrase property) {
		this.property = property;
	}
	public NounPhrase getTargetIndividual() {
		return targetIndividual;
	}
	public void setTargetIndividual(NounPhrase targetIndividual) {
		this.targetIndividual = targetIndividual;
	}
}
