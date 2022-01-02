package com.es.dto;

import lombok.Data;

import java.util.List;

/**
 * Created on 2021/12/22 19:28
 *
 * @author Marfack
 */
@Data
public class ChatDto {

    /**
     * 发送方id
     */
    private long from;

    /**
     * 发送到账号id
     */
    private long to;

    /**
     * 消息
     */
    private List<String> message;
}
