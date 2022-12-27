package dz.bououza.quickpoll.v3.controller;

import dz.bououza.quickpoll.domain.Vote;
import dz.bououza.quickpoll.repository.VoteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController("VoteControllerV3")
@RequestMapping("/v3")
@Tag(name = "Votes",description = "Vote API v3")
public class VoteController {
    private final VoteRepository repository;

    public VoteController(VoteRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/polls/{pollId}/votes")
    @Operation(summary = "Create a new vote associated with pollId")
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
    @Operation(summary = "Retrieves all the votes associated with pollId")
    public Iterable<Vote> getAllVotes(@PathVariable Long pollId){
        return repository.findByPoll(pollId);
    }


}
