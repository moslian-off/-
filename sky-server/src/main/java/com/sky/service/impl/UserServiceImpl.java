package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    UserMapper userMapper;

    @Autowired
    WeChatProperties weChatProperties;

    @Autowired
    JwtProperties jwtProperties;

    @Override
    public UserLoginVO wxLogin(UserLoginDTO userLoginDTO) {
        String openId = getOpenId(userLoginDTO);

        if (openId == null || openId.isEmpty()) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getByOpenId(openId);
        if (user == null) {
            user = User.builder().openid(openId).createTime(LocalDateTime.now()).build();
            userMapper.insert(user);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        return new UserLoginVO(user.getId(), user.getOpenid(), token);
    }

    private String getOpenId(UserLoginDTO userLoginDTO) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", weChatProperties.getAppid());
        paramMap.put("secret", weChatProperties.getSecret());
        paramMap.put("js_code", userLoginDTO.getCode());
        paramMap.put("grant_type", "authorization_code");

        String res = HttpClientUtil.doGet(WX_LOGIN, paramMap);
        Gson gson = new Gson();
        JSONObject json = gson.fromJson(res, JSONObject.class);
        return json.getString("openid");

    }
}
