package swt6.soccer.config;

import java.util.Date;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import swt6.soccer.dao.UserRepository;
import swt6.soccer.domain.User;
import swt6.util.DateUtil;

@Configuration
@EnableJpaRepositories(basePackageClasses = { UserRepository.class })
@EnableTransactionManagement
@EntityScan(basePackageClasses = {User.class})
public class AppConfig {
	public static Date referenceDate = DateUtil.getDate(2016, 6, 17);
}