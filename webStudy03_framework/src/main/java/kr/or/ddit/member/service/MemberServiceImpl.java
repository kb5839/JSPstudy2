package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.commons.service.AuthenticateServiceImpl;
import kr.or.ddit.commons.service.IAuthenticateService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;

public class MemberServiceImpl implements IMemberService {
	private IMemberDAO memberDAO = MemberDAOImpl.getInstance();
	private IAuthenticateService authService = new AuthenticateServiceImpl();
	
	private static MemberServiceImpl self;
	
	private MemberServiceImpl() {
		super();
	}

	public static MemberServiceImpl getInstance() {
		if(self==null) self = new MemberServiceImpl();
		return self;
	}
	
	@Override
	public ServiceResult registMember(MemberVO member) {
		ServiceResult result = null;
		if( memberDAO.selectMember(member.getMem_id()) == null) {
			int rowcnt = memberDAO.insertMember(member);
			if(rowcnt>0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAILED;
			}
		}else {
			result = ServiceResult.PKDUPLICATED;
		}
		return result;
	}

	@Override
	public int retrieveMemberCount(PagingVO<MemberVO> pagingVO) {
		return memberDAO.selectMemberCount(pagingVO);
	}
	
	@Override
	public List<MemberVO> retrieveMemberList(PagingVO<MemberVO> pagingVO) {
		return memberDAO.selectMemberList(pagingVO);
	}

	@Override
	public MemberVO retrieveMember(String mem_id) {
		MemberVO savedMember = memberDAO.selectMember(mem_id);
		if(savedMember==null)
			throw new CustomException( mem_id+"는 존재하지 않는 회원임.");
		return savedMember;
	}

	@Override
	public ServiceResult modifyMember(MemberVO member) {
		Object result = authService.authenticate(member);
		ServiceResult serviceResult = null;
		if(result instanceof MemberVO) {
			int rowcnt = memberDAO.updateMember(member);
			if(rowcnt>0) {
				serviceResult = ServiceResult.OK;
			}else {
				serviceResult = ServiceResult.FAILED;
			}
		}else {
			if(ServiceResult.NOTEXIST.equals(result)) {
				throw new CustomException(member.getMem_id()+"는 회원이 아님.");
			}else {
				serviceResult = (ServiceResult) result;
			}
		}
		return serviceResult;
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		Object result = authService.authenticate(member);
		ServiceResult serviceResult = null;
		if(result instanceof MemberVO) {
			int rowcnt = memberDAO.deleteMember(member.getMem_id());
			if(rowcnt>0) {
				serviceResult = ServiceResult.OK;
			}else {
				serviceResult = ServiceResult.FAILED;
			}
		}else {
			if(ServiceResult.NOTEXIST.equals(result)) {
				throw new CustomException(member.getMem_id()+"는 회원이 아님.");
			}else {
				serviceResult = (ServiceResult) result;
			}
		}
		return serviceResult;
	}

}
