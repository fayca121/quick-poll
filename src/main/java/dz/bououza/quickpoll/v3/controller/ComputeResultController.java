package dz.bououza.quickpoll.v3.controller;

import dz.bououza.quickpoll.domain.Vote;
import dz.bououza.quickpoll.dto.OptionCount;
import dz.bououza.quickpoll.dto.VoteResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("ComputeResultControllerV3")
@RequestMapping("/v3")
public class ComputeResultController {
    private final VoteRepository voteRepository;

    public ComputeResultController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping("/computeresult")
    public ResponseEntity<?> computeResult(@RequestParam Long pollId){
        Iterable<Vote> votes = voteRepository.findByPoll(pollId);
        int totalVotes=0;
        Map<Long, OptionCount> tempMap=new HashMap<>();
       for(Vote vote: votes){
           totalVotes++;
           Long optionId = vote.getOption().getId();
           if(tempMap.containsKey(optionId)) {
               OptionCount optionCount = tempMap.get(optionId);
               optionCount.setCount(optionCount.getCount()+1);
           }
           else {
               OptionCount optionCount=new OptionCount();
               optionCount.setOptionId(optionId);
               optionCount.setCount(0);
               tempMap.put(optionId,optionCount);
           }
       }
       return new ResponseEntity<>(new VoteResult(totalVotes,tempMap.values()), HttpStatus.OK);
    }
}
