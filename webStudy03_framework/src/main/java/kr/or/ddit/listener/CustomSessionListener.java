package kr.or.ddit.listener;

import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import kr.or.ddit.vo.MemberVO;

//@WebListener
public class CustomSessionListener implements HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		String name = event.getName();
		if(!"authMember".equals(name)) return;
		
		MemberVO authMember = (MemberVO)event.getValue();
		Map<String, MemberVO> userList = (Map)event.getSession().getServletContext().getAttribute("userList");
		userList.put(authMember.getMem_id(), authMember);
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		String name = event.getName();
		if(!"authMember".equals(name)) return;
		
		MemberVO authMember = (MemberVO)event.getValue();
		Map<String, MemberVO> userList = (Map)event.getSession().getServletContext().getAttribute("userList");
		userList.remove(authMember.getMem_id());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}

}
