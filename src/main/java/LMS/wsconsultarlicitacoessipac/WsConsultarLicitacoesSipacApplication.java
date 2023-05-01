package LMS.wsconsultarlicitacoessipac;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class WsConsultarLicitacoesSipacApplication implements CommandLineRunner {

	@Autowired
	private Flyway flyway;
	public static void main(String[] args) {
		SpringApplication.run(WsConsultarLicitacoesSipacApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		flyway.migrate();
	}
}
