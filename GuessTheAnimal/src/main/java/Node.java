import com.fasterxml.jackson.annotation.JsonIgnore;

public class Node {
    public String value;
    public Node no;
    public Node yes;
    public String facts;

    public Node(){
    }

    public Node(String value, String facts) {
        this.value = value;
        this.facts = facts;
        this.no = this.yes = null;
    }
    @JsonIgnore
    public boolean isQuestion() {
        return this.yes != null && this.no != null;
    }
}