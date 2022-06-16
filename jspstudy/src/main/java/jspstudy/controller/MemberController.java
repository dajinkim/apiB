package jspstudy.controller;

import java.io.IOException;
//import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jspstudy.domain.MemberVo;
import jspstudy.service.MemberDao;

@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		String uri = request.getRequestURI();
		String pj = request.getContextPath();
		String command = uri.substring(pj.length());

		//System.out.println("command : " + command);

		if (command.equals("/member/memberJoinAction.do")) {

			request.setCharacterEncoding("UTF-8");
			String memberId = request.getParameter("memberId");
			String memberPwd = request.getParameter("memberPwd");
			String memberName = request.getParameter("memberName");
			String memberGender = request.getParameter("memberGender");
			String memberAddr = request.getParameter("memberAddr");
			String memberphone = request.getParameter("memberphone");
			String memberJumin = request.getParameter("memberJumin");
			String memberEmail = request.getParameter("memberEmail");

			String memberHobby[] = request.getParameterValues("memberHobby");

			String hobby = "";

			String ip = InetAddress.getLocalHost().getHostAddress();

			for (int i = 0; i < memberHobby.length; i++) {
				hobby = hobby + "," + memberHobby[i];
			}

			hobby = hobby.substring(1);

			MemberDao md = new MemberDao();
			
			int value = md.insertMember(memberId, memberPwd, memberName, memberGender, memberAddr, memberJumin,
					memberphone, hobby, memberEmail, ip);
			System.out.println(value);
			//PrintWriter out = response.getWriter();
			
			if (value == 1) {
				response.sendRedirect(request.getContextPath()+"/member/memberList.do");
				//out.println("<script>alert('회원 가입 성공'); location.href='"+request.getContextPath()+"/'</script>");
			} else {
				response.sendRedirect(request.getContextPath()+"/member/memberJoin.do");
				//out.println("<script>alert('회원 가입 실패'); location.href='./memberJoin.html'</script>");
			}
		}
		/*model1
		vo, dao를 클래스로 만듬

		mvc - model2
		controller jsp를 만듬 - servlet
		page이동


		forward
		client가 경로 클릭시 가상경로로 들어감
		가상경로를 포워드시켜 실 페이지를 보여줌
		내부 주소와 보이는 주소가 다름
		가상경로지만 이벤트 발생 시 내부 경로와 똑같이 이행함.
		setAttribute(name,value)를 이용해 controller가 view에 정보를 공유함

		sendredirect
		모든 처리가 끝난 후 새로 페이지를 이동할 때
		response.sendRedirect("url");*/
		
		else if(command.equals("/member/memberJoin.do")) {
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberJoin.jsp");
			rd.forward(request, response);
		}
		else if(command.equals("/member/memberList.do")) {
			
			MemberDao md = new MemberDao();
			ArrayList<MemberVo> alist = md.memberSelectAll();
			request.setAttribute("alist", alist);
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberList.jsp");
			rd.forward(request, response);
		}
		else if(command.equals("/member/memberLogin.do")) {
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberLogin.jsp");
			rd.forward(request, response);
		}
		else if(command.equals("/member/memberLoginAction.do")) {
			
			String memberId = request.getParameter("memberId");
			String memberPwd = request.getParameter("memberPwd");
			
			MemberDao md = new MemberDao();
			MemberVo mv = md.memberLogin(memberId, memberPwd);
			System.out.println("mv"+mv);
			
			HttpSession session = request.getSession();
			//session 변수
			if(mv != null) {
				session.setAttribute("midx", mv.getMidx());
				session.setAttribute("memberId", mv.getMemberid());
				session.setAttribute("memberName", mv.getMembername());
				
				if(session.getAttribute("saveUrl") != null) {
					response.sendRedirect((String)session.getAttribute("saveUrl"));
				}
				else {
					response.sendRedirect(request.getContextPath()+"/member/memberList.do");
				}
			}
			else {
				response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
			}
		}
		else if(command.equals("/member/memberLogoutAction.do")) {
			HttpSession session = request.getSession();
			session.invalidate();//session 초기화
			response.sendRedirect(request.getContextPath()+"/");
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
