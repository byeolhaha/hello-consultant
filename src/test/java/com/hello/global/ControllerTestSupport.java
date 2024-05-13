package com.hello.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.chat.controller.ChatController;
import com.hello.chat.controller.ChatRoomController;
import com.hello.chat.service.ChatMessageService;
import com.hello.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {
        ChatRoomController.class,
        ChatController.class
    }
)
public abstract class ControllerTestSupport {

    @Autowired
    WebApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ChatService chatService;

    @MockBean
    protected ChatMessageService chatMessageService;

}
