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

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import com.softwarementors.kstructs.*
import com.softwarementors.kpointers.BytePointer
import com.softwarementors.kpointers.Size
import com.softwarementors.kpointers.NULL
import com.softwarementors.kpointers.Pointer
import com.softwarementors.kstructs.ShortStringAllocator

@kotlin.ExperimentalUnsignedTypes
class ShortStringAllocatorTest {

   val outstandingAllocations = mutableListOf<ShortString>()
   val allocator = ShortStringAllocator.unsafeAllocator

   @AfterEach
   fun afterEach() {
      outstandingAllocations.forEach { allocator.free(it)}
   }
   
   @Test
   fun test_allocate_initializesFieldsCorrectly() {
      var s = allocator.allocate(0u)     
      assertEquals( 0.toUByte(), s.maxLen )
      assertEquals( 0.toUByte(), s.length )

      s = allocator.allocate(133u)
      assertEquals( 133.toUByte(), s.maxLen )
      assertEquals( 0.toUByte(), s.length )
  }
   
}