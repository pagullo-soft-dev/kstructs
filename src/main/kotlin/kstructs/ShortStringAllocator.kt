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

import com.softwarementors.kstructs.ShortString
import com.softwarementors.kpointers.malloc.RawAllocator
import com.softwarementors.kpointers.UBytePointer
import com.softwarementors.kpointers.malloc.UnsafeBackedAllocator


@kotlin.ExperimentalUnsignedTypes
class ShortStringAllocator(private val rawAllocator : RawAllocator) {
   companion object {
      val unsafeAllocator = ShortStringAllocator( UnsafeBackedAllocator())      
   }
   
   fun allocate( maxLen: UByte, zeroMem: Boolean = StructArrayAllocator.zeroMem ): ShortString {
      val memSize = maxLen.toLong() + ShortString.NON_STR_BYTES.toLong()
      val mem = this.rawAllocator.allocate(memSize, zeroMem)
      
      // Initialize to valid empty string
      val r = ShortString(mem)
      r.initializeMem(maxLen)
      return r
   }
   
   fun free( pointerToArray: ShortString) {
      assert(!pointerToArray.isNull())
   
      this.rawAllocator.free( pointerToArray.toPointer() )
   }
}