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

package pt.uc.dei.cms.incerto.learners.sources.searchengines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YahooBossAPI implements SearchEngineAPI{
	
	private static String APPID = "3g0Q_DrV34H2eCpp4v30CWj8cLZWX6B2AWBl__0ejAQl9OXjJblGxMn9yqBA";
	private int NRESULTS;	//MAX=100
	
	public YahooBossAPI()
	{
		this.NRESULTS = 8;
	}
	
	public YahooBossAPI(int nresults)
	{
		if((this.NRESULTS = nresults)>100)
			this.NRESULTS = 100;
	}

	public List<String> getResults(String query) throws Exception
	{
		query = URLEncoder.encode(query, "UTF-8");
		return getQueryResults(query);
	}
	
	public long getQueryEstimatedResultCount(String query) throws Exception
	{
		query = URLEncoder.encode(query, "UTF-8");
		JSONObject json = new JSONObject(getRawQueryResultsFromYahoo(query));
		return json.getJSONObject("ResultSet").getLong("totalResultsAvailable");
	}
	
	private ArrayList<String> getQueryResults(String query) throws IOException, JSONException 
	{
		ArrayList<String> results = new ArrayList<String>(NRESULTS*2);
		JSONObject json = new JSONObject(getRawQueryResultsFromYahoo(query));
		JSONArray ja = json.getJSONObject("ResultSet").getJSONArray("Result");
		for (int i = 0; i < ja.length(); i++)
		{			
			results.add(SearchEngineUtils.cleanResult(ja.getJSONObject(i).get("Title").toString()));
			results.add(SearchEngineUtils.cleanResult(ja.getJSONObject(i).get("Summary").toString()));
		}

		return results;
	}
	
	private String getRawQueryResultsFromYahoo(String query) throws IOException
	{
		URL url = new URL("http://api.search.yahoo.com/WebSearchService/V1/webSearch?appid="+APPID+"&query="+query+"&results="+NRESULTS+"&output=json&abstract=long");
		URLConnection connection = url.openConnection();

		String line;
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	
		while((line = reader.readLine()) != null)
			sb.append(line);
		return sb.toString();
		
	}
}
