package com.example.dbutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUtil {
	ArrayList<String> select; //fields
	String from;  //table
	Map<String,String> where;
	String orderBy; //order field
	String order; //"asc" or "desc"
	
	public DatabaseUtil(ArrayList<String> select, String from) {
		super();
		this.select = new ArrayList<String>();
		this.select.addAll(select);
		this.from = from;
	}
	public DatabaseUtil(ArrayList<String> select, String from, String orderBy, String order) {
		this(select,from);
		this.orderBy = orderBy;
		this.order = order;
	}
	public DatabaseUtil(ArrayList<String> select, String from, Map<String,String> where) {
		this(select,from);
		this.where = new HashMap<String,String>();
		this.where.putAll(where);
	}
	
	public String buildQuery() {
		String query = "query?q=select+";
		int x = 0;
		for (String s : select) {
			query += ((x==0)?"":",+") + s;
			x++;
		}
		query+= "+from+" + from;
		
		if(where!=null){
			int y = 0;
			query+= "+where+";
			for(Map.Entry<String, String> entry : where.entrySet()) {
				query += ((y==0)?"":"+and+") + entry.getKey() + "+=+" + "\'" + entry.getValue() + "\'"; 
				y++;
			}
		}
		
		if(orderBy!=null) {
			query+= "+order+by+" + orderBy;
			if (order!=null) {
				query+= "+" + order;
			}
		}
		
		return query;
	}
}
