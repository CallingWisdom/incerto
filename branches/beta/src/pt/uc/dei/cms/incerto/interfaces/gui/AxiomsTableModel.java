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

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;

/**
 * TableModel for axioms in the Main Window
 * @author Pedro Oliveira
 *
 */
public class AxiomsTableModel extends AbstractTableModel {
	
	private ArrayList<InternalAxiom> elements;
	private static final int FORMULA_INDEX=0;
	private static final int WEIGHT_INDEX=1;
	private static final int USE_INDEX=2;
	
	private static final String[] columnNames = new String[]{"Formula","Weight","Use"};
	private static final boolean[] isEditable = new boolean[]{false,true,true};
	private static final Class[] types = new Class [] {
		Formula.class,java.lang.Double.class, java.lang.Boolean.class
    };

	public AxiomsTableModel() {
		elements = new ArrayList<InternalAxiom>();
	}
	
	public void addElement(Formula f, boolean use)
	{
		elements.add(new InternalAxiom(f,use));
	}
	
	public ArrayList<InternalAxiom> getElements()
	{
		return elements;
	}

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return elements.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		InternalAxiom t = elements.get(rowIndex);
		switch(columnIndex)
		{
		case FORMULA_INDEX: return t.f;
		case WEIGHT_INDEX: return t.f.getWeight();
		case USE_INDEX: return t.use;
		default: return new Object();
		}
	}
	
	public void setValueAt(Object value, int row, int column) {
		InternalAxiom axiom = elements.get(row);
		switch(column)
		{
		case FORMULA_INDEX: break;
		case WEIGHT_INDEX: axiom.f.setWeight((Double)value);break;
		case USE_INDEX: axiom.use = (Boolean)value;
		}
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
