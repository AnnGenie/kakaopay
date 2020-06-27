package com.kakaopay.restservice.service;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.restservice.mapper.DevideMapper;

@Service("DevideService")
public class DevideServiceImpl {
    
    //DevideMapper 자동연결
    @Autowired
    private DevideMapper devideMapper;
    
    public HashMap<String, Object> getRoomDB(HashMap<String, Object> map) {
        return devideMapper.getRoomDB(map);
    }
    
    public int getDevideDB(HashMap<String, Object> map) {
        return devideMapper.getDevideDB(map);
    }
    
    public int insertDevideDB(HashMap<String, Object> map) {
        return devideMapper.insertDevideDB(map);
    }
    
    public int insertReceiveDB(HashMap<String, Object> map) {
        return devideMapper.insertReceiveDB(map);
    }

}
