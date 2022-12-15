package dz.bououza.quickpoll.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.Set;

@Entity
public class Poll {
    @Id
    @GeneratedValue
    @Column(name = "POLL_ID")
    private Long id;

    @Column(name = "QUESTION")
    @NotEmpty
    private String question;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "POLL_ID")
    @OrderBy
    @Size(min = 2,max = 6)
    private Set<Option> options;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poll poll = (Poll) o;
        return id.equals(poll.id) && Objects.equals(question, poll.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
