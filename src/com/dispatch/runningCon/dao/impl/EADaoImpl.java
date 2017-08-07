package com.dispatch.runningCon.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dispatch.runningCon.dao.EADao;
import com.frames.jdbc.PageListJdbcTemplate;

@Repository
public class EADaoImpl extends PageListJdbcTemplate implements EADao {

	@Override
	public List<Map<String, Object>> tongbi(String type,String eaType) {
		String sql = "SELECT C.Y,C.AB,NVL(SUM(C.DL),0) DL ";
		sql += " FROM (                                   "
		+"  SELECT                                       "
		+"    A.Y,A.AB,ROUND(A.TOTAL-NVL(B.TTVV,0),2) DL "
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

	@Override
	public List<Map<String, Object>> airTongbi1(String startDate, String endDate,String type) {
		String sql = "SELECT                                    "
		+"  D.\"TYPE\" AITYPE,D.AB,                                    "
		+"  NVL(SUM(D.DL),0) DL                                 "
		+"FROM                                                  "
		+"  (                                                   "
		+"  SELECT                                              "
		+"    A.\"D\",A.AB,A.\"K\",A.\"TYPE\",                  "
		+"    ROUND(A.TOTAL - NVL(B.TTVV, 0), 2) DL             "
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

	@Override
	public List<Map<String, Object>> qushi(String startDate, String endDate,String type,String eaType) {
		String sql = "SELECT D.\"D\" YD,D.AB, NVL(SUM(D.DL),0) DL";
		sql += " FROM                                       "
		+"  (                                               "
		+"  SELECT                                          "
		+"    A.\"D\",A.AB,A.\"K\",                         "
		+"    ROUND(A.TOTAL - NVL(B.TTVV, 0),2) DL          "
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
		+"              SUM (A.TOTAL - NVL(B.TTVV, 0)) * "+kv.get("elec")
		+"            )                                     "
		+"          WHEN 'W' THEN                           "
		+"            (                                     "
		+"              SUM (A.TOTAL - NVL(B.TTVV, 0)) * "+kv.get("water")
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

	@Override
	public List<Map<String, Object>> getEnergyPrice() {
		String sql = "select ETYPE et,PRICE p from  E_PRICE";
		return super.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getArea(String string) {
		String sql = "select NVL(AREA,0) from unitarea where unitcode = '"+string+"'";
		return super.queryForList(sql);
	}

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
		+"			DECODE(SIGN(MAX(V)),-1,0,MAX(V)) V             "
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

	@Override
	public List<Map<String, Object>> drsh() {
		String sql = "SELECT A.Y,SUM(A.V) DRSH FROM (SELECT   "
		+"	\"D\",Y,                                          "
		+"	ROUND (AVG(NVL(V, 0))-26, 2) V                    "
		+"FROM                                                "
		+"	AI_KV                                             "
		+"WHERE                                               "
		+"	1 = 1                                             "
		+"AND SUBSTR (\"K\", INSTR(\"K\", '\\' ,-1) + 1) = 'tq_wd'"
		+"AND \"TYPE\" = 'tq' AND V>0                         "
		+"GROUP BY                                            "
		+"	\"D\",Y ) A                                       "
		+"GROUP BY A.Y                                        ";
		return super.queryForList(sql);
	}

}
