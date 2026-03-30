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

import static org.bigraphs.framework.core.factory.BigraphFactory.pureSignatureBuilder;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.bigraphs.framework.core.factory.BigraphFactory;
import org.bigraphs.framework.core.impl.BigraphEntity;
import org.bigraphs.framework.core.impl.signature.DynamicControl;
import org.bigraphs.framework.core.impl.signature.DynamicSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for PureBigraphMutable navigation methods.
 */
public class PureBigraphMutableNavigationTest {
    
    private DynamicSignature signature;
    
    @BeforeEach
    public void setup() {
        signature = pureSignatureBuilder()
            .add("Room", 1)
            .add("Computer", 0)
            .create();
    }
    
    @Test
    public void testGetChildrenOf_AfterAddingNode() {
        PureBigraphBuilder<DynamicSignature> builder = BigraphFactory.pureBuilder(signature);
        PureBigraphMutable bigraph = builder.createMutable();
        
        // 1. Add Root
        BigraphEntity.RootEntity root = bigraph.addRoot();
        
        // 2. Add Parent Node (Room)
        DynamicControl roomControl = signature.getControlByName("Room");
        BigraphEntity.NodeEntity<DynamicControl> room = bigraph.addNode(root, roomControl, "room1");
        
        // 3. Add Child Node (Computer)
        DynamicControl computerControl = signature.getControlByName("Computer");
        BigraphEntity.NodeEntity<DynamicControl> computer = bigraph.addNode(room, computerControl, "pc1");
        
        // 4. Verify getChildrenOf(room) contains computer
        List<BigraphEntity<?>> children = bigraph.getChildrenOf(room);
        assertTrue(children.contains(computer), "Room should contain Computer as child");
        assertEquals(1, children.size(), "Room should have exactly 1 child");
        
        // 5. Verify getParent(computer) returns room
        BigraphEntity<?> parent = bigraph.getParent(computer);
        assertEquals(room, parent, "Computer's parent should be Room");
    }
}
