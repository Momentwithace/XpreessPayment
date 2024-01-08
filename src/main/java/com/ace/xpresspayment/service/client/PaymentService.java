package com.ace.xpresspayment.service.client;

import com.ace.xpresspayment.dtos.request.AirtimePurchaseDto;
import com.ace.xpresspayment.dtos.response.AirtimePurchaseResponse;
import com.ace.xpresspayment.exceptions.XpressPaymentException;
import com.ace.xpresspayment.messages.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class PaymentService {

    @Value("${public.key:}")
    private String publicKey;

    @Value("${airtime.url:}")
    private String airtimeUrl;

    @Value("${private.key:}")
    private String privateKey;

    public AirtimePurchaseResponse getAirtime(AirtimePurchaseDto dto) throws XpressPaymentException {
        String payload = generatePayload(dto);
        String res = calculateHMAC512(payload, privateKey);

        try {
            WebClient webClient = WebClient.builder().baseUrl(airtimeUrl).build();

            AirtimePurchaseResponse response = webClient.post()
                    .uri(airtimeUrl)
                    .header("Authorization", "Bearer " + publicKey)
                    .header("PaymentHash", res)
                    .header("channel", "pos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(payload))
                    .retrieve()
                    .bodyToMono(AirtimePurchaseResponse.class)
                    .block();

            if (response != null) {
                return response;
            } else {
                throw new XpressPaymentException(ErrorMessage.INVALID_PHONE_NUMBER, HttpStatus.BAD_REQUEST);
            }
        } catch (WebClientResponseException e) {
            log.error(e.getLocalizedMessage());
            throw new XpressPaymentException(ErrorMessage.AIRTIME_PURCHASE_UNSUCCESSFUL, e.getStatusCode());
        }
    }

    private AirtimePurchaseResponse parseResponseJson(String responseJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseJson, AirtimePurchaseResponse.class);
    }

    private String generatePayload(AirtimePurchaseDto dto) {
        return "{\n" +
                "\"requestId\":\"" + dto.getRequestId() + "\",\n" +
                "\"uniqueCode\":\"" + dto.getUniqueCode() + "\",\n" +
                "\"details\":{\n" +
                "\"phoneNumber\":\"" + dto.getDetails().getPhoneNumber() + "\",\n" +
                "\"amount\":" + dto.getDetails().getAmount() + "\n" +
                "}\n" +
                "}";
    }
    private String calculateHMAC512(String data, String key) {
        String HMAC_SHA512 = "HmacSHA512";

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_SHA512);
            mac.init(secretKeySpec);

            return Hex.encodeHexString(mac.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
