package org.jtalks.jcommune.web.util;

import org.jtalks.common.model.entity.User;

/**
 * Created by Ilgiz on 29.03.2017.
 */
public class FacebookUserTransformer {
    private final String defaultPassHash = "sdf";

    public User transform(org.springframework.social.facebook.api.User facebookUser) {
        String username = facebookUser.getName();
        if (username == null) {
            username = "Facebook User";
        }

        String email = facebookUser.getEmail();
        if (email == null) {
            email = username + "@facebook.com";
        }

        User user = new User(username, email, defaultPassHash, null);
        return user;
    }
}
