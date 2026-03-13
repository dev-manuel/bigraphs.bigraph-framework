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

import org.bigraphs.framework.core.impl.BigraphEntity;
import org.bigraphs.framework.core.impl.signature.DynamicControl;

/**
 * Base class for bigraph change events.
 * 
 * <p>Contains information about structural changes to a bigraph, such as
 * nodes being added, removed, or moved.</p>
 * 
 * @author AI Assistant
 * @see BigraphChangeListener
 * @see PureBigraphMutable
 */
public abstract class BigraphChangeEvent {
    
    private final PureBigraphMutable source;
    
    protected BigraphChangeEvent(PureBigraphMutable source) {
        this.source = source;
    }
    
    /**
     * Returns the bigraph that was modified.
     * 
     * @return the source bigraph
     */
    public PureBigraphMutable getSource() {
        return source;
    }
    
    // ============================================
    // NODE EVENTS
    // ============================================
    
    /**
     * Event fired when a node is added to the bigraph.
     */
    public static class NodeAdded extends BigraphChangeEvent {
        private final BigraphEntity.NodeEntity<DynamicControl> node;
        private final BigraphEntity<?> parent;
        
        public NodeAdded(PureBigraphMutable source, BigraphEntity.NodeEntity<DynamicControl> node, BigraphEntity<?> parent) {
            super(source);
            this.node = node;
            this.parent = parent;
        }
        
        public BigraphEntity.NodeEntity<DynamicControl> getNode() {
            return node;
        }
        
        public BigraphEntity<?> getParent() {
            return parent;
        }
    }
    
    /**
     * Event fired when a node is removed from the bigraph.
     */
    public static class NodeRemoved extends BigraphChangeEvent {
        private final BigraphEntity.NodeEntity<DynamicControl> node;
        private final BigraphEntity<?> parent;
        
        public NodeRemoved(PureBigraphMutable source, BigraphEntity.NodeEntity<DynamicControl> node, BigraphEntity<?> parent) {
            super(source);
            this.node = node;
            this.parent = parent;
        }
        
        public BigraphEntity.NodeEntity<DynamicControl> getNode() {
            return node;
        }
        
        public BigraphEntity<?> getParent() {
            return parent;
        }
    }
    
    /**
     * Event fired when a node is moved to a different parent.
     */
    public static class NodeMoved extends BigraphChangeEvent {
        private final BigraphEntity.NodeEntity<DynamicControl> node;
        private final BigraphEntity<?> oldParent;
        private final BigraphEntity<?> newParent;
        
        public NodeMoved(PureBigraphMutable source, BigraphEntity.NodeEntity<DynamicControl> node, 
                        BigraphEntity<?> oldParent, BigraphEntity<?> newParent) {
            super(source);
            this.node = node;
            this.oldParent = oldParent;
            this.newParent = newParent;
        }
        
        public BigraphEntity.NodeEntity<DynamicControl> getNode() {
            return node;
        }
        
        public BigraphEntity<?> getOldParent() {
            return oldParent;
        }
        
        public BigraphEntity<?> getNewParent() {
            return newParent;
        }
    }
    
    // ============================================
    // ROOT EVENTS
    // ============================================
    
    /**
     * Event fired when a root is added to the bigraph.
     */
    public static class RootAdded extends BigraphChangeEvent {
        private final BigraphEntity.RootEntity root;
        
        public RootAdded(PureBigraphMutable source, BigraphEntity.RootEntity root) {
            super(source);
            this.root = root;
        }
        
        public BigraphEntity.RootEntity getRoot() {
            return root;
        }
    }
    
    /**
     * Event fired when a root is removed from the bigraph.
     */
    public static class RootRemoved extends BigraphChangeEvent {
        private final BigraphEntity.RootEntity root;
        
        public RootRemoved(PureBigraphMutable source, BigraphEntity.RootEntity root) {
            super(source);
            this.root = root;
        }
        
        public BigraphEntity.RootEntity getRoot() {
            return root;
        }
    }
    
    // ============================================
    // LINK EVENTS
    // ============================================
    
    /**
     * Event fired when an edge is added to the bigraph.
     */
    public static class EdgeAdded extends BigraphChangeEvent {
        private final BigraphEntity.Edge edge;
        
        public EdgeAdded(PureBigraphMutable source, BigraphEntity.Edge edge) {
            super(source);
            this.edge = edge;
        }
        
        public BigraphEntity.Edge getEdge() {
            return edge;
        }
    }
    
    /**
     * Event fired when an edge is removed from the bigraph.
     */
    public static class EdgeRemoved extends BigraphChangeEvent {
        private final BigraphEntity.Edge edge;
        
        public EdgeRemoved(PureBigraphMutable source, BigraphEntity.Edge edge) {
            super(source);
            this.edge = edge;
        }
        
        public BigraphEntity.Edge getEdge() {
            return edge;
        }
    }
    
    /**
     * Event fired when an outer name is added to the bigraph.
     */
    public static class OuterNameAdded extends BigraphChangeEvent {
        private final BigraphEntity.OuterName outerName;
        
