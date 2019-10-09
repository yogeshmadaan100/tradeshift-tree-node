package tree.spring.data.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import tree.spring.data.neo4j.model.Node;

import java.util.Collection;

public interface NodeRepository extends Neo4jRepository<Node, Long> {

    Node findByName(@Param("name") String title);

    @Query("MATCH (p:Node)-[r:children]->(n:Node) WHERE n.name = {name} RETURN p")
    Collection<Node> getParent(@Param("name") String name);

    @Query("MATCH (p:Node)-[r:children]->(n:Node) WHERE p.name = {name} RETURN n")
    Collection<Node> getChildren(@Param("name") String name);

    @Query("MATCH ()-[r:children]-(n:Node) WHERE n.name = {name} DELETE r")
    Collection<Node> deleteRelation(@Param("name") String name);

    @Query("MATCH (n:Node) DETACH DELETE n")
    void deleteAllNode();
}
