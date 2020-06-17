package com.project.webstore.errorhandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorResponseBodyGenerator {
	public Map<String, Object> generateErrorResponseBody(Instant time, String status, List<String> messages) {
		Map<String, Object> body = new HashMap<String, Object>();

		body.put("timestamp", time);
		body.put("status", status);
		body.put("message", messages);

		return body;
	}
}
