package com.ll.vhr.server.controller.system.basic;

import com.ll.vhr.server.domain.Department;
import com.ll.vhr.server.domain.ResultBean;
import com.ll.vhr.server.mapper.DepartmentMapper;
import com.ll.vhr.server.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;
    @Resource
    private DepartmentMapper departmentMapper;

    @GetMapping("test")
    public ResultBean test(){
//        Department department = departmentMapper.selectById(200);
//        departmentService.deleteDepById(department);


        Department department = new Department();
        department.setName("部门测试111");
        department.setParentId(199);
        departmentService.addDep(department);

        return new ResultBean();
    }
}
