/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  CreateXmlUtils.java     <BR>
 * Author: xupei  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: 2015-11-8 下午4:38:30 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

package com.frames.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.frames.page.PageConfig;
import com.frames.page.PageConfigBo;

public class CreateXmlUtils {
	
	public String getUrl() {
		return "";
	}
	
	
	/**
	 * 增加 XML value节点
	 * @param ele
	 * @param xid
	 * @param text
	 */
	private void addValue(Element ele,String xid,String text){
		Element value = ele.addElement("value");
		value.addAttribute("xid", xid);
		value.setText(text);
	}
	
	
	/**
	 * 生成曲线 XML
	 * @param datalist
	 * @param pageConfig
	 * @param dataset_list
	 * @param xfieldname
	 * @param morefieldname
	 * @param title
	 */
	public void writeXml(List<Object> datalist, PageConfig pageConfig, List<PageConfigBo> dataset_list, String xfieldname, String morefieldname, String title) {
		if (datalist == null) datalist = new ArrayList<Object>();
		String tempurl = this.getUrl() + "charts\\flashcharts\\data\\amline_data" + pageConfig.getConfigKey() + ".xml";
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("chart");
		root.addComment("<message><![CDATA[You can broadcast any message to chart from data XML file]]></message>");
		Element series = root.addElement("series");
		int c = 0, cs = 0, cg = 1, ccs = 0;
		//List timelist = new ArrayList<String>();
		Map timemap = new LinkedHashMap<String, String>();// 保存横轴数据（通常是时间为轴）
		Map moremap = new LinkedHashMap<String, String>();
		List customTimelist = pageConfig.getChartTimeList();
		// 生成指定日期横轴
		if (customTimelist != null) {
			for (int i = 0; i < customTimelist.size(); i++) {
				String xvalue = (String) customTimelist.get(i);
//				String[] custom = xvalue.split(":");
//				boolean istrue = true;
//				for (int s = datalist.size() - 1; s >= 0; s--) {
//					Map map = (Map) datalist.get(s);
//					String xval = map.get(xfieldname) + "";
//					String[] data = xval.split(":");
//					int v = Integer.parseInt(data[1]) - Integer.parseInt(custom[1]);
//					int nodeCount = 2;
//					// 小时相等，数据的分钟数自定义分钟数大于等于零且小于上数时间间隔
//					// 用数据的分钟数代替自定义分钟数
//					if (custom[0].equals(data[0]) && v >= 0 && v < nodeCount) {
//						xvalue = xval;
//						if (!timemap.containsKey(xvalue)) {
//							addValue(series, i + "", xvalue);
//							timemap.put(xvalue, xvalue);
//							istrue = false;
//							ccs++;
//						}
//					}
//				}
//				if (istrue && !timemap.containsKey(xvalue)) {
//					addValue(series, i + "", xvalue);
//					timemap.put(xvalue, xvalue);
//					ccs++;
//				}
				addValue(series, i + "", xvalue);
				timemap.put(xvalue, xvalue);
				ccs++;
				
			}
		}
		// 生成横轴数据(通常是时间类型)
		for (int s = datalist.size() - 1; s >= 0; s--) {
			Map map = (Map) datalist.get(s);
			String xvalue = map.get(xfieldname) + "";
			
			if (customTimelist == null) {
				if (!timemap.containsKey(xvalue)) {
					addValue(series, cs + "", xvalue);
					cs++;
					timemap.put(xvalue, xvalue);
				}
			}
			
			if (morefieldname != null && !"".equals(morefieldname)) {
				// 多站多曲线（保存站名称）,默认最多选三个站
				String morevalue = map.get(morefieldname) + "";
				if (!moremap.containsKey(morevalue) && c < pageConfig.getMaxNodeNum()) {
					moremap.put(morevalue, morevalue);
					c++;
				}
			} else {
				moremap.put("", "");
			}
		}
		// 选中的显示列
		Map tempmap = StringToMap(pageConfig.getChartCheck());
		// 循环生成曲线数据
		Element graphs = root.addElement("graphs");
		Iterator it = moremap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			for (int i = 0; i < dataset_list.size(); i++) {
				PageConfigBo bo = dataset_list.get(i);
				// 判断是否有自定义选中列
				if(tempmap==null) continue;
				if(!tempmap.containsKey(bo.getFieldname())) continue;
				
				Element graph = graphs.addElement("graph");
				graph.addAttribute("gid", cg + "");
				if (bo.getPosition() != null && bo.getPosition().equals("right")) graph.addAttribute("axis", "right");
				
				int j = 0;
				Iterator itt = timemap.keySet().iterator();
				while (itt.hasNext()) {
					String xtime = (String) itt.next();
					boolean istrue = true;
					for (int s = datalist.size() - 1; s >= 0; s--) {
						Map map = (Map) datalist.get(s);
						String xvalue = map.get(xfieldname)==null?"":map.get(xfieldname).toString();
						String morevalue = map.get(morefieldname)==null?"":map.get(morefieldname).toString();

						if (xvalue.equals(xtime) && morevalue.equals(key)) {
							addValue(graph, j + "", map.get(bo.getFieldname()) + "");
							istrue = false;
							break;
						}
					}
					if(istrue) addValue(graph,j+"","null");
					j++;
				}
				cg++;
			}
		}

