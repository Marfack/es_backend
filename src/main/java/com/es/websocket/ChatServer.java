package com.es.websocket;

import com.es.dto.ChatDto;
import com.es.dto.ChatObjectDto;
import com.es.enums.ResponseCode;
import com.es.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2021/12/22 18:55
 *
 * @author Marfack
 */
@Component
@ServerEndpoint("/chat/{id}")
public class ChatServer {

    private static final Log logger = LogFactory.getLog(ChatServer.class);

    private static final Map<Long, ChatObjectDto> USERS = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("id") long id) throws JsonProcessingException {
        logger.info(String.format("User %d has connected successfully at %s.", id, new Date()));
        if (!USERS.containsKey(id)) {
            USERS.put(id, new ChatObjectDto(session, new ArrayList<>()));
        } else {
            USERS.get(id).setSession(session);
            String res = JsonUtils
                    .createJson()
                    .put("code", ResponseCode.SUCCESS.getCode())
                    .put("msg", ResponseCode.SUCCESS.getMsg())
                    .put("data", USERS.get(id).getMessages())
                    .returnJson();
            USERS.get(id).getMessages().clear();
            session.getAsyncRemote().sendText(res);
        }
    }

    @OnClose
    public void onClose(@PathParam("id") long id) {
        logger.info(String.format("User %d has disconnected at %s.", id, new Date()));
        USERS.get(id).setSession(null);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("id") long id) throws JsonProcessingException {
        logger.info(String.format("User %d send a message \"%s\" at %s", id, message, new Date()));
        ObjectMapper mapper = new ObjectMapper();
        ChatDto chatDto = mapper.readValue(message, ChatDto.class);
        ChatObjectDto chatObjectDto = USERS.get(chatDto.getTo());
        if (chatObjectDto == null) {
            chatObjectDto = new ChatObjectDto();
            chatObjectDto.setSession(null);
            chatObjectDto.setMessages(new ArrayList<>());
            USERS.put(chatDto.getTo(), chatObjectDto);
            chatObjectDto.getMessages().add(chatDto);
        } else if (chatObjectDto.getSession() == null) {
            chatObjectDto.getMessages().add(chatDto);
        } else {
            String res = JsonUtils
                    .createJson()
                    .put("code", ResponseCode.SUCCESS.getCode())
                    .put("msg", ResponseCode.SUCCESS.getMsg())
                    .put("data", chatDto)
                    .returnJson();
            chatObjectDto.getSession().getAsyncRemote().sendText(res);
        }
    }

    @OnError
    public void onError(Throwable err) {
        logger.error(err.getMessage());
        err.printStackTrace();
    }

}
