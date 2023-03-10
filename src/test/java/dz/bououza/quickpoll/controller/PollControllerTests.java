package dz.bououza.quickpoll.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.bououza.quickpoll.domain.Proposal;
import dz.bououza.quickpoll.domain.Poll;
import dz.bououza.quickpoll.repository.PollRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PollController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PollControllerTests {

    @MockBean
    private PollRepository pollRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Poll poll;

    @BeforeEach
    public void setUp(){
        poll = new Poll();
        poll.setId(100L);
        poll.setQuestion("Question1");
        poll.setProposals(new HashSet<>(Arrays.asList(new Proposal(1000L, "option1"),
                new Proposal(1001L, "option2"))));
    }

    @Test
    public void givenPageable_whenGetAllPolls_thenReturnPageablePollsResult() throws Exception {
        //given


        Poll poll2 = new Poll();
        poll2.setId(101L);
        poll2.setQuestion("Question2");
        poll2.setProposals(new HashSet<>(Arrays.asList(new Proposal(2000L, "optionA"),
                new Proposal(2001L, "optionB"))));

        Page<Poll> pollPage = new PageImpl<>(Arrays.asList(poll, poll2));

        given(pollRepository.findAll(any(Pageable.class))).willReturn(pollPage);

        //when
        ResultActions response = mockMvc.perform(get("/polls"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.last",is(true)))
                .andExpect(jsonPath("$.totalElements",is(2)));
    }

    @Test
    public void givenNewPoll_whenCreatePoll_thenReturn201() throws Exception {

        //given
        given(pollRepository.save(any(Poll.class))).willReturn(poll);

        //when
        ResultActions response = mockMvc.perform(post("/polls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(poll)));

        //then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location","http://localhost/polls/"+poll.getId()));

    }

    @Test
    public void givenExistingPollId_whenGetPoll_thenReturnPoll() throws Exception {
        //given
        given(pollRepository.findById(any(Long.class))).willReturn(Optional.of(poll));

        //when
        ResultActions response = mockMvc.perform(get("/polls/{pollId}",poll.getId()));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id",is(poll.getId().intValue())))
                .andExpect(jsonPath("$.question",is(poll.getQuestion())))
                .andExpect(jsonPath("$.proposals.size()",is(2)));
    }

    @Test
    public void givenNotExistingPollId_whenGetPoll_thenReturn400() throws Exception {
        //given
        given(pollRepository.findById(any(Long.class))).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/polls/{pollId}",poll.getId()));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void givenPollAndPollId_whenUpdatePoll_thenReturn200() throws Exception {
        //given
        given(pollRepository.save(any(Poll.class))).willReturn(poll);

        //when
        ResultActions response= mockMvc.perform(put("/polls/{pollId}",poll.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(poll)));
        //then
        response.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void givenPollId_whenCallDeletePoll_thenReturn204() throws Exception {
        //given
        willDoNothing().given(pollRepository).deleteById(any(Long.class));

        //when
        ResultActions response = mockMvc.perform(delete("/polls/{pollId}",poll.getId()));

        //then
        response.andExpect(status().isNoContent()).andDo(print());

    }

    
}
