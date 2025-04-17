package uk.gov.hmcts.case_management;

import org.springframework.boot.SpringApplication;

public class TestCaseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(CaseManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
