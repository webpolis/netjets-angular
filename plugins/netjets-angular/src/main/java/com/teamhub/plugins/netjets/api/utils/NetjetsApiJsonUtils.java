package com.teamhub.plugins.netjets.api.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.teamhub.controllers.utils.PaginatedList;
import com.teamhub.infrastructure.utils.BeanUtils;
import com.teamhub.models.node.Question;
import com.teamhub.models.user.User;

@Service
public class NetjetsApiJsonUtils {

	public String encodeQuestionsList(final List<Question> list){
		final List<Map<String, Object>> questions = new ArrayList<Map<String,Object>>();
		final ObjectMapper mapper = new ObjectMapper();
		
		if(list != null){	
			for(final Question question : list){
				final Map<String, Object> qMap = new HashMap<String, Object>();
				qMap.put("id", question.getId());
				qMap.put("title", question.getTitle());
				qMap.put("plug", question.getPlug());
				
				final Map<String, Object> authorMap = new HashMap<String, Object>();
				final User author = (User) BeanUtils.deproxyAsObject(question.getAuthor());
				
				authorMap.put("id", author.getId());
				authorMap.put("username", author.getUsername());
				
				qMap.put("author", authorMap);
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
