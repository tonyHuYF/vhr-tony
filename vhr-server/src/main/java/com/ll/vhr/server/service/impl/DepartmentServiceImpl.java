package com.ll.vhr.server.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ll.vhr.server.domain.CommonException;
import com.ll.vhr.server.domain.Department;
import com.ll.vhr.server.domain.Employee;
import com.ll.vhr.server.domain.Error;
import com.ll.vhr.server.mapper.DepartmentMapper;
import com.ll.vhr.server.service.DepartmentService;
import com.ll.vhr.server.service.EmployeeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private EmployeeService employeeService;

    @Override
    @Cacheable(value = "department", key = "'getAllDepartments'")
    public List<Department> getAllDepartments() {
        List<Department> departments = getAllDepartmentsWithOutChildren();

        //补全子菜单
        List<Department> list = departments.stream().filter(p -> p.getParentId() == 0)
                .peek(p -> p.setChildren(getChildDepartment(p, departments)))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    @Transactional
    @CacheEvict(value = "department", allEntries = true)
    public void addDep(Department dep) {
        //将父节点的 is_parent改为 1
        Department parentDept = departmentMapper.selectById(dep.getParentId());
        parentDept.setIsParent(1);
        departmentMapper.updateById(parentDept);

        dep.setEnabled(1);
        dep.setIsParent(0);
        departmentMapper.insert(dep);

        Department department = departmentMapper.selectById(dep.getId());
        department.setDepPath(parentDept.getDepPath() + "." + department.getId());
        departmentMapper.updateById(department);
    }

    @Override
    @Transactional
    @CacheEvict(value = "department", allEntries = true)
    public void deleteDepById(Integer id) {
        Department department = departmentMapper.selectById(id);

        if (department.getIsParent() == 1) {
            throw new CommonException(Error.exists_child_department_error);
        }

        //查询 employee ,是否存在员工
        List<Employee> employee = employeeService.getEmployeeByDeptId(id);
        if (ObjectUtil.isNotEmpty(employee)) {
            throw new CommonException(Error.exists_employee_error);
        }

        //查看父节点是否仅有当前子节点，是的话，要将其is_parent改为0
        Long count = departmentMapper.selectCount(
                new LambdaQueryWrapper<Department>().eq(Department::getParentId, department.getParentId()));

        if (count.intValue() == 1) {
            Department parentDept = departmentMapper.selectById(department.getParentId());
            parentDept.setIsParent(0);
            departmentMapper.updateById(parentDept);
        }

        departmentMapper.deleteById(department);
    }

    @Override
    @Cacheable(value = "department", key = "'getAllDepartmentsWithOutChildren'")
    public List<Department> getAllDepartmentsWithOutChildren() {
        List<Department> departments = departmentMapper.selectList(null);
        return departments;
    }

    private List<Department> getChildDepartment(Department dept, List<Department> list) {
        List<Department> child = list.stream().filter(p -> p.getParentId().intValue() == dept.getId().intValue())
                .peek(d -> d.setChildren(getChildDepartment(d, list)))
                .collect(Collectors.toList());
        return child;
    }
}
