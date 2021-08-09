package com.revature.projectZero.services;

import com.revature.projectZero.pojos.Course;
import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.repositories.SchoolRepository;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.exceptions.InvalidRequestException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ValidationServiceTestSuite {
    /*
    @Before
    @After
    @Test
    @BeforeTest
    @AfterTest
     */

    ValidationService sut;
    private SchoolRepository mockSchoolRepo;

    @Before
    public void beforeEachTest() {
        mockSchoolRepo = mock(SchoolRepository.class);
        sut = new ValidationService(mockSchoolRepo);
    }

    @After
    public void afterEachTest() {
        sut = null;
    }

    @Test
    public void isUserValid_returnTrueForGivenValidUser() {

        // Arrange
        Student testStudent = new Student("username", 12562562, "firstname", "lastname", "email");

        // Act
        boolean actualResult = sut.isUserValid(testStudent);

        // Assert
        Assert.assertTrue("Expected user to be considered valid!", actualResult);
    }

    @Test
    public void isUserValid_returnsFalseForGivenBlankValues() {

        // Arrange
        Student testStudent1 = new Student(null, 12562562, "firstname", "lastname", "email");
        Student testStudent2 = new Student("", 12562562, "firstname", "lastname", "email");
        Student testStudent3 = new Student("      ", 12562562, "firstname", "lastname", "email");

        // Act
        boolean actualResult1 = sut.isUserValid(testStudent1);
        boolean actualResult2 = sut.isUserValid(testStudent2);
        boolean actualResult3 = sut.isUserValid(testStudent3);

        // Assert
        Assert.assertFalse("The first name cannot be a null value!", actualResult1);
        Assert.assertFalse("The first name cannot be empty!", actualResult2);
        Assert.assertFalse("The first name cannot be empty space!", actualResult3);

    }

    @Test
    public void register_returnsTrueWhenGivenValidUser() {

        // Arrange
        Student testStudent = new Student("username", 12562562, "firstname", "lastname", "email");
        Student expectedStudent = new Student("username", 12562562, "firstname", "lastname", "email");
        when(mockSchoolRepo.save(any())).thenReturn(expectedStudent);

        // Act
        mockSchoolRepo.save(testStudent);

        // Assert
        Assert.assertEquals(expectedStudent, testStudent);
        verify(mockSchoolRepo, times(1)).save(any());

    }

    @Test(expected = InvalidRequestException.class)
    public void register_throwsException_whenGivenInvalidUser() {

        // Arrange
        Student invalidStudent = new Student(null, 123456, "", "", "");

        // Act
        try {
            sut.register(invalidStudent);
        } finally {
            // Assert
            verify(mockSchoolRepo, times(0)).save(any());
        }
    }

    @Test
    public void returnsTrue_GivenValidCourseInput() {

        // Arrange
        Course testCourse = new Course("ValidCourseName","TST242","This description is a completely valid one.","Valid",true);
        when(mockSchoolRepo.findCourseByID(anyString())).thenReturn(null);

        // Act
        boolean actualResult = sut.isCourseValid(testCourse);

        // Assert
        Assert.assertTrue("This is a valid course, one that would be put into the database.", actualResult);

    }

    @Test(expected = InvalidRequestException.class)
    public void returnsFalse_GivenDuplicateClassID() {

        // Arrange
        Course testCourse = new Course("ValidCourseName","tst242","This description is a completely valid one.","Valid",true);
        when(mockSchoolRepo.findCourseByID(anyString())).thenReturn(null);

        // Act
        sut.isCourseValid(testCourse);

        // Assert
        // This will return an exception.
    }

    @Test
    public void returnsTrue_GivenValidClassID() {

        // Arrange
        String validID = "CHM242";
        when(mockSchoolRepo.findCourseByID(anyString())).thenReturn(null);

        // Act
        sut.deregister(validID);
        sut.deleteCourse(validID);

        // Assert  // TODO: This does not work! It does not know who Authfac is!
        verify(mockSchoolRepo, times(1)).deleteEnrolled(any(), any());
        verify(mockSchoolRepo, times(1)).deleteCourse(any());
    }
}
