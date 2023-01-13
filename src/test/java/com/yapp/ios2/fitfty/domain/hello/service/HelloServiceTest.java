//package com.yapp.ios2.fitfty.domain.hello.service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class HelloServiceTest {
//    @InjectMocks
//    private HelloService helloService;
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    @DisplayName("getHello_param 없이 호출 시_'HELLO' 반환")
//    void test_getHello_1() {
//        // Arrange
//        String inputString = null;
//
//        // Act
//        String resultString = helloService.getHello(inputString);
//
//        // Assert
//        Assertions.assertEquals("HELLO", resultString);
//    }
//
//    @Test
//    @DisplayName("getHello_param 호출 시_'HELLO : id' 반환")
//    void test_getHello_2() {
//        // Arrange
//        String inputString = "fitfty";
//
//        // Act
//        String resultString = helloService.getHello(inputString);
//
//        // Assert
//        Assertions.assertEquals("HELLO : fitfty", resultString);
//    }
//
//}
