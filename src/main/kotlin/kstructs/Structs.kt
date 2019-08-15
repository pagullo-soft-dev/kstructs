/*
LGPL v3.0
Copyright (C) 2019 Pedro Agullo Soliveres
p.agullo.soliveres@gmail.com

KStructs is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

KStructs is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.softwarementors.kstructs

import com.softwarementors.kpointers.Size
import com.softwarementors.kstructs.util.isValidIdentifier

@kotlin.ExperimentalUnsignedTypes
class Structs {
   private var structs_ = mutableListOf<Struct>()
   private var sizeBytes_ : Size = 0L
   private var currentOffset : Size = 0L
   
   val structs : List<Struct> get() {
      return structs_.toList()
   }
   
   public fun hasStruct( name : String ) : Boolean {
      val field = structs_.find { it.name == name }
      return field != null
   }

   public fun add( name : String ) : Struct {
      assert(!hasStruct(name))
      assert(isValidIdentifier(name))
      
      val struct = Struct(name, this)
      structs_.add(struct)
      return struct
   }
}