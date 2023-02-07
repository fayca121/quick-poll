package dz.bououza.quickpoll.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.bououza.quickpoll.domain.Option;
import dz.bououza.quickpoll.domain.Vote;
import dz.bououza.quickpoll.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VoteControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenPollId_whenFindByPoll_thenReturnVotes() throws Exception {
        //given
        Vote vote =new Vote();
        vote.setId(100L);
        vote.setOption(new Option(1000L,"option1"));

        Vote vote1 =new Vote();
        vote.setId(101L);
        vote.setOption(new Option(1001L,"option2"));

        Vote vote2 =new Vote();
        vote.setId(102L);
        vote.setOption(new Option(1002L,"option3"));

        List<Vote> votes = Arrays.asList(vote,vote1,vote2);
        given(repository.findByPoll(any(Long.class))).willReturn(votes);

        //when
        ResultActions response= mockMvc.perform(get("/polls/{pollId}/votes",100L));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(3)));
    }

    @Test
    public void givenVote_whenCreateVote_thenReturn201() throws Exception {
        //given
        Vote vote=new Vote();
        vote.setId(100L);
        vote.setOption(new Option(1000L,"option1"));

        given(repository.save(any(Vote.class))).willReturn(vote);

        //when
        ResultActions response = mockMvc.perform(post("/polls/{pollId}/votes",500L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)));

        //then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location","http://localhost/polls/500/votes/"+vote.getId()));

    }
}
