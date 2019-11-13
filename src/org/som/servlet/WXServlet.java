package org.som.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.som.service.WXService;

@WebServlet("/wx")
public class WXServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		 * timestamp	时间戳
		 * nonce	随机数
		 * echostr	随机字符串
		 */
		
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String signature = req.getParameter("signature");
		String echostr = req.getParameter("echostr");
		if(WXService.check(timestamp,nonce,signature)) {
			
			System.out.println("接入成功");
			PrintWriter out = resp.getWriter();
			//原样返回echostr参数
			out.print(echostr);
			out.flush();
			out.close();
		}else {
			System.out.println("接入失败");
			
		}
		//System.out.println("get");
	}
	
	/**
	 * post请求在微信公众号中用来消息接收和事件推送
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//System.out.println("post");
		//doGet(req, resp);
		//调用消息测试代码
		//msgTest(req,resp);
		
		//处理消息和事件推送
		Map<String,String> requestMap = WXService.parseRequest(req.getInputStream());
		//打印解析出来返回的消息
		System.out.println(requestMap);
	}
	
	
	/**
	 * 消息消息接收测试
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void msgTest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		ServletInputStream is =  req.getInputStream();
		byte[] b = new byte[1024];
		int len;
		StringBuilder sb = new StringBuilder();
		while((len =is.read(b))!= -1) {
			sb.append(new String(b,0,len));
		}
		System.out.println(sb.toString());
	}
}
