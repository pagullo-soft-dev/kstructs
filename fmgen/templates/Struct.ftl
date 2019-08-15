/*
LGPL v3.0
Copyright (C) 2019 Pedro Agullo Soliveres
p.agullo.soliveres@gmail.com

${projectNameInLicense} is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

${projectNameInLicense} is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

/*****************************************************************

*DO NOT* modify manually
File generated automatically by fmgen, by Pedro Agullo Soliveres 

******************************************************************/

package ${package}

import com.softwarementors.kstructs.*
import com.softwarementors.kpointers.*

@kotlin.ExperimentalUnsignedTypes
inline class ${type}(internal val address : Pointer) {
   // ******************************************
   // Internal utility methods to get pointers to data
   <#list fields as field>
   internal val ${field.name}Ptr : ${field.type}Pointer get() =  ${field.type}Pointer(this.address + ${field.offset}L)  
   </#list>   
   <#list inlineFields as field>
   internal val ${field.name}Ptr : ${field.type} get() =  ${field.type}(this.address + ${field.offset}L)  
   </#list>   
   
   // ******************************************
   // Public interface
   fun isNull() : Boolean = address == NULL
   operator fun not() : Boolean = address == NULL
   
   <#list fields as field>
   var ${field.name} : ${field.type}
      get() = ${field.name}Ptr.it
      set(v : ${field.type}) { ${field.name}Ptr.it = v }
      
   </#list>
   <#list inlineFields as field>
   val ${field.name} : ${field.type}
      get() = ${field.name}Ptr      
      
   </#list>   
   companion object {
      <#list fields as field>
      internal const val ${field.name?upper_case}_OFFSET : Size = ${field.offset}
      <#sep>
      
      </#list>   
      <#list inlineFields as field>
      <#if field.size??>
      const val ${field.name?upper_case}_SIZE : Size = ${field.size}
      </#if>
      <#sep>
      
      </#list>   
      internal const val SIZE_BYTES : Int = ${sizeBytes}
      <#list inlineFields as field>
      internal const val ${field.name?upper_case}_OFFSET : Size = ${field.offset}
      internal const val ${field.name?upper_case}_BYTE_SIZE : Size = ${field.sizeBytes}
      <#sep>  
      
      </#list>    
   }
   // ****** Public interface
}