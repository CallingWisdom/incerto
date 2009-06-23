package pt.uc.dei.cms.incerto.learners.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SimpleStrings implements OntologyPopulationSource {
	
	private List<String> strings;
	
	public SimpleStrings() {
		this.strings = new ArrayList<String>();
	}
	
	public void addString(String st)
	{
		strings.add(st);
	}

	//@Override
	public boolean containsFiles() {
		return false;
	}

	//@Override
	public boolean containsStrings() {
		return true;
	}

	//@Override
	public List<File> getFiles() {
		return null;
	}

	//@Override
	public List<String> getStrings() {
		return strings;
	}

}
