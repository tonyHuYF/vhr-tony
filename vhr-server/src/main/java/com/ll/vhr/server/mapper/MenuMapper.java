package com.ll.vhr.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ll.vhr.server.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    public List<Menu> getMenusByHrId(Integer hrid);

    public List<Integer> getMidsByRid(Integer rid);

    public void deleteByRid(Integer rid);

    public Integer insertRecord(@Param("rid") Integer rid,@Param("mids") Integer[] mids);
}
