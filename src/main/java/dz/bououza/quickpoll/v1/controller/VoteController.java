package dz.bououza.quickpoll.v1.controller;

import dz.bououza.quickpoll.domain.Vote;
import dz.bououza.quickpoll.repository.VoteRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController("VoteControllerV1")
@RequestMapping("/v1")
public class VoteController {
    private final VoteRepository repository;

    public VoteController(VoteRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/polls/{pollId}/votes")
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote){
        vote = repository.save(vote);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{voteId}")
                .buildAndExpand(vote.getId())
                .toUri());
        return new ResponseEntity<>(null,responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/polls/{pollId}/votes")
    public Iterable<Vote> getAllVotes(@PathVariable Long pollId){
        return repository.findByPoll(pollId);
    }


}
