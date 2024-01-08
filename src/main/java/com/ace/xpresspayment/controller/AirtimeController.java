package com.ace.xpresspayment.controller;

import com.ace.xpresspayment.dtos.request.AirtimePurchaseRequest;
import com.ace.xpresspayment.dtos.request.PageDto;
import com.ace.xpresspayment.dtos.request.ResponseDto;
import com.ace.xpresspayment.exceptions.XpressPaymentException;
import com.ace.xpresspayment.models.CustomUser;
import com.ace.xpresspayment.service.AirtimeService;
import com.ace.xpresspayment.utils.PageRequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/xpress")
public class AirtimeController {

    private final AirtimeService airtimeService;

    @PostMapping("/airtime/purchase")
    public ResponseDto<?> purchaseAirtime(Authentication authentication, @RequestBody @Valid AirtimePurchaseRequest airtimePurchaseRequest) throws  XpressPaymentException {
        long userId = CustomUser.getId(authentication);
        return ResponseDto.wrapSuccessResult(airtimeService.purchaseAirtime(userId, airtimePurchaseRequest), "successful");
    }

    @GetMapping("/airtime/history")
    public PageDto getAirtimeHistory(Authentication authentication, @RequestParam int page, @RequestParam int size){
        long userId = CustomUser.getId(authentication);
        return airtimeService.getAirtimePurchaseHistory(userId, PageRequestUtils.normalize(page, size));
    }
}
