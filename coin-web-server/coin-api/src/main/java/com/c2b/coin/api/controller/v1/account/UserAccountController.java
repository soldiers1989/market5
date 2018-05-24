package com.c2b.coin.api.controller.v1.account;

import com.c2b.coin.account.api.AccountClient;
import com.c2b.coin.api.annotation.Sign;
import com.c2b.coin.web.common.rest.RestBaseController;
import com.c2b.coin.web.common.rest.bean.RestResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/account")
@Api(value = "/v1/account", description = "资产")
public class UserAccountController extends RestBaseController {

  @Autowired
  private AccountClient accountClient;

  @Sign
  @GetMapping(value = "/asset/total")
  @ApiOperation(value = "获取用户总资产")
  public RestResponseBean getAssetTotal() {
    accountClient.getAssetTotal(1);
    return onSuccess();
  }

  @Sign
  @GetMapping(value = "/asset/list")
  @ApiOperation(value = "获取用户列表")
  public RestResponseBean getAssetList() {
    accountClient.getAssetList(1);
    return onSuccess();
  }

}
