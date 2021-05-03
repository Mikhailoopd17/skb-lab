package org.skb_lab.test.rest;


import org.skb_lab.test.exception.UserExceptions;
import org.skb_lab.test.pojo.RequestBean;
import org.skb_lab.test.service.RegistrationService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

@RestController
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestMapping(value = "/api/registration", produces = "application/json; charset=UTF-8")
public class RegisterController {

    private RegistrationService registrationService;

    public RegisterController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public void registration(@RequestBody RequestBean requestBean) throws Exception {
        if (registrationService.isExistsLogin(requestBean.getLogin())) {
            throw new UserExceptions.RestException(String.format("User with login %s already exists, please input another login", requestBean.getLogin()));
        }
        registrationService.registration(requestBean);
    }
}
