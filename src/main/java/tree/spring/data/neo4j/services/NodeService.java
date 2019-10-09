package tree.spring.data.neo4j.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tree.spring.data.neo4j.model.Node;
import tree.spring.data.neo4j.model.NodeDTO;
import tree.spring.data.neo4j.repositories.NodeRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class NodeService {
    private final NodeRepository nodeRepository;

    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Transactional
    public void add(String name, String parentName) {
        Node currentNode = new Node(name);
        Node newParent = nodeRepository.findByName(parentName);
        Collection<Node> newParentCol = nodeRepository.getChildren(parentName);
        nodeRepository.save(currentNode);
        newParentCol.add(currentNode);
        newParent.setChildren(new ArrayList<>(newParentCol));
        nodeRepository.save(newParent);
    }

    @Transactional
    public void updateParent(String name, String parentName) {
        Node currentNode = nodeRepository.findByName(name);
        Collection<Node> parents = nodeRepository.getParent(name);
        Iterator<Node> parentsIterator = parents.iterator();
        Node oldParent = parentsIterator.next();
        Collection<Node> oldParentChildrenCol = nodeRepository.getChildren(oldParent.getName());

        List<Node> oldParentChildrenList = new ArrayList<>(oldParentChildrenCol);
        oldParentChildrenList.remove(currentNode);


        Collection<Node> currentNodeChildrenCol = nodeRepository.getChildren(name);
        Iterator<Node> currentNodeChildrenItr = currentNodeChildrenCol.iterator();
        while (currentNodeChildrenItr.hasNext()) {
            Node currentNodeChild = currentNodeChildrenItr.next();
            nodeRepository.save(currentNodeChild);
            oldParentChildrenList.add(currentNodeChild);
        }
        oldParent.setChildren(oldParentChildrenList);
        nodeRepository.deleteRelation(name);
        nodeRepository.save(oldParent);


        Node newParent = nodeRepository.findByName(parentName);
        Collection<Node> newParentCol = nodeRepository.getChildren(parentName);
        nodeRepository.save(currentNode);
        newParentCol.add(currentNode);
        newParent.setChildren(new ArrayList<>(newParentCol));
        nodeRepository.save(newParent);
    }

    int calculateHeight(String name) {
        Collection<Node> parents = nodeRepository.getParent(name);
        int height = 0;
        while (parents.iterator().hasNext()) {
            Node nextParent = parents.iterator().next();
            parents = nodeRepository.getParent(nextParent.getName());
            height++;
        }
        return height;
    }

    private String getRoot(String name) {
        Node currentNode = nodeRepository.findByName(name);
        Collection<Node> parents = nodeRepository.getParent(name);
        Node root = currentNode;
        while (parents.iterator().hasNext()) {
            Node nextParent = parents.iterator().next();
            parents = nodeRepository.getParent(nextParent.getName());
            root = nextParent;
        }
        return root.getName();
    }

    @Transactional(readOnly = true)
    public Node getParentNode(String name) {
        Collection<Node> parents = nodeRepository.getParent(name);
        Iterator<Node> iterator = parents.iterator();
        if (iterator.hasNext())
            return iterator.next();
        else
            return nodeRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<NodeDTO> getChildren(String name) {
        Collection<Node> nodes = nodeRepository.getChildren(name);

        List<NodeDTO> nodeDTOList = new ArrayList<>();
        nodes.iterator().forEachRemaining(currentNode ->  nodeDTOList.add(new NodeDTO(currentNode.getId(), getRoot(currentNode.getName()), getParentNode(currentNode.getName()).getName(), calculateHeight(currentNode.getName()))));
        return nodeDTOList;
    }

    @Transactional
    public void reset() {
        nodeRepository.deleteAllNode();
        nodeRepository.save(new Node("Root"));
    }
}
