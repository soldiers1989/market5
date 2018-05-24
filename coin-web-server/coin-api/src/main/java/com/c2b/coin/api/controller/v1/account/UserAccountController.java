package com.c2b.coin.api.controller.v1.account;

import com.c2b.coin.api.annotation.Sign;
import com.c2b.coin.web.common.rest.RestBaseController;
import com.c2b.coin.web.common.rest.bean.RestResponseBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/account")
public class UserAccountController extends RestBaseController {

    @Sign
    @RequestMapping(value = "get", method = {RequestMethod.GET, RequestMethod.POST})
    public RestResponseBean get(@RequestParam(required = false, defaultValue = "") String orderId) {
        return onSuccess();
    }

}
