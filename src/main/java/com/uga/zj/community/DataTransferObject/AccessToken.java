package com.uga.zj.community.DataTransferObject;

import lombok.Data;

@Data
public class AccessToken {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
