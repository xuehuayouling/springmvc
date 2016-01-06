package com.ysq.test.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionContext {

	private static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
	private static SessionContext mSelf;
	private SessionContext() {
		
	}
	
	public static synchronized SessionContext getInstance() {
		if (mSelf == null) {
			mSelf = new SessionContext();
		}
		return mSelf;
	}

	public synchronized HttpSession getSession(String token) {
		return sessionMap.get(token);
	}

	public synchronized void AddSession(String token, HttpSession session) {
		if (sessionMap.containsKey(token)) {
			
		}
	}
}
