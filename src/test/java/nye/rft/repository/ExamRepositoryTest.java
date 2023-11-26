package nye.rft.repository;

import nye.rft.model.Exam;
import nye.rft.model.User;
import nye.rft.model.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;


class ExamRepositoryTest {

    private final Calendar calendar = Calendar.getInstance();
    private static final String EXAM_ID_FIRST = "exam1";
    private static final String EXAM_ID_SECOND = "exam2";
    private static final String TEACHER_ID = "teacher1";
    private static final String TEACHER_NAME = "Kovacs Aladar";
    private static final String COURSE_NAME = "course1";
    private static final String LOCATION = "location1";
    private static final int MAX_STUDENT_NUMBER = 10;
    private static Date EXAM_DATE;
    private AutoCloseable closeable;

    @InjectMocks
    private ExamRepository underTest;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        calendar.set(2022,04,03);
        calendar.set(Calendar.MILLISECOND, 0);
        EXAM_DATE = calendar.getTime();
    }

    @AfterEach
    void tearDown()  {
        try {
            closeable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveExam_ShouldAddExamToTheExamRepository_WhenCalled() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam =new Exam(EXAM_ID_FIRST,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        //When
        underTest.saveExam(exam);
        //Then
        assertTrue(underTest.getAllExams().contains(exam));
    }

    @Test
    void saveExam_ShouldRefuseToAddExamToTheExamRepository_WhenTheExamAlreadyExists() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam = new Exam(EXAM_ID_FIRST,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        Exam examSame = new Exam(EXAM_ID_FIRST,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        underTest.saveExam(exam);

        //When & Then
        assertFalse(underTest.saveExam(examSame));
    }

    @Test
    void getAllExams_ShouldReturnAllTheExams_WhenCalled() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam expected =new Exam(EXAM_ID_FIRST,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        underTest.saveExam(expected);
        //When
        List<Exam> actual= underTest.getAllExams();
        //Then
        Assertions.assertEquals(actual, List.of(expected));
    }

    @Test
    void findExamById_ShouldReturnTheExam_WhenTheExamIsAlreadyStored() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam expected = new Exam(EXAM_ID_FIRST,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER) ;
        underTest.saveExam(expected);

        //When
        Exam actual = underTest.findExamById(EXAM_ID_FIRST);
        //Then
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findExamById_ShouldReturnNull_WhenTheExamIsNotYetStored() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam = new Exam(EXAM_ID_FIRST,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER) ;
        underTest.saveExam(exam);

        //When
        Exam actual = underTest.findExamById(EXAM_ID_SECOND);
        //Then
        Assertions.assertNull(actual);
    }
}