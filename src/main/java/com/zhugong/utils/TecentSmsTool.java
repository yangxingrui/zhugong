package com.zhugong.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

public class TecentSmsTool {
    public void main(String phone,String text) {

        //调用腾讯云API发送短信验证码
        int appid = 1400640876;
        String appkey = "44fd2727ecb1180ad51cb0bce7d40038";
        String smsSign = "YoungStarUI";
        int templateid = 1409521;
        int min=1;

        try{
            String[] params = {text,Integer.toString(min)};
            SmsSingleSender smssender = new SmsSingleSender(appid,appkey);
            SmsSingleSenderResult smsresult = smssender.sendWithParam("86",
                    phone,templateid,params,smsSign,"","");
        }catch( JSONException | HTTPException | IOException e){
            e.printStackTrace();
        }
    }
}
