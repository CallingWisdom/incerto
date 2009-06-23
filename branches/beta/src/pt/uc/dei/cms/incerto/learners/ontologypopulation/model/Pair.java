package pt.uc.dei.cms.incerto.learners.ontologypopulation.model;

public class Pair <S,T>{
	
	private S first;
	private T second;
	
	public Pair(S first, T second) {
		this.first = first;
		this.second = second;
	}

	public S getFirst() {
		return first;
	}

	public void setFirst(S first) {
		this.first = first;
	}

	public T getSecond() {
		return second;
	}

	public void setSecond(T second) {
		this.second = second;
	}
	
	public String toString()
	{
		return "("+first+","+second+")";
	}
	
	
	@Override
	public boolean equals(Object other)
	{
		if(other==null)
			return false;
		if(other==this)
			return true;
		if (!(other instanceof Pair))
			return false;
		Pair<S, T> p = (Pair<S, T>)other;
		if(this.first.equals(p.getFirst()) && this.second.equals(p.getSecond()))
			return true;
		return false;
	}

	@Override 
	public int hashCode() {
		return 127*first.hashCode()+second.hashCode();
	}
}
