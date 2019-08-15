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
   companion object {
      const val SIZE_BYTES : Int = ${pointedType}.SIZE_BYTES
   }

   // ******************************************
   // Public interface
   operator fun get(i : PointerOffset) : ${type} = ${type}(this.address + (i * SIZE_BYTES))
   fun toPointer() : Pointer = address

   fun isNull() : Boolean = address == NULL   
   operator fun not() = address == NULL
   operator fun inc() = ${type}(address + SIZE_BYTES.toLong())
   operator fun dec() = ${type}(address - SIZE_BYTES.toLong())
   operator fun plus(i : PointerOffset)  = ${type}(address + (i * SIZE_BYTES))
   operator fun minus(i : PointerOffset) = ${type}(address - (i * SIZE_BYTES))
   operator fun minus(v : ${type}) : PointerOffset = ((address.toUnsafePointer() - v.address.toUnsafePointer()) / SIZE_BYTES)
   operator fun compareTo( p : ${type} ) : Int = address.compareTo( p.address); 
   // ****** Public interface
}

@kotlin.ExperimentalUnsignedTypes
fun StructArrayAllocator.allocate${type}ForArray( itemCount : Size, zeroMem : Boolean = StructArrayAllocator.zeroMem ) : ${type} {
   val mem = this.rawAllocator.allocate( itemCount, ${type}.SIZE_BYTES.toLong(), zeroMem )
   return ${type}(mem)
}

@kotlin.ExperimentalUnsignedTypes
fun StructArrayAllocator.free( pointerToArray : ${type}) {
   this.rawAllocator.free( pointerToArray.toPointer() )
}