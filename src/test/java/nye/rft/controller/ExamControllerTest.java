package nye.rft.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import nye.rft.model.Exam;
import nye.rft.model.User;
import nye.rft.model.UserRole;
import nye.rft.service.ExamService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.*;


@WebMvcTest(ExamController.class)
class ExamControllerTest {

    private static final String EXAM_ID = "exam1";
    private static final String TEACHER_ID = "teacher1";
    private static final String TEACHER_NAME = "Kovacs Aladar";
    private static final String COURSE_NAME = "course1";
    private static final String LOCATION = "location1";
    private static final int MAX_STUDENT_NUMBER = 10;
    private static final Date EXAM_DATE = new GregorianCalendar(2022, Calendar.APRIL, 3).getTime();


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamService examService;


    @Test
    void createExam_ShouldReturnOk_WhenExamIsCreated() throws Exception {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam =new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        ObjectMapper objectMapper = new ObjectMapper();
        String examJson = objectMapper.writeValueAsString(exam);

        Mockito.when(examService.createExam(Mockito.any(Exam.class)))
                .thenReturn("Exam successfully created.");

        mockMvc.perform(post("/exams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(examJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully")));
    }

    @Test
    void createExam_ShouldReturnConflict_WhenExamAlreadyExists() throws Exception {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam =new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        ObjectMapper objectMapper = new ObjectMapper();
        String examJson = objectMapper.writeValueAsString(exam);

        Mockito.when(examService.createExam(Mockito.any(Exam.class)))
                .thenReturn("The exam already exists.");

        mockMvc.perform(post("/exams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(examJson))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("already")));
    }

    @Test
    void getAllExams_ShouldReturnAllTheStoredExams_WhenCalled() throws Exception {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam =new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        List<Exam> expected = List.of(exam);
        given(examService.getAllExams()).willReturn(expected);
        Mockito.when(examService.getAllExams()).thenReturn(expected);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/exams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(EXAM_ID)));
    }

    @Test
    void registerStudentForExam_ShouldReturnOk_WhenRegistrationIsSuccessful() throws Exception {
        // Given
        String examId = "someExamId";
        User student = new User("studentId", "Student Name", UserRole.STUDENT);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(student);

        Mockito.when(examService.registerStudentForExam(examId, student))
                .thenReturn("Student successfully registered.");

        // When & Then
        mockMvc.perform(post("/exams/{examId}", examId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully")));
    }

    @Test
    void registerStudentForExam_ShouldReturnConflict_WhenRegistrationHasConflict() throws Exception {
        // Given
        String examId = "someExamId";
        User student = new User("studentId", "Student Name", UserRole.STUDENT);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(student);

        Mockito.when(examService.registerStudentForExam(examId, student))
                .thenReturn("The student cannot be registered.");

        // When & Then
        mockMvc.perform(post("/exams/{examId}", examId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("cannot be registered")));
    }

}