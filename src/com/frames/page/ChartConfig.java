package com.frames.page;


/**
 * 
 * @author yrc
 * @version 1.0
 * @date 2014-04-22
 */
public class ChartConfig{
	
	/* <chart 参数
	 * -------------------------------------------------------------------------*/
	private String caption = "";
	private String xAxisName = "";
	private String yAxisName = "";
	private String showValues = "0";
	private String numberPrefix = "";
	private String useRoundEdges = "1";
	private String legendBorderAlpha = "100";
	private String showBorder = "1";
	private String showLegend = "1";
	private String formatNumberScale = "0";
	private String showLabels = "1";
	private String bgAlpha = "100";
	private String canvasBgAlpha = "100";
	private String canvasBorderThickness = "1";
	private String paletteColors;
	private String showPercentValues;
	private String showPercentInToolTip;
	private String baseFontSize="12";
	private String pieRadius;//饼图半径
	private String plotSpacePercent;//柱宽(数值范围：0~80)80最小，0最大，超出范围数值无效
	// X 轴标签的显示方式控制
	private String labelDisplay = "WRAP";
	private String labelStep = "0";
	private String numVisibleLabels;
	
	private String legendPosition="";
	private String chartrightmargin="";
	private String bgratio="";
	private String startingangle="";
	private String animation="";
	
	// 导出用
	private String exportEnabled = "1";
	private String exportAtClient = "1";
	private String exportHandler = "fcExporter";
	private String exportFormats = "JPG=生成JPG图片|PNG=生成PNG图片|PDF=生成PDF文件";
	
