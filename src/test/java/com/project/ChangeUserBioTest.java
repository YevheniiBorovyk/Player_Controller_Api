package com.project;

import com.api.apiclient.Api;
import com.api.model.request.user.User;
import com.utils.StringMan;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChangeUserBioTest {

    @Test
    public void checkChangingUserBio() {
        Api api = new Api("jon01@gmail.com");
        String expectedUserBio = StringMan.getRandomString(4);
        String actualUserBio = api.userApi.changeUserBio(User.builder()
                        .bio(expectedUserBio)
                        .build())
                .getUser()
                .getBio();
        Assert.assertEquals(actualUserBio, expectedUserBio, "user bio assertion failed");
    }
}
