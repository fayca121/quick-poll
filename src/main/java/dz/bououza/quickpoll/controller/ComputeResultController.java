package dz.bououza.quickpoll.controller;

import dz.bououza.quickpoll.dto.VoteResult;
import dz.bououza.quickpoll.service.ComputeResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("ComputeResultController")
@RequiredArgsConstructor
public class ComputeResultController {
    private final ComputeResultService service;

    @GetMapping("/computeresult")
    public ResponseEntity<?> computeResult(@RequestParam Long pollId){
        VoteResult voteResult = service.computeResult(pollId);
       return new ResponseEntity<>(voteResult, HttpStatus.OK);
    }
}