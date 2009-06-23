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

package pt.uc.dei.cms.incerto.utils;

/**
 * String Utilities
 * @author Pedro Oliveira
 *
 */
public class StringUtils {
	
	/**
	 * Capitalize String
	 * @param st
	 * @return
	 */
	public static String capitalize(String st)
	{
		if (st.length() == 0) 
			return st;
        return st.substring(0, 1).toUpperCase() + st.substring(1);
	}
	
	/**
	 * Decapitalize String
	 * @param st
	 * @return
	 */
	public static String decapitalize(String st)
	{
		if (st.length() == 0) 
			return st;
        return st.substring(0, 1).toLowerCase() + st.substring(1);
	}
	
	/**
	 * Parse boolean from String
	 * @param st
	 * @return
	 */
	public static boolean parseBoolean(String st)
	{
		if(st.compareTo("0")==0 || st.compareTo("false")==0)
			return false;
		if(st.compareTo("1")==0 || st.compareTo("true")==0)
			return true;
		return Boolean.parseBoolean(st);
	}
	
	/**
	 * Remove white spaces from String
	 * @param st
	 * @return
	 */
	public static String removeWS(String st)
	{
		return st.replaceAll(" ", "");
	}
	
	
	/**
	 * Minimum of three integers
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	private static int minimum(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}
 
	/**
	 * Levensthein Distance between two Strings
	 * @param st1
	 * @param st2
	 * @return
	 */
	public static int levenshteinDistance(String st1, String st2) {
		int n = st1.length(), m= st2.length();
		
		if(n==0)
			return m;
		if(m==0)
			return n;
		
		int[][] distance = new int[n + 1][m + 1];
 
		for (int i = 0; i <= n; i++)
			distance[i][0] = i;
		for (int j = 0; j <= m; j++)
			distance[0][j] = j;
 
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++)
				distance[i][j] = minimum(
						distance[i - 1][j] + 1,
						distance[i][j - 1] + 1,
						distance[i - 1][j - 1] + ((st1.charAt(i - 1) == st2.charAt(j - 1)) ? 0 : 1));
 
		return distance[st1.length()][st2.length()];
	}

}
