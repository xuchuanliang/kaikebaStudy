package capter02.mybatis.Mapper;

import capter02.mybatis.bean.Dept;

import java.util.List;

public interface DeptMapper {
    void insertDept(Dept dept);

    List<Dept> findAll();
}
