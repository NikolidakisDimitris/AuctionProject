//package com.nikolidakis;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.nikolidakis.requests.Request;
//import org.junit.BeforeClass;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class MockMVSClass {
//
//    public static ObjectMapper jsonMapper;
//
//    @Autowired
//    public MockMvc mockMvc;
//
//    @BeforeClass
//    public static void init() {
//        //jsonMapper config
//        jsonMapper = new ObjectMapper().registerModule(new JavaTimeModule());
//        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//    }
//
//    public MvcResult getMvcResult(String endPoint, Request request) throws Exception {
//        return mockMvc.perform(post(endPoint)
//                .content(jsonMapper.writeValueAsString(request))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn();
//    }
//}
