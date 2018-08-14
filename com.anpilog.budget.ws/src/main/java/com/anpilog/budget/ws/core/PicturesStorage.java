package com.anpilog.budget.ws.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PicturesStorage {

	private static Logger logger = LoggerFactory.getLogger(PicturesStorage.class);
	public final static PicturesStorage INSTANCE = new PicturesStorage();
	private Stack<Map<String, File>> picStack;

	private PicturesStorage() {
		picStack = new Stack<Map<String, File>>();
	}
	
	public int size(){
		return picStack.size();
	}
	
	public void addPicture(String accountName, File file){
		Map<String, File> newPic = new HashMap<String, File>();
		newPic.put("account", file);
		picStack.push(newPic);		
		logger.info("Added new screenshot: {}", file.getName());
	}
	
	public Stack<Map<String, File>> getAllPictures(){		
		return picStack;
	}
}
