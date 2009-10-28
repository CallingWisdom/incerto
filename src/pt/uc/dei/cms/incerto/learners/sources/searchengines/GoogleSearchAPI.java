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

public class GoogleSearchAPI implements SearchEngineAPI{

	private int NRESULTS;	//MAX = 64
	
	public GoogleSearchAPI()
	{	
		this.NRESULTS = 8;
	}
	
	public GoogleSearchAPI(int nresults)
	{
		if((this.NRESULTS = nresults)>64)
			this.NRESULTS = 64;
	}

	public List<String> getResults(String query) throws Exception
	{
		query = URLEncoder.encode(query, "UTF-8");
		ArrayList<String> results = new ArrayList<String>(NRESULTS*2);
		for(int i=0; i<NRESULTS; i+=8)
			results.addAll(getQueryResults(query, i));
		return results;
	}
	
	public long getQueryEstimatedResultCount(String query) throws Exception
	{
		long res;
		query = URLEncoder.encode(query, "UTF-8");
		JSONObject json = new JSONObject(getRawQueryResultsFromGoogle(query, 0));
		try
		{
			res = json.getJSONObject("responseData").getJSONObject("cursor").getLong("estimatedResultCount");
		}
		catch(JSONException e)	//The query doesn't contains any results
		{
			res = 0;
		}
		return res;
	}

	private ArrayList<String> getQueryResults(String query, int start) throws IOException, JSONException 
	{
		ArrayList<String> results = new ArrayList<String>(16);
		JSONObject json = new JSONObject(getRawQueryResultsFromGoogle(query, start));
		JSONArray ja = json.getJSONObject("responseData").getJSONArray("results");
		for (int i = 0; i < ja.length(); i++)
		{			
			results.add(SearchEngineUtils.cleanResult(ja.getJSONObject(i).get("titleNoFormatting").toString()));
			results.add(SearchEngineUtils.cleanResult(ja.getJSONObject(i).get("content").toString()));
		}

		return results;
	}
	
	private String getRawQueryResultsFromGoogle(String query, int start) throws IOException
	{
		URL url = new URL("http://ajax.googleapis.com/ajax/services/search/web?start="+start+"&rsz=large&v=1.0&q="+query);
		URLConnection connection = url.openConnection();
		//connection.addRequestProperty("Referer", HTTP_REFERER);

		String line;
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while((line = reader.readLine()) != null)
			sb.append(line);
		return sb.toString();
	}
}
