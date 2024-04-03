package kr.or.ddit.principal;

import java.security.Principal;

import kr.or.ddit.vo.MemberVO;


// 실제 작동하는것은 memberVO
public class MemberVOWrapper implements Principal {
	private final MemberVO realUser;	// final이기 때문에 setter 없음
	
	public MemberVOWrapper(MemberVO realUser) {
		super();
		this.realUser = realUser;
	}

	@Override
	public String getName() {
		return realUser.getMemId();
	}

	public MemberVO getRealUser() {
		return realUser;
	}
}
