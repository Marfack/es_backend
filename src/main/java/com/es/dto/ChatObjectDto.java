package com.es.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;
import java.util.List;

/**
 * Created on 2021/12/22 19:35
 *
 * @author Marfack
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatObjectDto {

    /**
     * 用户的session
     */
    private Session session;

    /**
     * 用户离线消息列表
     */
    private List<ChatDto> messages;
}
