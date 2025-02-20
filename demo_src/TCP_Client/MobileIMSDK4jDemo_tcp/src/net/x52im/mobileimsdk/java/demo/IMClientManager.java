/*
 * Copyright (C) 2021  即时通讯网(52im.net) & Jack Jiang.
 * The MobileIMSDK_TCP (MobileIMSDK v6.x TCP版) Project. 
 * All rights reserved.
 * 
 * > Github地址：https://github.com/JackJiang2011/MobileIMSDK
 * > 文档地址：  http://www.52im.net/forum-89-1.html
 * > 技术社区：  http://www.52im.net/
 * > 技术交流群：320837163 (http://www.52im.net/topic-qqgroup.html)
 * > 作者公众号：“即时通讯技术圈】”，欢迎关注！
 * > 联系作者：  http://www.52im.net/thread-2792-1-1.html
 *  
 * "即时通讯网(52im.net) - 即时通讯开发者社区!" 推荐开源工程。
 * 
 * IMClientManager.java at 2021-7-7 22:06:47, code by Jack Jiang.
 */
package net.x52im.mobileimsdk.java.demo;

import net.x52im.mobileimsdk.java.ClientCoreSDK;
import net.x52im.mobileimsdk.java.conf.ConfigEntity;
import net.x52im.mobileimsdk.java.conf.ConfigEntity.SenseMode;
import net.x52im.mobileimsdk.java.demo.event.ChatBaseEventImpl;
import net.x52im.mobileimsdk.java.demo.event.ChatMessageEventImpl;
import net.x52im.mobileimsdk.java.demo.event.MessageQoSEventImpl;

public class IMClientManager
{
	private static String TAG = IMClientManager.class.getSimpleName();
	
	private static IMClientManager instance = null;
	
	/** MobileIMSDK是否已被初始化. true表示已初化完成，否则未初始化. */
	private boolean init = false;
	
	// 
	private ChatBaseEventImpl baseEventListener = null;
	//
	private ChatMessageEventImpl transDataListener = null;
	//
	private MessageQoSEventImpl messageQoSListener = null;

	public static IMClientManager getInstance()
	{
		if(instance == null)
			instance = new IMClientManager();
		return instance;
	}
	
	private IMClientManager()
	{
		initMobileIMSDK();
	}

	public void initMobileIMSDK()
	{
		if(!init)
		{
		
			// 设置服务器ip和服务器端口
//			ConfigEntity.serverIP = "192.168.82.138";
//			ConfigEntity.serverIP = "rbcore.openmob.net";
//			ConfigEntity.serverUDPPort = 7901;
	    
			// MobileIMSDK核心IM框架的敏感度模式设置
			ConfigEntity.setSenseMode(SenseMode.MODE_5S);
			
			// 设置最大TCP帧内容长度（不设置则默认最大是 6 * 1024字节）
//			LocalSocketProvider.TCP_FRAME_MAX_BODY_LENGTH = 60 * 1024;
	    
			// 开启/关闭DEBUG信息输出
	    	ClientCoreSDK.DEBUG = true;
	    
			// 设置事件回调
			baseEventListener = new ChatBaseEventImpl();
			transDataListener = new ChatMessageEventImpl();
			messageQoSListener = new MessageQoSEventImpl();
			ClientCoreSDK.getInstance().setChatBaseEvent(baseEventListener);
			ClientCoreSDK.getInstance().setChatMessageEvent(transDataListener);
			ClientCoreSDK.getInstance().setMessageQoSEvent(messageQoSListener);
			
			init = true;
		}
	}

	public void release()
	{
		ClientCoreSDK.getInstance().release();
		resetInitFlag();
	}

	/**
	 * 重置init标识。
	 * <p>
	 * <b>重要说明：</b>不退出APP的情况下，重新登陆时记得调用一下本方法，不然再
	 * 次调用 {@link #initMobileIMSDK()} 时也不会重新初始化MobileIMSDK（
	 * 详见 {@link #initMobileIMSDK()}代码）而报 code=203错误！
	 * 
	 */
	public void resetInitFlag()
	{
		init = false;
	}

	public ChatMessageEventImpl getChatMessageListener()
	{
		return transDataListener;
	}
	public ChatBaseEventImpl getBaseEventListener()
	{
		return baseEventListener;
	}
	public MessageQoSEventImpl getMessageQoSListener()
	{
		return messageQoSListener;
	}
}
