package dz.bououza.quickpoll.service;

import dz.bououza.quickpoll.domain.Proposal;
import dz.bououza.quickpoll.domain.Vote;
import dz.bououza.quickpoll.dto.ProposalCount;
import dz.bououza.quickpoll.dto.VoteResult;
import dz.bououza.quickpoll.repository.VoteRepository;
import dz.bououza.quickpoll.service.impl.ComputeResultServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ComputeResultServiceTests {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private ComputeResultServiceImpl computeResultService;

    private static final String OPTION1 = "option1";
    private static final String OPTION2 = "option2";

    @Test
    public void givenVotesList_whenComputeResult_thenOptionsWithVotes(){

        //given
        Proposal proposal1 = new Proposal(OPTION1);
        proposal1.setId(1000L);
        Proposal proposal2 =new Proposal(OPTION2);
        proposal2.setId(1001L);

        Vote vote1=new Vote();
        vote1.setProposal(proposal1);

        Vote vote2=new Vote();
        vote2.setProposal(proposal1);

        Vote vote3=new Vote();
        vote3.setProposal(proposal2);

        Iterable<Vote> votes = Arrays.asList(vote1,vote2,vote3);

        given(voteRepository.findByPoll(100L)).willReturn(votes);

        //when
        VoteResult voteResult = computeResultService.computeResult(100L);
        Collection<ProposalCount> proposalCounts = voteResult.results();
        assertThat(voteResult.totalVotes()).isEqualTo(3);
        assertThat(proposalCounts).isNotEmpty();
        assertThat(getOptionCount(proposal1, proposalCounts).getCount()).isEqualTo(2);
        assertThat(getOptionCount(proposal2, proposalCounts).getCount()).isEqualTo(1);

    }

    private ProposalCount getOptionCount(Proposal proposal, Collection<ProposalCount> proposalCounts){
        return proposalCounts.stream()
                .filter(proposalCount -> proposalCount.getProposalId().equals(proposal.getId()))
                .findFirst().orElse(null);
    }

}
