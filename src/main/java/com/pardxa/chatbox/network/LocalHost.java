package com.pardxa.chatbox.network;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class LocalHost {
  public static List<InetAddress> getNicBroadcastAddr() throws SocketException {
    List<InetAddress> addrs = new ArrayList<InetAddress>();
    getInterfaceAddrs().stream().map(ifs -> ifs.getBroadcast()).filter(Objects::nonNull)
        .forEach(addr -> {
          addrs.add(addr);
        });
    return addrs;
  }

  public static Set<InetAddress> getNicAddrs() {
    Set<InetAddress> addrSet = new HashSet<InetAddress>();
    getInterfaceAddrs().stream().map(ifs -> ifs.getAddress()).filter(Objects::nonNull)
        .forEach(addr -> {
          addrSet.add(addr);
        });
    return addrSet;
  }

  public static List<InterfaceAddress> getInterfaceAddrs() {
    List<InterfaceAddress> interfaces = new ArrayList<InterfaceAddress>();
    try {
      Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
      while (nics.hasMoreElements()) {
        NetworkInterface nic = nics.nextElement();
        if (nic.isLoopback() || !nic.isUp()) {
          continue;
        }
        interfaces.addAll(nic.getInterfaceAddresses());
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }
    return interfaces;
  }

  public static InetAddress getLocalHost() {
    InetAddress localhost = null;
    try {
      localhost = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return localhost;
  }
}
