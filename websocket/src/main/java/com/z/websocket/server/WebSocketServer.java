package com.z.websocket.server;

import com.alibaba.fastjson.JSONObject;
import com.z.websocket.view.MessageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocketServer 其实就相当于一个 ws 协议的 Controller。
 * @ServerEndpoint 它的功能主要是将目前的类定义成一个 websocket 服务器端.
 * 注解的值将被用于监听用户连接的终端访问 URL 地址，
 * 客户端可以通过这个 URL 来连接到 WebSocket 服务器端
 */

@Component
@ServerEndpoint("/api/websocket/{sid}")
public class WebSocketServer {
    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    //当前在线连接数
    private static final AtomicInteger onlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private Session session;

    //接收sid
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        this.sid = sid;
        onlineCount.incrementAndGet();           //在线数加1
        sendMessage("conn_success");
        log.info("有新窗口开始监听:" + sid + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        onlineCount.decrementAndGet();           //在线数减1
        log.info("释放的sid为："+sid);
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param msg 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String msgData, Session session) {
        MessageData data = JSONObject.parseObject(msgData, MessageData.class);
        log.info("收到来自窗口" + sid + "的信息:" + data.getMessage());
        sendInfo(data.getMessage(), data.getToUserId());
    }

    /**
     * @ Param session
     * @ Param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    private void sendMessage(String msg) {
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
        }
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) {
        System.out.println("推送消息到窗口" + sid + "，推送内容:" + message);

        for (WebSocketServer item : webSocketSet) {
            //为null则全部推送
            if (StringUtils.isEmpty(sid) || item.sid.equals(sid)) {
                item.sendMessage(message);
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
}
