package com.dispatch.runningCon.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dispatch.runningCon.dao.EADao;
import com.frames.jdbc.PageListJdbcTemplate;

@Repository
public class EADaoImpl extends PageListJdbcTemplate implements EADao {

	/**
	 * 同比柱状图（A座，B座，总体）
	 */
	@Override
	public List<Map<String, Object>> tongbi(String type,String eaType) {
		String sql = "SELECT C.Y,C.AB,NVL(SUM(C.DL),0) DL ";
		sql += " FROM (                                   "
		+"  SELECT                                       "
		+"    A.Y,A.AB,(case when A.TOTAL<0 OR NVL(B.TTVV,0)<=0 then 0 WHEN A.TOTAL - NVL(B.TTVV, 0)<=0 THEN 0 else ROUND(A.TOTAL - NVL(B.TTVV, 0),2) end) DL "
		+"  FROM (                                       "
		+"    SELECT TD, Y, \"K\", AB, MAX(V) TOTAL      "
		+"    FROM AI_KV                                 "
		+"    WHERE \"TYPE\" IN ("+type+")               "
		+"    GROUP BY TD, Y, \"K\", AB                  "
		+"  ) A LEFT JOIN (                              "
		+"    SELECT TD,\"K\",MAX(V) TTVV                "
		+"    FROM AI_KV                                 "
		+"    GROUP BY TD,\"K\"                          "
		+"  ) B ON B.TD = A.TD-1 AND A.\"K\"=B.\"K\"     "
		+") C                                            "
		+"GROUP BY C.Y,C.AB                              ";
		return super.queryForList(sql);
	}

	/**
	 * 柱状图
	 */
	@Override
	public List<Map<String, Object>> airTongbi1(String startDate, String endDate,String type) {
		String sql = "SELECT                                    "
		+"  D.\"TYPE\" AITYPE,D.AB,                                    "
		+"  NVL(SUM(D.DL),0) DL                                 "
		+"FROM                                                  "
		+"  (                                                   "
		+"  SELECT                                              "
		+"    A.\"D\",A.AB,A.\"K\",A.\"TYPE\",                  "
		+"  (case when A.TOTAL<0 OR NVL(B.TTVV,0)<=0 then 0 WHEN A.TOTAL - NVL(B.TTVV, 0)<=0 THEN 0 else ROUND(A.TOTAL - NVL(B.TTVV, 0),2) end) DL "
		+"  FROM                                                "
		+"    (                                                 "
		+"      SELECT                                          "
		+"        \"D\",TD,\"K\",AB,\"TYPE\",                   "
		+"        MAX (V) TOTAL                                 "
		+"      FROM                                            "
		+"        AI_KV                                         "
		+"      WHERE \"TYPE\" IN("+type+")                     ";
		if(null!=startDate&&!"".equals(startDate)){
			sql += " AND \"D\" >= '"+startDate+"'";
		}
		if(null!=endDate&&!"".equals(endDate)){
			sql += " AND \"D\" <= '"+endDate+"'";
		}
		sql += "      GROUP BY                                  "
		+"        \"D\",TD,\"K\",AB,\"TYPE\"                    "
		+"    ) A                                               "
		+"    LEFT JOIN (                                       "
		+"        SELECT                                        "
		+"          TD,\"K\",                                   "
		+"          MAX (V) AS TTVV                             "
		+"        FROM                                          "
		+"          AI_KV                                       "
		+"        GROUP BY                                      "
		+"          TD,\"K\"                                    "
		+"    ) B ON B.TD = A.TD - 1 AND A.\"K\" = B.\"K\"      "
		+") D                                                   "
		+"GROUP BY                                              "
		+"  D.\"TYPE\",D.AB		                                ";
		return super.queryForList(sql);
	}

