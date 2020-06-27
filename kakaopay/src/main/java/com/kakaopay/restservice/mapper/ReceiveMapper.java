package com.kakaopay.restservice.mapper;

import java.util.HashMap;

public interface ReceiveMapper {
	
	public HashMap<String, Object> getReceiveDB(HashMap<String, Object> map);

	public int updateReceiveDB(HashMap<String, Object> map);
	
}
