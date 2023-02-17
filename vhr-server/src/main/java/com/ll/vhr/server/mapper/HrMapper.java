package com.ll.vhr.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ll.vhr.server.domain.Hr;
import com.ll.vhr.server.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HrMapper extends BaseMapper<Hr> {

    public List<Role> getHrRolesById(Integer id);

    public void deleteByHrid(Integer id);

    public Integer insertRecord(@Param("hrid") Integer hrid, @Param("rids") Integer[] rids);


}
