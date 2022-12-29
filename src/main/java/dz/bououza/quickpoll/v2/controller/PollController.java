package dz.bououza.quickpoll.v2.controller;

import dz.bououza.quickpoll.domain.Poll;
import dz.bououza.quickpoll.exception.ResourceNotFoundException;
import dz.bououza.quickpoll.repository.PollRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController("PollControllerV2")
@RequestMapping("/v2")
@Tag(name = "Polls",description = "Poll API v2")
public class PollController {
    private final PollRepository pollRepository;

    public PollController(PollRepository pollRepository){
        this.pollRepository=pollRepository;
    }

    @GetMapping("/polls")
    public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable){
        Page<Poll> polls=pollRepository.findAll(pageable);
        return new ResponseEntity<>(polls, HttpStatus.OK);
    }

    @PostMapping("/polls")
    public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll){
        poll = pollRepository.save(poll);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);
        return new ResponseEntity<>(null,responseHeaders,HttpStatus.CREATED);
    }

    @GetMapping("/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) throws Exception {
        Optional<Poll> poll= pollRepository.findById(pollId);
        if(!poll.isPresent())
            throw new ResourceNotFoundException("Poll with Id: "+pollId+" not found!");
        return new ResponseEntity<>(poll.get(),HttpStatus.OK);
    }

    @PutMapping("/polls/{pollId}")
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll,@PathVariable Long pollId){
        poll.setId(pollId);
        poll = pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId){
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
