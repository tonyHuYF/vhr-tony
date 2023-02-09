package com.ll.vhr.server.controller.system.basic;

import com.ll.vhr.server.domain.Department;
import com.ll.vhr.server.domain.ResultBean;
import com.ll.vhr.server.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @GetMapping
    public ResultBean<List<Department>> getAllDepartments() {
        List<Department> allDepartments = departmentService.getAllDepartments();
        return new ResultBean<>(allDepartments);
    }


    @PostMapping
    public ResultBean<Void> addDep(@RequestBody Department department) {
        departmentService.addDep(department);
        return new ResultBean<>();
    }

    @DeleteMapping("/{id}")
    public ResultBean<Void> deleteDepById(@PathVariable Integer id) {
        departmentService.deleteDepById(id);
        return new ResultBean<>();
    }

}
