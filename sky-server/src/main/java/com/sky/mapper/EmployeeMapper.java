package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Insert("INSERT INTO employee (id, name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user)" +
            "VALUES " +
            "(#{id},#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    List<Employee> list(String name);
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    @Select("SELECT * from employee where id = #{id}")
    Employee getById(Long id);
}
