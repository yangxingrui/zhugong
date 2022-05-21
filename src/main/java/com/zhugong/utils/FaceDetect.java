package com.zhugong.utils;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FaceDetect {
    //设置APPID/AK/SK
    public static final String APP_ID = "25958867";
    public static final String API_KEY = "gxpPIfoleejxZZlB7X4RkTHm";
    public static final String SECRET_KEY = "q54aeekAF4vSfyOx7qfGIdpUgkFvryuF";


    public Double detect(String img1,String img2) {
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        // image1/image2也可以为url或facetoken, 相应的imageType参数需要与之对应。
        MatchRequest req1 = new MatchRequest(img1, "BASE64");
        MatchRequest req2 = new MatchRequest(img2, "BASE64");
        ArrayList<MatchRequest> requests = new ArrayList<>();
        requests.add(req1);
        requests.add(req2);

        //调用百度人脸比对关键
        JSONObject res = client.match(requests);
        String sres = res.toString(2);
        //创建JSON解析器GSON
        JsonParser parse=new JsonParser();
        //将String转成json
        JsonObject json=(JsonObject) parse.parse(sres);
        //如果result为空，标示图片中没有人脸
        JsonObject  result= json.get("result").getAsJsonObject();

        String score = result.get("score").getAsString();
        return Double.valueOf(score);
    }
}
