package pt.uc.dei.cms.incerto.learners.ontologypopulation.model;

public class NounPhrase implements Comparable<String>{

	private TextualEntity head;
	private TextualEntity completePhrase;
	
	public NounPhrase(String head)
	{
		this.head = new TextualEntity(head);
		this.completePhrase = this.head;
	}
	
	public NounPhrase(TextualEntity head)
	{
		this.head = head;
		this.completePhrase = this.head;
	}
	
	public NounPhrase(String head, String modifiers)
	{
		this.head = new TextualEntity(head);
		this.completePhrase = new TextualEntity(modifiers+head);
	}
	
	public NounPhrase(TextualEntity head, String modifiers)
	{
		this.head = head;
		this.completePhrase = new TextualEntity(modifiers+head);
	}
	
	public TextualEntity getHead() {
		return head;
	}

	public void setHead(TextualEntity head) {
		this.head = head;
	}

	public TextualEntity getCompletePhrase() {
		return completePhrase;
	}

	public void setCompletePhrase(TextualEntity completePhrase) {
		this.completePhrase = completePhrase;
	}

	//@Override
	public int compareTo(String o) {
		if(head.compareTo(o) == 0 || completePhrase.compareTo(o) == 0)
			return 0;
		return -1;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other==null)
			return false;
		if(other==this)
			return true;
		if (!(other instanceof NounPhrase))
			return false;
		NounPhrase o = (NounPhrase)other;
		if(this.head.getLemmatizedName().compareTo(o.getHead().getLemmatizedName())== 0)
			return true;
		return false;
	}
	
	@Override 
	public int hashCode() {
		return this.head.getLemmatizedName().hashCode();
	}
	
	public String toString()
	{
		return "NP(Head("+head.toString()+")Complete("+completePhrase.toString()+"))";
	}
	
}
