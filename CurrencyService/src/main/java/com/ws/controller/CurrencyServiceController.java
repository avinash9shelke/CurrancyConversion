package com.ws.controller;

import javax.annotation.Resource;

import net.webservicex.ConversionRate;
import net.webservicex.ConversionRateResponse;
import net.webservicex.Currency;
import net.webservicex.ObjectFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ws.client.core.WebServiceTemplate;


@Controller
public class CurrencyServiceController
{
    @Resource(name="webServiceTemplate")
	private WebServiceTemplate webServiceTemplate;
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public ModelAndView callServicePage()
	{
		ModelAndView view = new ModelAndView("currency");
		return view;
	}
	
	@RequestMapping(value = "/currencyrate.html" ,method=RequestMethod.GET)
	public String getCurrency(Model model,@RequestParam("fromCurrency")Currency fromCurrency,@RequestParam("toCurrency")Currency toCurrency)
	{
	  
		 ConversionRate conversionRate = new ObjectFactory().createConversionRate();
	        conversionRate.setFromCurrency(fromCurrency);
	        conversionRate.setToCurrency(toCurrency);
	 
	        ConversionRateResponse response = (ConversionRateResponse) webServiceTemplate.marshalSendAndReceive(
	                conversionRate);
	   double responseValue=response.getConversionRateResult();
	  
	   model.addAttribute("currency", responseValue);
	   model.addAttribute("fromCurrency", fromCurrency);
	   model.addAttribute("toCurrency", toCurrency);
		 
	   return "currency"; 
	}

}
