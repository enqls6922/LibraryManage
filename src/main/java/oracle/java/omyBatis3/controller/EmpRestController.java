package oracle.java.omyBatis3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oracle.java.omyBatis3.controller.VO.SampleVO;
import oracle.java.omyBatis3.model.Dept;
import oracle.java.omyBatis3.service.EmpService;

//@Controller+@ResponseBody
	@RestController
	@RequestMapping("/sample")
	public class EmpRestController {
		@Autowired
		private EmpService es;

	@RequestMapping("/sendVO2")
	public SampleVO sendVO2(int deptno) {
		System.out.println("@RestController deptno->"+deptno);
		SampleVO vo = new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setMno(123);
		return vo;
		
	}
	@RequestMapping("/sendVO3")
	public List<Dept> sendVO3(){
		System.out.println("@RestController sendVO3 START");
		List<Dept> deptList = es.select();
		
		return deptList;
	}
	
}
