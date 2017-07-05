package com.frames.page;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author yrc
 * @version 1.0
 * @date 2014-04-03
 */
public class PageConfig{
	private String pageId;       
	private String colModel;              //表头
	private String data;                  //数据
    private Integer currPage = 1;         //当前页
    private Integer pageNumber = 20;      //每页记录数
    private Integer pageCount;            //总页数
    private Integer start;                //起始位置
    private Integer end;                  //结束位置
    private Integer totalRecords;         //总记录
    private String  ascOrDesc;            //升序或者降序
    private String  sortCols;             //参与排序的所有列
    private String  sortCol;              //需要排序的列
    private Integer summary = 0;		  //是否合计，1合计
    private Integer tabIndex ;		  //页签索引
    private String configKey;			  //配置Key
    private boolean isPage = true;		  //是否分页,默认分页
    
    private String tabIndexs = "1";		  //用于页面显示几个表格，格式为：1,2,3(与tabIndex对应)
    private ArrayList<String> colModelArray = new ArrayList<String>();//多表格时，用于存储表头
    private ArrayList<String> chartGroupArray = new ArrayList<String>();//多表格时，用于存储图表分组
    private ArrayList<String> chartCheckArray = new ArrayList<String>();//多表格时，用于存储图表默认显示字段
    
    private String filepath;			//导出文件路径的路径
	private String filename;			//导出excel的文件名
	private InputStream expInputStream;	//导出excel的输入流
	private String excelOrZip;			//导出excel的压缩方式
	private String returnStr;			//导出excel的返回方式
	
	private boolean isCreatChart = false; //是否加载图形
	private String chartGroup;			  //图形分组
	private String chartGroupHtml;		  //图形分组Html代码
	private Integer maxNodeNum = 11;		  //最多选多少个站;
	private String chartCheck;			  //曲线选中显示的配置字段
	private String chartCheckId;		  //返回值的input id,主要用于一个页面多表格
	private List chartTimeList;		  	  //图形指定时间轴
	
	private Object editorObject;		  //对象
	private Integer isview = 0;			  //查看标识,1查看,0修改
	
	private String condition;	//查询条件
	private String conditionId = "condition"; //查询条件返回值的input id,主要用于一个页面多表格
	
	private Map requestParamMap;		  //请求参数哈希表
	private String datePattern = "yyyy-MM-dd HH:mm:ss"; //指定对象转json的时间格式
	
	private String userDataPermissions = "-1"; //用户数据权限
	
	// 用于存储左侧树的数据
	private String paramOrgid;		//组织机构IDs
	private String paramFeedcode;	//热源IDs
	private String paramNodecode;	//换热站IDs
	

	private String sqlSelect;           //一个action对应多个列表，sql多样化
	
	// 添加log用
	private String pageName;
	private String person;
	

	private String uname;				  //session key
	private Integer uid;					  //userid
	
	private String refcolumns;
	
