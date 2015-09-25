package com.sap.hackthon.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sap.hackthon.utils.GlobalConstants;

/**
 * 
 * @author I075885
 *
 */
@Controller
public class HtmlPageController {

	@RequestMapping(value = "/page/home.html", method = RequestMethod.GET)
    public ModelAndView home(Model model, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("home", "", model);
        String tenantId = (String)request.getSession().getAttribute(GlobalConstants.TENANT);
        
        model.addAttribute("tenant_id", tenantId);
        mv.addObject("tenant_id", tenantId);
        
        return mv;
    }

	@RequestMapping(value = "/page/udfList.html", method = RequestMethod.GET)
	public String udfList(Model model) {
		model.addAttribute("title", "Mainstay - Web");
		return "udfList";
	}

	@RequestMapping(value = "/page/udfCreate.html", method = RequestMethod.GET)
	public String udfCreate(Model model) {
		model.addAttribute("title", "Mainstay - Web");
		return "udfCreate";
	}

	@RequestMapping(value = "/page/udfView.html", method = RequestMethod.GET)
	public String udfView(Model model) {
		model.addAttribute("title", "Mainstay - Web");
		return "udfView";
	}

	@RequestMapping(value = "/page/orderList.html", method = RequestMethod.GET)
	public String orderList(Model model) {
		model.addAttribute("title", "Mainstay - Web");
		return "orderList";
	}

	@RequestMapping(value = "/page/orderCreate.html", method = RequestMethod.GET)
	public String orderCreate(Model model) {
		model.addAttribute("title", "Mainstay - Web");
		return "orderCreate";
	}

	@RequestMapping(value = "/page/orderView.html", method = RequestMethod.GET)
	public String orderView(Model model) {
		model.addAttribute("title", "Mainstay - Web");
		return "orderView";
	}
}
