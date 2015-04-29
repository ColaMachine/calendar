import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import cola.machine.calendar.user.bean.User;



public class test {
	public static void main(String[] args)
	{
		User user =new User();
//		user.setEmail("123@qq.com");
		user.setEmail("123");
		user.setLoginname("123");
		user.setPwd("123");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<User>> constraintViolations =validator.validate(user);
	//	Set<ConstraintViolation<User>> constraintViolations =validator.validateValue(User.class,"email",null, javax.validation.groups.Default.class);
		if (!constraintViolations.isEmpty()) {
			HashMap errorMap = new HashMap();
			for (ConstraintViolation<User> constraintViolation : constraintViolations) {
				errorMap.put(constraintViolation.getPropertyPath(),
						constraintViolation.getMessage());
				System.out.println(constraintViolation.getPropertyPath()+"    "+constraintViolation.getMessage());
			}
		
		}
		
	}
}
