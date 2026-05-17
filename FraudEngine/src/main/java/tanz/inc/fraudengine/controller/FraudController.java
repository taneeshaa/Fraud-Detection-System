package tanz.inc.fraudengine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tanz.inc.fraudengine.Service.FraudScoreService;
import tanz.inc.fraudengine.dto.FraudResult;

import java.math.BigDecimal;

@RestController
@RequestMapping("/fraud-check")
@RequiredArgsConstructor
public class FraudController {
    private final FraudScoreService fraudScoreService;

    @GetMapping
    public Mono<FraudResult> check(@RequestParam String userId, @RequestParam BigDecimal amount){
        return fraudScoreService.processCheck(userId, amount);
    }

}
