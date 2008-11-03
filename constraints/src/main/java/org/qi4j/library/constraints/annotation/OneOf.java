package org.qi4j.library.constraints.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.qi4j.composite.ConstraintDeclaration;
import org.qi4j.composite.Constraints;
import org.qi4j.library.constraints.ContainsConstraint;
import org.qi4j.library.constraints.OneOfConstraint;

@ConstraintDeclaration
@Retention( RetentionPolicy.RUNTIME )
@Constraints( OneOfConstraint.class )
public @interface OneOf
{
    public String[] value();
}