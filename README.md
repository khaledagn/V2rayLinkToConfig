# V2Ray Link to Config Converter

## Introduction
This Java class, `V2rayLinkToConfig`, provides a utility for converting VMess links to V2Ray configuration settings. It decodes the Base64 URL-safe encoded VMess link and extracts various parameters such as server address, port, user ID, encryption settings, and more. It then generates a V2Ray configuration JSON string based on the extracted parameters, allowing users to easily configure their V2Ray client.

## Usage
To use this converter, simply call the `convertVMessLinkToV2RayConfig` method and pass the VMess link as a parameter. It will return a V2Ray configuration JSON string.

Example usage:
```java
String vmessLink = "vmess://..."; // Your VMess link here
String v2rayConfig = V2rayLinkToConfig.convertVMessLinkToV2RayConfig(vmessLink);
System.out.println(v2rayConfig);
