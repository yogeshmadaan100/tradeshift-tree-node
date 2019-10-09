package tree.spring.data.neo4j.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tree.spring.data.neo4j.model.NodeDTO;
import tree.spring.data.neo4j.services.NodeService;

import java.util.List;


@RestController
@RequestMapping("/node/")
public class NodeController {

    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping("/add")
    public void add(@RequestParam(value = "name") String name, @RequestParam(value = "parentName") String parentName) {
        nodeService.add(name, parentName);
    }

    @GetMapping("/updateParent")
    public void updateParent(@RequestParam(value = "name") String name, @RequestParam(value = "parentName") String parentName) {
        nodeService.updateParent(name, parentName);
    }

    @GetMapping("/getChildren")
    public List<NodeDTO> getChildren(@RequestParam(value = "name") String name) {
        return nodeService.getChildren(name);
    }

    @GetMapping("/reset")
    public void reset() {
        nodeService.reset();
    }
}
