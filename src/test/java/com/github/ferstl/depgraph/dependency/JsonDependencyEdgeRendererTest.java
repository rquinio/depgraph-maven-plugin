package com.github.ferstl.depgraph.dependency;

import org.junit.Test;

import static com.github.ferstl.depgraph.dependency.DependencyNodeUtil.createDependencyNode;
import static com.github.ferstl.depgraph.dependency.DependencyNodeUtil.createDependencyNodeWithConflict;
import static org.junit.Assert.*;

public class JsonDependencyEdgeRendererTest {

  @Test
  public void renderWithoutVersion() {
    // arrange
    JsonDependencyEdgeRenderer renderer = new JsonDependencyEdgeRenderer(false);
    DependencyNode from = createDependencyNode("group1", "artifact1", "version1");
    DependencyNode to = createDependencyNode("group2", "artifact2", "version2");

    // act
    String result = renderer.render(from, to);

    // assert
    assertEquals("{\"resolution\":\"INCLUDED\"}", result);
  }

  @Test
  public void renderWithNonConflictingVersion() {
    // arrange
    JsonDependencyEdgeRenderer renderer = new JsonDependencyEdgeRenderer(true);
    DependencyNode from = createDependencyNode("group1", "artifact1", "version1");
    DependencyNode to = createDependencyNode("group2", "artifact2", "version2");

    // act
    String result = renderer.render(from, to);

    // assert
    assertEquals("{\"resolution\":\"INCLUDED\"}", result);
  }

  @Test
  public void renderWithConflictingVersion() {
    // arrange
    JsonDependencyEdgeRenderer renderer = new JsonDependencyEdgeRenderer(true);
    DependencyNode from = createDependencyNode("group1", "artifact1", "version1");
    DependencyNode to = createDependencyNodeWithConflict("group2", "artifact2", "version2");

    // act
    String result = renderer.render(from, to);

    // assert
    assertEquals("{\"version\":\"version2\",\"resolution\":\"OMITTED_FOR_CONFLICT\"}", result);
  }
}
