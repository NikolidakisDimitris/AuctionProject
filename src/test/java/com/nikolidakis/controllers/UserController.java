package com.nikolidakis.controllers;

import com.nikolidakis.MockMVSClass;
import com.nikolidakis.requests.AuthenticateUserRequest;
import com.nikolidakis.responses.AuthenticationResponse;
import org.junit.Test;

import static com.nikolidakis.models.constants.StatusCodes.SUCCESS;
import static org.junit.Assert.assertEquals;

public class UserController extends MockMVSClass {

    @Test
    public void authenticateUser_Success() throws Exception {
        String EXPECTED = jsonMapper.writeValueAsString(new AuthenticationResponse(SUCCESS,
                "Authenticated User OK", "81DC9BDB52D04DC20036DBD8313ED055"));

        AuthenticateUserRequest request = new AuthenticateUserRequest("tasos kopanos", "1234");
        String ACTUAL = getMvcResult("/users/authenticateuser", request).getResponse().getContentAsString();

        assertEquals(EXPECTED, ACTUAL);
    }

}
