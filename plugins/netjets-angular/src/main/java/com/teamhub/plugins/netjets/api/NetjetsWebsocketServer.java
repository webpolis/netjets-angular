package com.teamhub.plugins.netjets.api;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.teamhub.managers.site.ServerManager;
import com.teamhub.models.site.Container;
import com.teamhub.plugins.netjets.api.utils.NetjetsApiResponse;
import com.teamhub.util.NotFoundException;

@Controller
public class NetjetsWebsocketServer extends WebSocketServer {

	public static final int PORT = 8888;
	
	private final Logger logger = Logger.getLogger(NetjetsWebsocketServer.class);

	@Autowired
	ServerManager serverManager;

	@Autowired
	NetjetsApiResponse apiResponse;

	public NetjetsWebsocketServer() {
		super(new InetSocketAddress(NetjetsWebsocketServer.PORT));
	}

	@PostConstruct
	public void init() {
		this.start();
		logger.debug("Netjets Websocket API listening on port " + NetjetsWebsocketServer.PORT);
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
					final Container container = this.serverManager
							.getContainer(siteId);

					if (container != null) {
						ret = this.apiResponse.process(jsonMsg, container);
					} else {
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