package pjsassy.mbtichatclon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pjsassy.mbtichatclon.post.domain.Category;

@EnableJpaAuditing
@SpringBootApplication
public class MbtiChatCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(MbtiChatCloneApplication.class, args);
	}

}
