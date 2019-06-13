package mybatis.Mapper;

import mybatis.bean.Dept;

import java.util.List;

public interface DeptMapper {
    void insertDept(Dept dept);
    List<Dept> findAll();
}
