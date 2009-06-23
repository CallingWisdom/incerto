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

package pt.uc.dei.cms.incerto.learners;

import pt.uc.dei.cms.incerto.exceptions.LearnerException;
import pt.uc.dei.cms.incerto.learners.sources.OntologyPopulationSource;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;

public interface EvidenceLearner {

	public Evidence learn(MLN mln, OntologyPopulationSource source) throws LearnerException;
}
