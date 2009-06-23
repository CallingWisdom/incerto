package pt.uc.dei.cms.incerto.learners.sources;

import java.io.File;
import java.util.List;

public interface OntologyPopulationSource {

	public boolean containsFiles();
	public boolean containsStrings();
	public List<File> getFiles();
	public List<String> getStrings();
}
