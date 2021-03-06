package com.tvd12.ezyfoxserver.chat.client.socket;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.cmd.EzySendRequest;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.client.controller.EzyLoginController;
import com.tvd12.ezyfoxserver.client.entity.EzyClientSession;
import com.tvd12.ezyfoxserver.client.request.EzyAccessAppRequest;

public class ChatLoginController extends EzyLoginController {

	@Override
	protected void processNotReconnect(EzyClientContext ctx, EzyClientSession session, EzyArray data) {
		ctx.get(EzySendRequest.class)
			.sender(ctx.getMe())
			.request(newAccessAppRequest())
			.execute();
	}
	
	protected EzyAccessAppRequest newAccessAppRequest() {
		System.out.println("Chat Login SOcket COnect");
		return EzyAccessAppRequest.builder()
				.appName("ezyfox-chat")
				.data(newAccessAppData())
				.build();
	}
	
	protected EzyObject newAccessAppData() {
		return newObjectBuilder().build();
	}
}
