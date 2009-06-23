package pt.uc.dei.cms.incerto.learners.sources.searchengines;

import java.util.List;

public interface SearchEngineAPI {

	public List<String> getResults(String query) throws Exception;
	public long getQueryEstimatedResultCount(String query) throws Exception;
}
