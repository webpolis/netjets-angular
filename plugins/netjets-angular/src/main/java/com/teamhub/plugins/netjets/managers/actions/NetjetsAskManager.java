package com.teamhub.plugins.netjets.managers.actions;

import java.util.Collections;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamhub.infrastructure.spring.RequestInfo;
import com.teamhub.managers.action.ActionExtensionManager;
import com.teamhub.models.action.Action;
import com.teamhub.models.action.AskAction;
import com.teamhub.models.action.exceptions.ActionFailedException;
import com.teamhub.plugins.netjets.api.NetjetsWebsocketServer;
import com.teamhub.plugins.netjets.api.utils.NetjetsApiJsonUtils;

@Service
public class NetjetsAskManager extends ActionExtensionManager<AskAction> {

	@Autowired
	NetjetsWebsocketServer websocketServer;

	@Autowired
	NetjetsApiJsonUtils jsonUtils;

	@Override
	public void processAction(final Action action,
			final RequestInfo requestInfo, final Map<String, Object> data)
					throws ActionFailedException {
		if ((this.websocketServer != null)
				&& (this.websocketServer.connections() != null)) {
			for (final WebSocket socket : this.websocketServer.connections()) {
				if (socket.isOpen()) {
					socket.send(this.jsonUtils
							.encodeMessage((Map<String, Object>) Collections
									.<String, Object> singletonMap("update",
											true)));
				}
			}
		}
	}

}
