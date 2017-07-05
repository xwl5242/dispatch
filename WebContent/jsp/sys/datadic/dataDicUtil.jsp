<%@ page language="java" import="java.util.*,com.dispatch.sys.cache.*,org.codehaus.jackson.map.JsonSerializer,com.util.common.*" pageEncoding="utf-8"%>
<% 
	String name = request.getParameter("typecode");
	String showNumber = request.getParameter("showNumber");
	String outNumber = request.getParameter("outNumber");
	String selected=request.getParameter("selected");
	
	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	if("true".equals(selected)){
		 list=GetDataDicCache.dataDicMapSelected.get(name);
	}else{
		 list=GetDataDicCache.dataDicMap.get(name);
	}
	
	List<Map<String,Object>> showList=new ArrayList<Map<String,Object>>();
	String json="";
	JsonSrzer srzer = new JsonSrzer(null);
	if(showNumber!=""&&showNumber!=null){
		String[] numbers=showNumber.split(",");
		for(int j=0;j<numbers.length;j++){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map=list.get(i);
				if(map.get("ID").equals(numbers[j])){
					showList.add(map);
				}
			}
		}
		json=srzer.serializer(showList);
		
	}else if(outNumber!=""&&outNumber!=null){
		for(int a=0;a<list.size();a++){
			showList.add(list.get(a));
		}
		String[] numbers=outNumber.split(","); 
		for(int j=0;j<numbers.length;j++){
			for(int i=0;i<showList.size();i++){ 
				Map<String,Object> map=showList.get(i);
				if(map.get("ID").equals(numbers[j])){
					showList.remove(i); 
				}
			}
		} 
		json=srzer.serializer(showList);
	}else{
		json=srzer.serializer(list);
	
	}
%>

<%=json %>

