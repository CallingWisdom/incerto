package pt.uc.dei.cms.incerto.utils;

import pt.uc.dei.cms.incerto.learners.sources.searchengines.SearchEngineAPI;
import pt.uc.dei.cms.incerto.learners.sources.searchengines.SearchEngineUtils;

public class SemanticSimilarity {

	public static long GOOGLE_SIZE = 40000000000L;
	public static long YAHOO_SIZE = 20000000000L;

	private SearchEngineAPI api;
	private long N;
	private long P;
	private long Q;
	private long PQ;
	private double POWEREDN;


	public SemanticSimilarity(SearchEngineAPI api, long n) {
		this.api = api;
		this.N = n;
		this.P = 0;
		this.Q = 0;
		this.PQ = 0;
		this.POWEREDN = Math.pow(N, DIVIDE23);
	}

	public void getSimilarity(String s1, String s2) throws Exception
	{
		s1 = SearchEngineUtils.surroundWithQuotationMarks(s1);
		s2 = SearchEngineUtils.surroundWithQuotationMarks(s2);	

		P = api.getQueryEstimatedResultCount(s1);
		Q = api.getQueryEstimatedResultCount(s2);
		PQ = api.getQueryEstimatedResultCount(s1+" "+s2);

		System.out.println("P: "+P+"\tQ: "+Q+"\tPQ: "+PQ);
		System.out.println("("+s1+","+s2+")\t"+webJaccard()+"\t"+webOverlap()+"\t"+webDice()+"\t"+webPMI()+"\t"+CCP()+"\t"+NWD());
	}

	public void getSimilarity(String subject, String predicate, String object) throws Exception
	{
		String sentence, q1,q2;

		sentence = SearchEngineUtils.surroundWithQuotationMarks(subject+" "+predicate+" "+object);
		q1 = SearchEngineUtils.surroundWithQuotationMarks(subject+" "+predicate+" *");
		q2 = SearchEngineUtils.surroundWithQuotationMarks("* "+predicate+" "+object);

		P = api.getQueryEstimatedResultCount(q1);
		Q = api.getQueryEstimatedResultCount(q2);
		PQ = api.getQueryEstimatedResultCount(sentence);
		//System.out.println(q1+" "+q2);
		//System.out.println("P: "+P+"\tQ: "+Q+"\tPQ: "+PQ);
		//System.out.println("("+sentence+")\t"+webJaccard()+"\t"+webOverlap()+"\t"+webDice()+"\t"+webPMI()+"\t"+CCP()+"\t"+NWD());
		System.out.println(webJaccard()+"\t"+webOverlap()+"\t"+webDice()+"\t"+webPMI()+"\t"+CCP()+"\t"+NWD());
		
	}

	public double getSimilarityWithHearstPatterns(String subject, String object) throws Exception
	{
		System.out.println(subject+","+object);
		String sentence, q1,q2;
		double total = 0;
		for(String pattern: SearchEngineUtils.CLASS_ASSERTION_PATTERNS)
		{
			sentence = pattern.replaceFirst("[*]", subject).replaceFirst("[$]", object);
			sentence = SearchEngineUtils.surroundWithQuotationMarks(sentence);
			q1 = pattern.replaceFirst("[*]", subject).replaceFirst("[$]", "*");
			q1 = SearchEngineUtils.surroundWithQuotationMarks(q1);
			q2 = pattern.replaceFirst("[$]", object);
			q2 = SearchEngineUtils.surroundWithQuotationMarks(q2);
			
			//System.out.println(sentence);
			P = api.getQueryEstimatedResultCount(q1);
			Q = api.getQueryEstimatedResultCount(q2);
			PQ = api.getQueryEstimatedResultCount(sentence);
			total+=CCP();
			//System.out.println(P+"\t"+Q+"\t"+PQ);
			//System.out.println(NWD());
		}
		return total/SearchEngineUtils.CLASS_ASSERTION_PATTERNS.length;
	}

	public double webJaccard()
	{
		double d = P+Q-PQ;
		if(d!=0)
			return PQ/d;
		else
			return 0;
	}

	public double webOverlap()
	{
		double d = Math.min(P, Q);
		if(d!=0)
			return PQ/d;
		else
			return 0;
	}

	public double webDice()
	{
		double d = P+Q;
		if(d!=0)
			return (2*PQ)/d;
		else
			return 0;
	}

	public double webPMI()
	{
		double d = P*Q;
		if(PQ==0)
			return 0;
		if(d!=0)
			return MathUtils.log2((PQ/d)*N);
		else
			return 0;
	}

	public double CCP()
	{
		double d = P*Math.pow(Q, DIVIDE23);
		if(d!=0)
			return (PQ/d)*POWEREDN;
		else
			return 0;
	}

	public double NWD()
	{
		double logP = Math.log(P);
		double logQ = Math.log(Q);
		double u = Math.max(logP, logQ)-Math.log(PQ);
		double d = Math.log(N)-Math.min(logP, logQ);
		if(d!=0)
		{
			double res = u/d;
			if(Double.isInfinite(res) || Double.isNaN(res))
				return 100;
			return res;
		}
		else
			return 2;
	}

	private static double DIVIDE23 = 2.0/3;
}
