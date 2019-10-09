package tree.spring.data.neo4j.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NodeEntity
public class Node {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Relationship(type = "children")
    private List<Node> children;

    public Node(String name) {
        this.name = name;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id) &&
                Objects.equals(name, node.name) &&
                Objects.equals(children, node.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, children);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
