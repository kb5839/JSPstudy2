package kr.or.ddit.vo;

import org.junit.Test;

public class RegularExpressionTest {
	@Test
	public void regexTest() {
		String password = "@2";
		String regex = "(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%&*]).*";
		System.out.println(password.matches(regex));
	}
}
