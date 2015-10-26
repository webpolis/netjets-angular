package com.teamhub.plugins.netjets.api.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.teamhub.controllers.utils.PaginatedList;
import com.teamhub.models.node.Question;

@Component
public class NetjetsApiJsonEncoder {

	public String encodeQuestionsList(final List<Question> list){
		final List<Map<String, Object>> questions = new ArrayList<Map<String,Object>>();
		final ObjectMapper mapper = new ObjectMapper();
		
		if(list != null){	
			for(final Question question : list){
				final Map<String, Object> qMap = new HashMap<String, Object>();
				qMap.put("id", question.getId());
				qMap.put("title", question.getTitle());
				qMap.put("plug", question.getPlug());
				
				final Map<String, Object> author = new HashMap<String, Object>();
				author.put("id", question.getAuthor().getId());
				author.put("username", question.getAuthor().getUsername());
				
				qMap.put("author", author);
				qMap.put("creationDate", question.getCreationDate());
				qMap.put("lastActivityDate", question.getLastActivity());
				qMap.put("body", question.asHTML());
				qMap.put("topics", question.getTopicNamesList());

				questions.add(qMap);
			}
		}
		
		try {
			return mapper.writeValueAsString(questions);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
