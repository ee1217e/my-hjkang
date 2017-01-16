package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StringCalculatorTest {
	/*
	 * 쉼표(,) 또는 콜론(:)을 구분자로 가지는 문자열을 전달하는 경우 구분자를 기준으로 분리한 각 숫자의 합을 반환한다.(예: “”
	 * => 0, "1,2" => 3, "1,2,3" => 6, “1,2:3” => 6) • 앞의 기본 구분자(쉼표, 콜론)외에 커스텀
	 * 구분자를 지정할 수 있다. 커스텀 구분자는 문자열 앞부분의 “//”와 “\n” 사이에 위치하는 문자를 커스텀 구분자로 사용한다.
	 * 예를 들어 “//;\n1;2;3”과 같이 값을 입력할 경우 커스텀 구분자는 세미콜론(;)이며, 결과 값은 6이 반환되어야 한다. •
	 * 문자열 덧셈 계산기에 음수를 전달하는 경우 RuntimeException으로 예외처리해야 한다.
	 */
	
	StringCalculator sc;

	@Before
	public void setup() {
		sc = new StringCalculator();
	}

	@Test
	public void 빈문자체크() {
		assertEquals(0, sc.add(""));
		assertEquals(0, sc.add(null));
	}

	@Test
	public void 숫자하나입력() {
		assertEquals(123, sc.add("123"));
	}

	@Test
	public void 숫자더하기() {
		assertEquals(11, sc.add("1,2:3:5"));
	}

	@Test
	public void 뉴라인구분() {
		assertEquals(9 ,sc.add("1\n3,5"));
	}
	
	@Test
	public void 커스텀구분자추가(){
		assertEquals(6 ,sc.add("//;\n1;2;3"));
	}

	@Test(expected=RuntimeException.class)
	public void 음수체크() throws RuntimeException{
		sc.add("-12");
	}

	

}
