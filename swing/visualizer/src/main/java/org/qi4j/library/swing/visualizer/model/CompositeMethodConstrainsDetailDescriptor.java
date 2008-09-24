/*  Copyright 2008 Edward Yakop.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
* implied.
*
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.qi4j.library.swing.visualizer.model;

import java.util.LinkedList;
import java.util.List;
import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.spi.composite.ConstraintDescriptor;
import org.qi4j.spi.composite.MethodConstraintsDescriptor;

/**
 * @author edward.yakop@gmail.com
 * @see MethodConstraintsDescriptor
 * @since 0.5
 */
public final class CompositeMethodConstrainsDetailDescriptor
{
    private final MethodConstraintsDescriptor descriptor;
    private final List<ConstraintDescriptor> constraints;

    CompositeMethodConstrainsDetailDescriptor( MethodConstraintsDescriptor aDescriptor )
        throws IllegalArgumentException
    {
        validateNotNull( "aDescriptor", aDescriptor );

        descriptor = aDescriptor;
        constraints = new LinkedList<ConstraintDescriptor>();
    }

    /**
     * @return Descriptor of this {@code CompositeMethodConstrainsDetailDescriptor}. Never return {@code null}.
     * @since 0.5
     */
    public final MethodConstraintsDescriptor descriptor()
    {
        return descriptor;
    }

    /**
     * @return Constraints of this {@code CompositeMethodConstrainsDetailDescriptor}. Never return {@code null}.
     * @since 0.5
     */
    public final Iterable<ConstraintDescriptor> constraints()
    {
        return constraints;
    }

    final void addConstraint( ConstraintDescriptor aDescriptor )
    {
        validateNotNull( "aDescriptor", aDescriptor );
        constraints.add( aDescriptor );
    }
}
