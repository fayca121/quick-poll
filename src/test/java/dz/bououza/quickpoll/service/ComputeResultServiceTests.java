package dz.bououza.quickpoll.service;

import dz.bououza.quickpoll.domain.Option;
import dz.bououza.quickpoll.domain.Vote;
import dz.bououza.quickpoll.dto.OptionCount;
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
        Option option1 = new Option(OPTION1);
        option1.setId(1000L);
        Option option2 =new Option(OPTION2);
        option2.setId(1001L);

        Vote vote1=new Vote();
        vote1.setOption(option1);

        Vote vote2=new Vote();
        vote2.setOption(option1);

        Vote vote3=new Vote();
        vote3.setOption(option2);

        Iterable<Vote> votes = Arrays.asList(vote1,vote2,vote3);

        given(voteRepository.findByPoll(100L)).willReturn(votes);

        //when
        VoteResult voteResult = computeResultService.computeResult(100L);
        Collection<OptionCount> optionCounts = voteResult.results();
        assertThat(voteResult.totalVotes()).isEqualTo(3);
        assertThat(optionCounts).isNotEmpty();
        assertThat(getOptionCount(option1,optionCounts).getCount()).isEqualTo(2);
        assertThat(getOptionCount(option2,optionCounts).getCount()).isEqualTo(1);

    }

    private OptionCount getOptionCount(Option option, Collection<OptionCount> optionCounts){
        return optionCounts.stream()
                .filter(optionCount -> optionCount.getOptionId().equals(option.getId()))
                .findFirst().orElse(null);
    }

}