	/**
	 * 所有曲线图
	 */
	@Override
	public List<Map<String, Object>> qushi(String startDate, String endDate,String type,String eaType) {
		String sql = "SELECT D.\"D\" YD,D.AB, NVL(SUM(D.DL),0) DL";
		sql += " FROM                                       "
		+"  (                                               "
		+"  SELECT                                          "
		+"    A.\"D\",A.AB,A.\"K\",                         "
		+" (case when A.TOTAL<0 OR NVL(B.TTVV,0)<=0 then 0 WHEN A.TOTAL - NVL(B.TTVV, 0)<=0 THEN 0 else ROUND(A.TOTAL - NVL(B.TTVV, 0),2) end) DL "
		+"  FROM                                            "
		+"    (                                             "
		+"      SELECT                                      "
		+"        \"D\",TD,\"K\",AB,                        "
		+"        MAX(V) TOTAL                              "
		+"      FROM                                        "
		+"        AI_KV                                     "
		+"      WHERE \"TYPE\" IN("+type+")                 ";
		if(null!=startDate&&!"".equals(startDate)){
			sql += " AND \"D\" >= '"+startDate+"'";
		}
		if(null!=endDate&&!"".equals(endDate)){
			sql += " AND \"D\" <= '"+endDate+"'";
		}
		sql += "      GROUP BY                              "
		+"        \"D\",TD,\"K\",AB                         "
		+"    ) A                                           "
		+"    LEFT JOIN (                                   "
		+"        SELECT                                    "
		+"          TD,\"K\",                               "
		+"          MAX(V) AS TTVV                          "
		+"        FROM                                      "
		+"          AI_KV                                   "
		+"        GROUP BY                                  "
		+"          TD,\"K\"                                "
		+"    ) B ON B.TD = A.TD-1 AND A.\"K\" = B.\"K\"    "
		+") D                                               "
		+" GROUP BY                                         "
		+"  D.\"D\",D.AB                                    ";
		return super.queryForList(sql);
	}

	/**
	 * 饼图
	 */
	@Override
	public List<Map<String, Object>> pricePie(String startDate, String endDate,List<Map<String,Object>> epList) {
		Map<String,Double> kv = new HashMap<String, Double>();
		for(Map<String,Object> map:epList){
			kv.put(map.get("ET")+"",Double.valueOf(map.get("P")+""));
		}
		
		String sql = "SELECT                                "
		+"	(                                               "
		+"		CASE                                        "
		+"		WHEN D .AB = 'A'                            "
		+"		AND D .\"TYPE\" = 'E' THEN                  "
		+"			'A座电'                                 "
		+"		WHEN D .AB = 'A'                            "
		+"		AND D .\"TYPE\" = 'W' THEN                  "
		+"			'A座水'                                 "
		+"		WHEN D .AB = 'B'                            "
		+"    AND D .\"TYPE\" = 'E' THEN                    "
		+"      'B座电'                                     "
		+"    WHEN D .AB = 'B'                              "
		+"    AND D .\"TYPE\" = 'W' THEN                    "
		+"      'B座水'                                     "
		+"    END                                           "
		+"  ) NAME,                                         "
		+"  D.P VALUE                                       "
		+" FROM                                             "
		+"  (                                               "
		+"    SELECT                                        "
		+"      A.AB,A.\"TYPE\",                            "
		+"      ROUND (                                     "
		+"        (                                         "
		+"          CASE A.\"TYPE\"                         "
		+"          WHEN 'E' THEN                           "
		+"            (                                     "
		+"              SUM ((case when A.TOTAL<0 OR NVL(B.TTVV,0)<=0 then 0 WHEN A.TOTAL - NVL(B.TTVV, 0)<=0 THEN 0 else ROUND(A.TOTAL - NVL(B.TTVV, 0),2) end)) * "+kv.get("elec")
		+"            )                                     "
		+"          WHEN 'W' THEN                           "
		+"            (                                     "
		+"              SUM ((case when A.TOTAL<0 OR NVL(B.TTVV,0)<=0 then 0 WHEN A.TOTAL - NVL(B.TTVV, 0)<=0 THEN 0 else ROUND(A.TOTAL - NVL(B.TTVV, 0),2) end)) * "+kv.get("water")
		+"            )                                     "
		+"          END                                     "
		+"        ),                                        "
		+"        2                                         "
		+"      ) P                                         "
		+"    FROM (                                        "
		+"      SELECT                                      "
		+"        TD,\"K\",                                 "
		+"        MAX(V) AS TOTAL,                          "
		+"        (                                         "
		+"          CASE                                    "
		+"          WHEN \"TYPE\" BETWEEN '1' AND '6' THEN  "
		+"            'E'                                   "
		+"          WHEN \"TYPE\" = '8' THEN                "
		+"            'W'                                   "
		+"          END                                     "
		+"        ) \"TYPE\",                               "
		+"        AB                                        "
		+"      FROM                                        "
		+"        AI_KV AA                                  "
		+"      WHERE \"TYPE\" BETWEEN '1' AND '8'          "
		+"			AND \"D\" >='"+startDate+"' AND \"D\" <= '"+endDate+"'"
		+"      GROUP BY                                    "
		+"        TD,\"K\",AB,\"TYPE\"                      "
		+"    ) A                                           "
		+"    LEFT JOIN (                                   "
		+"      SELECT                                      "
		+"        TD,\"K\",MAX(V) AS TTVV                   "
		+"      FROM                                        "
		+"        AI_KV                                     "
		+"      GROUP BY                                    "
		+"        TD,\"K\"                                  "
		+"    ) B ON B.TD = A.TD - 1                        "
		+"    AND A.\"K\" = B.\"K\"                         "
		+"    GROUP BY                                      "
		+"      A.AB,                                       "
		+"      A.\"TYPE\"                                  "
		+") D                                               ";
		return super.queryForList(sql);
	}

