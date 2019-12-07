package application;

import java.io.Serializable;

public class Request implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int id;
	private int system;
	private String desc;
	private String change;
	private String handler;
	private String status;
	
	public Request(int id, String name, int system, String desc, String change, String status, String handler) {
		this.id = id;
		this.name = name;
		this.system = system;
		this.desc = desc;
		this.change = change;
		this.status = status;
		this.handler = handler;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getSystem() {
		return SystemEnum.getByInt(system).toString();
	}

	public String getDesc() {
		return desc;
	}

	public int getSystemInt() {
		return system;
	}

	public String getChange() {
		return change;
	}

	public String getHandler() {
		return handler;
	}

	public String getStatus() {
		return status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSystem(int system) {
		this.system = system;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return "" + (id + ", " + name + ", " + system + ", " + desc + ", " + change + ", " + status + ", " + handler);
	}

	enum SystemEnum {
		InfoStation, Moodle, Library, Computers, Labs, Site;
		public static SystemEnum getByInt(int i) {
			switch (i) {
			case 0:
				return InfoStation;
			case 1:
				return Moodle;
			case 2:
				return Library;
			case 3:
				return Computers;
			case 4:
				return Labs;
			case 5:
				return Site;

			}
			return null;
		}
	}
}
