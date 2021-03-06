package com.tvd12.ezyfoxserver.plugin.auth.command.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.entity.EzyData;
import com.tvd12.ezyfox.util.EzyEntityBuilders;
import com.tvd12.ezyfoxserver.command.EzyAppResponse;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.entity.EzySession;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.plugin.auth.command.EzyChatResponse;

@SuppressWarnings("unchecked")
public abstract class EzyAbstractResponse<T extends EzyChatResponse<T>> 
		extends EzyEntityBuilders
		implements EzyChatResponse<T> {

	protected Object data;
	protected String command;
    protected Set<EzySession> recipients = new HashSet<>();
    
    protected final EzyPluginContext context;
    protected final EzyMarshaller marshaller;
    
    public EzyAbstractResponse(EzyPluginContext context, EzyMarshaller marshaller) {
        this.context = context;
        this.marshaller = marshaller;
    }
    
	public T command(String command) {
        this.command = command;
        return (T) this;
    }

	@Override
    public T data(Object data) {
        this.data = data;
        return (T) this;
    }

    public T user(EzyUser user) {
        if(user != null)
            this.recipients.addAll(user.getSessions());
        return (T) this;
    }
    
    public T users(EzyUser... users) {
        return users(Arrays.asList(users));
    }

    public T users(Iterable<EzyUser> users) {
        users.forEach(this::user);
        return (T) this;
    }
    
    @Override
    public T session(EzySession session) {
        this.recipients.add(session);
        return (T) this;
    }
    
    @Override
    public T sessions(EzySession... sessions) {
        Arrays.stream(sessions).forEach(this::session);
        return (T) this;
    }
    
    @Override
    public T sessions(Iterable<EzySession> sessions) {
        sessions.forEach(this::session);
        return (T) this;
    }
    
    public Boolean execute() {
    	response(getResponseData());
        return Boolean.TRUE;
    }
    
    protected abstract EzyData getResponseData();
    
    protected void response(EzyData data) {
        context.get(EzyAppResponse.class)
        	.command(command)
        	.params(data)
        	.sessions(recipients)
        	.execute();
     }
	
}
