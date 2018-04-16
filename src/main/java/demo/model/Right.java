package demo.model;

import javax.persistence.*;

@Entity
@Table(name = "my_user_rights")
public class Right {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name = "right_string")
    private String right;

    public Right(){
    }

    public Right(Long id, String right) {
        this.id = id;
        this.right = right;
    }

    public Right(String right) {
        this.right = right;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

}
