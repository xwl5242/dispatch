package com.dispatch.sys.bean;

import java.util.List;

import com.frames.base.BaseData;

/**
 * 功能描述：权限  .  <BR>
 */
public class Right extends BaseData{
	
	private String  id   ;//ID  
	private String  parentid ;//父权限ID
	private String  rightname ;//权限名称
	private String  righturl; //资源链接'
	private String  righttype;//1：菜单，2：按钮    权限类型
	private String  rightdesc;//权限描述
	private String  rightstat; //0：是，1：否 是否公共权限
	private String  createtime; //创建时间
	private String  createman; //创建人
	private String  isDelete;	//是否删除
	private String  seq;
	private String picUrl;
	private String treeUrl;
	private String childnums;
	private List subRight;
	private boolean hasSubRight;
	
	public String getTreeUrl() {
		return treeUrl;
	}
	public void setTreeUrl(String treeUrl) {
		this.treeUrl = treeUrl;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getChildnums() {
		return childnums;
	}
	public void setChildnums(String childnums) {
		this.childnums = childnums;
	}
	public List getSubRight() {
		return subRight;
	}
	public void setSubRight(List subRight) {
		this.subRight = subRight;
	}
	public boolean isHasSubRight() {
		return hasSubRight;
	}
	public void setHasSubRight(boolean hasSubRight) {
		this.hasSubRight = hasSubRight;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getRightname() {
		return rightname;
	}
	public void setRightname(String rightname) {
		this.rightname = rightname;
	}
	public String getRighturl() {
		return righturl;
	}
	public void setRighturl(String righturl) {
		this.righturl = righturl;
	}
	public String getRighttype() {
		return righttype;
	}
	public void setRighttype(String righttype) {
		this.righttype = righttype;
	}
	public String getRightdesc() {
		return rightdesc;
	}
	public void setRightdesc(String rightdesc) {
		this.rightdesc = rightdesc;
	}
	public String getRightstat() {
		return rightstat;
	}
	public void setRightstat(String rightstat) {
		this.rightstat = rightstat;
	}
	public String getCreateman() {
		return createman;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public void setCreateman(String createman) {
		this.createman = createman;
	}

}
