package com.sparta_express.hub.presentation.controller;

import com.sparta_express.hub.application.HubService;
import com.sparta_express.hub.presentation.response.GetHubRes;
import com.sparta_express.hub.presentation.response.ResponseDataDto;
import com.sparta_express.hub.presentation.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/hubs")
@RequiredArgsConstructor
public class HubController {
    private final HubService hubService;

    @GetMapping("/{hubId}")
    public ResponseEntity<ResponseDataDto<GetHubRes>>
    getInterhubRoute(@PathVariable UUID hubId) {

        GetHubRes hubRes = GetHubRes.from(hubService.readHub(hubId));


        return ResponseEntity.ok(
                new ResponseDataDto<>(
                        ResponseStatus.GET_INTERHUB_ROUTE_SUCCESS,
                        hubRes
                )
        );
    }

    @DeleteMapping("/{hubId}")
    public ResponseEntity<ResponseDataDto<Void>>
    deleteInterhubRoute(@PathVariable UUID hubId) {
        hubService.deleteHub(hubId);

        return ResponseEntity.ok(
                new ResponseDataDto<>(
                        ResponseStatus.REQUEST_SUCCESS,
                        null
                )
        );
    }
}
