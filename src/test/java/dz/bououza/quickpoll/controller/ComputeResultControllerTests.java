package dz.bououza.quickpoll.controller;

import dz.bououza.quickpoll.dto.ProposalCount;
import dz.bououza.quickpoll.dto.VoteResult;
import dz.bououza.quickpoll.service.ComputeResultService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ComputeResultController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ComputeResultControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComputeResultService computeResultService;

    @Test
    public void givenPollId_whenComputeResult_thenVoteResult() throws Exception {
        //given
        Collection<ProposalCount> proposalCounts =
                Arrays.asList(new ProposalCount(100L,2),new ProposalCount(101L,1));
        VoteResult voteResult = new VoteResult(3, proposalCounts);

        //when
        given(computeResultService.computeResult(100L)).willReturn(voteResult);

        ResultActions response = mockMvc.perform(get("/computeresult").param("pollId","100"));
        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.totalVotes",is(voteResult.totalVotes())))
                .andExpect(jsonPath("$.results.size()",is(proposalCounts.size())));
    }


}