		this.createFile(tempurl, doc);
		
		this.writeSetingXml(title, pageConfig, dataset_list, moremap, tempmap);
	}
	/**
	 * 生成曲线设置 XML
	 * @param title
	 * @param pageConfig
	 * @param dataset_list
	 * @param moremap
	 * @param tempmap 自定义选中列
	 */
	private void writeSetingXml(String title, PageConfig pageConfig, List<PageConfigBo> dataset_list, Map moremap, Map tempmap) {
		String tempurl = this.getUrl() + "charts\\flashcharts\\data\\amline_settings" + pageConfig.getConfigKey() + ".xml";
		XMLWriter writer = null; 
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		Document doc = DocumentHelper.createDocument(); 
		Element root = doc.addElement("settings"); 
		Element legend = root.addElement("legend");
		Element eleabled = legend.addElement("enabled");
		eleabled.setText("true");
		Element root1 = root.addElement("connect"); 
		root1.setText("true");
		Element root5 = root.addElement("text_size"); 
		root5.setText("12");
		//[,]小数分隔符。注意，只用来显示数据。数据的XML文件中必须使用小数点
		Element ds = root.addElement("decimals_separator"); 
		ds.setText(".");
		//true，图表会被重画
		Element redraw = root.addElement("redraw"); 
		redraw.setText("true");
		Element root2 = root.addElement("plot_area");
		Element root3 = root2.addElement("margins");
		Element root4 = root3.addElement("top");
		root4.setText("40");
		Element bottom = root3.addElement("bottom");
		bottom.setText("40");
		//Element root6 = root3.addElement("left");
		//root6.setText("80");
		Element root7 = root.addElement("grid");
		Element root8 = root7.addElement("x");
		Element root9 = root8.addElement("approx_count");
		root9.setText("6");//横轴有多少数据
		
		Element yleft = root7.addElement("y_left");
		Element approxcount = yleft.addElement("approx_count");
		approxcount.setText("6");
		Element yright = root7.addElement("y_right");
		Element approycount = yright.addElement("approx_count");
		approycount.setText("6");
		//导出提示
		Element eleStrings = root.addElement("strings");
		Element eleCollecting = eleStrings.addElement("collecting_data");
		eleCollecting.setText("正在导出...");
		//显示右上角
		Element root10 = root.addElement("zoom_out_button");
		Element root11 = root10.addElement("text");
		root11.setText("返回");
		//滚动条颜色
		Element root12 = root.addElement("scroller");
		Element root13 = root12.addElement("color");
		root13.setText("FF0000");
		
		Element root14 = root.addElement("values");
		Element root15 = root14.addElement("x");
		Element root16 = root15.addElement("skip_first");
		root16.setText("false");
		
		Element root17 = root.addElement("values");
		Element root18 = root17.addElement("x");
		Element root19 = root18.addElement("skip_last");
		root19.setText("false");
		Element ele1 = root.addElement("graphs");
		
		int rrr=0;
		Iterator it = moremap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			for (int i = 0; i < dataset_list.size(); i++) {
				PageConfigBo bo = dataset_list.get(i);
				// 判断是否有自定义选中列
				if(tempmap==null) continue;
				if(!tempmap.containsKey(bo.getFieldname())) continue;
				
				Element ele2 = ele1.addElement("graph");
					ele2.addAttribute("gid",""+(rrr+1));
				Element eleiki = ele2.addElement("title");
					eleiki.setText(key+bo.getCaption());
				Element eleaxisrl= ele2.addElement("axis");
					eleaxisrl.setText(bo.getPosition());
				Element elehidden= ele2.addElement("hidden");
					elehidden.setText("FALSE");
				Element elewidth= ele2.addElement("line_width");
					elewidth.setText("2");
				Element eleiki2 = ele2.addElement("text_size");
					eleiki2.setText("16");
				if(bo.getChartcolor()!=null && !"".equals(bo.getChartcolor())){
					Element elecolor = ele2.addElement("color");
					elecolor.setText("#"+bo.getChartcolor());
				}
				rrr++;
			}
		}
		
		Element eleLabels = root.addElement("labels");
		Element eleLabel = eleLabels.addElement("label");	
		Element eleY = eleLabel.addElement("y");
		eleY.setText("0");
		Element eleAlign = eleLabel.addElement("align");
		eleAlign.setText("center");
		Element eleText = eleLabel.addElement("text");
		//if(title==null || "".equals(title)) title = "曲线图";
		eleText.setText("<b>"+title+"</b>");
		
		this.createFile(tempurl, doc);
	}
	
	/**
	 * 适用于两个数据集，双x轴的曲线
	 * @param datalist
	 * @param datalist2
	 * @param pageConfig
	 * @param dataset_list
	 * @param xfieldname
	 * @param morefieldname
	 * @param title
	 */
	public void writeXml(List<Object> datalist,List<Object> datalist2, PageConfig pageConfig, List<PageConfigBo> dataset_list, String xfieldname, String morefieldname, String title) {
		if (datalist == null) datalist = new ArrayList<Object>();
		String tempurl = this.getUrl() + "charts\\flashcharts\\data\\amline_data" + pageConfig.getConfigKey() + ".xml";
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("chart");
		root.addComment("<message><![CDATA[You can broadcast any message to chart from data XML file]]></message>");
		Element series = root.addElement("series");
		int c = 0, cs = 0, cg = 1, ccs = 0;
		Map timemap = new LinkedHashMap<String, String>();// 保存横轴数据（通常是时间为轴）
		Map moremap = new LinkedHashMap<String, String>();
		List customTimelist = pageConfig.getChartTimeList();
		
		// 生成横轴数据(通常是时间类型)
		for (int s = datalist.size() - 1; s >= 0; s--) {
			Map map = (Map) datalist.get(s);
			String xvalue = map.get(xfieldname) + "";
			String xvalue2 = "";
			
			if(datalist.size()>datalist2.size()){
				int cz = datalist.size()-datalist2.size();
				if((s-cz)>=0){
					Map map2 = (Map) datalist2.get(s-cz);
					xvalue2 = map2.get(xfieldname) + "";
				}
			}else{
				Map map2 = (Map) datalist2.get(s);
				xvalue2 = map2.get(xfieldname) + "";
			}
			xvalue = xvalue+"<br/>"+xvalue2;
			if (!timemap.containsKey(xvalue)) {
				addValue(series, cs + "", xvalue);
				cs++;
				timemap.put(xvalue, xvalue);
			}
		}
		// 选中的显示列
		Map tempmap = StringToMap(pageConfig.getChartCheck());
		// 循环生成曲线数据
		Element graphs = root.addElement("graphs");
		for (int i = 0; i < dataset_list.size(); i++) {
			PageConfigBo bo = dataset_list.get(i);
			// 判断是否有自定义选中列
			if(tempmap==null) continue;
			if(!tempmap.containsKey(bo.getFieldname())) continue;
			
			Element graph = graphs.addElement("graph");
			graph.addAttribute("gid", cg + "");
			if (bo.getPosition() != null && bo.getPosition().equals("right")) graph.addAttribute("axis", "right");
			
			int j = 0;
			for (int s = datalist.size() - 1; s >= 0; s--) {
				Map map = (Map) datalist.get(s);
				addValue(graph, j + "", map.get(bo.getFieldname()) + "");
				j++;
				
				String morevalue = map.get(morefieldname)==null?"":map.get(morefieldname).toString();
				if (!moremap.containsKey(morevalue)) moremap.put(morevalue, morevalue);
			}
			cg++;
		}
		for (int i = 0; i < dataset_list.size(); i++) {
			PageConfigBo bo = dataset_list.get(i);
			// 判断是否有自定义选中列
			if(tempmap==null) continue;
			if(!tempmap.containsKey(bo.getFieldname())) continue;
			
			Element graph = graphs.addElement("graph");
			graph.addAttribute("gid", cg + "");
			if (bo.getPosition() != null && bo.getPosition().equals("right")) graph.addAttribute("axis", "right");
			
			int j = 0;
			for (int s = datalist.size() - 1; s >= 0; s--) {
				if(datalist.size()>datalist2.size()){
					int cz = datalist.size()-datalist2.size();
					if((s-cz)>=0){
						Map map = (Map) datalist2.get(s-cz);
						addValue(graph, j + "", map.get(bo.getFieldname()) + "");
						
						String morevalue = map.get(morefieldname)==null?"":map.get(morefieldname).toString();
						if (!moremap.containsKey(morevalue)) moremap.put(morevalue, morevalue);
					}else{
						addValue(graph, j + "", "null");
					}
				}else{
					Map map = (Map) datalist2.get(s);
					addValue(graph, j + "", map.get(bo.getFieldname()) + "");
					
					String morevalue = map.get(morefieldname)==null?"":map.get(morefieldname).toString();
					if (!moremap.containsKey(morevalue)) moremap.put(morevalue, morevalue);
				}
				j++;
			}
			cg++;
		}

		this.createFile(tempurl, doc);
		
		this.writeSetingXml(title, pageConfig, dataset_list, moremap, tempmap);
	}
	
	/**
	 * 大屏页面专用生成曲线设置 XML
	 * @param title
	 * @param pageConfig
	 * @param dataset_list
	 * @param moremap
	 * @param tempmap 自定义选中列
	 */
	private void writeSetingXmlDisplay(String title, PageConfig pageConfig, List<PageConfigBo> dataset_list, Map moremap, Map tempmap) {
		String tempurl = this.getUrl() + "charts\\flashcharts\\data\\amline_settings" + pageConfig.getConfigKey() + ".xml";
		XMLWriter writer = null; 
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		Document doc = DocumentHelper.createDocument(); 
		Element root = doc.addElement("settings"); 
		Element legend = root.addElement("legend");
		Element eleabled = legend.addElement("enabled");
		eleabled.setText("true");
		Element root1 = root.addElement("connect"); 
		root1.setText("true");
		Element root5 = root.addElement("text_size"); 
		root5.setText("8");//X Y轴字体大小

		Element root20 = root.addElement("text_color"); 
		root20.setText("#FFFFFF");//X Y轴字体颜色
		//[,]小数分隔符。注意，只用来显示数据。数据的XML文件中必须使用小数点
		Element ds = root.addElement("decimals_separator"); 
		ds.setText(".");
		//true，图表会被重画
		Element redraw = root.addElement("redraw"); 
		redraw.setText("true");
		Element root2 = root.addElement("plot_area");
		Element root3 = root2.addElement("margins");
		Element root4 = root3.addElement("top");
		root4.setText("40");
		Element bottom = root3.addElement("bottom");
		bottom.setText("40");
		//Element root6 = root3.addElement("left");
		//root6.setText("80");
		Element root7 = root.addElement("grid");
		Element root8 = root7.addElement("x");
		Element root9 = root8.addElement("approx_count");
		root9.setText("6");//横轴有多少数据
		
		Element yleft = root7.addElement("y_left");
		Element approxcount = yleft.addElement("approx_count");
		approxcount.setText("6");
		Element yright = root7.addElement("y_right");
		Element approycount = yright.addElement("approx_count");
		approycount.setText("6");
		//导出提示
		Element eleStrings = root.addElement("strings");
		Element eleCollecting = eleStrings.addElement("collecting_data");
		eleCollecting.setText("正在导出...");
		//显示右上角
		Element root10 = root.addElement("zoom_out_button");
		Element root11 = root10.addElement("text");
		root11.setText("返回");
		//滚动条颜色
		Element root12 = root.addElement("scroller");
		Element root13 = root12.addElement("color");
		root13.setText("FF0000");
		
		Element root14 = root.addElement("values");
		Element root15 = root14.addElement("x");
		Element root16 = root15.addElement("skip_first");
		root16.setText("false");
		
		Element root17 = root.addElement("values");
		Element root18 = root17.addElement("x");
		Element root19 = root18.addElement("skip_last");
		root19.setText("false");
		Element ele1 = root.addElement("graphs");
		
		int rrr=0;
		Iterator it = moremap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			for (int i = 0; i < dataset_list.size(); i++) {
				PageConfigBo bo = dataset_list.get(i);
				// 判断是否有自定义选中列
				if(tempmap==null) continue;
				if(!tempmap.containsKey(bo.getFieldname())) continue;
				
				Element ele2 = ele1.addElement("graph");
					ele2.addAttribute("gid",""+(rrr+1));
				Element eleiki = ele2.addElement("title");
					eleiki.setText(key+bo.getCaption());
				Element eleaxisrl= ele2.addElement("axis");
					eleaxisrl.setText(bo.getPosition());
				Element elehidden= ele2.addElement("hidden");
					elehidden.setText("FALSE");
				Element elewidth= ele2.addElement("line_width");
					elewidth.setText("2");
				Element eleiki2 = ele2.addElement("text_size");
					eleiki2.setText("16");
				if(bo.getChartcolor()!=null && !"".equals(bo.getChartcolor())){
					Element elecolor = ele2.addElement("color");
					elecolor.setText("#"+bo.getChartcolor());
				}
				rrr++;
			}
		}
		
		Element eleLabels = root.addElement("labels");
		Element eleLabel = eleLabels.addElement("label");	
		Element eleY = eleLabel.addElement("y");
		eleY.setText("0");
		Element eleAlign = eleLabel.addElement("align");
		eleAlign.setText("center");
		Element eleText = eleLabel.addElement("text");
		//if(title==null || "".equals(title)) title = "曲线图";
		eleText.setText("<b>"+title+"</b>");
		
		this.createFile(tempurl, doc);
	}
	/**
	 * 大屏页面专用生成曲线 XML
	 * @param datalist
	 * @param pageConfig
	 * @param dataset_list
	 * @param xfieldname
	 * @param morefieldname
	 * @param title
	 */
	public void writeXmlDisplay(List<Object> datalist, PageConfig pageConfig, List<PageConfigBo> dataset_list, String xfieldname, String morefieldname, String title) {
		if (datalist == null) datalist = new ArrayList<Object>();
		String tempurl = this.getUrl() + "charts\\flashcharts\\data\\amline_data" + pageConfig.getConfigKey() + ".xml";
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("chart");
		root.addComment("<message><![CDATA[You can broadcast any message to chart from data XML file]]></message>");
		Element series = root.addElement("series");
		int c = 0, cs = 0, cg = 1, ccs = 0;
		//List timelist = new ArrayList<String>();
		Map timemap = new LinkedHashMap<String, String>();// 保存横轴数据（通常是时间为轴）
		Map moremap = new LinkedHashMap<String, String>();
		List customTimelist = pageConfig.getChartTimeList();
		// 生成指定日期横轴
		if (customTimelist != null) {
			for (int i = 0; i < customTimelist.size(); i++) {
				String xvalue = (String) customTimelist.get(i);
				String[] custom = xvalue.split(":");
				boolean istrue = true;
				for (int s = datalist.size() - 1; s >= 0; s--) {
					Map map = (Map) datalist.get(s);
					String xval = map.get(xfieldname) + "";
					String[] data = xval.split(":");
					int v = Integer.parseInt(data[1]) - Integer.parseInt(custom[1]);
					int nodeCount = 1;
					// 小时相等，数据的分钟数自定义分钟数大于等于零且小于上数时间间隔
					// 用数据的分钟数代替自定义分钟数
					if (custom[0].equals(data[0]) && v >= 0 && v < nodeCount) {
						xvalue = xval;
						if (!timemap.containsKey(xvalue)) {
							addValue(series, i + "", xvalue);
							timemap.put(xvalue, xvalue);
							istrue = false;
							ccs++;
						}
					}
				}
				if (istrue && !timemap.containsKey(xvalue)) {
					addValue(series, i + "", xvalue);
					timemap.put(xvalue, xvalue);
					ccs++;
				}
			}
		}
		// 生成横轴数据(通常是时间类型)
		for (int s = datalist.size() - 1; s >= 0; s--) {
			Map map = (Map) datalist.get(s);
			String xvalue = map.get(xfieldname) + "";
			
			if (customTimelist == null) {
				if (!timemap.containsKey(xvalue)) {
					addValue(series, cs + "", xvalue);
					cs++;
					timemap.put(xvalue, xvalue);
				}
			}
			
			if (morefieldname != null && !"".equals(morefieldname)) {
				// 多站多曲线（保存站名称）,默认最多选三个站
				String morevalue = map.get(morefieldname) + "";
				if (!moremap.containsKey(morevalue) && c < pageConfig.getMaxNodeNum()) {
					moremap.put(morevalue, morevalue);
					c++;
				}
			} else {
				moremap.put("", "");
			}
		}
		// 选中的显示列
		Map tempmap = StringToMap(pageConfig.getChartCheck());
		// 循环生成曲线数据
		Element graphs = root.addElement("graphs");
		Iterator it = moremap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			for (int i = 0; i < dataset_list.size(); i++) {
				PageConfigBo bo = dataset_list.get(i);
				// 判断是否有自定义选中列
				if(tempmap==null) continue;
				if(!tempmap.containsKey(bo.getFieldname())) continue;
				
				Element graph = graphs.addElement("graph");
				graph.addAttribute("gid", cg + "");
				if (bo.getPosition() != null && bo.getPosition().equals("right")) graph.addAttribute("axis", "right");
				
				int j = 0;
				Iterator itt = timemap.keySet().iterator();
				while (itt.hasNext()) {
					String xtime = (String) itt.next();
					boolean istrue = true;
					for (int s = datalist.size() - 1; s >= 0; s--) {
						Map map = (Map) datalist.get(s);
						String xvalue = map.get(xfieldname)==null?"":map.get(xfieldname).toString();
						String morevalue = map.get(morefieldname)==null?"":map.get(morefieldname).toString();

						if (xvalue.equals(xtime) && morevalue.equals(key)) {
							addValue(graph, j + "", map.get(bo.getFieldname()) + "");
							istrue = false;
							break;
						}
					}
					if(istrue) addValue(graph,j+"","null");
					j++;
				}
				cg++;
			}
		}

		this.createFile(tempurl, doc);
		
		this.writeSetingXmlDisplay(title, pageConfig, dataset_list, moremap, tempmap);
	}
	
	/**
	 * 大屏页面专用生成饼图 XML
	 * @param datalist
	 * @param pageConfig
	 * @param dataset_list
	 * @param xfieldname
	 * @param morefieldname
	 * @param title
	 */
	public void writeXmlDisplayPie(List<Object> datalist,String key,String value, String title) {
		if (datalist == null) datalist = new ArrayList<Object>();
		String tempurl = this.getUrl() + "charts\\flashcharts\\data\\pie_data.xml";
		Document doc = DocumentHelper.createDocument();
	
		StringBuffer sb = new StringBuffer();
		sb.append("<chart caption='"+title+"' showLabels='1' pieRadius='60' showColumnShadow='0' animation='1' showAlternateHGridColor='1' AlternateHGridColor='137986' divLineColor='ffffff' divLineIsDashed='1' divLineAlpha='20' alternateHGridAlpha='20' canvasBorderColor='ffffff' canvasBorderAlpha='20' canvasBorderThickness='1' baseFontColor='ffffff' lineColor='FF5904' lineAlpha='85' showValues='1' rotateValues='0' valuePosition='auto' canvasbgAlpha='100' bgAlpha ='0' baseFontSize='8' borderThickness ='0'  showBorder='0' showYAxisValues='0' showToolTip='0' anchorsides='3' anchorradius='5'>");
		for(Object obj:datalist){
			Map map = (Map)obj;
			sb.append("<set label='"+map.get(key)+"' value='"+map.get(value)+"' isSliced='0'/>");
		}
	    sb.append("</chart>");
	    try {
			doc = DocumentHelper.parseText(sb.toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		this.createFile(tempurl, doc);	
	}
	
	/**
	 * 大屏页面专用生成线性刻度表 XML 暂时没写活
	 * @param datalist
	 * @param pageConfig
	 * @param dataset_list
	 * @param xfieldname
	 * @param morefieldname
	 * @param title
	 * 
	 * 5C8F0E 绿色 FFCC00黄色 B40001红色
	 */
	public void writeXmlDisplayDialGauge(String filename,String value,List list) {
		    for(int i=0 ; i<list.size();i++){
		    	Map map = (Map)list.get(i);
				String tempurl = this.getUrl() + "charts\\flashcharts\\data\\"+filename+i+"_data.xml";
				Document doc = DocumentHelper.createDocument();
				StringBuffer sb = new StringBuffer();
				sb.append("<chart bgColor='FFFFFF' canvasbgAlpha='100' bgAlpha ='0' borderAlpha ='0' chartTopMargin='0' chartBottomMargin='0' upperLimit='100' lowerLimit='0' ticksBelowGauge='1' tickMarkDistance='3' valuePadding='-2' pointerRadius='5' majorTMColor='000000' majorTMNumber='3' minorTMNumber='4' minorTMHeight='4' majorTMHeight='8' showShadow='0' pointerBgColor='FFFFFF' pointerBorderColor='000000' gaugeBorderThickness='3' baseFontColor='000000' gaugeFillMix='{color},{FFFFFF}' gaugeFillRatio='50,50'>");
				sb.append("<colorrange> "+
						 "<color minValue='0' maxValue='30' code='B40001' label='"+map.get(value)+"'/> "+
						 "</colorrange>"+
						 "<value>"+map.get(value)+"</value> "+
						 "</chart> ");
			    try {
					doc = DocumentHelper.parseText(sb.toString());
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				this.createFile(tempurl, doc);
		    }	
		}
	
	/**
	 * 生成文件
	 * @param tempurl
	 * @param doc
	 */
	private void createFile(String tempurl,Document doc){
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		FileOutputStream fos = null;
		File file = new File(tempurl);
		if (file.exists()) {
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			writer = new XMLWriter(fos, format);
			try {
				writer.write(doc);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 字符串转Map
	 * @param param
	 * @return
	 */
	public static Map StringToMap(String param){
		Map map = null;
		if(param!=null && !"".equals(param) && !"null".equals(param)){
			map = new HashMap();
			for(String key:param.split(",")){
				map.put(key, key);
			}
		}
		return map;
	}

}
