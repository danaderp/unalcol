package test.test2;

public class Test5 {

	private static String field1;
	private String field2;
	private String field3 = new String();
	private static String field4 = new String();
	public String[] field5;
	public List<String> field6;

	public void method1(String field1) {

	}

	public void method2(String field1) {
		String field2;

	}

	public void method3(String field1) {
		String field2;

		field3 = "test";

	}

	public void method4(String field1) {
		String field2;

		field3 = "test";

		thisisacall(field4);

	}

	public void method5(String field1) {
		String field2;

		field3 = "test";

		thisisacall(field4);

		this.field5 = "";

	}

	public Test5() {
		String field2;

		field3 = "test";

		thisisacall(field4);

		this.field5 = "";
		field6 = "";
	}

}