package cn.ekgc.itrip.pojo.vo;

import java.io.Serializable;

public class SearchUserLinkUserVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String linkUserName;

	public String getLinkUserName() {
		return linkUserName;
	}

	public void setLinkUserName(String linkUserName) {
		this.linkUserName = linkUserName;
	}
}
