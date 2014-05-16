package br.com.yaw.ssjc.app;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.yaw.ssjc.controller.ListaMercadoriaController;

/**
 * Ponto de entrada da aplicação.
 * 
 * @author YaW Tecnologia
 */
public class Main {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt","BR"));
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring-config.xml");
    	new ListaMercadoriaController(ctx);
    }
	
}
