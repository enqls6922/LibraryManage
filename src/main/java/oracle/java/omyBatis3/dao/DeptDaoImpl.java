package oracle.java.omyBatis3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oracle.java.omyBatis3.model.Dept;

@Repository // dao가 바뀜
public class DeptDaoImpl implements DeptDao {

	@Autowired
	private SqlSession session;

	public List<Dept> deptSelect() {
		return session.selectList("TKselectDept");
	}

}
