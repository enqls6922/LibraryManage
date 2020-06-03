package oracle.java.omyBatis3.dao;

import java.util.List;

import oracle.java.omyBatis3.model.Dept;
import oracle.java.omyBatis3.model.Emp;
import oracle.java.omyBatis3.model.EmpDept;

public interface EmpDao {
   List<Emp> list(Emp emp);

int total();

Emp detail(int empno);
int update(Emp emp);

List<Emp> listManager();

int insert(Emp emp);

int delete(Emp emp);

List<EmpDept> listEmp(EmpDept empDept);

String deptName(int deptNo);


}

