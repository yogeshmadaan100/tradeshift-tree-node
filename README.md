# tradeshift-tree-node
Coding assignment for Tradeshift

## What is the problem ?

We in Amazing Co need to model how our company is structured so we can do awesome stuff.

We have a root node (only one) and several children nodes,each one with its own children as well. It's a tree-based structure.

Something like:

         root
        /    \
       a      b
       |
       c

We need two HTTP APIs that will serve the two basic operations:

1) Get all children nodes of a given node (the given node can be anyone in the tree structure).
2) Change the parent node of a given node (the given node can be anyone in the tree structure).

They need to answer quickly, even with tons of nodes. Also,we can't afford to lose this information, so some sort of persistence is required.

Each node should have the following info:

1) node identification
2) who is the parent node
3) who is the root node
4) the height of the node. In the above example,height(root) = 0 and height(a) == 1.

# My Solution

## The Stack

* Application Type: Spring-Boot Java Web Application with embedded tomcat
* Web framework: Spring-Boot enabled Spring-WebMVC, Rest Controllers
* Persistence Access: Spring-Data-Neo4j 5.0.5
* Database: Neo4j-Server 3.5.1
* Build tools and Dependency management: Maven
* Deployment : Docker Container

## Build and Deploy Process
docker-compose up

## Endpoints:
Prerequisite

Reset Everything
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET  "http://localhost:8085/node/reset"
```
Add Node
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET  "http://localhost:8085/node/add?name=Child7&parentName=Child6"
```

get All Children
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET  http://localhost:8085/node/getChildren?name=Root
```
UpdateParent
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET  "http://localhost:8085/node/updateParent?name=Child7&parentName=Child6"
```


#### what is happen after changing the parent of Child?
All the children of the child become the children of the old parent ie grandson becomes son

### Visualising the graph
With neo4j you can visualise graph at any time at http://localhost:7474/browser/

## TODO
* Add benchmarking
* Add more tests and fix tests integration
* Introduce validation in service