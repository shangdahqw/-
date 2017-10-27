package sd.hqw.domain;

import java.io.Serializable;
public class Series implements Serializable {


	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private User uer;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public User getUer() {
		return uer;
	}

	public void setUer(User uer) {
		this.uer = uer;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
