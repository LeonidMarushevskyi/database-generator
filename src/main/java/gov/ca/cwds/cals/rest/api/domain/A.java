package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A A.
 */
@Entity
@Table(name = "a")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class A implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(min = 1, max = 42)
    @Column(name = "name", length = 42)
    private String name;

    @Size(min = 20, max = 40)
    @Lob
    @Column(name = "content")
    private String content;

    @Min(value = 0)
    @Max(value = 41)
    @Column(name = "count")
    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public A name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public A content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public A count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        A a = (A) o;
        if (a.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, a.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "A{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", content='" + content + "'" +
            ", count='" + count + "'" +
            '}';
    }
}
