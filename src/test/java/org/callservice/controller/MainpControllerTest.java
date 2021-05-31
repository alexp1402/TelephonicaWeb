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
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LoggerConfig.class, WebConfig.class, WebSecurityConfig.class})
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
    public void contextLoad(){
        assertNotNull(webApplicationContext);
    }


    @Test
    public void mainPageReturnViewIndex() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Index"));

//                .andExpect(redirectedUrl("/ddd"))
    }

    @Test
    public void loginPageReturnLoginView() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginout/login"))
                .andExpect(model().attributeDoesNotExist("loginError"));
    }

    @Test
    public void loginPageWithErrorView() throws Exception {
        mvc.perform(get("/login-error"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("loginout/login"))
                .andExpect(model().attributeExists("loginError"));
    }

    @Test
    public void logoutPageLogoutView() throws Exception {
        mvc.perform(get("/logout"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("loginout/logout"));
    }
}