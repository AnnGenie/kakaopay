package com.kakaopay.restservice.service;

import java.util.HashMap;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.restservice.mapper.InquiryMapper;

@Service("InquiryService")
public class InquiryServiceImpl {
    
    //InquiryMapper 자동연결
    @Autowired
    private InquiryMapper inquiryMapper;
    
    public List<?> getListDevideDB(HashMap<String, Object> map) {
        return inquiryMapper.getListDevideDB(map);
    }

}
