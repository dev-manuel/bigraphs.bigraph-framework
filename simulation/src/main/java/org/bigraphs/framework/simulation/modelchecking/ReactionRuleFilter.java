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