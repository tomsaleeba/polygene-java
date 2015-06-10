/*
 * Copyright 2011 Rickard Öberg
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.qi4j.sample.scala

import java.lang.reflect.Method
import org.qi4j.api.concern.GenericConcern
import org.qi4j.api.common.{AppliesToFilter, AppliesTo}

/**
 * Add an exclamation mark to the returned string
 */

@AppliesTo(Array(classOf[ StringFilter ]))
class ExclamationGenericConcern
  extends GenericConcern
{
  def invoke(composite: AnyRef, method: Method, args: Array[ AnyRef ] ) = next.invoke(composite, method, args) + "!"
}

class StringFilter
  extends AppliesToFilter
{
  def appliesTo(method: Method, mixin: Class[ _ ], compositeType: Class[ _ ], fragmentClass: Class[ _ ] ) = method
    .getReturnType
    .equals(classOf[ String ])
}
