package dz.bououza.quickpoll.dto;

import java.util.Collection;

public record VoteResult(int totalVotes, Collection<ProposalCount> results) {
}

