package run;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {

	List<String> queries1 = new ArrayList<String>();
	List<String> queries2 = new ArrayList<String>();
	

	
	public void addQuerySet(String queries){
		String[] singleQueries = queries.split("[,]");	
		
		addQueries(Arrays.c(singleQueries,0));
		addQueries();
		
	}
	
	private void addQueries(String[] queries, List<String> queryList){
		
		for(int i=0; i<queries.length;i++){
			queryList.add(queries[i]);
		}
		
	}
	
}
