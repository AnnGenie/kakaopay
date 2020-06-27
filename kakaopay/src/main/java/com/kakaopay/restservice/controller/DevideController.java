package com.kakaopay.restservice.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kakaopay.restservice.service.DevideService;

public class DevideController {
    
    @Resource(name = "devideService")
    private DevideService devideService;
    
        
    @RequestMapping(value = "/devide", method = RequestMethod.POST)
    public String devide(@RequestParam(value = "x-room-id") String roomId
			,@RequestParam(value = "x-user-id") String devideUserId
			,@RequestParam(value = "x-devide-amount") String devideAmount
			,@RequestParam(value = "x-devide-count") String devideCount
			, Model model) {
    	
    	//방 인원수 확인
    	HashMap<String, Object> map = new HashMap<String, Object>();
        
    	map.put("roomId", roomId);
    	
        HashMap<String, Object> roomMap = devideService.getRoomDB(map);
        
        String roomCount = (String) roomMap.get("roomCount");
        if(Integer.parseInt(devideCount) > Integer.parseInt(roomCount))
        	return "뿌릴 대상의 수는 현재 방 인원보다 클 수 없습니다.";
    	
        
        //token 발급 및 고유여부 확인
        String privateToken = "";
        
        while(true)
        {
        	privateToken = createToken();
            map.put("roomId", roomId);
            map.put("privateToken", privateToken);
            
            int tokenCount = devideService.getDevideDB(map);
            
            if(tokenCount == 0)
            	break;
        }
        
        
        //뿌릴내용 저장
        String regAvailableDateTime = getRegAvailableDateTime(); //유효날짜 및 시간
        
        map.put("roomId", roomId);
        map.put("privateToken", privateToken);
        map.put("devideUserId", devideUserId);
        map.put("devideAmount", devideAmount);
        map.put("devideCount", devideCount);
        map.put("regAvailableDateTime", regAvailableDateTime);
        
        int sqlOk = devideService.insertDevideDB(map);
        
        if(sqlOk != 0) {
        	return "뿌릴 내용 저장 중 error";
        }
        
        
        //분배 및 저장
        String availableDateTime = getAvailableDateTime(); //유효날짜 및 시간
        int[] receiveAmount = new int[Integer.parseInt(devideCount)];
        int tempDevideAmount = Integer.parseInt(devideAmount);
        int tempDevideCount = Integer.parseInt(devideCount);
        
        for(int i=0; i<=tempDevideCount; i++)
        {
            map.put("roomId", roomId);
            map.put("privateToken", privateToken);
        	if(i == 0) {
                map.put("receiveAmount", "0");
                map.put("receiveUserId", devideUserId);
        	}
        	else {
        		receiveAmount[i] = randomReceiveAmount(tempDevideAmount, tempDevideCount-i);
        		tempDevideAmount = tempDevideAmount - receiveAmount[i];
        		
                map.put("receiveAmount", i==tempDevideCount ? tempDevideAmount : receiveAmount[i]);
                map.put("receiveUserId", "");
        	}
            map.put("availableDateTime", availableDateTime);
            
            sqlOk = devideService.insertReceiveDB(map);

            if(sqlOk != 0) {
            	return "받을 내용 저장 중 error";
            }
        }
                
        return privateToken;
    }
    
    //token 발급
    public String createToken() {
    	StringBuffer tempToken = new StringBuffer();
    	
    	Random rnd = new Random();
    	
    	for (int i = 0; i < 3; i++) {
    	    int rIndex = rnd.nextInt(3);
    	    switch (rIndex) {
    	    case 0:
    	        // a-z
    	    	tempToken.append((char) ((int) (rnd.nextInt(26)) + 97));
    	        break;
    	    case 1:
    	        // A-Z
    	    	tempToken.append((char) ((int) (rnd.nextInt(26)) + 65));
    	        break;
    	    case 2:
    	        // 0-9
    	    	tempToken.append((rnd.nextInt(10)));
    	        break;
    	    }
    	}
    	
    	return tempToken.toString();
    }
    
    //유효날짜 및 시간
    public String getRegAvailableDateTime() {
    	Date date = new Date();
    	
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        
        date = calendar.getTime();
        
        return (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
    }
    
    //유효날짜 및 시간
    public String getAvailableDateTime() {
    	Date date = new Date();
    	
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 10);
        
        date = calendar.getTime();
        
        return (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
    }

    //무작위 수
    public int randomReceiveAmount(int devideAmount, int i) {
        Random rnd = new Random();
        int rndInt = rnd.nextInt(devideAmount);
        if(rndInt == 0)
        	randomReceiveAmount(devideAmount, i);
        if(rndInt == devideAmount - i)
        	randomReceiveAmount(devideAmount, i);
        return rndInt;
    }
    
}
