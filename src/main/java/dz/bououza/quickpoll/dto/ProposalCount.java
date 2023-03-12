package dz.bououza.quickpoll.dto;

public class ProposalCount {
    private Long proposalId;
    private int count;

    public ProposalCount() {
    }

    public ProposalCount(Long proposalId, int count) {
        this.proposalId = proposalId;
        this.count = count;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
