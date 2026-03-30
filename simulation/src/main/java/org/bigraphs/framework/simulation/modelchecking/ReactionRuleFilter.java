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
package org.bigraphs.framework.simulation.modelchecking;

import org.bigraphs.framework.core.Bigraph;
import org.bigraphs.framework.core.Signature;
import org.bigraphs.framework.core.reactivesystem.ReactionRule;

@FunctionalInterface
public interface ReactionRuleFilter<B extends Bigraph<? extends Signature<?>>> {

    static <B extends Bigraph<? extends Signature<?>>> ReactionRuleFilter<B> alwaysAccept() {
        return (r, a) -> true;
    }

    /**
     * @return true if this rule should be considered for the given agent
     */
    boolean accept(ReactionRule<B> rule, B agent);

    default ReactionRuleFilter<B> and(ReactionRuleFilter<B> other) {
        return (r, a) -> this.accept(r, a) && other.accept(r, a);
    }

    default ReactionRuleFilter<B> or(ReactionRuleFilter<B> other) {
        return (r, a) -> this.accept(r, a) || other.accept(r, a);
    }

    default ReactionRuleFilter<B> negate() {
        return (r, a) -> !this.accept(r, a);
    }
}