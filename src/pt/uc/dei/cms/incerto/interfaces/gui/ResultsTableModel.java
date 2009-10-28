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

package pt.uc.dei.cms.incerto.interfaces.gui;

import javax.swing.table.AbstractTableModel;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.model.ReasoningResults;

/**
 * TableModel for Results window
 * @author Pedro Oliveira
 *
 */
public class ResultsTableModel extends AbstractTableModel {

	private ReasoningResults results;;
	private static final int FORMULA_INDEX=0;
	private static final int PROB_INDEX=1;
	
	private static final String[] columnNames = new String[]{"Assertion","Probability"};
	private static final boolean[] isEditable = new boolean[]{false,false};
	private static final Class[] types = new Class [] {
		Formula.class,java.lang.Double.class
    };
	
	public ResultsTableModel(ReasoningResults results)
	{
		this.results = results;
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return results.getFormulas().size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Formula f = results.getFormulas().get(rowIndex);
		switch(columnIndex)
		{
		case FORMULA_INDEX: return f;
		case PROB_INDEX: return f.getWeight();
		default: return new Object();
		}
	}
	
	public void setValueAt(Object value, int row, int column) {
		return;
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return isEditable[columnIndex];
	}
	
	public Class getColumnClass(int column) {
		return types[column];
	}


}
