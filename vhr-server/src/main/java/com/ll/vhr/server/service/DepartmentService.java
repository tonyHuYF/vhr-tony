package com.ll.vhr.server.service;

import com.ll.vhr.server.domain.Department;

import java.util.List;

public interface DepartmentService {
    public List<Department> getAllDepartments();

    public void addDep(Department dep);

    public void deleteDepById(Integer id);

    public List<Department> getAllDepartmentsWithOutChildren();
}
