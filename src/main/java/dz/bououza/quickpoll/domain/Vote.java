package dz.bououza.quickpoll.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Vote {
    @Id
    @GeneratedValue
    @Column(name = "VOTE_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "OPTION_ID")
    private Option option;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return id.equals(vote.id) && Objects.equals(option, vote.option);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
