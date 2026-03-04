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
package org.bigraphs.framework.simulation.matching;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.bigraphs.framework.core.Bigraph;
import org.bigraphs.framework.core.Signature;
import org.bigraphs.framework.core.reactivesystem.BigraphMatch;
import org.bigraphs.framework.simulation.matching.pure.PureBigraphMatchingEngine;
import org.bigraphs.framework.simulation.matching.pure.PureMatchIteratorImpl;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

/**
 *
 * @param <B> type of the bigraph
 * @author Dominik Grzelak
 */
public abstract class AbstractBigraphMatchIterator<B extends Bigraph<? extends Signature<?>>> implements Iterator<BigraphMatch<B>> {

    protected int cursor = 0;
    protected MutableList<BigraphMatch<B>> matches = Lists.mutable.empty();
    protected BigraphMatchingEngine<B> matchingEngine;


    @SuppressWarnings("unchecked")
    public static <B extends Bigraph<? extends Signature<?>>> AbstractBigraphMatchIterator<B> create(BigraphMatchingEngine<B> engine) {
        if (engine instanceof PureBigraphMatchingEngine pure) {
            @SuppressWarnings("unchecked")
            AbstractBigraphMatchIterator<B> it =
                    (AbstractBigraphMatchIterator<B>) new PureMatchIteratorImpl(pure);
            return it;
        }
        throw new UnsupportedOperationException("Unsupported engine: " + engine.getClass());
    }

    public AbstractBigraphMatchIterator(BigraphMatchingEngine<B> matchingEngine) {
        this.matchingEngine = matchingEngine;
    }

    /**
     * load matches (or compute lazily)
     */
    protected abstract void findMatches();

    @Override
    public boolean hasNext() {
        if (matches.isEmpty()) return false;
        return cursor != matches.size();
    }

    @Override
    public BigraphMatch<B> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return matches.get(cursor++);
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constrained Matcher
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * A Simple Constrained Matcher Implementation
     *
     */
    @SuppressWarnings("unchecked")
    public static abstract class FirstMatchOnly<B extends Bigraph<? extends Signature<?>>> extends AbstractBigraphMatchIterator<B> {

        public static <B extends Bigraph<? extends Signature<?>>> AbstractBigraphMatchIterator.FirstMatchOnly<B> create(BigraphMatchingEngine<B> engine) {
            if (engine instanceof PureBigraphMatchingEngine pure) {
                @SuppressWarnings("unchecked")
                AbstractBigraphMatchIterator.FirstMatchOnly<B> it =
                        (AbstractBigraphMatchIterator.FirstMatchOnly<B>) new PureMatchIteratorImpl.FirstMatchOnly(pure);
                return it;
            }
            throw new UnsupportedOperationException("Unsupported engine: " + engine.getClass());
        }

        public FirstMatchOnly(BigraphMatchingEngine<B> matchingEngine) {
            super(matchingEngine);
        }
    }
}