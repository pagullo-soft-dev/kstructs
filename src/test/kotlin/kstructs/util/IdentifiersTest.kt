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
import com.softwarementors.kstructs.StructsArrayAllocator
import com.softwarementors.kstructs.*
import com.softwarementors.kpointers.BytePointer
import com.softwarementors.kpointers.Size

@kotlin.ExperimentalUnsignedTypes
class IdentifiersTest {

   @Test
   fun test_cantStartWithNumbers() {
      assertFalse( isValidIdentifier("3KB"))
      assertFalse( isValidIdentifier("99"))
      assertFalse( isValidIdentifier("0"))
   }
   
   @Test
   fun test_forbiddenSymbols() {
      val forbiddentSymbols = listOf(
         "$", "#", "?", "<", ">", "~", "!", " ", "\n", "\r", "\t", ".", "-", "\"",
         "'", "´", "`", "{", "[")
      forbiddentSymbols.forEach {
         assertFalse( isValidIdentifier(it))
         assertFalse( isValidIdentifier("a$it"))
         assertFalse( isValidIdentifier("${it}a"))
      }
   }
   
   @Test
   fun test_underscore() {
      assertTrue( isValidIdentifier("_"))
      assertTrue( isValidIdentifier("_a"))
      assertTrue( isValidIdentifier("a_"))
   }

   @Test
   fun test_cantUseNonAsciiChars() {
      val someForbiddenChars = listOf( "ñ", "ç", "á" )
      someForbiddenChars.forEach {
         assertFalse( isValidIdentifier(it))
         assertFalse( isValidIdentifier("a$it"))
         assertFalse( isValidIdentifier("${it}a"))
      }
   }   
}