package net.yunzj.demo;

import java.lang.Object;
import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

// import net.yunzj.search.SearchHelper;

public class IKTester{

	public static void main(String[] args) {
		test_highlight();
	}

	protected static void test_highlight(){
		System.out.println("mrlong");
	}


	public String gettext(){
		Analyzer analyzer = new IKAnalyzer();
		return "mrlong233332222321111";
	}
}

