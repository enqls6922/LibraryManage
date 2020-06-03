package oracle.java.omyBatis3.dao;

import java.util.List;

import oracle.java.omyBatis3.model.Dept;

public interface DeptDao {

	List<Dept> deptSelect();
//void insertDept(DeptVO deptVO);    //Procedure VO
//void SelListDept(Map<String,Object> map);  //Procedure Cursor
	
}
