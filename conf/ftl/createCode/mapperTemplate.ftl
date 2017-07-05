<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectName}Mapper">
	
	
	
	
	<!-- 列表 -->
	<select id="datalistPage" parameterType="page" resultType="pd">
		<!--这里写要使用的sql--->
	</select>
	
	<!-- 列表(全部) -->
	<select id="listAll" parameterType="pd" resultType="pd">
		<!--这里写要使用的sql--->
	</select>
	
		<insert id="save" parameterType="pd">
		<!--这里写要使用的sql-->
	</insert>
	
</mapper>