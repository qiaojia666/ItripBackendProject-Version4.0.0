package cn.ekgc.itrip.pojo.vo;

import java.io.Serializable;

public class AddUserLinkUserVO  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String linkUserName;
	private String linkPhone;
	private String linkIdCard;
	private int linkIdCardType;

	public String getLinkUserName() {
		return linkUserName;
	}

	public void setLinkUserName(String linkUserName) {
		this.linkUserName = linkUserName;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkIdCard() {
		return linkIdCard;
	}

	public void setLinkIdCard(String linkIdCard) {
		this.linkIdCard = linkIdCard;
	}

	public int getLinkIdCardType() {
		return linkIdCardType;
	}

	public void setLinkIdCardType(int linkIdCardType) {
		this.linkIdCardType = linkIdCardType;
	}
}
