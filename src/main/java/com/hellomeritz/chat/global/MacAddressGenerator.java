package com.hellomeritz.chat.global;

import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

@Configuration
public class MacAddressGenerator {

    public String get() {
        String macAddress = "";
        InetAddress ip;

        try {
            ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            macAddress = sb.toString();
        } catch (UnknownHostException e) {
            throw new IllegalStateException("host를 알 수 없습니다.");
        } catch (SocketException e) {
            throw new IllegalStateException("ip를 통해 mac 주소를 얻어낼 수 없습니다.");
        }
        return macAddress;
    }
}
