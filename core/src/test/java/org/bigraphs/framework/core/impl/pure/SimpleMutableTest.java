/*
 * Copyright (c) 2026 Bigraph Toolkit Suite Developers
 * Main Developer: Dominik Grzelak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bigraphs.framework.core.impl.pure;

import static org.junit.jupiter.api.Assertions.*;

import org.bigraphs.framework.core.factory.BigraphFactory;
import org.bigraphs.framework.core.impl.BigraphEntity;
import org.bigraphs.framework.core.impl.signature.DynamicControl;
import org.bigraphs.framework.core.impl.signature.DynamicSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple test for PureBigraphMutable - tests basic mutable operations.
 */
public class SimpleMutableTest {
    
    private DynamicSignature signature;
    
    @BeforeEach
    public void setup() {
        // Create signature with controls
        signature = BigraphFactory.pureSignatureBuilder()
            .add("Room", 1)
            .add("Computer", 0)
            .create();
    }
    
    @Test
    public void testCreateMutable() {
        // Create builder
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        
        // Create mutable bigraph
        PureBigraphMutable bigraph = builder.createMutable();
        
        assertNotNull(bigraph, "Mutable bigraph should not be null");
        assertTrue(bigraph instanceof PureBigraph, "Should be instance of PureBigraph");
    }
    
    @Test
    public void testAddRoot() {
        // Create empty mutable bigraph
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();
        
        assertEquals(0, bigraph.getRoots().size(), "Should start with 0 roots");
        
        // Add root
        BigraphEntity.RootEntity newRoot = bigraph.addRoot();
        
        assertNotNull(newRoot, "New root should not be null");
        assertEquals(1, bigraph.getRoots().size(), "Should have 1 root");
    }
    
    @Test
    public void testAddNodeToRoot() {
        // Create bigraph with root
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        builder.root(); // Create root
        PureBigraphMutable bigraph = builder.createMutable();
        
        // Get root
        BigraphEntity.RootEntity root = (BigraphEntity.RootEntity) bigraph.getRoots().toArray()[0];
        
        // Get control
        DynamicControl roomControl = (DynamicControl) signature.getControlByName("Room");
        
        // Add node
        BigraphEntity.NodeEntity<DynamicControl> newNode = 
            bigraph.addNode(root, roomControl, "room_1");
        
        assertNotNull(newNode, "New node should not be null");
        assertEquals(1, bigraph.getNodes().size(), "Should have 1 node");
    }
    
    @Test
    public void testAddEdge() {
        // Create mutable bigraph
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();
        
        assertEquals(0, bigraph.getEdges().size(), "Should start with 0 edges");
        
        // Add edge
        BigraphEntity.Edge newEdge = bigraph.addEdge();
        
        assertNotNull(newEdge, "New edge should not be null");
        assertEquals(1, bigraph.getEdges().size(), "Should have 1 edge");
    }
    
    @Test
    public void testAddOuterName() {
        // Create mutable bigraph
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();
        
        assertEquals(0, bigraph.getOuterNames().size(), "Should start with 0 outer names");
        
        // Add outer name
        BigraphEntity.OuterName newOuterName = bigraph.addOuterName("network");
        
        assertNotNull(newOuterName, "New outer name should not be null");
        assertEquals(1, bigraph.getOuterNames().size(), "Should have 1 outer name");
        assertEquals("network", newOuterName.getName(), "Outer name should match");
    }
    
    @Test
    public void testChangeListener() {
        // Create bigraph with root
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        builder.root(); // Create root
        PureBigraphMutable bigraph = builder.createMutable();
        
        // Track events
        final boolean[] eventFired = {false};
        
        bigraph.addChangeListener(new BigraphChangeListener() {
            @Override
            public void onNodeAdded(BigraphChangeEvent event) {
                eventFired[0] = true;
            }
        });
        
        // Add node
        BigraphEntity.RootEntity root = (BigraphEntity.RootEntity) bigraph.getRoots().toArray()[0];
        DynamicControl roomControl = (DynamicControl) signature.getControlByName("Room");
        bigraph.addNode(root, roomControl, "test");
        
        assertTrue(eventFired[0], "Event should have been fired");
    }
    
    @Test
    public void testRemoveNode() {
        // Create bigraph with root and node
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        builder.root().child("Room");
        PureBigraphMutable bigraph = builder.createMutable();
        
        assertEquals(1, bigraph.getNodes().size(), "Should start with 1 node");
        
        // Get and remove the node
        BigraphEntity.NodeEntity<DynamicControl> node = 
            (BigraphEntity.NodeEntity<DynamicControl>) bigraph.getNodes().toArray()[0];
        bigraph.removeNode(node);
        
        assertEquals(0, bigraph.getNodes().size(), "Should have 0 nodes after removal");
    }
    
    @Test
    public void testAddInnerName() {
        // Create mutable bigraph
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();
        
        assertEquals(0, bigraph.getInnerNames().size(), "Should start with 0 inner names");
        
        // Add inner name
        BigraphEntity.InnerName newInnerName = bigraph.addInnerName("sensor_network");
        
        assertNotNull(newInnerName, "New inner name should not be null");
        assertEquals(1, bigraph.getInnerNames().size(), "Should have 1 inner name");
        assertEquals("sensor_network", newInnerName.getName(), "Inner name should match");
    }
    
    @Test
    public void testRemoveInnerName() {
        // Create bigraph with inner name
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        builder.createInner("test_inner");
        PureBigraphMutable bigraph = builder.createMutable();
        
        assertEquals(1, bigraph.getInnerNames().size(), "Should start with 1 inner name");
        
        // Get and remove the inner name
        BigraphEntity.InnerName innerName = 
            (BigraphEntity.InnerName) bigraph.getInnerNames().toArray()[0];
        bigraph.removeInnerName(innerName);
        
        assertEquals(0, bigraph.getInnerNames().size(), "Should have 0 inner names after removal");
    }
    
