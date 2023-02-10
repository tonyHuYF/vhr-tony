package com.ll.vhr.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ll.vhr.server.domain.Employee;
import com.ll.vhr.server.mapper.EmployeeMapper;
import com.ll.vhr.server.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> getEmployeeByDeptId(Integer deptId) {
        List<Employee> employees = employeeMapper.selectList(new LambdaQueryWrapper<Employee>().eq(Employee::getDepartmentId, deptId));
        return employees;
    }
}
