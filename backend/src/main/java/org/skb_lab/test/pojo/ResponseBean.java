package org.skb_lab.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResponseBean {
    private int code;
    private String message;

    public static ResponseBean getDefaultResponse() {
        return new ResponseBean(201, "Request accepted, check your email!");
    }
}
