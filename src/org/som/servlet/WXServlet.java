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
		 * signature	΢�ż���ǩ����signature����˿�������д��token�����������е�timestamp������nonce������
		 * timestamp	ʱ���
		 * nonce	�����
		 * echostr	����ַ���
		 */
		
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String signature = req.getParameter("signature");
		String echostr = req.getParameter("echostr");
		if(WXService.check(timestamp,nonce,signature)) {
			
			System.out.println("����ɹ�");
			PrintWriter out = resp.getWriter();
			//ԭ������echostr����
			out.print(echostr);
			out.flush();
			out.close();
		}else {
			System.out.println("����ʧ��");
			
		}
		//System.out.println("get");
	}
	
	/**
	 * post������΢�Ź��ں���������Ϣ���պ��¼�����
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//����������ַ�������Ӧ���ַ���
		req.setCharacterEncoding("utf8");
		resp.setCharacterEncoding("utf8");
		
		//System.out.println("post");
		//doGet(req, resp);
		//������Ϣ���Դ���
		//msgTest(req,resp);
		
		//������Ϣ���¼�����
		Map<String,String> requestMap = WXService.parseRequest(req.getInputStream());
		//��ӡ�����������ص���Ϣ
		System.out.println(requestMap);
		
		//�ظ����͹�������Ϣ,ʹ��xml����ʽ�ظ�,ʹ��΢�ſ����ĵ����ƹ����ĸ�ʽ
		String respXml = "<xml>\r\n" + 
				"  <ToUserName><![CDATA["+requestMap.get("FromUserName")+"]]></ToUserName>\r\n" + 
				"  <FromUserName><![CDATA["+requestMap.get("ToUserName")+"]]></FromUserName>\r\n" + 
				"  <CreateTime>"+ System.currentTimeMillis()/1000+"</CreateTime>\r\n" + 
				"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
				"  <Content><![CDATA[hello!!!]]></Content>\r\n" + 
				"</xml>";
		//String respXml = WXService.getResponse(requestMap);
		System.out.println(respXml);
		PrintWriter out = resp.getWriter();
		out.write(respXml);
		out.flush();
		out.close();
	}
	
	
	/**
	 * ��Ϣ��Ϣ���ղ���
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
