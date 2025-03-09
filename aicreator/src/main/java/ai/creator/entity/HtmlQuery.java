package ai.creator.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "html_queries")
public class HtmlQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    private LocalDateTime timestamp = LocalDateTime.now();

    // Constructors
    public HtmlQuery() {}
    public HtmlQuery(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setQuestion(String question) { this.question = question; }
    public void setAnswer(String answer) { this.answer = answer; }
}

