package com.github.ferstl.depgraph;

import java.io.File;
import java.nio.file.FileSystems;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.takari.maven.testing.TestResources;
import io.takari.maven.testing.executor.MavenExecutionResult;
import io.takari.maven.testing.executor.MavenRuntime;
import io.takari.maven.testing.executor.MavenVersions;
import io.takari.maven.testing.executor.junit.MavenJUnitTestRunner;

import static io.takari.maven.testing.TestResources.assertFileContents;
import static io.takari.maven.testing.TestResources.assertFilesPresent;

@RunWith(MavenJUnitTestRunner.class)
@MavenVersions({"3.5.0", "3.3.9"})
public class MergeOptionsIntegrationTest {

  @Rule
  public final TestResources resources = new TestResources();

  private final MavenRuntime mavenRuntime;

  public MergeOptionsIntegrationTest(MavenRuntime.MavenRuntimeBuilder builder) throws Exception {
    this.mavenRuntime = builder.build();
  }

  @Before
  public void before() {
    // Workaround for https://github.com/takari/takari-plugin-testing-project/issues/14
    FileSystems.getDefault();
  }

  @Test
  public void graphNoMerge() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .execute("clean", "package", "depgraph:graph");

    result.assertErrorFreeLog();
    assertFilesPresent(
        basedir,
        "module-1/target/dependency-graph.dot",
        "module-2/target/dependency-graph.dot",
        "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/graph_module-1_no-merge.dot", "module-1/target/dependency-graph.dot");
  }

  @Test
  public void aggregateNoMerge() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .execute("clean", "package", "depgraph:aggregate");

    result.assertErrorFreeLog();
    assertFilesPresent(basedir, "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/aggregate_no-merge.dot", "target/dependency-graph.dot");
  }

  @Test
  public void aggregateByGroupIdNoMerge() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .execute("clean", "package", "depgraph:aggregate-by-groupid");

    result.assertErrorFreeLog();
    assertFilesPresent(basedir, "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/aggregate-by-groupid_no-merge.dot", "target/dependency-graph.dot");
  }

  @Test
  public void aggregateMergeScopes() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .withCliOption("-DmergeScopes=true")
        .execute("clean", "package", "depgraph:aggregate");

    result.assertErrorFreeLog();
    assertFilesPresent(basedir, "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/aggregate_mergeScopes.dot", "target/dependency-graph.dot");
  }

  @Test
  public void aggregateByGroupIdMergeScopes() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .withCliOption("-DmergeScopes=true")
        .execute("clean", "package", "depgraph:aggregate-by-groupid");

    result.assertErrorFreeLog();
    assertFilesPresent(basedir, "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/aggregate-by-groupid_mergeScopes.dot", "target/dependency-graph.dot");
  }

  @Test
  public void graphMergeClassifiers() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .withCliOption("-DmergeClassifiers=true")
        .execute("clean", "package", "depgraph:graph");

    result.assertErrorFreeLog();
    assertFilesPresent(
        basedir,
        "module-1/target/dependency-graph.dot",
        "module-2/target/dependency-graph.dot",
        "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/graph_module-1_mergeClassifiers.dot", "module-1/target/dependency-graph.dot");
  }

  @Test
  public void graphMergeTypes() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .withCliOption("-DmergeTypes=true")
        .execute("clean", "package", "depgraph:graph");

    result.assertErrorFreeLog();
    assertFilesPresent(
        basedir,
        "module-1/target/dependency-graph.dot",
        "module-2/target/dependency-graph.dot",
        "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/graph_module-1_mergeTypes.dot", "module-1/target/dependency-graph.dot");
  }

  @Test
  public void graphMergeTypesAndClassifiers() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .withCliOption("-DmergeTypes=true")
        .withCliOption("-DmergeClassifiers=true")
        .execute("clean", "package", "depgraph:graph");

    result.assertErrorFreeLog();
    assertFilesPresent(
        basedir,
        "module-1/target/dependency-graph.dot",
        "module-2/target/dependency-graph.dot",
        "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/graph_module-1_mergeTypes-mergeClassifiers.dot", "module-1/target/dependency-graph.dot");
  }

  @Test
  public void aggregateMergeClassifiers() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .withCliOption("-DmergeClassifiers=true")
        .execute("clean", "package", "depgraph:aggregate");

    result.assertErrorFreeLog();
    assertFilesPresent(basedir, "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/aggregate_mergeClassifiers.dot", "target/dependency-graph.dot");
  }

  @Test
  public void aggregateMergeTypes() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .withCliOption("-DmergeTypes=true")
        .execute("clean", "package", "depgraph:aggregate");

    result.assertErrorFreeLog();
    assertFilesPresent(basedir, "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/aggregate_mergeTypes.dot", "target/dependency-graph.dot");
  }

  @Test
  public void aggregateMergeTypesAndClassifiers() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .withCliOption("-DmergeTypes=true")
        .withCliOption("-DmergeClassifiers=true")
        .execute("clean", "package", "depgraph:aggregate");

    result.assertErrorFreeLog();
    assertFilesPresent(basedir, "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/aggregate_mergeTypes-mergeClassifiers.dot", "target/dependency-graph.dot");
  }

  @Test
  public void aggregateMergeTypesAndClassifiersAndScopes() throws Exception {
    File basedir = this.resources.getBasedir("merge-test");
    MavenExecutionResult result = this.mavenRuntime
        .forProject(basedir)
        .withCliOption("-DmergeTypes=true")
        .withCliOption("-DmergeClassifiers=true")
        .withCliOption("-DmergeScopes=true")
        .execute("clean", "package", "depgraph:aggregate");

    result.assertErrorFreeLog();
    assertFilesPresent(basedir, "target/dependency-graph.dot");

    assertFileContents(basedir, "expectations/aggregate_mergeTypes-mergeClassifiers-mergeScopes.dot", "target/dependency-graph.dot");
  }
}
