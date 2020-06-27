package com.kakaopay.restservice.service;

import java.util.HashMap;

public interface DevideService {
	
	public HashMap<String, Object> getRoomDB(HashMap<String, Object> map);

	public int getDevideDB(HashMap<String, Object> map);

	public int insertDevideDB(HashMap<String, Object> map);

	public int insertReceiveDB(HashMap<String, Object> map);

}
