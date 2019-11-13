package org.som.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
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
		System.out.println("doget");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("dopost");
		//doGet(req, resp);
	}
}
