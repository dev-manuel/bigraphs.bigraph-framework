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

/**
 * Listener interface for receiving notifications about bigraph structure changes.
 * 
 * <p>Implementations of this interface can be registered with {@link PureBigraphMutable}
 * to be notified when the bigraph structure is modified.</p>
 * 
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * bigraph.addChangeListener(new BigraphChangeListener() {
 *     @Override
 *     public void onNodeAdded(BigraphChangeEvent event) {
 *         System.out.println("Node added: " + event.getNode());
 *     }
 *     
 *     @Override
 *     public void onNodeRemoved(BigraphChangeEvent event) {
 *         System.out.println("Node removed: " + event.getNode());
 *     }
 * });
 * }</pre>
 * 
 * @author AI Assistant
 * @see PureBigraphMutable
 * @see BigraphChangeEvent
 */
public interface BigraphChangeListener {
    
    /**
     * Called when a node is added to the bigraph.
     * 
     * @param event the event containing details about the added node
     */
    default void onNodeAdded(BigraphChangeEvent event) {}
    
    /**
     * Called when a node is removed from the bigraph.
     * 
     * @param event the event containing details about the removed node
     */
    default void onNodeRemoved(BigraphChangeEvent event) {}
    
    /**
     * Called when a node is moved to a different parent.
     * 
     * @param event the event containing details about the moved node
     */
    default void onNodeMoved(BigraphChangeEvent event) {}
    
    /**
     * Called when a root is added to the bigraph.
     * 
     * @param event the event containing details about the added root
     */
    default void onRootAdded(BigraphChangeEvent event) {}
    
    /**
     * Called when a root is removed from the bigraph.
     * 
     * @param event the event containing details about the removed root
     */
    default void onRootRemoved(BigraphChangeEvent event) {}
    
    /**
     * Called when an edge is added to the bigraph.
     * 
     * @param event the event containing details about the added edge
     */
    default void onEdgeAdded(BigraphChangeEvent event) {}
    
    /**
     * Called when an edge is removed from the bigraph.
     * 
     * @param event the event containing details about the removed edge
     */
    default void onEdgeRemoved(BigraphChangeEvent event) {}
    
    /**
     * Called when an outer name is added to the bigraph.
     * 
     * @param event the event containing details about the added outer name
     */
    default void onOuterNameAdded(BigraphChangeEvent event) {}
    
    /**
     * Called when an outer name is removed from the bigraph.
     * 
     * @param event the event containing details about the removed outer name
     */
    default void onOuterNameRemoved(BigraphChangeEvent event) {}
    
    /**
     * Called when a port is connected to a link.
     * 
     * @param event the event containing details about the connection
     */
    default void onLinkConnected(BigraphChangeEvent event) {}
    
    /**
     * Called when a port is disconnected from a link.
     * 
     * @param event the event containing details about the disconnection
     */
    default void onLinkDisconnected(BigraphChangeEvent event) {}
    
    /**
     * Called when an inner name is added to the bigraph.
     * 
     * @param event the event containing details about the added inner name
     */
    default void onInnerNameAdded(BigraphChangeEvent event) {}
    
    /**
     * Called when an inner name is removed from the bigraph.
     * 
     * @param event the event containing details about the removed inner name
     */
    default void onInnerNameRemoved(BigraphChangeEvent event) {}
    
    /**
     * Called when a site is added to the bigraph.
     *
     * @param event the event containing details about the added site
     */
    default void onSiteAdded(BigraphChangeEvent event) {}

    /**
     * Called when a site is removed from the bigraph.
     *
     * @param event the event containing details about the removed site
     */
    default void onSiteRemoved(BigraphChangeEvent event) {}

    /**
     * Called when an inner name is connected to a link.
     * 
     * @param event the event containing details about the connection
     */
    default void onInnerNameLinked(BigraphChangeEvent event) {}
    
    /**
     * Called when an inner name is disconnected from a link.
     * 
     * @param event the event containing details about the disconnection
     */
    default void onInnerNameUnlinked(BigraphChangeEvent event) {}
}
