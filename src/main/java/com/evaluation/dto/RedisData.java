package com.evaluation.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: RedisData
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/9/26 16:36
 * {@code @Version}  1.0
 */
@Data
public class RedisData {
    private LocalDateTime expireTime;
    private Object data;
}