	private String megerFields;
	
	
	
	
	public String getRefcolumns() {
		return refcolumns;
	}
	public void setRefcolumns(String refcolumns) {
		this.refcolumns = refcolumns;
	}
	public String getMegerFields() {
		return megerFields;
	}
	public void setMegerFields(String megerFields) {
		this.megerFields = megerFields;
	}
	public  String getColModel() {
		return colModel;
	}
	public void setColModel(String colModel) {
		this.colModel = colModel;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Integer getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public String getAscOrDesc() {
		return ascOrDesc;
	}

	public void setAscOrDesc(String ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}
	//返回需要排序的列
	public String[] getAllCols() {
		String[] cols = null;
		if(this.getSortCols()!=null){
			cols = this.getSortCols().split(",");
		}
		return cols;
	}
	public String getSortCols(){
		return sortCols; 
	}
	public void setSortCols(String sortCols) {
		this.sortCols = sortCols;
	}
	public String getSortCol() {
		return sortCol;
	}
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	public Integer getTabIndex() {
		if(tabIndex==null){
			tabIndex=1;
		}
		return tabIndex;
	}
	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}
	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public InputStream getExpInputStream() {
		return expInputStream;
	}
	public void setExpInputStream(InputStream expInputStream) {
		this.expInputStream = expInputStream;
	}
	public String getExcelOrZip() {
		return excelOrZip;
	}
	public void setExcelOrZip(String excelOrZip) {
		this.excelOrZip = excelOrZip;
	}
	public String getReturnStr() {
		return returnStr;
	}
	public void setReturnStr(String returnStr) {
		this.returnStr = returnStr;
	}
	public String getTabIndexs() {
		return tabIndexs;
	}
	public void setTabIndexs(String tabIndexs) {
		this.tabIndexs = tabIndexs;
	}
	public ArrayList<String> getColModelArray() {
		return colModelArray;
	}
	public void setColModelArray(ArrayList<String> colModelArray) {
		this.colModelArray = colModelArray;
	}
	public boolean isCreatChart() {
		return isCreatChart;
	}
	public void setCreatChart(boolean isCreatChart) {
		this.isCreatChart = isCreatChart;
	}
	public String getChartGroup() {
		return chartGroup;
	}
	public void setChartGroup(String chartGroup) {
		this.chartGroup = chartGroup;
	}
	public String getChartGroupHtml() {
		return chartGroupHtml;
	}
	public void setChartGroupHtml(String chartGroupHtml) {
		this.chartGroupHtml = chartGroupHtml;
	}
	public ArrayList<String> getChartGroupArray() {
		return chartGroupArray;
	}
	public void setChartGroupArray(ArrayList<String> chartGroupArray) {
		this.chartGroupArray = chartGroupArray;
	}
	public Object getEditorObject() {
		return editorObject;
	}
	public void setEditorObject(Object editorObject) {
		this.editorObject = editorObject;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Integer getIsview() {
		return isview;
	}
	public void setIsview(Integer isview) {
		this.isview = isview;
	}
	public Map getRequestParamMap() {
		return requestParamMap;
	}
	public void setRequestParamMap(Map requestParamMap) {
		this.requestParamMap = requestParamMap;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getDatePattern() {
		return datePattern;
	}
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
	public Integer getSummary() {
		return summary;
	}
	public void setSummary(Integer summary) {
		this.summary = summary;
	}
	public String getConditionId() {
		return conditionId;
	}
	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}
	public String getUserDataPermissions() {
		return userDataPermissions;
	}
	public void setUserDataPermissions(String userDataPermissions) {
		this.userDataPermissions = userDataPermissions;
	}
	public String getParamOrgid() {
		return paramOrgid;
	}
	public void setParamOrgid(String paramOrgid) {
		this.paramOrgid = paramOrgid;
	}
	public String getParamFeedcode() {
		return paramFeedcode;
	}
	public void setParamFeedcode(String paramFeedcode) {
		this.paramFeedcode = paramFeedcode;
	}
	public String getParamNodecode() {
		return paramNodecode;
	}
	public void setParamNodecode(String paramNodecode) {
		this.paramNodecode = paramNodecode;
	}
	public Integer getMaxNodeNum() {
		return maxNodeNum;
	}
	public void setMaxNodeNum(Integer maxNodeNum) {
		this.maxNodeNum = maxNodeNum;
	}
	public String getChartCheck() {
		return chartCheck;
	}
	public void setChartCheck(String chartCheck) {
		this.chartCheck = chartCheck;
	}
	public boolean isPage() {
		return isPage;
	}
	public void setPage(boolean isPage) {
		this.isPage = isPage;
	}
	public List getChartTimeList() {
		return chartTimeList;
	}
	public void setChartTimeList(List chartTimeList) {
		this.chartTimeList = chartTimeList;
	}

	public String getSqlSelect() {
		return sqlSelect;
	}
	public void setSqlSelect(String sqlSelect) {
		this.sqlSelect = sqlSelect;
	}

	public ArrayList<String> getChartCheckArray() {
		return chartCheckArray;
	}
	public void setChartCheckArray(ArrayList<String> chartCheckArray) {
		this.chartCheckArray = chartCheckArray;
	}
	public String getChartCheckId() {
		return chartCheckId;
	}
	public void setChartCheckId(String chartCheckId) {
		this.chartCheckId = chartCheckId;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
}
