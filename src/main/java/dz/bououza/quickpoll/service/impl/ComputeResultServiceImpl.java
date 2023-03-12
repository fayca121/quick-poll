package dz.bououza.quickpoll.service.impl;

import dz.bououza.quickpoll.domain.Vote;
import dz.bououza.quickpoll.dto.ProposalCount;
import dz.bououza.quickpoll.dto.VoteResult;
import dz.bououza.quickpoll.repository.VoteRepository;
import dz.bououza.quickpoll.service.ComputeResultService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ComputeResultServiceImpl implements ComputeResultService {

    private final VoteRepository voteRepository;

    public ComputeResultServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public VoteResult computeResult(Long pollId) {
        Iterable<Vote> votes = voteRepository.findByPoll(pollId);
        int totalVotes=0;
        Map<Long, ProposalCount> tempMap=new HashMap<>();
        for(Vote vote: votes) {
            totalVotes++;
            Long proposalId = vote.getProposal().getId();
            if (tempMap.containsKey(proposalId)) {
                ProposalCount proposalCount = tempMap.get(proposalId);
                proposalCount.setCount(proposalCount.getCount() + 1);
            } else {
                ProposalCount proposalCount = new ProposalCount();
                proposalCount.setProposalId(proposalId);
                proposalCount.setCount(1);
                tempMap.put(proposalId, proposalCount);
            }
        }
            return new VoteResult(totalVotes,tempMap.values());
    }
}
