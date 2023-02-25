package com.ll.vhr.server.service;

import com.ll.vhr.server.domain.Hr;

import java.util.Map;

public interface LoginService {
    public Map<String,String> login(Hr hr);

    public void logout();
}