    @Test
    public void testAddMultipleRoots() {
        // Create bigraph with one root
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        builder.root();
        PureBigraphMutable bigraph = builder.createMutable();
        
        assertEquals(1, bigraph.getRoots().size(), "Should start with 1 root");
        
        // Add another root
        BigraphEntity.RootEntity newRoot = bigraph.addRoot();
        
        assertNotNull(newRoot, "New root should not be null");
        assertEquals(2, bigraph.getRoots().size(), "Should have 2 roots");
        
        // Add node to new root
        DynamicControl roomControl = (DynamicControl) signature.getControlByName("Room");
        BigraphEntity.NodeEntity<DynamicControl> node = 
            bigraph.addNode(newRoot, roomControl, "room_in_second_root");
        
        assertNotNull(node, "Node should be added to second root");
        assertEquals(1, bigraph.getNodes().size(), "Should have 1 node");
    }

    @Test
    public void testCloseInnerRemovesInnerAndKeepsOuter() {
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();

        BigraphEntity.OuterName outer = bigraph.addOuterName("o");
        BigraphEntity.InnerName inner = bigraph.addInnerName("i");
        bigraph.connectInnerNameToLink(inner, outer);

        assertEquals(1, bigraph.getInnerNames().size(), "Should start with 1 inner name");
        assertEquals(1, bigraph.getOuterNames().size(), "Should start with 1 outer name");
        assertNotNull(bigraph.getLinkOfPoint(inner), "Inner should be linked before close");

        bigraph.closeInner(inner);

        assertEquals(0, bigraph.getInnerNames().size(), "Inner should be removed by closeInner");
        assertEquals(1, bigraph.getOuterNames().size(), "Outer should remain after closeInner");
    }

    @Test
    public void testCloseOuterRemovesOuterAndKeepsInnerDisconnected() {
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();

        BigraphEntity.OuterName outer = bigraph.addOuterName("o");
        BigraphEntity.InnerName inner = bigraph.addInnerName("i");
        bigraph.connectInnerNameToLink(inner, outer);

        assertEquals(1, bigraph.getInnerNames().size(), "Should start with 1 inner name");
        assertEquals(1, bigraph.getOuterNames().size(), "Should start with 1 outer name");
        assertNotNull(bigraph.getLinkOfPoint(inner), "Inner should be linked before close");

        bigraph.closeOuter(outer);

        assertEquals(1, bigraph.getInnerNames().size(), "Inner should remain after closeOuter");
        assertEquals(0, bigraph.getOuterNames().size(), "Outer should be removed by closeOuter");
        assertNull(bigraph.getLinkOfPoint(inner), "Inner should be disconnected after closeOuter");
    }

    @Test
    public void testCloseNameDispatchAndValidation() {
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();

        BigraphEntity.OuterName outer = bigraph.addOuterName("dispatch_outer");
        bigraph.closeName(outer);
        assertEquals(0, bigraph.getOuterNames().size(), "closeName should dispatch to closeOuter");

        BigraphEntity.RootEntity root = bigraph.addRoot();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> bigraph.closeName(root));
        assertTrue(ex.getMessage().contains("Unsupported name type"), "Should reject non-name entities");
    }

    @Test
    public void testCloseOuterDisconnectsConnectedNodePorts() {
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        builder.root().child("Room");
        PureBigraphMutable bigraph = builder.createMutable();

        @SuppressWarnings("unchecked")
        BigraphEntity.NodeEntity<DynamicControl> node =
            (BigraphEntity.NodeEntity<DynamicControl>) bigraph.getNodes().toArray()[0];
        BigraphEntity.OuterName outer = bigraph.addOuterName("port_link");

        BigraphEntity.Port usedPort = bigraph.connectNodeToLink(node, outer);
        assertNotNull(usedPort, "Connecting node to outer should return the used port");
        assertNotNull(bigraph.getLinkOfPoint(usedPort), "Used port should be linked before closeOuter");

        bigraph.closeOuter(outer);

        assertEquals(0, bigraph.getOuterNames().size(), "Outer should be removed");
        assertNull(bigraph.getLinkOfPoint(usedPort), "Used port should be disconnected by closeOuter");
    }

    @Test
    public void testCloseInnerWorksForDisconnectedInner() {
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();

        BigraphEntity.InnerName inner = bigraph.addInnerName("idle_inner");
        assertNull(bigraph.getLinkOfPoint(inner), "Inner should start disconnected");

        bigraph.closeInner(inner);
        assertEquals(0, bigraph.getInnerNames().size(), "Disconnected inner should still be removable via closeInner");
    }

    @Test
    public void testCloseNameAndRoleSpecificNullChecks() {
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();

        assertThrows(IllegalArgumentException.class, () -> bigraph.closeName(null));
        assertThrows(IllegalArgumentException.class, () -> bigraph.closeInner((BigraphEntity.InnerName) null));
        assertThrows(IllegalArgumentException.class, () -> bigraph.closeOuter((BigraphEntity.OuterName) null));
    }

    @Test
    public void testCloseOuterOnIdleOuterName() {
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();

        BigraphEntity.OuterName outer = bigraph.addOuterName("idle_outer");
        assertTrue(bigraph.getPointsFromLink(outer).isEmpty(), "Outer should be idle before closeOuter");

        bigraph.closeOuter(outer);
        assertEquals(0, bigraph.getOuterNames().size(), "Idle outer should be removable");
    }
}
