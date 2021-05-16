package cn.com.saint.example.config.center;

import lombok.Data;
import lombok.ToString;

/**
 * Zookeeper配置信息类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-17 6:20
 */
@Data
@ToString
public class ZkConf {

    private String address;

    private Integer sessionTime;

}
