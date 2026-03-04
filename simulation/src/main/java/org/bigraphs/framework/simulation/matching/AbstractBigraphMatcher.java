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
package org.bigraphs.framework.simulation.matching;

import com.google.common.base.Stopwatch;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import org.bigraphs.framework.core.Bigraph;
import org.bigraphs.framework.core.Signature;
import org.bigraphs.framework.core.impl.pure.PureBigraph;
import org.bigraphs.framework.core.reactivesystem.BigraphMatch;
import org.bigraphs.framework.core.reactivesystem.ReactionRule;
import org.bigraphs.framework.simulation.matching.pure.PureBigraphMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Abstract class for matching bigraphs against reaction rules. This class provides
 * the basic structure for implementing specific bigraph matchers by extending its
 * functionality. Subclasses are required to provide implementations for custom matching
 * logic and driven by a dedicated matching engine {@link #instantiateEngine()} w.r.t. to the bigraph type.
 * <p>
 * The correct one, is created using the factory method {@link AbstractBigraphMatcher#create(Class)} by supplying the bigraph type as class.
 * <p>
 * The matcher needs an agent and redex to perform bigraph matching.
 *
 * @param <B> the type of bigraph which extends from Bigraph with a specific signature
 * @author Dominik Grzelak
 */
public abstract class AbstractBigraphMatcher<B extends Bigraph<? extends Signature<?>>> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractBigraphMatcher.class);

    protected B agent;
    protected B redex;
    protected ReactionRule<B> rule;

    protected AbstractBigraphMatcher() {

    }

    @SuppressWarnings("unchecked")
    public static <B extends Bigraph<? extends Signature<?>>> AbstractBigraphMatcher<B> create(Class<B> bigraphClass) {
        if (bigraphClass == PureBigraph.class) {
            try {
                return (AbstractBigraphMatcher<B>) Class.forName(PureBigraphMatcher.class.getCanonicalName()).getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Not Implemented Yet");
    }

    /**
     * Use matchAll instead of match(); or use matchFirst() (constrained variant)
     */
    @Deprecated
    public abstract MatchIterable<? extends BigraphMatch<B>> match(B agent, ReactionRule<B> rule);

    /**
     * Finds all matches of a specified reaction rule within a given pure bigraph.
     *
     * @param agent the pure bigraph in which to search for matches.
     * @param rule  the reaction rule containing the redex to be matched against the agent.
     * @return an iterable collection of matches of type M found in the given pure bigraph agent.
     */
    public MatchIterable<? extends BigraphMatch<B>> matchAll(B agent, ReactionRule<B> rule) {
        return matchAllInternal(agent, rule, false);
    }

    /**
     * Finds the first match of a specified reaction rule within a given pure bigraph.
     *
     * @param agent the pure bigraph in which to search for the first match.
     * @param rule  the reaction rule containing the redex to be matched against the agent.
     * @return an iterable collection containing the first match of type M found in the given pure bigraph agent.
     */
    public MatchIterable<? extends BigraphMatch<B>> matchFirst(B agent, ReactionRule<B> rule) {
        return matchAllInternal(agent, rule, true);
    }

    /**
     * Provide the matching engine for the specific bigraph type implemented by the subclass
     *
     * @return concrete bigraph matching engine
     */
    protected abstract BigraphMatchingEngine<B> instantiateEngine();

    /**
     * Returns the supplied agent passed via the {@link AbstractBigraphMatcher#match(Bigraph, ReactionRule)} method.
     *
     * @return the agent for the match
     */
    public B getAgent() {
        return agent;
    }

    /**
     * Returns the supplied redex passed via the {@link AbstractBigraphMatcher#match(Bigraph, ReactionRule)} method.
     *
     * @return the redex for the match
     */
    public B getRedex() {
        return redex;
    }

    protected MatchIterable<? extends BigraphMatch<B>> matchAllInternal(
            B agent,
            ReactionRule<B> rule,
            boolean firstOnly
    ) {
        this.agent = agent;
        this.rule = rule;
        this.redex = rule.getRedex();

        BigraphMatchingEngine<B> matchingEngine = instantiateEngine();
        Stopwatch timer0 = LOGGER.isDebugEnabled() ? Stopwatch.createStarted() : null;

        MatchIterable<? extends BigraphMatch<B>> bigraphMatches =
                new MatchIterable<>(firstOnly
                        ? AbstractBigraphMatchIterator.FirstMatchOnly.create(matchingEngine)
                        : AbstractBigraphMatchIterator.create(matchingEngine));

        if (LOGGER.isDebugEnabled() && timer0 != null) {
            long elapsed0 = timer0.stop().elapsed(TimeUnit.NANOSECONDS);
            log(elapsed0);
        }
        return bigraphMatches;
    }

    private void log(long elapsed0) {
        LOGGER.debug("Complete Matching Time: {} (ms)", (elapsed0 / 1e+6f));
    }
}
