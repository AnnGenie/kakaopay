package com.kakaopay.restservice.service;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.restservice.mapper.ReceiveMapper;

@Service("ReceiveService")
public class ReceiveServiceImpl {
    
    //ReceiveMapper 자동연결
    @Autowired
    private ReceiveMapper receiveeMapper;
    
    public HashMap<String, Object> getReceiveDB(HashMap<String, Object> map) {
        return receiveeMapper.getReceiveDB(map);
    }
    
    public int updateReceiveDB(HashMap<String, Object> map) {
        return receiveeMapper.updateReceiveDB(map);
    }

}
