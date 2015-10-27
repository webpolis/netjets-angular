package com.teamhub.plugins.netjets.api.utils;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.teamhub.controllers.utils.PaginatedList;

@Service
public class NetjetsApiJsonUtils {

	public String encodeQuestionsList(final PaginatedList list) {
		final ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.writeValueAsString(list);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String encodeMessage(final Map<String, Object> msg){
		final ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.writeValueAsString(msg);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
