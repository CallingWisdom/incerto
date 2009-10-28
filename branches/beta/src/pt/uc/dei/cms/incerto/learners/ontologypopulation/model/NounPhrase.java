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
