package dz.bououza.quickpoll.repository;

import dz.bououza.quickpoll.domain.Proposal;
import dz.bououza.quickpoll.domain.Poll;
import dz.bououza.quickpoll.domain.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class VoteRepositoryTests {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PollRepository pollRepository;

    private static final String OPTION1 = "option1";
    private static final String OPTION2 = "option2";

    @BeforeEach
    public void init(){
        pollRepository.deleteAll();
    }

    @Test
    public void givenVotesList_whenFindByPoll_thenVotesList(){

        //Given Poll, options, votes
        Poll poll = new Poll();
        poll.setQuestion("Question");
        poll.setProposals(new HashSet<>(Arrays.asList(new Proposal(OPTION1),
                new Proposal(OPTION2))));
        poll = pollRepository.save(poll);

        for(Proposal op: poll.getProposals()){
            if(op.getValue().equals(OPTION1)){
                Vote v1 = new Vote();
                v1.setProposal(op);
                Vote v2 =new Vote();
                v2.setProposal(op);
                voteRepository.saveAll(Arrays.asList(v1,v2));
            }
            else if(op.getValue().equals(OPTION2)){
                Vote v =new Vote();
                v.setProposal(op);
                voteRepository.save(v);
            }
            
        }
        
        //When get all votes
        Iterable<Vote> votes = voteRepository.findByPoll(poll.getId());

        Map<String,Long> result = StreamSupport.stream(votes.spliterator(), false)
                .map(v -> v.getProposal().getValue())
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

        assertThat(result.get(OPTION1)).isNotNull();
        assertThat(result.get(OPTION2)).isNotNull();

        assertThat(result.get(OPTION1)).isEqualTo(2L);
        assertThat(result.get(OPTION2)).isEqualTo(1L);

    }
}
