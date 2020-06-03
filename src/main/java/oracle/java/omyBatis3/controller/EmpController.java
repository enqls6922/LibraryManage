package oracle.java.omyBatis3.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import oracle.java.omyBatis3.model.Dept;
import oracle.java.omyBatis3.model.Emp;
import oracle.java.omyBatis3.model.EmpDept;
import oracle.java.omyBatis3.service.EmpService;
import oracle.java.omyBatis3.service.Paging;

@Controller
public class EmpController {
	@Autowired
	private EmpService es;
	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping(value = "list")
	public String list(Emp emp, String currentPage, Model model) {
		System.out.println("EmpController list Start...");

		// Emp Table Count를 가져오기
		int total = es.total();
		System.out.println("EmpController list total->" + total);
		System.out.println("EmpController list currentPage->" + currentPage);

		Paging pg = new Paging(total, currentPage);
		emp.setStart(pg.getStart());
		emp.setEnd(pg.getEnd());

		List<Emp> list = es.list(emp);

		model.addAttribute("list", list);
		model.addAttribute("pg", pg);

		return "list";
	}

	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request, int empno, Model model) {
		// session test
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		System.out.println("session id->" + id);
		Emp emp = es.detail(empno);
		model.addAttribute("emp", emp);
		return "detail";
	}

	@RequestMapping(value = "updateForm")
	public String updateForm(int empno, Model model) {
		Emp emp = es.detail(empno);
		model.addAttribute("emp", emp);
		return "updateForm";

	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Emp emp, Model model) {
		int k = es.update(emp);

		return "redirect:list.do";
		// return "forward:list.do";
	}

	@RequestMapping(value = "writeForm")
	public String writeForm(Model model) {
		List<Emp> list = es.listManager();
		model.addAttribute("empMngList", list);
		List<Dept> deptList = es.select();
		model.addAttribute("deptList", deptList);
		return "writeForm";
	}

	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String write(Emp emp, Model model) {
		System.out.println("emp.getHiredate->" + emp.getHiredate());
		int result = es.insert(emp);
		if (result > 0)
			return "redirect:list.do";
		else {
			model.addAttribute("msg", "입력 실패 확인해 보세요");
			return "forward:writeForm.do";
		}
	}

	@RequestMapping(value = "delete")
	public String delete(Emp emp, Model model) {
		int k = es.delete(emp);
		return "redirect:list.do";
	}

	@RequestMapping(value = "confirm")
	public String confirm(int empno, Model model) {
		System.out.println("controller confirm start...");
		Emp emp = es.detail(empno);
		System.out.println("controller confirm es.detail...");
		model.addAttribute("empno", empno);
		if (emp != null) {
			model.addAttribute("msg", "중복된 사번입니다");
			return "forward:writeForm.do";

		} else {
			model.addAttribute("msg", "사용 가능한 사번입니다");
			return "forward:writeForm.do";
		}
	}

	@RequestMapping(value = "listEmp.do")
	public String listEmp(Model model) {
		EmpDept empDept = null;
		List<EmpDept> listEmp = es.listEmp(empDept);
		model.addAttribute("listEmp", listEmp);

		return "listEmp";
	}

	@RequestMapping(value = "mailTransport")
	public String mailTransport(HttpServletRequest request, Model model) {
		System.out.println("mailSending");
		String tomail = "hyerin3854@gmail.com"; // 받는 사람 이메일
		System.out.println(tomail);
		String setfrom = "hyerin3854@gmail.com"; // 제목
		String title = "mailTransport입니다";
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom);
			messageHelper.setTo(tomail);
			messageHelper.setSubject(title);
			String tempPassword = (int) (Math.random() * 999999) + 1 + "";
			messageHelper.setText("테스트 이메일"); // 메일 내용
			System.out.println("임시비밀번호입니다 :" + tempPassword);
			DataSource dataSource = new FileDataSource("c:\\log\\mimi.gif");
			messageHelper.addAttachment(MimeUtility.encodeText("airport.png", "UTF-8", "B"), dataSource);
			mailSender.send(message);
			model.addAttribute("check", 1); // 정상 전달
			// s.tempPw(u_id, tempPassword); //db에 비밀번호를 임시비밀번호로 업데이트
		} catch (Exception e) {
			System.out.println(e);
			model.addAttribute("check", 2);// 메일 전달 실패
		}
		return "mailResult";
	}

	@RequestMapping(value = "listEmpAjax")
	public String listEmpAjax(Model model) {
		EmpDept empDept = null;
		System.out.println("Ajax list Test Start");
		List<EmpDept> listEmp = es.listEmp(empDept);
		model.addAttribute("result", "kkk");
		model.addAttribute("listEmp", listEmp);
		return "listEmpAjax";
	}

	// Ajax Test
	@RequestMapping(value = "getDeptName", produces = "application/text;charset=UTF-8")
	@ResponseBody
	public String getDeptName(int deptno, Model model) {
		System.out.println("deptno->" + deptno);
		return es.deptName(deptno);
	}

	// Ajax  List Test
	@RequestMapping(value="listEmpAjax2")
	public String listEmpAjax2(Model model) {
		EmpDept empDept = null;
		System.out.println("Ajax  List Test Start");
		List<EmpDept> listEmp = es.listEmp(empDept);
		model.addAttribute("result","kkk");
		model.addAttribute("listEmp",listEmp);
		return "listEmpAjax2";
	}
	

}
