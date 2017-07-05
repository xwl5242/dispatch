package com.frames.page;


public  class PageConfigBo {
	private long pageid;
	private String fieldname;
	private String ishiddle;
	private int tabindex;
	private double orderid;
	private String renderer;
	private String caption;
	private double width;
	private String dataformat;
	private String isongraph;
	private String ischecked;
	private String isbarchartlabel;
	private String position;
	private String ischartorcurve;
	private String isusecheckbox;
	private String datatypes;
	private String isunitconversion;
	private String graphgroup;
	private String gridgroup;
	private String expressionname;
	private String summarytype;
	private String summaryrenderer;
	private String isprimary;
	private String editorobject;
	private String editordata;
	private int tabindex1;
	private int tabindex2;
	private long id;
	private String columntype;
	private String helpurl;
	private String renderchart;
	private String locked;
	private String gather;
	private String unit;
	private String columnalign;
	private int issearchcolumn;
	private String chartcolor;

	public PageConfigBo(){}
	
	public PageConfigBo(PageConfigBo bo) {
		setPageid(bo.getPageid());
		setFieldname(bo.getFieldname());
		setIshiddle(bo.getIshiddle());
		setTabindex(bo.getTabindex());
		setOrderid(bo.getOrderid());
		setRenderer(bo.getRenderer());
		setCaption(bo.getCaption());
		setWidth(bo.getWidth());
		setDataformat(bo.getDataformat());
		setIsongraph(bo.getIsongraph());
		setIschecked(bo.getIschecked());
		setIsbarchartlabel(bo.getIsbarchartlabel());
		setPosition(bo.getPosition());
		setIschartorcurve(bo.getIschartorcurve());
		setIsusecheckbox(bo.getIsusecheckbox());
		setDatatypes(bo.getDatatypes());
		setIsunitconversion(bo.getIsunitconversion());
		setGraphgroup(bo.getGraphgroup());
		setGridgroup(bo.getGridgroup());
		setExpressionname(bo.getExpressionname());
		setSummarytype(bo.getSummarytype());
		setSummaryrenderer(bo.getSummaryrenderer());
		setIsprimary(bo.getIsprimary());
		setEditorobject(bo.getEditorobject());
		setEditordata(bo.getEditordata());
		setTabindex1(bo.getTabindex1());
		setTabindex2(bo.getTabindex2());
		setId(bo.getId());
		setColumntype(bo.getColumntype());
		setHelpurl(bo.getHelpurl());
		setRenderchart(bo.getRenderchart());
		setLocked(bo.getLocked());
		setGather(bo.getGather());
		setUnit(bo.getUnit());
		setColumnalign(bo.getColumnalign());
		setIssearchcolumn(bo.getIssearchcolumn());
		setChartcolor(bo.getChartcolor());
	}

	public long getPageid() {
		return pageid;
	}

	public void setPageid(long pageid) {
		this.pageid = pageid;
	}

	public String getFieldname() {
		if (fieldname != null && !"".equals(fieldname)) {
			fieldname = fieldname.toUpperCase();
		}
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getIshiddle() {
		return ishiddle;
	}

	public void setIshiddle(String ishiddle) {
		this.ishiddle = ishiddle;
	}

	public int getTabindex() {
		return tabindex;
	}

	public void setTabindex(int tabindex) {
		this.tabindex = tabindex;
	}

	public double getOrderid() {
		return orderid;
	}

	public void setOrderid(double orderid) {
		this.orderid = orderid;
	}

	public String getRenderer() {
		return renderer;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public String getDataformat() {
		return dataformat;
	}

	public void setDataformat(String dataformat) {
		this.dataformat = dataformat;
	}

	public String getIsongraph() {
		return isongraph;
	}

	public void setIsongraph(String isongraph) {
		this.isongraph = isongraph;
	}

	public String getIschecked() {
		return ischecked;
	}

	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}

	public String getIsbarchartlabel() {
		return isbarchartlabel;
	}

	public void setIsbarchartlabel(String isbarchartlabel) {
		this.isbarchartlabel = isbarchartlabel;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIschartorcurve() {
		return ischartorcurve;
	}

	public void setIschartorcurve(String ischartorcurve) {
		this.ischartorcurve = ischartorcurve;
	}

	public String getIsusecheckbox() {
		return isusecheckbox;
	}

	public void setIsusecheckbox(String isusecheckbox) {
		this.isusecheckbox = isusecheckbox;
	}

	public String getDatatypes() {
		return datatypes;
	}

	public void setDatatypes(String datatypes) {
		this.datatypes = datatypes;
	}

	public String getIsunitconversion() {
		return isunitconversion;
	}

	public void setIsunitconversion(String isunitconversion) {
		this.isunitconversion = isunitconversion;
	}

	public String getGraphgroup() {
		return graphgroup;
	}

	public void setGraphgroup(String graphgroup) {
		this.graphgroup = graphgroup;
	}

	public String getGridgroup() {
		return gridgroup;
	}

	public void setGridgroup(String gridgroup) {
		this.gridgroup = gridgroup;
	}

	public String getExpressionname() {
		return expressionname;
	}

	public void setExpressionname(String expressionname) {
		this.expressionname = expressionname;
	}

	public String getSummarytype() {
		return summarytype;
	}

	public void setSummarytype(String summarytype) {
		this.summarytype = summarytype;
	}

	public String getSummaryrenderer() {
		return summaryrenderer;
	}

	public void setSummaryrenderer(String summaryrenderer) {
		this.summaryrenderer = summaryrenderer;
	}

	public String getIsprimary() {
		return isprimary;
	}

	public void setIsprimary(String isprimary) {
		this.isprimary = isprimary;
	}

	public String getEditorobject() {
		return editorobject;
	}

	public void setEditorobject(String editorobject) {
		this.editorobject = editorobject;
	}

	public String getEditordata() {
		return editordata;
	}

	public void setEditordata(String editordata) {
		this.editordata = editordata;
	}

	public int getTabindex1() {
		return tabindex1;
	}

	public void setTabindex1(int tabindex1) {
		this.tabindex1 = tabindex1;
	}

	public int getTabindex2() {
		return tabindex2;
	}

	public void setTabindex2(int tabindex2) {
		this.tabindex2 = tabindex2;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getColumntype() {
		return columntype;
	}

	public void setColumntype(String columntype) {
		this.columntype = columntype;
	}

	public String getHelpurl() {
		return helpurl;
	}

	public void setHelpurl(String helpurl) {
		this.helpurl = helpurl;
	}

	public String getRenderchart() {
		return renderchart;
	}

	public void setRenderchart(String renderchart) {
		this.renderchart = renderchart;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getGather() {
		return gather;
	}

	public void setGather(String gather) {
		this.gather = gather;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getColumnalign() {
		return columnalign;
	}

	public void setColumnalign(String columnalign) {
		this.columnalign = columnalign;
	}

	public int getIssearchcolumn() {
		return issearchcolumn;
	}

	public void setIssearchcolumn(int issearchcolumn) {
		this.issearchcolumn = issearchcolumn;
	}

	public String getChartcolor() {
		return chartcolor;
	}

	public void setChartcolor(String chartcolor) {
		this.chartcolor = chartcolor;
	}

}
