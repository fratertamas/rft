package nye.rft.APItest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nye.rft.model.Exam;
import nye.rft.model.User;
import nye.rft.model.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
public class APITest {

    private static final String EXAM_ID = "exam1";
    private static final String TEACHER_ID = "teacher1";
    private static final String TEACHER_NAME = "Kovacs Aladar";
    private static final String COURSE_NAME = "course1";
    private static final String LOCATION = "location1";
    private static final int MAX_STUDENT_NUMBER = 10;
    private static final Date EXAM_DATE = new GregorianCalendar(2022, Calendar.APRIL, 3).getTime();



    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    //get
    @Test
    public void request_ShouldReturnStatusOk_WhenUsersVisitCreateExam() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/createexam.html", String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void request_ShouldReturnStatusOk_WhenUsersVisitListExams() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/exams.html", String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void request_ShouldReturnStatusOk_WhenUsersVisitRegisterStudentSite() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/registerstudent.html", String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void Request_ShouldReturnExams_WhenCalled() throws Exception {
        // Perform the GET request
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/exams", String.class);

        // Assert the response status
        assertEquals(200, response.getStatusCodeValue());

        // Deserialize the response body to List<Exam>
        List<Exam> exams = objectMapper.readValue(response.getBody(), new TypeReference<List<Exam>>() {});

        // Assert the response body
        Assertions.assertFalse(exams.isEmpty());
        // Further assertions can be added based on the content of the response
    }

    //post
    @Test
    public void Post_ShouldAddANewExamAccordingToSpecification_WhenUsingTheEndpointFromTheAPI() throws JsonProcessingException {

        String examId = "someExamId";
        User student = new User("studentId", "Student Name", UserRole.STUDENT);
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam =new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        String requestBody = objectMapper.writeValueAsString(exam);


        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HTTP request
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/exams", request, String.class);

        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Exam successfully created.", response.getBody());

    }

    @Test
    public void Post_ShouldAddANewStudentToAnExistingExam_WhenUsingTheEndpointFromTheAPI() throws Exception {
        // Step 1: Create an Exam
        Exam newExam = new Exam("exam123", new Date(), "Test Course", "Test Location", new User("teacher1", "Teacher Name", UserRole.TEACHER), 50);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> createExamRequest = new HttpEntity<>(objectMapper.writeValueAsString(newExam), headers);

        ResponseEntity<String> createExamResponse = restTemplate.postForEntity("http://localhost:" + port + "/exams", createExamRequest, String.class);
        assertEquals(200, createExamResponse.getStatusCodeValue());
        assertEquals("Exam successfully created.", createExamResponse.getBody());

        // Step 2: Register a Student for the Exam
        User student = new User("student1", "Student Name", UserRole.STUDENT);
        HttpEntity<String> registerRequest = new HttpEntity<>(objectMapper.writeValueAsString(student), headers);

        ResponseEntity<String> registerResponse = restTemplate.postForEntity("http://localhost:" + port + "/exams/exam123", registerRequest, String.class);
        assertEquals(200, registerResponse.getStatusCodeValue());
        assertEquals("Student successfully registered.", registerResponse.getBody());
    }



}
