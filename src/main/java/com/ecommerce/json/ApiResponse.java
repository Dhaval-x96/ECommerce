package com.ecommerce.json;

import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class ApiResponse<T>  implements Serializable {

	private T response;
	private String errorCode;
	private String exception;
	private List<String> errorMessages;
	private String path;
	private Long timestamp;



	public ApiResponse(){}
	public ApiResponse(T response, String path){
		this.response = response;
		this.path = path;
		this.timestamp = LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.UTC).toEpochMilli();
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ApiResponse{" +
				"response=" + response +
				", errorCode='" + errorCode + '\'' +
				", exception='" + exception + '\'' +
				", errorMessages=" + errorMessages +
				", path='" + path + '\'' +
				", timestamp=" + timestamp +
				'}';
	}
}
