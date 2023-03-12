package dz.bououza.quickpoll.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROPOSAL_ID")
    private Long id;

    @Column(name = "PROPOSAL_VALUE")
    private String value;

    public Proposal() {
    }

    public Proposal(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Proposal(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposal proposal = (Proposal) o;
        return id.equals(proposal.id) && Objects.equals(value, proposal.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }
}
