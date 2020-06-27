package com.kakaopay.restservice.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kakaopay.restservice.service.ReceiveService;

public class ReceiveController {
    
    @Resource(name = "receiveService")
    private ReceiveService receiveService;
    
        
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public Model receive(@RequestParam(value = "x-room-id") String roomId
			,@RequestParam(value = "x-user-id") String receiveUserId
			,@RequestParam(value = "x-private-token") String privateToken
			, Model model) {
    	
    	//receiveUserId가 없는 row 하나에 receiveUserId update 함
    	HashMap<String, Object> map = new HashMap<String, Object>();

    	String currentDateTime = getCurrentDateTime(); //현재날짜 및 시간을 구함
        
    	map.put("roomId", roomId);
    	map.put("privateToken", privateToken);
    	map.put("receiveUserId", receiveUserId);
    	map.put("availableDateTime", currentDateTime);	//10분 이내의 건만 가능
    	
        int sqlOk = receiveService.updateReceiveDB(map);
        /*
         * update ReceiveDB
         * set receiveUserId = #receiveUserId#
         * where roomId = #roomId#
         * and privateToken = #privateToken#
         * and availableDateTime >= currentDateTime
         * and rownum =1;
         * 
         */

        if(sqlOk != 0) {
        	model.addAttribute("error", "뿌릴 내용 수정 중 error");
        	return model;
        }
    	
        HashMap<String, Object> receiveMap = receiveService.getReceiveDB(map);
        
        if(receiveMap.isEmpty())
        {
        	model.addAttribute("error", "받을 수 있는 뿌리기가 없습니다.");
        	return model;
        }

    	model.addAttribute("receiveMap", receiveMap);
                
        return model;
    }
    
    //현재날짜
    public String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        
        return (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
    }

}
