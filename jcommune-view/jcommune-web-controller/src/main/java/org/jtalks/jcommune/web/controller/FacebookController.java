package org.jtalks.jcommune.web.controller;

import org.jtalks.jcommune.model.dao.UserDao;
import org.jtalks.jcommune.model.dto.RegisterUserDto;
import org.jtalks.jcommune.model.dto.UserDto;
import org.jtalks.jcommune.model.entity.Language;
import org.jtalks.jcommune.plugin.api.exceptions.NoConnectionException;
import org.jtalks.jcommune.plugin.api.exceptions.UnexpectedErrorException;
import org.jtalks.jcommune.service.Authenticator;
import org.jtalks.jcommune.web.util.FacebookUserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping("/facebook")
public class FacebookController {


    private final Facebook facebook;
    private final ConnectionRepository connectionRepository;
    private final Authenticator authenticator;
    @Autowired
    private UserDao userDao;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationFailureHandler failureHandler;


    @Inject
    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository, Authenticator authenticator) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
        this.authenticator = authenticator;

    }

    @RequestMapping(value = "/sign_in", method = RequestMethod.GET)
    public String signInFacebook(Model model, HttpServletResponse response, HttpServletRequest request, Locale locale) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {

            User user = facebook.userOperations().getUserProfile();
            FacebookUserTransformer transformer = new FacebookUserTransformer();
            org.jtalks.common.model.entity.User jUser = transformer.transform(user);


            if (userDao.getByUsername(jUser.getUsername()) == null) {
                RegisterUserDto registerUserDto = new RegisterUserDto();
                registerUserDto.setUserDto(new UserDto(jUser));
                BindingResult errors;

                try {
                    registerUserDto.getUserDto().setLanguage(Language.byLocale(locale));
                    errors = authenticator.register(registerUserDto);
                } catch (NoConnectionException e) {
                    return "redirect:/sign_in?error=true";
                } catch (UnexpectedErrorException e) {
                    return "redirect:/sign_in?error=true";
                }
            }

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(jUser.getUsername(), jUser.getPassword());
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication auth;
            try {
                auth = authenticationManager.authenticate(token);
            } catch (AuthenticationException e) {
                //if failureHandler exists
                try {
                    failureHandler.onAuthenticationFailure(request, response, e);
                } catch (IOException | ServletException se) {
                    //ignore
                }
                throw e;
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/";
        }


        return "redirect:/sign_in?error=true";
    }


}
