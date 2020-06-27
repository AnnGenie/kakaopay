package com.kakaopay.restservice.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kakaopay.restservice.service.InquiryService;

public class InquiryController {
    
    @Resource(name = "inquiryService")
    private InquiryService inquiryService;
    
        
    @RequestMapping(value = "/inquiry", method = RequestMethod.POST)
    public Model inquiry(@RequestParam(value = "x-user-id") String devideUserId
			,@RequestParam(value = "x-private-token") String privateToken
			, Model model) {
    	
    	//뿌린 내역 
    	HashMap<String, Object> map = new HashMap<String, Object>();

    	String currentDateTime = getCurrentDateTime(); //현재날짜 및 시간을 구함
        
    	map.put("devideUserId", devideUserId);
    	map.put("privateToken", privateToken);
    	map.put("regAvailableDateTime", currentDateTime); //7일이내
    	
        List<?> devideList= inquiryService.getListDevideDB(map);
        /*
         * select de.roomId, de.privateToken, de.devideUserId, de.devideAmount, de.devideCount,
         *        re.receiveAmount, re.receiveUserId
         * from devideDB de, receive re
         * where de.roomId = re.roomId
         * and de.privateToken = re.privateToken;
         * 
         */
        
        if(devideList.size() == 0)
        {
        	model.addAttribute("error", "뿌리기 하신 내용이 없습니다.");
        	return model;
        }

    	model.addAttribute("devideList", devideList);
        
                
        return model;
    }
    
    //현재날짜
    public String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        
        return (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
    }
    
}
