package com.util.common;

/**
 * 功能描述：定义返回信息  .  <BR>
 */
public class ResponseMessage {
	/** 变量描述：级别. */
	private ResponseLevel level;
	/** 变量描述：消息. */
	private String message;
	/** 变量描述：参数. */
	private String[] args;
	public ResponseMessage(ResponseLevel level, String message)
    {
	    this.level = level;
	    this.message = message;
    }

    public ResponseMessage(ResponseLevel level, String message, String[] args) {
	    this.level = level;
	    this.message = message;
	    this.args = args;
    }
	public ResponseLevel getLevel() {
		return level;
	}
	public void setLevel(ResponseLevel level) {
		this.level = level;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}
	  
}
