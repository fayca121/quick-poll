package dz.bououza.quickpoll.controller;

import dz.bououza.quickpoll.domain.Option;
import dz.bououza.quickpoll.domain.Poll;
import dz.bououza.quickpoll.repository.PollRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PollController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PollControllerTests {

    @MockBean
    private PollRepository pollRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenPageable_whenGetAllPolls_thenReturnPageablePollsResult() throws Exception {
        //given
        Poll poll1 = new Poll();
        poll1.setId(100L);
        poll1.setQuestion("Question1");
        poll1.setOptions(new HashSet<>(Arrays.asList(new Option(1000L, "option1"),
                new Option(1001L, "option2"))));

        Poll poll2 = new Poll();
        poll2.setId(101L);
        poll2.setQuestion("Question2");
        poll2.setOptions(new HashSet<>(Arrays.asList(new Option(2000L, "optionA"),
                new Option(2001L, "optionB"))));

        Page<Poll> pollPage = new PageImpl<>(Arrays.asList(poll1, poll2));

        given(pollRepository.findAll(any(Pageable.class))).willReturn(pollPage);

        //when
        ResultActions response = mockMvc.perform(get("/polls"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.last",is(true)))
                .andExpect(jsonPath("$.totalElements",is(2)));
    }
}
