package com.guardjo.calendar.alarm.manager.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web-hook")
@Slf4j
public class GoogleWebHookController {
    @PostMapping("/receive")
    public void receiveData(@RequestHeader HttpHeaders httpHeaders, @RequestBody String body) {
        log.info("[Test] Receive Web-hook");
        log.info("headers : {}", httpHeaders.toString());
        log.info("body : {}", body);
    }
}
