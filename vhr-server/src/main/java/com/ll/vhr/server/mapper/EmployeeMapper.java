package com.ll.vhr.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ll.vhr.server.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