	/**
	 * 获取能源价格（水、电）
	 */
	@Override
	public List<Map<String, Object>> getEnergyPrice() {
		String sql = "select ETYPE et,PRICE p from  E_PRICE";
		return super.queryForList(sql);
	}

	/**
	 * 获取A座或B座面积
	 */
	@Override
	public List<Map<String, Object>> getArea(String string) {
		String sql = "select NVL(AREA,0) AREA from unitarea where unitcode = '"+string+"'";
		return super.queryForList(sql);
	}

	/**
	 * cop曲线
	 */
	@Override
	public List<Map<String, Object>> copLine(String startDate, String endDate) {
		String sql = "SELECT                                       "
		+"	A.HH,                                                  "
		+"	MAX(DECODE(A.F,'al_1',A.V)) A1,                        "
		+"	MAX(DECODE(A.F,'al_2',A.V)) A2,                        "
		+"	MAX(DECODE(A.F,'al_3',A.V)) A3,                        "
		+"	MAX(DECODE(A.F,'bl_1',A.V)) B1,                        "
		+"	MAX(DECODE(A.F,'bl_2',A.V)) B2,                        "
		+"	MAX(DECODE(A.F,'bl_3',A.V)) B3                         "
		+"FROM                                                     "
		+"	(                                                      "
		+"		SELECT                                             "
		+"			TO_CHAR (\"T\", 'yyyy-mm-dd hh24') HH,         "
		+"			\"K\",                                         "
		+"			SUBSTR (\"K\", INSTR(\"K\", '\\' ,- 1) + 1,4) F,"
		+"	(CASE WHEN NVL(DECODE(SIGN(MAX(V)),-1,0,MAX(V)),0)>10 THEN 10 ELSE ROUND(NVL(DECODE(SIGN(MAX(V)),-1,0,MAX(V)),0),2) END) V     "
		+"		FROM                                               "
		+"			AI_KV                                          "
		+"		WHERE 1=1                                          ";
		if(null!=startDate&&!"".equals(startDate)){
			sql += " AND \"D\" >= '"+startDate+"'";
		}
		if(null!=endDate&&!"".equals(endDate)){
			sql += " AND \"D\" <= '"+endDate+"'";
		}
		sql+="		AND \"TYPE\" = 'c'                             "
		+"		GROUP BY                                           "
		+"			TO_CHAR (\"T\", 'yyyy-mm-dd hh24'),            "
		+"			\"K\"                                          "
		+"	) A                                                    "
		+"GROUP BY A.HH                                            "
		+"ORDER BY A.HH                                            ";
		return super.queryForList(sql);
	}

