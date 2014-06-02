package terd.web;

import com.thoughtworks.selenium.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

public class Game {
	private Selenium selenium,selenium2;
	@Before
	public void setUp() throws Exception {
		FuncionesAuxiliaresTests.borrarBD();

		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://127.0.0.1:8888/");
		selenium2 = new DefaultSelenium("localhost", 4444, "*chrome", "http://127.0.0.1:8888/");
		
		selenium.start();
		selenium2.start();
	}

	@Test
	public void testLogin() throws Exception {
		selenium.open("/UltimateTicTacToeWeb.html");
		selenium2.open("/UltimateTicTacToeWeb.html");
		Thread.sleep(10000);
		try{
		selenium.type("css=#emailRegistro > input.gwt-TextBox", "user2");
		selenium.type("css=#passwordRegistro > input.gwt-PasswordTextBox", "user2");
		selenium.type("css=#repPasswordRegistro > input.gwt-PasswordTextBox", "user2");
		selenium.click("css=#registerButton > button.myButton");
		}
		catch(Exception e){
			
		}
		//selenium.waitForPageToLoad("3000");
	//	assertEquals("Usuario registrado", selenium.getAlert());
		try{
		selenium2.type("css=#emailRegistro > input.gwt-TextBox", "user3");
		selenium2.type("css=#passwordRegistro > input.gwt-PasswordTextBox", "user3");
		selenium2.type("css=#repPasswordRegistro > input.gwt-PasswordTextBox", "user3");
		selenium2.click("css=#registerButton > button.myButton");
		}catch(Exception e){
			
		}
		//selenium.waitForPageToLoad("3000");
	//	assertEquals("Usuario registrado", selenium.getAlert());

		
		selenium.type("css=input.gwt-TextBox", "user2");
		selenium.type("css=input.gwt-PasswordTextBox", "user2");
		selenium.click("css=#sendButtonContainer > button.myButton");
		
	//selenium.waitForPageToLoad("3000");
//
//		selenium2.type("css=#emailRegistro > input.gwt-TextBox", "user3");
//		selenium2.type("css=#passwordRegistro > input.gwt-PasswordTextBox", "user3");
//		selenium2.type("css=#repPasswordRegistro > input.gwt-PasswordTextBox", "user3");
//		selenium2.click("css=#registerButton > button.myButton");
//		//assertEquals("Usuario registrado", selenium.getAlert());
		selenium2.type("css=input.gwt-TextBox", "user3");
		selenium2.type("css=input.gwt-PasswordTextBox", "user3");
		selenium2.click("css=#sendButtonContainer > button.myButton");
		
		selenium2.waitForPageToLoad("3000");

		selenium.waitForPageToLoad("3000");

		
		Thread.sleep(5000);
		
//		assertTrue(selenium.isElementPresent("css=select.lista[value=\"user3\"]"));
//		assertTrue(selenium2.isElementPresent("css=select.lista[value=\"user2\"]"));

		selenium.doubleClick("css=select.lista[value=\"user3\"]");
	//	selenium.waitForPageToLoad("30000");
		
		Thread.sleep(10000);
		
	}
//	@Test
//	public void testRetar() throws Exception {
//	
//		
//		selenium.doubleClick("css=select.lista[value=\"user3\"]");
//		selenium.waitForPageToLoad("30000");
//		
//		Thread.sleep(10000);
//	}

	@After
	public void tearDown() throws Exception {
		selenium.click("css=button.myButton");
		selenium2.click("css=button.myButton");

		selenium.stop();
		selenium2.stop();
		FuncionesAuxiliaresTests.borrarBD();

	}
}
