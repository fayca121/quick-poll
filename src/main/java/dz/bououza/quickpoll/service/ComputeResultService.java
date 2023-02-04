package dz.bououza.quickpoll.service;

import dz.bououza.quickpoll.dto.VoteResult;

public interface ComputeResultService {
    VoteResult computeResult(Long pollId);
}