	/**
	 * 度日数柱状图，同比
	 */
	@Override
	public List<Map<String, Object>> drsh(String startDate,String endDate) {
		String sql = "SELECT A.Y,SUM(A.V) DRSH FROM (SELECT   "
		+"	\"D\",Y,                                          "
		+"	ROUND (AVG(NVL(V, 0))-26, 2) V                    "
		+"FROM                                                "
		+"	AI_KV                                             "
		+"WHERE                                               "
		+"	1 = 1                                             "
		+"AND SUBSTR (\"K\", INSTR(\"K\", '\\' ,-1) + 1) = 'tq_wd'"
		+"AND \"TYPE\" = 'tq' AND V>0                         "
		+"AND SUBSTR(\"D\",6)>='"+startDate.substring(5)+"' AND SUBSTR(\"D\",6)<='"+endDate.substring(5)+"'"
		+"GROUP BY                                            "
		+"	\"D\",Y ) A                                       "
		+"GROUP BY A.Y                                        ";
		return super.queryForList(sql);
	}

	/**
	 * 度日数曲线
	 */
	@Override
	public List<Map<String, Object>> DRSHtrend(String startDate, String endDate) {
		String sql = "SELECT   "
				+"	\"D\",Y,                                          "
				+"	ROUND (AVG(NVL(V, 0))-26, 2) V                    "
				+"FROM                                                "
				+"	AI_KV                                             "
				+"WHERE                                               "
				+"	1 = 1                                             "
				+"AND SUBSTR (\"K\", INSTR(\"K\", '\\' ,-1) + 1) = 'tq_wd'"
				+"AND \"TYPE\" = 'tq' AND V>0                         "
				+"AND SUBSTR(\"D\",6)>='"+startDate.substring(5)+"' AND SUBSTR(\"D\",6)<='"+endDate.substring(5)+"'"
				+"GROUP BY                                            "
				+"	\"D\",Y                                        ";
				return super.queryForList(sql);
	}

	/**
	 * 数据更新（分页查询）
	 */
	@Override
	public Map<String, Object> editKV(int currentPage, int pageSize,
			Map<String, String> param) {
		String sql="select \"T\",\"K\",V,IMPV from AI_KV WHERE 1=1 ";
		if(StringUtils.isNotEmpty(param.get("startTime"))){
			sql += " AND \"D\" >='"+param.get("startTime")+"'"; 
		}
		if(StringUtils.isNotEmpty(param.get("endTime"))){
			sql += " AND \"D\" <='"+param.get("endTime")+"'";
		}
		if(StringUtils.isNotEmpty(param.get("pname"))){
			sql += " AND \"K\" LIKE '%"+param.get("pname")+"%'"; 
		}
		sql+=" order by \"T\" desc,\"K\"";
		return super.queryGridist(sql, "", currentPage, pageSize);
	}

	/**
	 * 采集点名称集合
	 */
	@Override
	public List<Map<String, Object>> pnameListJson() {
		String sql = "SELECT SUBSTR(COLUMN_NAME,instr(COLUMN_NAME,'\\',-1)+1) ID,SUBSTR(COLUMN_NAME,instr(COLUMN_NAME,'\\',-1)+1) TEXT FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'AI' AND COLUMN_name !='Time' ORDER BY COLUMN_ID";
		return super.queryForList(sql);
	}

	/**
	 * 数据更新（修改）
	 */
	@Override
	public int edit(String t, String k, String v) {
		String sql = "update AI_KV SET V='"+v+"' WHERE T=TO_DATE('"+t+"','yyyy-mm-dd hh24:mi:ss') and K='"+k+"'";
		return super.update(sql);
	}

	/**
	 * 数据更新（批量修改）
	 */
	@Override
	public int batchSavePV(String pname, String startTime, String endTime,
			String pvalue) {
		String sql = "update AI_KV SET V='"+pvalue+"' WHERE T>=TO_DATE('"+(startTime+" 00:00:00")+"','yyyy-mm-dd hh24:mi:ss') and T<=TO_DATE('"+(endTime+" 23:59:59")+"','yyyy-mm-dd hh24:mi:ss') and K like '%"+pname+"%'";
		return super.update(sql);
	}

