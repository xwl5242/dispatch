package com.dispatch.warn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dispatch.warn.service.WarnService;
import com.frames.base.BaseController;

@Controller
@RequestMapping("/warn")
public class WarnController extends BaseController{

	@Autowired
	private WarnService warnService;
	
}
