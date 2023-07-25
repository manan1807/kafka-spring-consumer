package com.example.libraryeventlistener.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ObjectsMapper {

	@Autowired
	private ObjectMapper mapper;
	
	public String convertObjectToString(Object t) throws JsonProcessingException {
			return mapper.writeValueAsString(t);
	}
	
}
