package kr.or.ddit.vo;

import static org.junit.Assert.assertNull;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.validate.InsertGroup;

public class MemberVOValidationTest {

	private IMemberDAO dao;
	private static Validator validator;
	
	@BeforeClass
	public static void setUpClass() {
		ValidatorFactory factory = Validation.byDefaultProvider()
					.configure()
					.messageInterpolator(
							new ResourceBundleMessageInterpolator(
									new PlatformResourceBundleLocator("kr.or.ddit.msgs.CustomValidationMessage")
							)
					)
					.buildValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Before
	public void setUp() throws Exception {
		dao = MemberDAOImpl.getInstance();
		System.out.println("before");
	}

//	@Test
	public void test() {
		MemberVO member = dao.selectMember("afasdf01");
		assertNull(member);
	}
	
	@Test
	public void memberValidationTest() {
		MemberVO member = MemberVO.builder()
						.mem_hp("afasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf")
						.build();
		Set<ConstraintViolation<MemberVO>> errorSet = 
					validator.validate(member, InsertGroup.class);
		System.out.println(errorSet.size());
		for( ConstraintViolation<MemberVO> violation : errorSet) {
			Path propertyPath = violation.getPropertyPath();
			String message = violation.getMessage();
			System.out.printf("%s : %s\n", propertyPath, message);
		}
	}
	
//	@Test
	public void resourceBundleTest() {
		String baseName = "org.hibernate.validator.ValidationMessages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, Locale.JAPANESE);
		String message = bundle.getString("javax.validation.constraints.AssertFalse.message");
		System.out.println(message);
	}
}

















