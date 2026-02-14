package team.kitemc.verifymc.web;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ReviewWebSocketServer extends WebSocketServer {
    private final Set<WebSocket> clients = Collections.synchronizedSet(new HashSet<>());
    private final boolean debug;
    private final org.bukkit.plugin.Plugin plugin;
    private ConcurrentHashMap<String, Long> validTokens;

    public ReviewWebSocketServer(int port, org.bukkit.plugin.Plugin plugin) {
        super(new InetSocketAddress(port));
        this.plugin = plugin;
        this.debug = plugin.getConfig().getBoolean("debug", false);
    }

    /**
     * Compatible with old constructor
     */
    public ReviewWebSocketServer(int port) {
        super(new InetSocketAddress(port));
        this.plugin = null;
        this.debug = false;
    }

    /**
     * Set the valid tokens map for WebSocket authentication
     */
    public void setValidTokens(ConcurrentHashMap<String, Long> validTokens) {
        this.validTokens = validTokens;
    }

    private void debugLog(String msg) {
        if (debug) plugin.getLogger().info("[DEBUG] ReviewWebSocketServer: " + msg);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        debugLog("WebSocket connection opened: " + conn.getRemoteSocketAddress());

        // Validate token from query string: ws://host:port?token=xxx
        if (validTokens != null) {
            String resourceDescriptor = handshake.getResourceDescriptor();
            String token = null;
            if (resourceDescriptor != null && resourceDescriptor.contains("token=")) {
                String query = resourceDescriptor.contains("?") ? resourceDescriptor.substring(resourceDescriptor.indexOf('?') + 1) : resourceDescriptor;
                for (String param : query.split("&")) {
                    String[] kv = param.split("=", 2);
                    if (kv.length == 2 && "token".equals(kv[0])) {
                        token = kv[1];
                        break;
                    }
                }
            }
            if (token == null || token.isBlank()) {
                debugLog("WebSocket connection rejected: no token provided");
                conn.close(1008, "Authentication required");
                return;
            }
            Long expiryTime = validTokens.get(token);
            if (expiryTime == null || System.currentTimeMillis() > expiryTime) {
                debugLog("WebSocket connection rejected: invalid or expired token");
                conn.close(1008, "Invalid or expired token");
                return;
            }
        }

        clients.add(conn);
        debugLog("Total clients connected: " + clients.size());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        debugLog("WebSocket connection closed: " + conn.getRemoteSocketAddress() + ", code: " + code + ", reason: " + reason);
        clients.remove(conn);
        debugLog("Remaining clients: " + clients.size());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        debugLog("Received message from " + conn.getRemoteSocketAddress() + ": " + message);
        // Can handle messages from frontend as needed
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        debugLog("WebSocket error: " + ex.getMessage());
        if (conn != null) {
            debugLog("Error on connection: " + conn.getRemoteSocketAddress());
        }
    }

    @Override
    public void onStart() {
        debugLog("WebSocket server started on port: " + getPort());
    }

    public void broadcastMessage(String message) {
        debugLog("Broadcasting message to " + clients.size() + " clients: " + message);
        synchronized (clients) {
            int sentCount = 0;
            for (WebSocket ws : clients) {
                if (ws.isOpen()) {
                    ws.send(message);
                    sentCount++;
                }
            }
            debugLog("Message sent to " + sentCount + " clients");
        }
    }
}
