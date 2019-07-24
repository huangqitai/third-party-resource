package web;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import entity.User;

@Controller
public class ActionServlet{
	
	@RequestMapping("login")
	public void login(HttpServletRequest request ,
			HttpServletResponse response) throws IOException, ServletException {
		
		User user = new User("admin", "123abc");
		String userName = request.getParameter("username");
		String passWord = request.getParameter("PW");
		String contextPath = request.getContextPath();
		HttpSession loginSession = request.getSession();
		
		if((!userName.equals(user.getUserName()))||(!passWord.equals(user.getPassWord()))) {
			loginSession.setAttribute("user_not_found", "用户账号或密码错误！");
			System.out.println(user.getUserName()+user.getPassWord());
			System.out.println(userName+passWord);
			
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		else {
			response.sendRedirect(contextPath+"/main.action");
			loginSession.setAttribute("user", user);
		}
	}
	
	@RequestMapping("main")
	public String toMain() {
		return "main";
	}
	
	@RequestMapping("loginOut")
	public void loginOut(HttpServletRequest request ,
			HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	
	@RequestMapping("downloadFile")
		public ResponseEntity<byte[]> download(HttpServletRequest request,
			       Model model)throws Exception {
			String filename = request.getParameter("fileName");
			    //下载文件路径
			    String path = request.getServletContext().getRealPath("/WEB-INF/file/");
			    File file = new File(path + File.separator + "download");
			    HttpHeaders headers = new HttpHeaders(); 
			    //下载显示的文件名，解决中文名称乱码问题 
			    String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
			    //通知浏览器以attachment（下载方式）打开图片
			    headers.setContentDispositionFormData("attachment", downloadFielName); 
			    //application/octet-stream ： 二进制流数据（最常见的文件下载）。
			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),  
			        headers, HttpStatus.CREATED); 
			   }
}



























