package tests.compound;

import java.util.LinkedHashSet;

import org.checkerframework.common.aliasing.AliasingChecker;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.qual.TypeQualifiers;

import tests.compound.qual.CCBottom;
import tests.compound.qual.CCTop;
/**
 *  Used to test the compound checker design pattern.
 *  Alasing Checker and Annother Compound Checker are subcheckers of this checker
 *  Annother Compound Checker relies on the Alaising Checker, too.
 *  This is so that the order of subcheckers is tested.
 **/

@TypeQualifiers({ CCTop.class, CCBottom.class })
public class CompoundChecker extends BaseTypeChecker {
    protected LinkedHashSet<Class<? extends BaseTypeChecker>> getImmediateSubcheckerClasses() {
        LinkedHashSet<Class<? extends BaseTypeChecker>> subcheckers = new LinkedHashSet<>();
        subcheckers.addAll(super.getImmediateSubcheckerClasses());
        subcheckers.add(AliasingChecker.class);
        subcheckers.add(AnotherCompoundChecker.class);
        return subcheckers;
    }

    @Override
    protected BaseTypeVisitor<?> createSourceVisitor() {
        return new BaseTypeVisitor<CompoundCheckerAnnotatedTypeFactory>(this) {
            @Override
            protected CompoundCheckerAnnotatedTypeFactory createTypeFactory() {
                return new CompoundCheckerAnnotatedTypeFactory(checker);
            }
        };
    }
}
