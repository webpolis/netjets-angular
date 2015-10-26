package com.teamhub.plugins.netjets.api;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.teamhub.controllers.utils.PaginatedList;
import com.teamhub.infrastructure.utils.BeanUtils;
import com.teamhub.managers.site.ServerManager;
import com.teamhub.models.site.Container;
import com.teamhub.models.site.Network;
import com.teamhub.plugins.netjets.api.utils.NetjetsApiJsonEncoder;
import com.teamhub.plugins.netjets.managers.NetjetsApiManager;
import com.teamhub.util.NotFoundException;

@Controller
public class NetjetsWebsocketServer extends WebSocketServer {

	public static final int PORT = 8888;

	@Autowired
	static NetjetsApiManager apiManager;

	@Autowired
	ServerManager serverManager;

	public NetjetsWebsocketServer() {
		super(new InetSocketAddress(NetjetsWebsocketServer.PORT));
	}

	@PostConstruct
	public void init() {
		this.start();
	}

	@Override
	public void onOpen(final WebSocket conn, final ClientHandshake handshake) {
		return;
	}

	@Override
	public void onMessage(final WebSocket conn, final String message) {
		String ret = null;

		try {
			final JSONObject jsonMsg = new JSONObject(message);

			if (jsonMsg != null) {
				if (jsonMsg.get("action") != null) {
					final Long siteId = jsonMsg.getLong("site");
					final Container container = this.serverManager.getContainer(siteId);
					
					if(container != null){
						if (jsonMsg.get("action").equals("listBySpace")) {
							final String space = jsonMsg.getString("space");
							final String sort = jsonMsg.getString("sort");
							final int page = jsonMsg.getInt("page");
							final int pageSize = jsonMsg.getInt("pageSize");
	
							ret = NetjetsWebsocketServer.apiManager
									.getQuestionsBySpace(container, space, sort,
											page, pageSize);
						}
					}else{
						throw new NotFoundException();
					}
				}
			}
		} catch (final JSONException e) {
			e.printStackTrace();
		}

		conn.send(ret);
	}

	@Override
	public void onClose(final WebSocket conn, final int code,
			final String reason, final boolean remote) {
		return;
	}

	@Override
	public void onError(final WebSocket conn, final Exception exc) {
		return;
	}

}