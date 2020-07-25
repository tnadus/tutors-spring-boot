package net.elmadigital.tutorsmanager.exception;

import org.springframework.http.HttpStatus;

public class TutorResponseError {
	
	private HttpStatus statusCode;
	private String message;
	private long timeStamp;
	
	//Constructors
	public TutorResponseError(HttpStatus statusCode, String message, long timeStamp) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.timeStamp = timeStamp;
	}
	
	//Getters & Setters

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
