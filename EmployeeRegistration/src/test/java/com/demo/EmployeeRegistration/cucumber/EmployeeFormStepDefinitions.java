package com.demo.EmployeeRegistration.cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeFormStepDefinitions {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String token;
    private ResponseEntity<Map> saveResponse;
    private Map<String, String> employeeForm;

    @Given("the employee registration client is authenticated with username {string} and password {string}")
    public void login(String username, String password) {
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(
                url("/auth/login"),
                Map.of("username", username, "password", password),
                Map.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).containsKey("token");
        token = (String) loginResponse.getBody().get("token");
    }

    @When("the client fills the employee form with:")
    public void fillForm(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        employeeForm = rows.get(0);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(employeeForm, headers);
        saveResponse = restTemplate.postForEntity(url("/employee/save"), request, Map.class);
    }


    @When("the client submits predefined employee form data {string}, {string}, {string}, {string}")
    public void submitPredefinedData(String name, String email, String contactNumber, String position) {
        employeeForm = Map.of(
                "name", name,
                "email", email,
                "contactNumber", contactNumber,
                "position", position
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(employeeForm, headers);
        saveResponse = restTemplate.postForEntity(url("/employee/save"), request, Map.class);
    }

    @Then("the employee form should be submitted successfully")
    public void verifySubmittedSuccessfully() {
        assertThat(saveResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(saveResponse.getBody()).containsEntry("message", "Employee saved successfully!");
    }

    @Then("the saved employee list should include the email {string}")
    public void verifyEmployeeSaved(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map[]> response = restTemplate.exchange(url("/employee/all"), HttpMethod.GET, request, Map[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody())
                .extracting(row -> (String) row.get("email"))
                .contains(email);
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }
}