	public ChartConfig(){
		
	}
	public ChartConfig(String caption){
		setCaption(caption);
	}
	public ChartConfig(String caption,String xAxisName,String yAxisName){
		setCaption(caption);
		setXAxisName(xAxisName);
		setYAxisName(yAxisName);
	}
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getXAxisName() {
		return xAxisName;
	}
	public void setXAxisName(String axisName) {
		xAxisName = axisName;
	}
	public String getYAxisName() {
		return yAxisName;
	}
	public void setYAxisName(String axisName) {
		yAxisName = axisName;
	}
	public String getShowValues() {
		return showValues;
	}
	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}
	public String getNumberPrefix() {
		return numberPrefix;
	}
	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
	}
	public String getUseRoundEdges() {
		return useRoundEdges;
	}
	public void setUseRoundEdges(String useRoundEdges) {
		this.useRoundEdges = useRoundEdges;
	}
	public String getLegendBorderAlpha() {
		return legendBorderAlpha;
	}
	public void setLegendBorderAlpha(String legendBorderAlpha) {
		this.legendBorderAlpha = legendBorderAlpha;
	}
	public String getShowBorder() {
		return showBorder;
	}
	public void setShowBorder(String showBorder) {
		this.showBorder = showBorder;
	}
	public String getShowLegend() {
		return showLegend;
	}
	public void setShowLegend(String showLegend) {
		this.showLegend = showLegend;
	}
	public String getFormatNumberScale() {
		return formatNumberScale;
	}
	public void setFormatNumberScale(String formatNumberScale) {
		this.formatNumberScale = formatNumberScale;
	}
	public String getShowLabels() {
		return showLabels;
	}
	public void setShowLabels(String showLabels) {
		this.showLabels = showLabels;
	}
	public String getShowPercentValues() {
		return showPercentValues;
	}
	public void setShowPercentValues(String showPercentValues) {
		this.showPercentValues = showPercentValues;
	}
	public String getShowPercentInToolTip() {
		return showPercentInToolTip;
	}
	public void setShowPercentInToolTip(String showPercentInToolTip) {
		this.showPercentInToolTip = showPercentInToolTip;
	}
	public String getBaseFontSize() {
		return baseFontSize;
	}
	public void setBaseFontSize(String baseFontSize) {
		this.baseFontSize = baseFontSize;
	}
	public String getLabelDisplay() {
		return labelDisplay;
	}
	public void setLabelDisplay(String labelDisplay) {
		this.labelDisplay = labelDisplay;
	}
	public String getLabelStep() {
		return labelStep;
	}
	public void setLabelStep(String labelStep) {
		this.labelStep = labelStep;
	}
	public String getNumVisibleLabels() {
		return numVisibleLabels;
	}
	public void setNumVisibleLabels(String numVisibleLabels) {
		this.numVisibleLabels = numVisibleLabels;
	}
	public String getBgAlpha() {
		return bgAlpha;
	}
	public void setBgAlpha(String bgAlpha) {
		this.bgAlpha = bgAlpha;
	}
	public String getCanvasBgAlpha() {
		return canvasBgAlpha;
	}
	public void setCanvasBgAlpha(String canvasBgAlpha) {
		this.canvasBgAlpha = canvasBgAlpha;
	}
	public String getPaletteColors() {
		return paletteColors;
	}
	public void setPaletteColors(String paletteColors) {
		this.paletteColors = paletteColors;
	}
	
	//---------------------------------------------------------
	public String getLegendPosition() {
		return legendPosition;
	}
	public void setLegendPosition(String legendPosition) {
		this.legendPosition = legendPosition;
	}
	public String getChartrightmargin() {
		return chartrightmargin;
	}
	public void setChartrightmargin(String chartrightmargin) {
		this.chartrightmargin = chartrightmargin;
	}
	public String getBgratio() {
		return bgratio;
	}
	public void setBgratio(String bgratio) {
		this.bgratio = bgratio;
	}
	public String getStartingangle() {
		return startingangle;
	}
	public void setStartingangle(String startingangle) {
		this.startingangle = startingangle;
	}
	public String getAnimation() {
		return animation;
	}
	public void setAnimation(String animation) {
		this.animation = animation;
	}
	
	//---------------------------------------------------------
	public String getExportEnabled() {
		return exportEnabled;
	}
	public void setExportEnabled(String exportEnabled) {
		this.exportEnabled = exportEnabled;
	}
	public String getExportAtClient() {
		return exportAtClient;
	}
	public void setExportAtClient(String exportAtClient) {
		this.exportAtClient = exportAtClient;
	}
	public String getExportHandler() {
		return exportHandler;
	}
	public void setExportHandler(String exportHandler) {
		this.exportHandler = exportHandler;
	}
	public String getExportFormats() {
		return exportFormats;
	}
	public void setExportFormats(String exportFormats) {
		this.exportFormats = exportFormats;
	}
	public String getCanvasBorderThickness() {
		return canvasBorderThickness;
	}
	public void setCanvasBorderThickness(String canvasBorderThickness) {
		this.canvasBorderThickness = canvasBorderThickness;
	}
	public String getPieRadius() {
		return pieRadius;
	}
	public void setPieRadius(String pieRadius) {
		this.pieRadius = pieRadius;
	}
	public String getPlotSpacePercent() {
		return plotSpacePercent;
	}
	public void setPlotSpacePercent(String plotSpacePercent) {
		this.plotSpacePercent = plotSpacePercent;
	}
	/**
	 * 所有需要配置在<chart 中的参数都要写在toString()中，否则参数加不上
	 */
	public String toString(){
		String result = "";
		result += " caption='" + this.caption + "'";
		result += " xAxisName='" + this.xAxisName + "'";
		result += " yAxisName='" + this.yAxisName + "'";
		result += " showValues='" + this.showValues + "'";
		result += " numberPrefix='" + this.numberPrefix + "'";
		result += " useRoundEdges='" + this.useRoundEdges + "'";
		result += " legendBorderAlpha='" + this.legendBorderAlpha + "'";
		result += " showBorder='" + this.showBorder + "'";
		result += " showLegend='" + this.showLegend + "'";
		result += " formatNumberScale='" + this.formatNumberScale + "'";
		result += " showLabels='" + this.showLabels + "'";
		result += " showPercentInToolTip='" + this.showPercentInToolTip + "'";
		result += " showPercentValues='" + this.showPercentValues + "'";
		result += " baseFontSize='" + this.baseFontSize + "'";
		result += " exportEnabled='" + this.exportEnabled + "'";
		result += " exportAtClient='" + this.exportAtClient + "'";
		result += " exportHandler='" + this.exportHandler + "'";
		result += " exportFormats='" + this.exportFormats + "'";
		result += " legendPosition='" + this.legendPosition + "'";
		result += " chartrightmargin='" + this.chartrightmargin + "'";
		result += " bgratio='" + this.bgratio + "'";
		result += " startingangle='" + this.startingangle + "'";
		result += " animation='" + this.animation + "'";
		result += " labelDisplay='" + this.labelDisplay + "'";
		result += " labelStep='" + this.labelStep + "'";
		result += " numVisibleLabels='" + this.numVisibleLabels + "'";
		result += " bgAlpha='" + this.bgAlpha + "'";
		result += " canvasBgAlpha='" + this.canvasBgAlpha + "'";
		result += " canvasBorderThickness='" + this.canvasBorderThickness + "'";
		result += " pieRadius='" + this.pieRadius + "'";
		if(this.paletteColors!=null && !"".equals(this.paletteColors)){
			result += " paletteColors='" + this.paletteColors + "'";
		}
		if(this.plotSpacePercent!=null && !"".equals(this.plotSpacePercent)){
			result += " plotSpacePercent='" + this.plotSpacePercent + "'";
		}
		return result;
	}

}
