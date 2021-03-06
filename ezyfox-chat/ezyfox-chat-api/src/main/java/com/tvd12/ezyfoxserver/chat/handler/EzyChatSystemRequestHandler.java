package com.tvd12.ezyfoxserver.chat.handler;

import com.tvd12.ezyfox.annotation.EzyKeyValue;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfoxserver.command.EzyAppResponse;

import lombok.Setter;

@EzyPrototype(properties = {
		@EzyKeyValue(key = "type", value = "REQUEST_HANDLER"),
		@EzyKeyValue(key = "cmd", value = "1")
})
@EzyArrayBinding
public class EzyChatSystemRequestHandler 
		extends EzyClientRequestHandler
		implements EzyDataBinding {

	@Setter
	private String message;
	
	@Override
	public void handle() {
		EzyArray response = newArrayBuilder().append(message + ", too!").build();
		getLogger().info("user {} chat {}", user.getName(), message);
		appContext.get(EzyAppResponse.class)
			.command("1")
			.params(response)
			.user(user)
			.execute();
	}

}
