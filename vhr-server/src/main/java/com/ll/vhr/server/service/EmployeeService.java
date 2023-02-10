package com.ll.vhr.server.service;

import com.ll.vhr.server.domain.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getEmployeeByDeptId(Integer deptId);
}
