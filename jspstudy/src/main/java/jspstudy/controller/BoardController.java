package jspstudy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import jspstudy.domain.BoardVo;
import jspstudy.domain.PageMaker;
import jspstudy.domain.SearchCriteria;
import jspstudy.service.BoardDao;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardController() {
        super();
        // TODO Auto-generated constructor stub
    }
    //BIDX,SUBJ,CONTENT,WRITER,WRITEDAY,IP,DELYN,MIDX,ORIGINBIDX,DEPTH,LEVEL_
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//�����η� �Ѿ�� request�� ������ ó��
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		int sizeLimit = 1024*1024*15;
		String uploadPath = "D:\\openApi(B)\\dev\\jspstudy\\jspstudy\\src\\main\\webapp";
		String saveFolder = "\\images";
		String saveFullPath = uploadPath + saveFolder;
		
		String uri = request.getRequestURI();
		String pj = request.getContextPath();
		String command = uri.substring(pj.length());
		
		if(command.equals("/board/boardWrite.do")) {
			System.out.println("�۾��� ȭ�鿡 ����");
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardWrite.jsp");
			
			rd.forward(request, response);
		}
		
		else if(command.equals("/board/boardWriteAction.do")) {
			System.out.println("�۾��� ó��ȭ������ ������");
			
			MultipartRequest multipartRequest = null;
			multipartRequest = new MultipartRequest(request, saveFullPath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
			
			String subject = multipartRequest.getParameter("subject");
			String content = multipartRequest.getParameter("content");
			String writer = multipartRequest.getParameter("writer");
			
			//�������� ����� ������ ��� ��ü ����
			@SuppressWarnings("rawtypes")
			Enumeration files = multipartRequest.getFileNames();
			
			//��� ��ü�� ���� �̸��� ��´�.
			String file = (String)files.nextElement();
			
			//�Ѿ���� ��ü �� �ش�Ǵ� �����̸����� �Ǿ� �ִ� �����̸��� �����Ѵ�.(����Ǵ� ���� �̸�)
			String fileName = multipartRequest.getFilesystemName(file);
			
			//���� ���� �̸�
			@SuppressWarnings("unused")
			String originFileName = multipartRequest.getOriginalFileName(file);
			
			//System.out.println(subject + ":" + content + ":" + writer);
			
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			//int midx = 2;
			HttpSession session = request.getSession();
			int midx = (int)session.getAttribute("midx");
			
			BoardDao bd = new BoardDao();
			int value = bd.insertBoard(subject, content, writer, ip, midx, fileName);
			
			if(value == 1) {
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			else {
				response.sendRedirect(request.getContextPath()+"/board/boardWrite.do");

			}
			
		}
		else if(command.equals("/board/boardList.do")) {
			
			String page = request.getParameter("page");
			if(page == null) page = "1";
			int pageX = Integer.parseInt(page);
			
			String keyword = request.getParameter("keyword");
			String searchType = request.getParameter("searchType");
			if(keyword == null) {
				keyword = "";
			}
			if(searchType == null) {
				searchType = "subject";
			}
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pageX);
			scri.setSearchType(searchType);
			scri.setKeyword(keyword);
			
			
			BoardDao bd = new BoardDao();
			int cnt = bd.boardTotal(scri);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			pm.setTotalCount(cnt);
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(scri);
			request.setAttribute("alist", alist);
			
			request.setAttribute("pm", pm);
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardList.jsp");
			rd.forward(request, response);
		}
		
		else if(command.equals("/board/boardContent.do")) {
			
			String bidx = request.getParameter("bidx");
			int bidx_ = Integer.parseInt(bidx);
			
			request.setCharacterEncoding("utf-8");

			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_);
			request.setAttribute("bv", bv);
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardContent.jsp");
			rd.forward(request, response);
			
		}
		
		else if(command.equals("/board/boardModify.do")) {
			
			String bidx = request.getParameter("bidx");
			int bidx_ = Integer.parseInt(bidx);
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_);
			request.setAttribute("bv", bv);
			/*
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			String bidx = request.getParameter("bidx");
			int bidx_ = Integer.parseInt(bidx);*/
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardModify.jsp");
			rd.forward(request, response);
		}
		
		else if(command.equals("/board/boardModifyAction.do")) {
			
			System.out.println("����ó��ȭ�����");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			String bidx = request.getParameter("bidx");
			System.out.println("bidx"+bidx);
			int bidx_ = Integer.parseInt(bidx);
	
			
			BoardDao bd = new BoardDao();
			int value = bd.updateBoard(subject, content, writer, bidx_);
			
			System.out.println("value"+value);
			
			if(value == 1) {
				response.sendRedirect(request.getContextPath()+"/board/boardList.do");
				
			}
			else {
				response.sendRedirect(request.getContextPath()+"/board/boardModify.do?bidx="+bidx);
			}
		}
		
		else if(command.equals("/board/boardDelete.do")) {
			
			String bidx = request.getParameter("bidx");
			int bidx_ = Integer.parseInt(bidx);
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_);
			request.setAttribute("bv", bv);
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardDelete.jsp");
			rd.forward(request, response);
		}
		
		else if(command.equals("/board/boardDeleteAction.do")) {
			String bidx = request.getParameter("bidx");
			int bidx_ = Integer.parseInt(bidx);
			
			BoardDao bd = new BoardDao();
			int value = bd.deleteBoard(bidx_);
			
			if(value == 1) {
				response.sendRedirect(request.getContextPath()+"/board/boardList.do");
				
			}
			else {
				response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx);
			}
		}
		
		else if(command.equals("/board/boardReply.do")) {
			
			String bidx = request.getParameter("bidx");
			String originbidx = request.getParameter("originbidx");
			String level_ = request.getParameter("level_");
			String depth = request.getParameter("depth");
			
			BoardVo bv = new BoardVo();

			bv.setBidx(Integer.parseInt(bidx));
			bv.setOriginbidx(Integer.parseInt(originbidx));
			bv.setLevel_(Integer.parseInt(level_));
			bv.setDepth(Integer.parseInt(depth));
			
			request.setAttribute("bv", bv);
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardReply.jsp");
			rd.forward(request, response);
			
		}
		else if(command.equals("/board/boardReplyAction.do")) {
			
			String bidx = request.getParameter("bidx");
			String originbidx = request.getParameter("originbidx");
			String level_ = request.getParameter("level_");
			String depth = request.getParameter("depth");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			HttpSession session = request.getSession();
			int midx = (int)session.getAttribute("midx");
			
			BoardVo bv = new BoardVo();
			bv.setBidx(Integer.parseInt(bidx));
			bv.setOriginbidx(Integer.parseInt(originbidx));
			bv.setLevel_(Integer.parseInt(level_));
			bv.setDepth(Integer.parseInt(depth));
			bv.setSubject(subject);
			bv.setContent(content);
			bv.setWriter(writer);
			bv.setIp(ip);
			bv.setMidx(midx);

			BoardDao bd = new BoardDao();
			int value = bd.replyBoard(bv);
			
			if(value == 1) {
				response.sendRedirect(request.getContextPath()+"/board/boardList.do");
				
			}
			else {
				response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx);
			}
			
		}
		else if(command.equals("/board/fileDownload.do")) {
			
			String filename = request.getParameter("filename");
			//������ ��ü ���
			String filePath = saveFullPath + File.separator + filename;
			Path source = Paths.get(filePath);
			
			String mimeType = Files.probeContentType(source);
			
			//����� ����Ÿ���� ��Ƽ� �ѱ� �ش� ������ �������� �νĽ��� �ٿ�ޱ� ���� ���
			response.setContentType(mimeType);
			
			//���� ������ ��� ������ ��´�
			String encodingFileName = new String(filename.getBytes("utf-8"));
			
			//÷���ؼ� �ٿ�Ǵ� ������ ��� ������ ��´�.
			response.setHeader("Content-Disposition", "attachment;fileName = "+encodingFileName);
			
			//�ش� ��ġ�� �ִ� ������ �о����
			FileInputStream fileInputStream = new FileInputStream(filePath);
			
			//������ ���� ���� ��Ʈ��
			ServletOutputStream servletOutputStream = response.getOutputStream();
			
			byte[] b = new byte[4096];
			
			int read = 0;
			
			while((read = fileInputStream.read(b, 0, b.length)) != -1) {
				//���� ����
				servletOutputStream.write(b, 0, read);
				
			}
			//���� ���
			servletOutputStream.flush();
			servletOutputStream.close();
			fileInputStream.close();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
