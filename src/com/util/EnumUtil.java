package com.util;

/**
 * 功能描述：常量类  .  <BR>
 */
public enum EnumUtil {
	
	Company(1),泵(2),THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7);
	
	private int num;
	
	private EnumUtil(int num){
		this.num=num;
	}
	
	public  String toString(){
		return this.num+"";
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public String getNumString(){
		return num+"";
	}
	
	/**
	 * 采集点类型
	 */
	public final static String TYPE1 = "1";//普通
	public final static String TYPE2 = "2";//个别
	
	/**
	 * 集团性质
	 */
	public final static String GROUPTYPE1 = "1";//集团内
	public final static String GROUPTYPE2 = "2";//集团外
	
	/**
	 * 所属类型
	 */
	public final static String OWNERTYPE1 = "1";//用能单位
	public final static String OWNERTYPE2 = "2";//热力厂
	public final static String OWNERTYPE3 = "3";//热源
	public final static String OWNERTYPE4 = "4";//热力站
	public final static String OWNERTYPE5 = "5";//一次管网
	public final static String OWNERTYPE6 = "6";//二次管网
	public final static String OWNERTYPE7 = "7";//楼座
	public final static String OWNERTYPE8 = "8";//支路
	public final static String OWNERTYPE9 = "9";//中心
	public final static String OWNERTYPE10 = "10";//公共
	public final static String OWNERTYPE11 = "11";//全部
	
	/**
	 * 计量器具子表是否主要
	 */
	public final static String ISIMP1 = "1";//是
	public final static String ISIMP2 = "2";//否
	
	/**
	 * 计量器具子表运行状态	
	 */
	public final static String FUNCTIONSTATUS1 = "1";//运行
	public final static String FUNCTIONSTATUS2 = "2";//备用
	public final static String FUNCTIONSTATUS3 = "3";//维修
	public final static String FUNCTIONSTATUS4 = "4";//故障
	public final static String FUNCTIONSTATUS5 = "5";//报废
	
	/**
	 * 分项计量与采集点关系公式
	 */
	public final static String FORMULA1 = "1";//max
	public final static String FORMULA2 = "2";//abs
	public final static String FORMULA3 = "3";//min-max
	public final static String FORMULA4 = "4";//max-min
	public final static String FORMULA5 = "5";//min
	public final static String FORMULA6 = "6";//avg
	public final static String FORMULA7 = "7";//sum
	
	/**
	 * 分项计量类型
	 */
	public final static String PARAMETERSTYPE1 = "1";//填报
	public final static String PARAMETERSTYPE2 = "2";//计算
	
	/**
	 * 分项计量状态
	 */
	public final static String PARAMETERSSTATUS1 = "1";//可用
	public final static String PARAMETERSSTATUS2 = "2";//不可用
	
	/**
	 * 考核模板类型
	 */
	public final static String TEMPLETYPE1 = "1";//能耗总量考核
	public final static String TEMPLETYPE2 = "2";//综合能耗指标考核
	
	/**
	 * 考核模板状态
	 */
	public final static String TEMPLATESTATUS1 = "1";//草稿
	public final static String TEMPLATESTATUS2 = "2";//提交
	public final static String TEMPLATESTATUS3 = "3";//作废
	
	/**
	 * 采集历史数据来源（整理前）
	 */
	public final static String DATASOURCE1 = "1";//手动填报
	public final static String DATASOURCE2 = "2";//自动采集
	
	/**
	 * 采集类型
	 */
	public final static String COLLECTIONTYPE1 = "1";//实际
	public final static String COLLECTIONTYPE2 = "2";//预报
	
	/**
	 * 单位供热类型
	 */
	public final static String HEATINGTYPE1 = "1";//区域供热
	public final static String HEATINGTYPE2 = "2";//集中供热
	public final static String HEATINGTYPE3 = "3";//尖峰
	
	/**
	 * 单位管理类型
	 */
	public final static String MANAGETYPE1 = "1";//统管
	public final static String MANAGETYPE2 = "2";//自管
	public final static String MANAGETYPE3 = "3";//代管
	
	/**
	 * 预警类型
	 */
	public final static String WARNINGTYPE1 = "1";//分项计量
	public final static String WARNINGTYPE2 = "2";//采集点
	
	/**
	 * 预警周期
	 */
	public final static String WARINGCYCLE1 = "1";//采暖季
	public final static String WARINGCYCLE2 = "2";//年
	public final static String WARINGCYCLE3 = "3";//季度
	public final static String WARINGCYCLE4 = "4";//月
	public final static String WARINGCYCLE5 = "5";//周
	public final static String WARINGCYCLE6 = "6";//日
	public final static String WARINGCYCLE7 = "7";//时
	
	/**
	 * 权限类型
	 */
	public final static String RIGHTTYPE1 = "1";//菜单
	public final static String RIGHTTYPE2 = "2";//按钮
	
	/**
	 * 是否公共权限
	 */
	public final static String RIGHTSTAT1 = "1";//是
	public final static String RIGHTSTAT2 = "2";//否
	
	/**
	 * 组织类型
	 */
	public final static String ORGTYPE1 = "1";//单位
	public final static String ORGTYPE2 = "2";//部门
	public final static String ORGTYPE3 = "3";//组
	
	/**
	 * 管理类型
	 */
	public final static String ORGMANAGERTYPE1 = "1";//管理单位
	public final static String ORGMANAGERTYPE2 = "2";//热源
	
	/**
	 * 操作类型
	 */
	public final static String LOGTYPE1 = "1";//增加
	public final static String LOGTYPE2 = "2";//修改
	public final static String LOGTYPE3 = "3";//删除
	
	/**
	 * 日志类型
	 */
	public final static String OPTTYPE1 = "1";//增删改
	public final static String OPTTYPE2 = "2";//查
	
	/**
	 * 计量器具管理类型
	 */
	public final static String MIINFOOWNERTYPE1 = "1";//锅炉房
	public final static String MIINFOOWNERTYPE2 = "2";//热力站
	public final static String MIINFOOWNERTYPE3 = "3";//一次管网
	public final static String MIINFOOWNERTYPE4 = "4";//二次管网
	
	/**
	 * 采集点状态
	 */
	public final static String COLLINFOSTATUS1 = "1";//正常
	public final static String COLLINFOSTATUS2 = "2";//关闭
	
	/**
	 * 用户状态
	 */
	public final static String USERSTATE1 = "1";//启用
	public final static String USERSTATE2 = "2";//停用
	
	/**
	 * 转换类型
	 */
	public final static String CONVERTTYPE1 = "1";//单位
	public final static String CONVERTTYPE2 = "2";//采集点
	
	/**
	 * 考核模板公式
	 */
	public final static String CALCULATIONFORMULA1 = "1";//>
	public final static String CALCULATIONFORMULA2 = "2";//<
	public final static String CALCULATIONFORMULA3 = "3";//=
	public final static String CALCULATIONFORMULA4 = "4";//<=
	public final static String CALCULATIONFORMULA5 = "5";//>=
	
	/**
	 * 性别
	 */
	public final static String SEX1 = "1";//男
	public final static String SEX2 = "2";//女
	
	/**
	 * 公司类型
	 */
	public final static String COMPANYTYPE1 = "1";//机关本部
	public final static String COMPANYTYPE2 = "2";//分公司
	public final static String COMPANYTYPE3 = "3";//全资子公司
	public final static String COMPANYTYPE4 = "4";//控股子公司
	public final static String COMPANYTYPE5 = "5";//参股子公司
	
	/**
	 * 性质
	 */
	public final static String PARAMETERSPROPERTY1 = "1";//生产
	public final static String PARAMETERSPROPERTY2 = "2";//经营
	
	/**
	 * 地区类型
	 */
	public final static String AREATYPE1 = "1";//省
	public final static String AREATYPE2 = "2";//直辖市
	public final static String AREATYPE3 = "3";//市
	public final static String AREATYPE4 = "4";//区/县
	public final static String AREATYPE5 = "5";//乡/镇
	
	/**
	 * 设备类型
	 */
	public final static String EQUTYPE1 = "1";//锅炉
	public final static String EQUTYPE2 = "2";//泵
	public final static String EQUTYPE3 = "3";//换热器
	
	/**
	 * 对应组织类型
	 */
	public final static String CORRESPONDINGTYPE1 = "1";//用能单位
	public final static String CORRESPONDINGTYPE2 = "2";//热力厂
	public final static String CORRESPONDINGTYPE3 = "3";//热源
	public final static String CORRESPONDINGTYPE4 = "4";//热力站
	public final static String CORRESPONDINGTYPE5 = "5";//一次管网
	public final static String CORRESPONDINGTYPE6 = "6";//二次管网
	public final static String CORRESPONDINGTYPE7 = "7";//楼座
	public final static String CORRESPONDINGTYPE8 = "8";//支路
	public final static String CORRESPONDINGTYPE9 = "9";//公共
	public final static String CORRESPONDINGTYPE10 = "10";//中心
	
	/**
	 * 是否可以删除
	 */
	public final static String ISDELETE1 = "1";//不可以删除
	public final static String ISDELETE2 = "2";//可以删除
	
	/**
	 * 异常数值排除
	 */
	public final static String heatUnusal="1000";
	public final static String powerUnusal="1000";
	public final static String waterUnusal="1000";
}
