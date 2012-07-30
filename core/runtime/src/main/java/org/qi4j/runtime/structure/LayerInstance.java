/*
 * Copyright (c) 2008, Rickard Öberg. All Rights Reserved.
 * Copyright (c) 2012, Paul Merlin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.qi4j.runtime.structure;

import java.util.ArrayList;
import java.util.List;
import org.qi4j.api.common.Visibility;
import org.qi4j.api.event.ActivationEvent;
import org.qi4j.api.event.ActivationEventListener;
import org.qi4j.api.service.ServiceReference;
import org.qi4j.api.structure.Layer;
import org.qi4j.api.structure.Module;
import org.qi4j.functional.Function;
import org.qi4j.functional.Iterables;
import org.qi4j.runtime.activation.ActivationEventListenerSupport;
import org.qi4j.runtime.activation.ActivationDelegate;
import org.qi4j.runtime.composite.TransientModel;
import org.qi4j.runtime.entity.EntityModel;
import org.qi4j.runtime.object.ObjectModel;
import org.qi4j.runtime.value.ValueModel;

/**
 * Instance of a Qi4j application layer. Contains a list of modules which are managed by this layer.
 */
public class LayerInstance
    implements Layer
{
    private final LayerModel layerModel;
    private final ApplicationInstance applicationInstance;
    private final List<ModuleInstance> moduleInstances = new ArrayList<ModuleInstance>();
    private final ActivationDelegate activation = new ActivationDelegate( this );
    private final ActivationEventListenerSupport activationEventSupport = new ActivationEventListenerSupport();
    private final UsedLayersInstance usedLayersInstance;

    public LayerInstance( LayerModel model,
                          ApplicationInstance applicationInstance,
                          UsedLayersInstance usedLayersInstance
    )
    {
        this.layerModel = model;
        this.applicationInstance = applicationInstance;
        this.usedLayersInstance = usedLayersInstance;
    }

    void addModule( ModuleInstance module )
    {
        module.registerActivationEventListener( activationEventSupport );
        moduleInstances.add( module );
    }

    public LayerModel model()
    {
        return layerModel;
    }

    public ApplicationInstance applicationInstance()
    {
        return applicationInstance;
    }

    public String name()
    {
        return layerModel.name();
    }

    public <T> T metaInfo( Class<T> infoType )
    {
        return layerModel.metaInfo( infoType );
    }

    public List<Module> modules()
    {
        List<Module> result = new ArrayList<Module>();
        for( ModuleInstance moduleInstance : moduleInstances )
        {
            result.add( moduleInstance );
        }
        return result;
    }

    public UsedLayersInstance usedLayersInstance()
    {
        return usedLayersInstance;
    }

    Iterable<ModelModule<ObjectModel>> visibleObjects( final Visibility visibility )
    {
        return Iterables.flattenIterables( Iterables.map( new Function<ModuleInstance, Iterable<ModelModule<ObjectModel>>>()
        {
            @Override
            public Iterable<ModelModule<ObjectModel>> map( ModuleInstance moduleInstance )
            {
                return moduleInstance.visibleObjects( visibility );
            }
        }, moduleInstances ) );
    }

    Iterable<ModelModule<TransientModel>> visibleTransients( final Visibility visibility )
    {
        return Iterables.flattenIterables( Iterables.map( new Function<ModuleInstance, Iterable<ModelModule<TransientModel>>>()
        {
            @Override
            public Iterable<ModelModule<TransientModel>> map( ModuleInstance moduleInstance )
            {
                return moduleInstance.visibleTransients( visibility );
            }
        }, moduleInstances ) );
    }

    Iterable<ModelModule<EntityModel>> visibleEntities( final Visibility visibility )
    {
        return Iterables.flattenIterables( Iterables.map( new Function<ModuleInstance, Iterable<ModelModule<EntityModel>>>()
        {
            @Override
            public Iterable<ModelModule<EntityModel>> map( ModuleInstance moduleInstance )
            {
                return moduleInstance.visibleEntities( visibility );
            }
        }, moduleInstances ) );
    }

    Iterable<ModelModule<ValueModel>> visibleValues( final Visibility visibility )
    {
        return Iterables.flattenIterables( Iterables.map( new Function<ModuleInstance, Iterable<ModelModule<ValueModel>>>()
        {
            @Override
            public Iterable<ModelModule<ValueModel>> map( ModuleInstance moduleInstance )
            {
                return moduleInstance.visibleValues( visibility );
            }
        }, moduleInstances ) );
    }

    Iterable<ServiceReference> visibleServices( final Visibility visibility )
    {
        return Iterables.flattenIterables( Iterables.map( new Function<ModuleInstance, Iterable<ServiceReference>>()
        {
            @Override
            public Iterable<ServiceReference> map( ModuleInstance moduleInstance )
            {
                return moduleInstance.visibleServices( visibility );
            }
        }, moduleInstances ) );
    }

    public ModuleInstance findModule( String moduleName )
    {
        for( ModuleInstance moduleInstance : moduleInstances )
        {
            if( moduleInstance.model().name().equals( moduleName ) )
            {
                return moduleInstance;
            }
        }

        throw new IllegalArgumentException( "No such module:" + moduleName );
    }

    public void activate()
        throws Exception
    {
        activationEventSupport.fireEvent( new ActivationEvent( this, ActivationEvent.EventType.ACTIVATING ) );
        activation.activate( layerModel.newActivatorsInstance(), moduleInstances );
        activationEventSupport.fireEvent( new ActivationEvent( this, ActivationEvent.EventType.ACTIVATED ) );
    }

    public void passivate()
        throws Exception
    {
        activationEventSupport.fireEvent( new ActivationEvent( this, ActivationEvent.EventType.PASSIVATING ) );
        activation.passivate();
        activationEventSupport.fireEvent( new ActivationEvent( this, ActivationEvent.EventType.PASSIVATED ) );
    }

    @Override
    public String toString()
    {
        return layerModel.toString();
    }

    public void registerActivationEventListener( ActivationEventListener listener )
    {
        activationEventSupport.registerActivationEventListener( listener );
    }

    public void deregisterActivationEventListener( ActivationEventListener listener )
    {
        activationEventSupport.deregisterActivationEventListener( listener );
    }
}
