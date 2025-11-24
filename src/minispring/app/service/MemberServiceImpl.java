package minispring.app.service;

import minispring.annotations.Service;

@Service
public class MemberServiceImpl implements MemberService {

	public void login() {
		System.out.println("로그인 서비스 동작");
	}
}
