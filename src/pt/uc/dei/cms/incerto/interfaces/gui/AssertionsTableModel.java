package pt.uc.dei.cms.incerto.interfaces.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;

public class AssertionsTableModel extends AbstractTableModel {

	private ArrayList<InternalAxiom> elements;
	private static final int FORMULA_INDEX=0;
	private static final int USE_INDEX=1;
	
	private static final String[] columnNames = new String[]{"Assertion","Use"};
	private static final boolean[] isEditable = new boolean[]{false,true};
	private static final Class[] types = new Class [] {
		Formula.class,java.lang.Boolean.class
    };
	
	public AssertionsTableModel()
	{
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
		return 2;
	}

	public int getRowCount() {
		return elements.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		InternalAxiom t = elements.get(rowIndex);
		switch(columnIndex)
		{
		case FORMULA_INDEX: return t.f;
		case USE_INDEX: return t.use;
		default: return new Object();
		}
	}
	
	public void setValueAt(Object value, int row, int column) {
		InternalAxiom axiom = elements.get(row);
		switch(column)
		{
		case FORMULA_INDEX: break;
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
