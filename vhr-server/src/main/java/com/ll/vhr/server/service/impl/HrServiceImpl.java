package com.ll.vhr.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ll.vhr.server.domain.CommonException;
import com.ll.vhr.server.domain.Error;
import com.ll.vhr.server.domain.Hr;
import com.ll.vhr.server.domain.Role;
import com.ll.vhr.server.domain.dto.HrRoleRel;
import com.ll.vhr.server.mapper.HrMapper;
import com.ll.vhr.server.service.HrService;
import com.ll.vhr.server.service.MenuService;
import com.ll.vhr.server.service.RoleService;
import com.ll.vhr.server.util.HrUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HrServiceImpl implements HrService {

    @Resource
    private HrMapper hrMapper;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.selectOne(new LambdaQueryWrapper<Hr>().eq(Hr::getUsername, username));
        if (ObjectUtil.isEmpty(hr)) {
            throw new CommonException(Error.username_not_exist);
        }
        List<Role> roles = hrMapper.getHrRolesById(hr.getId());

        if (ObjectUtil.isNotEmpty(roles)) {
            hr.setRoles(roles);
            List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
            hr.setMenus(menuService.getMenusByRoleIds(roleIds.toArray(new Integer[0])));
        }

        return hr;
    }

    @Override
    public List<Hr> getAllHrs(String keywords) {
        List<Hr> hrs = hrMapper.selectList(new LambdaQueryWrapper<Hr>()
                .like(StrUtil.isNotBlank(keywords), Hr::getName, keywords));

        if (ObjectUtil.isNotEmpty(hrs)) {
            //补充 role
            List<HrRoleRel> allHrRoleRel = roleService.getAllHrRoleRel();
            Map<Integer, List<HrRoleRel>> roleMap = allHrRoleRel.stream().collect(Collectors.groupingBy(HrRoleRel::getHrid));

            hrs.forEach(p -> {
                List<HrRoleRel> hrRoles = roleMap.get(p.getId());
                if (ObjectUtil.isNotEmpty(hrRoles)) {
                    p.setRoles(BeanUtil.copyToList(hrRoles, Role.class));
                }
            });
        }

        return hrs;
    }

    @Override
    @CacheEvict(value = "hr", allEntries = true)
    public Integer updateHr(Hr hr) {
        return hrMapper.updateById(hr);
    }

    @Override
    @Transactional
    @CacheEvict(value = "hr", allEntries = true)
    public boolean updateHrRole(Integer hid, Integer[] rids) {
        hrMapper.deleteByHrid(hid);
        Integer record = hrMapper.insertRecord(hid, rids);
        return record == rids.length;
    }

    @Override
    @CacheEvict(value = "hr", allEntries = true)
    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteById(id);
    }

    @Override
    @Cacheable(value = "hr", key = "'getAllHrsExceptCurrentHr'")
    public List<Hr> getAllHrsExceptCurrentHr() {
        List<Hr> hrs = hrMapper.selectList(new LambdaQueryWrapper<Hr>().ne(Hr::getId, HrUtil.getCurrentHr().getId()));
        return hrs;
    }


    @Override
    @CacheEvict(value = "hr", allEntries = true)
    public boolean updateHrPwd(String oldPwd, String pwd, Integer hrId) {
        Hr hr = hrMapper.selectById(hrId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldPwd, hr.getPassword())) {
            String encodePwd = encoder.encode(pwd);
            hr.setPassword(encodePwd);
            int record = hrMapper.updateById(hr);
            return record == 1;
        }
        return false;
    }

    @Override
    @CacheEvict(value = "hr", allEntries = true)
    public Integer updateUserface(String url, Integer id) {
        Hr hr = hrMapper.selectById(id);
        hr.setUserface(url);
        return hrMapper.updateById(hr);
    }

}
