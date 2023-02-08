package com.ll.vhr.server.service;

import com.ll.vhr.server.domain.Department;

import java.util.List;

public interface DepartmentService {
    public List<Department> getAllDepartments();

    public void addDep(Department dep);

    public void deleteDepById(Department dep);

    public List<Department> getAllDepartmentsWithOutChildren();
}
