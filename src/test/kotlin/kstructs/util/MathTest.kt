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

package com.softwarementors.kstructs.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import com.softwarementors.kstructs.*
import com.softwarementors.kpointers.BytePointer
import com.softwarementors.kpointers.Size

@kotlin.ExperimentalUnsignedTypes
class MathTest {

   @Test
   fun test_easyCases() {
      assertEquals( 4L, roundUp( 1L, 4L))
      assertEquals( 4L, roundUp( 2L, 4L))
      assertEquals( 4L, roundUp( 3L, 4L))
      assertEquals( 4L, roundUp( 4L, 4L))
      assertEquals( 8L, roundUp( 5L, 4L))
      assertEquals( 1024L, roundUp( 1024L, 4L))
   }
   
   @Test
   fun test_zero() {
      assertEquals( 0L, roundUp( 0L, 4L))
   }

   @Test
   fun test_negativeNumbers() {
      assertEquals( 0L, roundUp( -1L, 4L))
      assertEquals( 0L, roundUp( -2L, 4L))
      assertEquals( 0L, roundUp( -3L, 4L))
      assertEquals( -4L, roundUp( -4L, 4L))
      assertEquals( -4L, roundUp( -5L, 4L))
      assertEquals( -1020L, roundUp( -1023L, 4L))
      assertEquals( -1024L, roundUp( -1024L, 4L))
      assertEquals( -1024L, roundUp( -1025L, 4L))
   }
}