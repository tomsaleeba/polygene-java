package org.qi4j.runtime.util;

import java.lang.reflect.Type;
import java.util.Collection;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.sideeffect.SideEffects;

/**
 * @author mh14 @ jexp.de
 * @since 15.06.2008 00:58:32 (c) 2008 jexp.de
 */
public class AnnotationUtilTest
{
    @Mixins( value = AnnotatedClass.class )
    interface AnnotatedClass<T>
    {
        Collection<T> list();
    }

    @Test
    public void getAnnotationOrNull() throws NoSuchMethodException
    {
        assertNotNull( "Mixins annotation found", AnnotationUtil.getAnnotation( AnnotatedClass.class, Mixins.class ) );

        assertNull( "No SideEffects annotation found", AnnotationUtil.getAnnotation( AnnotatedClass.class, SideEffects.class ) );

        final Type returnType = AnnotatedClass.class.getDeclaredMethod( "list" ).getGenericReturnType();
        assertNull( "Null on no class type", AnnotationUtil.getAnnotation( returnType, Mixins.class ) );
    }
}