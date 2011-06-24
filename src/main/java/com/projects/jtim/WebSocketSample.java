package com.projects.jtim;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.websocket.Extension;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketConnection;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class WebSocketSample extends WebSocketServlet {

	private static final long serialVersionUID = 1L;

	private final Set<ChatWebSocket> connectedClients = new CopyOnWriteArraySet<ChatWebSocket>();
 
	private final Logger log = Logger
			.getLogger(WebSocketSample.class.getName());

	/**
	 * Doing the upgrade of the http request, known as the handshake
	 */
	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {

		return new ChatWebSocket();
	}

	class ChatWebSocket implements WebSocket, WebSocketConnection, WebSocket.OnTextMessage {

		private Connection connection;

		@Override
		public void onOpen(Connection connection) {
			this.connection = connection;
			connectedClients.add(this);
			log.info("Client connected! ");
		}

		@Override
		public void onMessage(String data) {
			try {
				
				for(ChatWebSocket client : connectedClients)
				{
					client.connection.sendMessage(data);
					log.info("You said: " + data);
				}
				
			} catch (IOException e) {
				log.info("WebSocket error:" + e.getMessage());
			}
		}

		@Override
		public void onClose(int closeCode, String message) {
			connectedClients.remove(this);
			log.info("Server closed! " + message);
		}

		@Override
		public org.eclipse.jetty.io.Connection handle() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getTimeStamp() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isIdle() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSuspended() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void closed() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void idleExpired() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void fillBuffersFrom(Buffer buffer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handshake(HttpServletRequest request,
				HttpServletResponse response, String origin, String subprotocol)
				throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<Extension> getExtensions() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
