package model.messages;

public class ErrorReplyMessage implements Message{

	public String error;
	public Object data;
	
	@Override
	public String getName() {
		return "Error";
	}

	@Override
	public void setName(String name) {
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
