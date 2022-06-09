package com.bidauc.BidAuctions;

import com.bidauc.BidAuctions.ocsf.AbstractClient;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 2523);
		}
		return client;
	}

}