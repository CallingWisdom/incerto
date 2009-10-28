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
