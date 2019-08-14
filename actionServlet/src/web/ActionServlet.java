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
    public ResponseEntity<byte[]> DownloadFile(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
         //  接受的是UTF-8
       req.setCharacterEncoding("utf-8");
       //获取项目根目录	我试过用来下载pdf、jpg、txt格式的文件，都可以。
       String path="D:\\InstallPackage\\apache-tomcat-9.0.22-windows-x64\\apache-tomcat-9.0.22\\wtpwebapps\\actionServlet\\WEB-INF\\file\\readme.txt";
       //获取文件名
       String filename="readme.txt";
        File file = null;
       HttpHeaders headers =null;
       try {
          System.out.println(filename);//看看文件名对不对
          file =new File(path);
          //请求头
          //解决文件名乱码，gb2312不行的时候再试试utf-8
          String fileName1 =new String(filename.getBytes("gb2312"),"iso-8859-1");
          headers =new HttpHeaders();
          //通知浏览器以attachment（下载方式）打开文件，我也不知道这种方式是什么
          headers.setContentDispositionFormData("attachment",fileName1);
          //application/octet-stream二进制流数据（最常见的文件下载）。
          headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       } catch (Exception e) {
          throw new RuntimeException(e);
       }
       return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
    }
}



























