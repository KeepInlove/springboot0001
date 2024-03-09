package com.example.springboot.utils;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class IpUtils {

    private static final Dict ipMap = Dict.create();

    private static String getIP() throws IOException {
        Document document = Jsoup.connect("https://ip.900cha.com/").get();
        // /html/body/div/div/div/div[1]/div[1]/h3
        return document.selectXpath("/html/body/div/div/div/div[1]/div[1]/h3").get(0).text().trim();
    }

    /**
     * {
     * "address": "CN|安徽省|合肥市|None|None|100|100",
     * "content": {
     * "address_detail": {
     * "province": "安徽省",
     * "city": "合肥市",
     * "district": "",
     * "street": "",
     * "street_number": "",
     * "city_code": 127,
     * "adcode": "340100"
     * },
     * "address": "安徽省合肥市",
     * "point": {
     * "x": "13050509.14",
     * "y": "3717827.44"
     * }
     * },
     * "status": 0
     * }
     */
    public static String getLocation(String ip) {
        if (ip == null) {
            try {
                ip = getIP();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String url = "https://api.map.baidu.com/location/ip?ip=" + ip + "&ak=bmvg8yeOopwOB4aHl5uvx52rgIa3VrPO";
        String res = HttpUtil.createRequest(Method.GET, url).execute().body();
        String json = UnicodeUtil.toString(res);
        return JSONUtil.parseObj(json).getStr("address");
    }

}
