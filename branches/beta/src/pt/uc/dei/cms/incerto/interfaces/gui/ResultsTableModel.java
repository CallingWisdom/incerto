package pt.uc.dei.cms.incerto.interfaces.gui;

import javax.swing.table.AbstractTableModel;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.model.ReasoningResults;

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