	/**
	 * 平均cop曲线
	 */
	@Override
	public List<Map<String, Object>> copLine4Avg(String startDate,
			String endDate) {
		String sql = "SELECT A .D, NVL(SUM(DECODE(A .F, 'al_1', ROUND(A.V/A.C,2))),0) A1, "
				+ " NVL(SUM (DECODE(A .F, 'al_2', ROUND(A.V/A.C,2))),0) A2, NVL(SUM (DECODE(A .F, 'al_3', ROUND(A.V/A.C,2))),0) A3, "
				+ " NVL(SUM (DECODE(A .F, 'bl_1', ROUND(A.V/A.C,2))),0) B1, NVL(SUM (DECODE(A .F, 'bl_2', ROUND(A.V/A.C,2))),0) B2, "
				+ " NVL(SUM (DECODE(A .F, 'bl_3', ROUND(A.V/A.C,2))),0) B3 "
				+ " FROM ( SELECT \"D\", \"K\", SUBSTR (\"K\", INSTR(\"K\", '\\' ,- 1) + 1, 4) F, "
				+ " SUM(V) V, COUNT(1) C FROM AI_KV WHERE 1 = 1 ";
				if(null!=startDate&&!"".equals(startDate)){
					sql += " AND \"D\" >= '"+startDate+"'";
				}
				if(null!=endDate&&!"".equals(endDate)){
					sql += " AND \"D\" <= '"+endDate+"'";
				}
				sql+= " AND V > 0 AND V < 10 AND \"TYPE\" = 'c' GROUP BY \"D\", \"K\") A GROUP BY A .\"D\" ORDER BY A .\"D\" ";
		return super.queryForList(sql);
	}

	/**
	 * 室内外温度曲线
	 */
	@Override
	public List<Map<String, Object>> roomAndoutTempLine(String startDate,
			String endDate) {
		String sql="SELECT C.*, ROUND(nvl(LAST_VALUE (D .V IGNORE NULLS) OVER (ORDER BY C.HH),(C.AA+C.BB)/2),2) W "
				+ "FROM ( SELECT B.HH, ROUND (B.AZ / B.AC, 2) AA, ROUND (B.BZ / B.BC, 2) BB "
				+ "FROM ( SELECT A .HH, SUM ( DECODE ( INSTR (\"TagName\", 'abc', 1, 1), 6, A .V / A .C ) ) AZ, "
				+ "SUM ( DECODE ( INSTR (\"TagName\", 'bbc', 1, 1), 6, A .V / A .C ) ) BZ, "
				+ "( SELECT COUNT (DISTINCT \"TagName\") FROM AIR_T "
				+ "WHERE INSTR (\"TagName\", 'abc', 1, 1) = 6 ) AC, ( SELECT COUNT (DISTINCT \"TagName\") "
				+ "FROM AIR_T WHERE INSTR (\"TagName\", 'bbc', 1, 1) = 6 ) BC "
				+ "FROM ( SELECT TO_CHAR (\"Time\", 'yyyy-mm-dd hh24') HH, \"TagName\", SUM (\"PV\") V, COUNT (1) C "
				+ "FROM AIR_T WHERE 1 = 1";
				if(null!=startDate&&!"".equals(startDate)){
					sql += " AND TO_CHAR (\"Time\", 'yyyy-mm-dd') >= '"+startDate+"'";
				}
				if(null!=endDate&&!"".equals(endDate)){
					sql += " and TO_CHAR (\"Time\", 'yyyy-mm-dd') <= '"+endDate+"'";
				}
				sql+= " AND \"PV\" >0 GROUP BY TO_CHAR (\"Time\", 'yyyy-mm-dd hh24'), \"TagName\" ) A GROUP BY A .HH ) B ) C "
				+ "LEFT JOIN ( SELECT TO_CHAR (\"T\", 'yyyy-mm-dd hh24') HH, V "
				+ "FROM AI_KV WHERE \"TYPE\" = 'tq' AND INSTR (\"K\", 'wd', 1, 1) = LENGTH (\"K\") - 1 "
				+ "ORDER BY TO_CHAR (\"T\", 'yyyy-mm-dd hh24') ) D ON C.HH = D .HH ORDER BY C.HH";
		return super.queryForList(sql);
	}

}
