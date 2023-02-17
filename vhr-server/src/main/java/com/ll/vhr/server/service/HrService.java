package com.ll.vhr.server.service;

import com.ll.vhr.server.domain.Hr;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface HrService extends UserDetailsService {
    public List<Hr> getAllHrs(String keywords);

    public Integer updateHr(Hr hr);

    public boolean updateHrRole(Integer hid, Integer[] rids);

    public Integer deleteHrById(Integer id);

    public List<Hr> getAllHrsExceptCurrentHr();

    public boolean updateHrPwd(String oldPwd, String pwd, Integer hrId);

    public Integer updateUserface(String url, Integer id);


}
