package org.callservice.controller;

import org.callservice.configuration.LoggerConfig;
import org.callservice.configuration.PersistenceConfig;
import org.callservice.configuration.WebConfig;
import org.callservice.configuration.WebSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LoggerConfig.class, PersistenceConfig.class, WebConfig.class, WebSecurityConfig.class,})
@WebAppConfiguration
class MainpControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void mainPageReturnViewIndex() throws Exception {
        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("Index"));
        //.andExpect(view());

//                .andExpect(redirectedUrl("/ddd"))
    }

    //with param name and model attribute
//    @Test
//    public void testwithParamName() {
//        String name = "stranger";
//        String expected = "Привет " + name;
//        mvc.perform(get("view/{name}", name))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("msg", expected))
//                .andExpect(view().name("/index"));

//    response body
//    mvc.perfom()
//    .andExpect(content().contentTypeCompatableWith....)
//    .andExpect(contett().string("RAW DATA"));
//    }


//    @GetMapping("/")
//    public String mainPage() {
//        log.info("INFO");
//        log.error("error");
//        log.debug("debug");
//        log.trace("trace");
//
//
//        return "Index";
//    }
//
//    @GetMapping("/init")
//    public String initSite() {
//        uService.initRolesAdmin();
//        return "redirect:/";
//    }


//    @Test
//    void mainPage() {
//    }
//
//    @Test
//    void login() {
//    }
//
//    @Test
//    void loginError() {
//    }
//
//    @Test
//    void logout() {
//    }
}