package com.ll.vhr.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ll.vhr.server.domain.Department;
import com.ll.vhr.server.mapper.DepartmentMapper;
import com.ll.vhr.server.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        return null;
    }

    @Override
    @Transactional
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
    public void deleteDepById(Department dep) {
        //查看父节点是否仅有当前子节点，是的话，要将其is_parent改为0
        Long count = departmentMapper.selectCount(
                new LambdaQueryWrapper<Department>().eq(Department::getParentId, dep.getParentId()));

        if (count.intValue() == 1) {
            Department parentDept = departmentMapper.selectById(dep.getParentId());
            parentDept.setIsParent(0);
            departmentMapper.updateById(parentDept);
        }

        //不仅删除自身，还要删除自身下所有关联的部门
        List<Department> departments = departmentMapper.selectList(
                new LambdaQueryWrapper<Department>().like(Department::getDepPath, dep.getId()));
        List<Integer> ids = departments.stream().map(Department::getId).collect(Collectors.toList());
        ids.add(dep.getId());
        departmentMapper.delete(new LambdaQueryWrapper<Department>().in(Department::getId, ids));
    }

    @Override
    public List<Department> getAllDepartmentsWithOutChildren() {
        return null;
    }
}
