package com.softwarementors.kstructs.debug

import java.util.BitSet

fun toBinaryDebugString( b: BitSet): String {
   val r = StringBuffer(b.length() + 8)
   // r.append(b.length())
   //r.append(':')
   for( i in 0..b.length()-1) {
      var bit = '0'
      if( b.get(i) ) {
         bit = '1'
      }
      r.append(bit)
   }
   return r.toString()
}

