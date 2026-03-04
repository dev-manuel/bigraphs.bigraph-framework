/*
 * Copyright (c) 2019-2026 Bigraph Toolkit Suite Developers
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
package org.bigraphs.framework.simulation.matching.pure;

import org.bigraphs.framework.core.impl.pure.PureBigraph;
import org.bigraphs.framework.core.reactivesystem.ReactionRule;
import org.bigraphs.framework.simulation.matching.AbstractBigraphMatcher;
import org.bigraphs.framework.simulation.matching.MatchIterable;

/**
 * PureBigraphMatcher is a concrete implementation of AbstractBigraphMatcher specialized for handling
 * pure bigraphs. It is responsible for executing matching operations between a pure bigraph agent
 * and a reaction rule. The matcher uses a PureBigraphMatchingEngine for processing the matches.
 *
 * @author Dominik Grzelak
 */
public class PureBigraphMatcher extends AbstractBigraphMatcher<PureBigraph> {

    public PureBigraphMatcher() {
        super();
    }

    @Override
    public PureBigraphMatchingEngine instantiateEngine() {
        return new PureBigraphMatchingEngine(this.agent, this.rule);
    }

    @Override
    @Deprecated
    public MatchIterable<PureBigraphMatch> match(PureBigraph agent, ReactionRule<PureBigraph> rule) {
        return (MatchIterable<PureBigraphMatch>) super.matchAll(agent, rule);
    }

    @Override
    public MatchIterable<PureBigraphMatch> matchAll(PureBigraph agent, ReactionRule<PureBigraph> rule) {
        return (MatchIterable<PureBigraphMatch>) super.matchAllInternal(agent, rule, false);
    }

    /**
     * Finds the first match of a specified reaction rule within a given pure bigraph.
     *
     * @param agent the pure bigraph in which to search for the first match.
     * @param rule  the reaction rule containing the redex to be matched against the agent.
     * @return an iterable collection containing the first match of type M found in the given pure bigraph agent.
     */
    @Override
    public MatchIterable<PureBigraphMatch> matchFirst(PureBigraph agent, ReactionRule<PureBigraph> rule) {
        return (MatchIterable<PureBigraphMatch>) matchAllInternal(agent, rule, true);
    }
}
