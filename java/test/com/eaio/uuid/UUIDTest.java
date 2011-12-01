package com.eaio.uuid;

import com.eaio.uuid.UUID;

public class UUIDTest {
 public static void main(String[] args) {
  UUID u = new UUID();
     System.out.println(u);
     System.out.println( "mac address=" + UUIDGen.getMACAddress() );
 }
}