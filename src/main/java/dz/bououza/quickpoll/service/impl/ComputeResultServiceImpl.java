package dz.bououza.quickpoll.service.impl;

import dz.bououza.quickpoll.domain.Vote;
import dz.bououza.quickpoll.dto.OptionCount;
import dz.bououza.quickpoll.dto.VoteResult;
import dz.bououza.quickpoll.repository.VoteRepository;
import dz.bououza.quickpoll.service.ComputeResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ComputeResultServiceImpl implements ComputeResultService {

    private final VoteRepository voteRepository;

    @Override
    public VoteResult computeResult(Long pollId) {
        Iterable<Vote> votes = voteRepository.findByPoll(pollId);
        int totalVotes=0;
        Map<Long, OptionCount> tempMap=new HashMap<>();
        for(Vote vote: votes) {
            totalVotes++;
            Long optionId = vote.getOption().getId();
            if (tempMap.containsKey(optionId)) {
                OptionCount optionCount = tempMap.get(optionId);
                optionCount.setCount(optionCount.getCount() + 1);
            } else {
                OptionCount optionCount = new OptionCount();
                optionCount.setOptionId(optionId);
                optionCount.setCount(0);
                tempMap.put(optionId, optionCount);
            }
        }
            return new VoteResult(totalVotes,tempMap.values());
    }
}
