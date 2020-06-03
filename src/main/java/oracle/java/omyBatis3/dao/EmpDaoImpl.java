package oracle.java.omyBatis3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oracle.java.omyBatis3.model.Emp;
import oracle.java.omyBatis3.model.EmpDept;

@Repository
public class EmpDaoImpl implements EmpDao {
	@Autowired
	private SqlSession session;

	@Override
	public List<Emp> list(Emp emp) {
		// TODO Auto-generated method stub
		return session.selectList("listAll", emp); //listAll: MAP 아이디
	}

	@Override
	public int total() {
		System.out.println("EmpDaoImpl total...");
		return session.selectOne("total");
	}

	@Override
	public Emp detail(int empno) {
		System.out.println("EmpDaoImpl detail...");
		// TODO Auto-generated method stub
		return session.selectOne("detail", empno);
	}

	@Override
	public int update(Emp emp) {
		// TODO Auto-generated method stub
		System.out.println("EmpDaoImpl update empno->"+emp.getEmpno());
		return session.update("TKempUpdate", emp); //dao에서 mapping id에 emp를 담아줌
	}

	@Override
	//파라미터 없음
	public List<Emp> listManager() {
		System.out.println("EmpDaoImpl listManager()...");
		
		return session.selectList("selectManager");//Mapping Id만 지정
	}

	@Override
	public int insert(Emp emp) {
		return session.insert("insert",emp);
	}

	@Override
	public int delete(Emp emp) {
		return session.delete("delete",emp);
	}

	@Override
	public List<EmpDept> listEmp(EmpDept empDept) {
		// TODO Auto-generated method stub
		return session.selectList("HYlistEmp",empDept);
	}

	@Override
	public String deptName(int deptNo) {
		// TODO Auto-generated method stub
		return session.selectOne("HYdeptName",deptNo);
	}

}
