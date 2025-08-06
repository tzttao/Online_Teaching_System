package com.dao;

import com.model.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteDepartmentByDid(Integer dId);

    int addDepartment(Department department);

    List<Department> selectDepartment();

    int updateDepartmentByDid(Department department);

}