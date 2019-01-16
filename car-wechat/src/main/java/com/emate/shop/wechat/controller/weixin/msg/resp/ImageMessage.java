package com.emate.shop.wechat.controller.weixin.msg.resp;


/**
 * 图片消息
 * ImageMessage.java
 * @author likk
 * 2015-7-24 上午10:45:04
 *
 */
public class ImageMessage extends BaseMessage {
	// 图片
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
}
