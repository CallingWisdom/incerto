package pt.uc.dei.cms.incerto.learners.ontologypopulation.model;

public class ClassAssertion {
	
	private NounPhrase assertionClass;
	private NounPhrase individual;
	
	public ClassAssertion(NounPhrase assertionClass,NounPhrase individual) {
		this.assertionClass = assertionClass;
		this.individual = individual;
	}
	public NounPhrase getAssertionClass() {
		return assertionClass;
	}
	public void setAssertionClass(NounPhrase assertionClass) {
		this.assertionClass = assertionClass;
	}
	public NounPhrase getIndividual() {
		return individual;
	}
	public void setIndividual(NounPhrase individual) {
		this.individual = individual;
	}
}
