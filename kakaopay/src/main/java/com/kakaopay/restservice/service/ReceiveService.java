package com.kakaopay.restservice.service;

import java.util.HashMap;

public interface ReceiveService {
	
	public HashMap<String, Object> getReceiveDB(HashMap<String, Object> map);

	public int updateReceiveDB(HashMap<String, Object> map);

}
