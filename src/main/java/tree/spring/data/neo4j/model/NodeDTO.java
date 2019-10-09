package tree.spring.data.neo4j.model;

import java.util.Objects;

public class NodeDTO {

	private Long id;
	private String rootName;
	private String parentName;
	private Integer height;

	public NodeDTO(Long id, String rootName, String parentName, Integer height) {
		this.id = id;
		this.rootName = rootName;
		this.parentName = parentName;
		this.height = height;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NodeDTO nodeDTO = (NodeDTO) o;
		return Objects.equals(id, nodeDTO.id) &&
				Objects.equals(rootName, nodeDTO.rootName) &&
				Objects.equals(parentName, nodeDTO.parentName) &&
				Objects.equals(height, nodeDTO.height);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, rootName, parentName, height);
	}

	@Override
	public String toString() {
		return "NodeDTO{" +
				"id=" + id +
				", rootName='" + rootName + '\'' +
				", parentName='" + parentName + '\'' +
				", height=" + height +
				'}';
	}
}