        public OuterNameAdded(PureBigraphMutable source, BigraphEntity.OuterName outerName) {
            super(source);
            this.outerName = outerName;
        }
        
        public BigraphEntity.OuterName getOuterName() {
            return outerName;
        }
    }
    
    /**
     * Event fired when an outer name is removed from the bigraph.
     */
    public static class OuterNameRemoved extends BigraphChangeEvent {
        private final BigraphEntity.OuterName outerName;
        
        public OuterNameRemoved(PureBigraphMutable source, BigraphEntity.OuterName outerName) {
            super(source);
            this.outerName = outerName;
        }
        
        public BigraphEntity.OuterName getOuterName() {
            return outerName;
        }
    }
    
    /**
     * Event fired when a port is connected to a link.
     */
    public static class LinkConnected extends BigraphChangeEvent {
        private final BigraphEntity.Port port;
        private final BigraphEntity.Link link;
        
        public LinkConnected(PureBigraphMutable source, BigraphEntity.Port port, BigraphEntity.Link link) {
            super(source);
            this.port = port;
            this.link = link;
        }
        
        public BigraphEntity.Port getPort() {
            return port;
        }
        
        public BigraphEntity.Link getLink() {
            return link;
        }
    }
    
    /**
     * Event fired when a port is disconnected from a link.
     */
    public static class LinkDisconnected extends BigraphChangeEvent {
        private final BigraphEntity.Port port;
        private final BigraphEntity.Link link;
        
        public LinkDisconnected(PureBigraphMutable source, BigraphEntity.Port port, BigraphEntity.Link link) {
            super(source);
            this.port = port;
            this.link = link;
        }
        
        public BigraphEntity.Port getPort() {
            return port;
        }
        
        public BigraphEntity.Link getLink() {
            return link;
        }
    }
    
    /**
     * Event fired when an inner name is added to the bigraph.
     */
    public static class InnerNameAdded extends BigraphChangeEvent {
        private final BigraphEntity.InnerName innerName;
        
        public InnerNameAdded(PureBigraphMutable source, BigraphEntity.InnerName innerName) {
            super(source);
            this.innerName = innerName;
        }
        
        public BigraphEntity.InnerName getInnerName() {
            return innerName;
        }
    }
    
    /**
     * Event fired when an inner name is removed from the bigraph.
     */
    public static class InnerNameRemoved extends BigraphChangeEvent {
        private final BigraphEntity.InnerName innerName;
        
        public InnerNameRemoved(PureBigraphMutable source, BigraphEntity.InnerName innerName) {
            super(source);
            this.innerName = innerName;
        }
        
        public BigraphEntity.InnerName getInnerName() {
            return innerName;
        }
    }
    
    // ============================================
    // SITE EVENTS
    // ============================================

    /**
     * Event fired when a site is added to the bigraph.
     */
    public static class SiteAdded extends BigraphChangeEvent {
        private final BigraphEntity.SiteEntity site;
        private final BigraphEntity<?> parent;

        public SiteAdded(PureBigraphMutable source, BigraphEntity.SiteEntity site, BigraphEntity<?> parent) {
            super(source);
            this.site = site;
            this.parent = parent;
        }

        public BigraphEntity.SiteEntity getSite() {
            return site;
        }

        public BigraphEntity<?> getParent() {
            return parent;
        }
    }

    /**
     * Event fired when a site is removed from the bigraph.
     */
    public static class SiteRemoved extends BigraphChangeEvent {
        private final BigraphEntity.SiteEntity site;
        private final BigraphEntity<?> parent;

        public SiteRemoved(PureBigraphMutable source, BigraphEntity.SiteEntity site, BigraphEntity<?> parent) {
            super(source);
            this.site = site;
            this.parent = parent;
        }

        public BigraphEntity.SiteEntity getSite() {
            return site;
        }

        public BigraphEntity<?> getParent() {
            return parent;
        }
    }

    /**
     * Event fired when an inner name is connected to a link.
     */
    public static class InnerNameLinked extends BigraphChangeEvent {
        private final BigraphEntity.InnerName innerName;
        private final BigraphEntity.Link link;
        
        public InnerNameLinked(PureBigraphMutable source, BigraphEntity.InnerName innerName, BigraphEntity.Link link) {
            super(source);
            this.innerName = innerName;
            this.link = link;
        }
        
        public BigraphEntity.InnerName getInnerName() {
            return innerName;
        }
        
        public BigraphEntity.Link getLink() {
            return link;
        }
    }
    
    /**
     * Event fired when an inner name is disconnected from a link.
     */
    public static class InnerNameUnlinked extends BigraphChangeEvent {
        private final BigraphEntity.InnerName innerName;
        private final BigraphEntity.Link link;
        
        public InnerNameUnlinked(PureBigraphMutable source, BigraphEntity.InnerName innerName, BigraphEntity.Link link) {
            super(source);
            this.innerName = innerName;
            this.link = link;
        }
        
        public BigraphEntity.InnerName getInnerName() {
            return innerName;
        }
        
        public BigraphEntity.Link getLink() {
            return link;
        }
    }
}
